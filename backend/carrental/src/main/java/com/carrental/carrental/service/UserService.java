package com.carrental.carrental.service;

import com.carrental.carrental.model.*;
import com.carrental.carrental.model.enums.UserRole;
import com.carrental.carrental.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidatorService emailValidatorService;
    private final EmailService emailService;

    private final String CONFIRM_TOKEN_URL = "http://localhost:8080/api/v1/user/confirm?token=";

    public ResponseEntity<String> register(RegistrationRequest request) {
        boolean isValidEmail = emailValidatorService.test(request.getEmail()); //validate request email
        if(!isValidEmail) {
            return new ResponseEntity<>("email not valid", HttpStatus.UNAUTHORIZED);
        }
        ResponseEntity<String> responseEntity  = createUser(new User(request.getFirstName(),
                request.getLastName(), request.getEmail(), request.getPassword(), UserRole.USER));
        if(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
            String token = responseEntity.getBody();
            String link = CONFIRM_TOKEN_URL + token;
            emailService.send(request.getEmail(), emailService.buildEmail(request.getFirstName(), link));
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        return new ResponseEntity<>("user already exists",HttpStatus. UNAUTHORIZED);
    }

    @Transactional
    public ResponseEntity<String> confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token).orElse(null);
        if(confirmationToken == null){
            return new ResponseEntity<>("token not found", HttpStatus.UNAUTHORIZED);
        }

        if (confirmationToken.getConfirmedAt() != null) {
            return new ResponseEntity<>("email already confirmed", HttpStatus.UNAUTHORIZED);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("token expired", HttpStatus.UNAUTHORIZED);
        }

        confirmationTokenService.setConfirmedAt(token);
        enableAppUser(confirmationToken.getUser().getEmail());

        return new ResponseEntity<>("confirmed", HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(String.format("user with email %s not found",email)));
    }

    private ResponseEntity<String> createUser(User user) {
        boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            return new ResponseEntity<>("Email Already taken", HttpStatus.UNAUTHORIZED);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);//now we have user in database
        // TODO: SEND CONFIRMATION TOKEN
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15), user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        // TODO:SEND EMAIL
        return new ResponseEntity<>(token, HttpStatus.CREATED); //change this
    }

    public void enableAppUser(String email) {
        userRepo.enableUser(email);
    }

    public ResponseEntity<?> login(LoginRequest request){
        Optional<User> userOptional = userRepo.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.isEnabled()){
                if(bCryptPasswordEncoder.matches(request.getPassword(),user.getPassword())){
                    return new ResponseEntity<>(user,HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("Wrong password",HttpStatus.UNAUTHORIZED);
                }
            }
            else {
                confirmationTokenService.deleteToken(user.getId());
                String token = UUID.randomUUID().toString();
                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15), user
                );
                confirmationTokenService.saveConfirmationToken(confirmationToken);
                String link = CONFIRM_TOKEN_URL + token;
                emailService.send(user.getEmail(), emailService.buildEmail(user.getFirstName(), link));
                return new ResponseEntity<>("Email not confirmed, a confirmation mail has been resent",HttpStatus.UNAUTHORIZED);
            }

        }
        else{
            // email doesn't exist
            return new ResponseEntity<>("Email doesn't exist, register now!",HttpStatus.UNAUTHORIZED);
        }
    }

    public void deleteUser (String email){
        userRepo.deleteUserByEmail(email);
    }

    public ResponseEntity<?> findUsersByName(String name) {
        Optional<List<User>> users = userRepo.findUsersByName(name);
        if(users.isPresent())
        {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("No users with name " + name + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findUsersById(Integer id) {
        Optional<List<User>> users = userRepo.findUserById(id);
        if(users.isPresent())
        {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("No users with id " + id + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findUsersByEmail(String email) {
        Optional<List<User>> users = userRepo.findUserByEmail(email);
        if(users.isPresent())
        {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("No users with email " + email + " was found", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findAllUsers() {
        Optional<List<User>> users = userRepo.findAllUsers();
        if(users.isPresent())
        {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("No users found", HttpStatus.NO_CONTENT);
    }
}

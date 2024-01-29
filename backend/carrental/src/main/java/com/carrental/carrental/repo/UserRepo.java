package com.carrental.carrental.repo;


import com.carrental.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true) //MIGHT REMOVE THIS
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);

    void deleteUserByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE user_role = 'USER' AND (first_name LIKE ?1% OR last_name LIKE ?1%)", nativeQuery = true)
    Optional<List<User>> findUsersByName(String name);

    @Query(value = "SELECT * FROM user WHERE user_role = 'USER' AND id = ?1", nativeQuery = true)
    Optional<List<User>> findUserById(Integer id);

    @Query(value = "SELECT * FROM user WHERE user_role = 'USER' AND email LIKE ?1%", nativeQuery = true)
    Optional<List<User>> findUserByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE user_role = 'USER'", nativeQuery = true)
    Optional<List<User>> findAllUsers();
}

# Car Rental System
Bon Voyage is a car rental system providing many features for users, employees and admin implemented in Spring Boot for backend and Angular for frontend.
## 1. Introduction
This system implements a car rental system for a purposed car rental company which has many offices all around the world and allows the client to rent a car that is managed and owned to a certain office.
The company has some constraints in constructing its hierarchy and allows its system users to get some functionalities according to their role and aims to aid them with modifying the system components (car, office, client, and reservation) and having the ability to get some useful statistical information to monitor the system status and history.
## 2. System Users
Our purposed system has three users of the following type (roles):

**1.	Admin**

  The head user of the system who has all the facilities concerned with concrete structure of the system.

**2.	Office**

  The second level of users in the system hierarchy which has some core managemental facilities in the system. It is dealled with as a user with name equivalent to its location.

**3.	Client**

  The targeted customer of the system who has the sufficient facilities that serves his needs and fulfil his requirements.

Each of these users has an interface providing him with all possible allowed procedures to be taken.
## 3. Implementation
The system has two portions for implantation:

**3.1.	Backend**

It holds all the logical operations that is done for the system as inserting added records to the system’s database, modifying some records without breaking any constraints as uniqueness of some fields, and deleting records that aren’t needed any more. We used Spring Boot framework in implementing the backend.

**3.2.	Frontend**

It holds the way of allowing the user to perform some functions and call and send requests to the backend to be answered and show the responses in an oriented way. It also performs some minor logical filtrations for the sent requests to the backend to be fulfilled by the system’s database in order to be checked before being sent to minimize the number of missed calls and requests to the backend. We implemented our frontend portion of project using Angular framework.
Angular framework mainly uses HTML (hyperlink text markup language), CSS (cascaded style sheets), and TS (typescript) in implementing the code. The HTML is responsible for identifying the needed components to appear and get used on the user interface page. The CSS is used to add some fine styling portions to the user interface to make it more clear, usable, user friendly, and catchy. The TS holds the previously mentioned minor logic and the responsible for implementing the backend calling.
 
## 4. System Design
Our system is composed of 4 main objects which are:
1.	User
2.	Office
3.	Car
4.	Reservation
5.	Status Log (week entity)

The User object contains a list of all the system users assigned to them their role in the system to be used in identifying their authorities to perform some operations. The Office object refers to the company branches all around the world. It has an email to be used as a user in dealing with some functionalities. The Car object represents our product containing detailed description about it and assigned as well to the owning office. The Reservation object is used to record the transactions of renting and returning cars. The Status Log object is used to keep track of any changes of status that occurred to a car and save a timestamp for that change.
Some constraints about available features for each system user are declared as follows:

•	Client can search among available cars for reservation using any car property as: brand name, body style, fuel and transmission types, rate per day of reservation, car’s plate id, color, and year of manufacturing. He also can view the details of any available car. He obviously also can rent a car.

•	Office can search among all cars belonging to it using any of previously mention properties in addition to availability. He also can edit these cars properties. Moreover, he has the authority to add new cars to the system.

•	Admin can see all cars in the system or modify any of them. He can also add new cars or even new offices. He also has the authority to view the personal data of all system users as: id, email, and first and last names.

The following ERD (entity relational diagram) shows the system entities design and the relationships between them:
![plot](https://github.com/omar24ibrahim/Car_Rental_System/blob/main/ERD.png)

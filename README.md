## Team Members (Alphabetical Order)
Rambod Azimi, Daniel Ha, Shahin Jowkar, Matthew Spagnuolo

## Introduction
The Arena Ticketing System (ATS) is a Software-As-A-Service product designed to assist concert and sports venue owners in managing their events' ticketing, seating arrangements, sponsorship information, and user base within a single system, while also deriving insightful data from these events. From a general user's perspective, ATS enables users to discover upcoming concerts in their city, select their preferred seats, and conveniently purchase tickets. This web-based application offers a user-friendly interface, allowing easy exploration of a wide variety of events. ATS caters to a diverse range of needs, from operational management for venue owners to a seamless ticketing experience for general users.

## ER Model
![ER Model](https://i.ibb.co/5swJTrp/ER.png)

## Functionalities

### 1. Add a new event to the system
Admin can add a new event to the system. A new record (tuple) of Event will be created.

INSERT INTO Events (EID, AgeRestriction, StartDate, StartTime, DURATION, TypeID) VALUES (1, 0, '2023-07-28', '10:00', 90, 2)


### 2. Register a new user to the system
User can create a new account and register on the system. A new record (tuple) of RegisteredUsers will be created.

INSERT INTO RegisteredUsers (UserID, Username, Password, CreditCard, ExpiryDate, Location, PreferredType, PreferredGenre) VALUES (1, 'username1', 'password1', '1111-2222-3333-4444', '2025-01-01', 'Address1', 'Performance', 'Concert')


### 3. Purchase a ticket from the system
User can select an event and purchase a ticket within the system. A new record (tuple) of Tickets will be created.

INSERT INTO Tickets (TicketID, Price, DesignatedEntrance, UserID, PurchaseDate, EID) VALUES (1, 10, 3, 7, '2023-05-20', 50)


### 4. Add a new sponsor to an event
Admin add add a new sponsor to an event within the system. A new record (tuple) of Sponsors and Sponsorship will be created.

INSERT INTO Sponsors (SID, SponsorName, SponsorType) VALUES (1, 'Nike', 'Sportswear')

INSERT INTO Sponsorship (EID, SID) VALUES (32, 67)


### 5. Update/Delete an event from the system
Admin can update an event information or remove an event from the system (with EID).

UPDATE Events SET AgeRestriction = 0, StartDate = '2023-07-28', StartTime = '11:00', DURATION = 90, TypeID = 2 WHERE EID = 1;


## Description

#### Write a user-friendly application program for your database in Java connecting to the DBMS via JDBC. You can base this on the example JDBC program that is given in mycourses as part of the JDBC tutorial. There is no need for a fancy interface. For example, a menu printed via simple console I/O is ok. Your program should consist of a menu with a loop in which:

#### • A list of at least five alternative tasks is offered to the user. An additional alternative should be quit.

#### • The loop works as follows:

  – The user selects an alternative.

  – The system prompts the user for appropriate input values.

  – The system accesses the database to perform the appropriate queries and/or modifications. 
  
  – Data or an appropriate acknowledgment is returned to the user.

  – The user is returned to the menu after the execution of the task is completed.
  

#### Your program should follow the following guidelines.

• Your options should include both queries and modifications.

• Some of your options should contain more than one SQL statement.

• At least one of the tasks should lead to a sub-menu that is created out of a database query.

• Your program must handle errors appropriately. For Java, catch exceptions and print the error messages. Ensure that your program terminates gracefully (after closing any connections) even in the case of a database/SQL exception. If we notice your programs are leaving open hundreds of connections that are piling up at the database end and blocking resources, you might get penalized.


#### For example, if your project were about skaters and competitions.

1. Look up whether a skater participates in a certain competition by skater name.
2. Unroll a skater S in a competition C. If the rating level is below 3, S cannot enroll in any competition. If it is between 3 and 6, S can enroll in regional competitions only, if its is between 7 and 9, he/she can enroll in regional and national levels, and only with a skating level of 10 can S enroll in all types of competitions. If S is not qualified for the competition C, return a list of alternative competitions for which the S has the minimum rating level and which are close to C in terms of the date.
3. A competition is cancelled: find all skaters participating and replace the participation with a competition close in time to the cancelled competition.
4. Add a new skater.
5. Increase the rating of skaters that were among the first 5 in at least 2 competitions of the highest level they can participate.
6. Quit

#### To be turned in:

Hand in all your .java files as separate files. Furthermore, create a section Application program in document project3.pdf. In this section include the screenshot of the execution of this program. Each of the options should be exercised at least once in your script.

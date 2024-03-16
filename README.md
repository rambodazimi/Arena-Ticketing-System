## Team Members (Alphabetical Order)
Rambod Azimi, Daniel Ha, Shahin Jowkar, Matthew Spagnuolo

## Introduction
The Arena Ticketing System (ATS) is a Software-As-A-Service product designed to assist concert and sports venue owners in managing their events' ticketing, seating arrangements, sponsorship information, and user base within a single system, while also deriving insightful data from these events. From a general user's perspective, ATS enables users to discover upcoming concerts in their city, select their preferred seats, and conveniently purchase tickets. This web-based application offers a user-friendly interface, allowing easy exploration of a wide variety of events. ATS caters to a diverse range of needs, from operational management for venue owners to a seamless ticketing experience for general users.

## ER Model
![ER Model](https://i.ibb.co/5swJTrp/ER.png)

## Application Program Functionalities

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

## Graphical User Interface
To be completed!

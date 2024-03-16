-- Include your create table DDL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO COMP421;

-- Remember to put the create table ddls for the tables with foreign key references
--    ONLY AFTER the parent tables have already been created.

-- This is only an example of how you add create table ddls to this file.
--   You may remove it.
CREATE TABLE Users 
( 
	UserID INTEGER NOT NULL, 
	EmailAddress VARCHAR(100), 
	DOB DATE, 
	PRIMARY KEY(UserID) 
); 

CREATE TABLE RegisteredUsers
(
	UserID INTEGER NOT NULL,
	Username VARCHAR(30),
	Password VARCHAR(30),
	CreditCard VARCHAR(30),
	ExpiryDate DATE,
	Location VARCHAR(30),
	PreferredType VARCHAR(30),
	PreferredGenre VARCHAR(30),
	PRIMARY KEY(UserID),
	FOREIGN KEY(UserID) REFERENCES Users
);

CREATE TABLE Layouts
(
	TypeID INTEGER NOT NULL,
	FloorCapacity Integer CHECK(FloorCapacity >= 0),
	PRIMARY KEY(TypeID)
);

CREATE TABLE Events
(
	EID INTEGER NOT NULL,
	AgeRestriction INTEGER CHECK(AgeRestriction >= 0),
	StartDate DATE,
	StartTime Time,
	DURATION INTEGER,
	TypeID INTEGER NOT NULL,
	PRIMARY KEY(EID),
	FOREIGN KEY(TypeID) REFERENCES Layouts
);

CREATE TABLE Performances
(
	EID INTEGER NOT NULL,
	PerformanceType VARCHAR(30),
	Entertainer VARCHAR(30),
	PRIMARY KEY(EID),
	FOREIGN KEY(EID) REFERENCES Events
);

CREATE TABLE SportGames
(
	EID INTEGER NOT NULL,
	AwayTeam VARCHAR(30),
	HomeTeam VARCHAR(30),
	GameType VARCHAR(30),
	PRIMARY KEY(EID),
	FOREIGN KEY(EID) REFERENCES Events
);

CREATE TABLE Sponsors
(
	SID INTEGER NOT NULL,
	SponsorName VARCHAR(30),
	SponsorType VARCHAR(30),
	PRIMARY KEY(SID)
);

CREATE TABLE Sections
(
	SectionNumber INTEGER NOT NULL,
	Capacity INTEGER,
	PRIMARY KEY(SectionNumber)
);

CREATE TABLE Seats
(
	SectionNumber INTEGER NOT NULL,
	SeatNumber INTEGER NOT NULL,
	RowNumber INTEGER NOT NULL,
	Aisle VARCHAR(30),
	PRIMARY KEY(SectionNumber, SeatNumber, RowNumber),
	FOREIGN KEY(SectionNumber) REFERENCES Sections
);

CREATE TABLE Tickets
(
	TicketID INTEGER NOT NULL,
	Price FLOAT,
	DesignatedEntrance INTEGER,
	UserID INTEGER,
	PurchaseDate DATE,
	EID INTEGER NOT NULL,
	PRIMARY KEY(TicketID),
	FOREIGN KEY(UserID) REFERENCES Users,
	FOREIGN KEY(EID) REFERENCES Events
);

CREATE TABLE SeatedTickets
(
	TicketID INTEGER NOT NULL,
	isVIP BOOLEAN,
	SeatNumber INTEGER NOT NULL,
	RowNumber INTEGER NOT NULL,
	SectionNumber INTEGER NOT NULL,
	PRIMARY KEY(TicketID),
	FOREIGN KEY(TicketID) REFERENCES Tickets,
	FOREIGN KEY(SeatNumber, RowNumber, SectionNumber) REFERENCES Seats
);

CREATE TABLE FloorTickets
(
	TicketID INTEGER NOT NULL,
	BraceletRequired BOOLEAN,
	PRIMARY KEY(TicketID),
	FOREIGN KEY(TicketID) REFERENCES Tickets
);

CREATE TABLE Sponsorship
(
	EID INTEGER NOT NULL,
	SID INTEGER NOT NULL,
	PRIMARY KEY(EID, SID),
	FOREIGN KEY(EID) REFERENCES Events,
	FOREIGN KEY(SID) REFERENCES Sponsors
);

CREATE TABLE HasAvailable
(
	TypeID INTEGER NOT NULL,
	SectionNumber INTEGER NOT NULL,
	PRIMARY KEY(TypeID, SectionNumber),
	FOREIGN KEY(TypeID) REFERENCES Layouts,
	FOREIGN KEY(SectionNumber) REFERENCES Sections
);

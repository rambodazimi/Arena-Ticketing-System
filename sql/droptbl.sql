-- Include your drop table DDL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO COMP421;

-- Remember to put the drop table ddls for the tables with foreign key references
--    BEFORE the ddls to drop the parent tables (reverse of the creation order).

-- This is only an example of how you add drop table ddls to this file.
--   You may remove it.
DROP TABLE Users;
DROP TABLE RegisteredUsers;
DROP TABLE Layouts;
DROP TABLE Events;
DROP TABLE Performances;
DROP TABLE SportGames;
DROP TABLE Sponsors;
DROP TABLE Sections;
DROP TABLE Seats;
DROP TABLE Tickets;
DROP TABLE SeatedTickets;
DROP TABLE FloorTickets;
DROP TABLE Sponsorship;
DROP TABLE HasAvailable;

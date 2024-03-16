Write a user-friendly application program for your database in Java connecting to the DBMS via JDBC. You can base this on the example JDBC program that is given in mycourses as part of the JDBC tutorial. There is no need for a fancy interface. For example, a menu printed via simple console I/O is ok. Your program should consist of a menu with a loop in which:

• A list of at least five alternative tasks is offered to the user. An additional alternative should be quit.
• The loop works as follows
– The user selects an alternative.
– The system prompts the user for appropriate input values.
– The system accesses the database to perform the appropriate queries and/or modifications. – Data or an appropriate acknowledgment is returned to the user.
– The user is returned to the menu after the execution of the task is completed.
Your program should follow the following guidelines.
• Your options should include both queries and modifications.
• Some of your options should contain more than one SQL statement.
• At least one of the tasks should lead to a sub-menu that is created out of a database query.
• Your program must handle errors appropriately. For Java, catch exceptions and print the error messages. Ensure that your program terminates gracefully (after closing any connections) even in the case of a database/SQL exception. If we notice your programs are leaving open hundreds of connections that are piling up at the database end and blocking resources, you might get penalized.
For example, if your project were about skaters and competitions.
1. Look up whether a skater participates in a certain competition by skater name.
2. Unroll a skater S in a competition C. If the rating level is below 3, S cannot enroll in any competition. If it is between 3 and 6, S can enroll in regional competitions only, if its is between 7 and 9, he/she can enroll in regional and national levels, and only with a skating level of 10 can S enroll in all types of competitions. If S is not qualified for the competition C, return a list of alternative competitions for which the S has the minimum rating level and which are close to C in terms of the date.
3. A competition is cancelled: find all skaters participating and replace the participation with a competition close in time to the cancelled competition.
4. Add a new skater.
5. Increase the rating of skaters that were among the first 5 in at least 2 competitions of the highest level they can participate.
6. Quit
With this, the main menu could look like:
To be turned in:
Hand in all your .java files as separate files. Furthermore, create a section Application program in document project3.pdf. In this section include the screenshot of the execution of this program. Each of the options should be exercised at least once in your script.

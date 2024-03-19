import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;


class G01JDBC {
	
    public static void main (String[] args) throws SQLException {
    	
    	Scanner scanner = new Scanner(System.in);
    	
        int sqlCode = 0;
        String sqlState = "00000";

        // Register the driver
        try { 
        	DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
        }catch(Exception cnfe) {
        	System.out.println("Class not found"); 
        }

        String url = "jdbc:db2://winter2024-comp421.cs.mcgill.ca:50000/comp421";

        // REMEMBER to remove your user id and password before submitting your code!!
        String your_userid = "cs421g01";
        String your_password = "comp421g01";
        
        
        if(your_userid == null && (your_userid = System.getenv("SOCSUSER")) == null) {
          System.err.println("Error!! do not have a password to connect to the database!");
          System.exit(1);
        }
        
        if(your_password == null && (your_password = System.getenv("SOCSPASSWD")) == null) {
          System.err.println("Error!! do not have a password to connect to the database!");
          System.exit(1);
        }
        
        Connection con = DriverManager.getConnection(url,your_userid,your_password);
        Statement statement = con.createStatement();

        // Main Menu
        System.out.println("Welcome to the Arena Ticketing System (ATS):");
        int option = 0;
        do{
        	System.out.println("Main Menu:");
            System.out.println("1. Add a new event");
            System.out.println("2. Register a new user");
            System.out.println("3. Purchase a ticket");
            System.out.println("4. Add a new sponsor to an event");
            System.out.println("5. Analytics");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            try {
            	option = scanner.nextInt();
            } catch(Exception e) {
        		System.out.println("Invalid option! Please enter a number from 1 to 6.");
                scanner.nextLine();
                continue;
            }

            switch(option) {
            	case 1: // Add a new event
                    System.out.print("Age restriction: ");
                    int ageRestriction = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Start date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    
                    Date startDate = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        
                        java.util.Date utilDate = sdf.parse(date);
                        
                        startDate = new Date(utilDate.getTime());
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        continue;
                    }
                    
                    System.out.print("Start time (HH:mm:ss): ");
                    String time = scanner.nextLine();

                    Time startTime = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        
                        java.util.Date utilDate = sdf.parse(time);
                        
                        startTime = new Time(utilDate.getTime());
                    } catch (ParseException e) {
                        System.out.println("Invalid time format. Please enter the time in HH:mm:ss format.");
                        continue;
                    }
                    
                    System.out.print("Duration (minutes): ");
                    int duration = scanner.nextInt();
                    
                    System.out.print("Type ID (layout): ");
                    int typeId = scanner.nextInt();

                    
                    // generating EID
                    String query = "SELECT MAX(EID) FROM EVENTS";
                    java.sql.ResultSet rs = statement.executeQuery(query);
                    int maxID = 0;
                    if (rs.next()) {
                        maxID = rs.getInt(1);
                    }
                    
                    int eid = maxID + 1;
                                        
            		addEvent(statement, eid, ageRestriction, startDate, startTime, duration, typeId);
            		break;
            	
            	case 2: // Add a new user
                    System.out.print("Email Address: ");
            		scanner.nextLine();
            		String emailAddress = scanner.nextLine();
            		
            		System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String dobi = scanner.nextLine();
                    
                    Date dob = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        
                        java.util.Date utilDate = sdf.parse(dobi);
                        
                        dob = new Date(utilDate.getTime());
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        continue;
                    }
            		
                    System.out.print("Username: ");
            		String username = scanner.nextLine();

            		System.out.print("Password: ");
             		String password = scanner.nextLine();
             		
             		System.out.print("Credit Card Number: ");
             		String creditCard = scanner.nextLine();
             		
             		System.out.print("Expiry date (YYYY-MM-DD): ");
                    String edate = scanner.nextLine();
                    
                    Date expiryDate = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        
                        java.util.Date utilDate = sdf.parse(edate);
                        
                        expiryDate = new Date(utilDate.getTime());
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        continue;
                    }
                    
             		System.out.print("Location: ");
             		String location = scanner.nextLine();
             		
             		System.out.print("Preferred Type: ");
             		String preferredType = scanner.nextLine();
             		
             		System.out.print("Preferred Genre: ");
             		String preferredGenre = scanner.nextLine();
             		
                    // generating UserID
                    String query2 = "SELECT MAX(USERID) FROM USERS";
                    java.sql.ResultSet rs2 = statement.executeQuery(query2);
                    int maxID2 = 0;
                    if (rs2.next()) {
                        maxID2 = rs2.getInt(1);
                    }
                    
                    int userID = maxID2 + 1;
            		
            		addUser(statement, userID, emailAddress, dob, username, password, creditCard, expiryDate, location, preferredType, preferredGenre);
            		break;
            		
            	case 3: // Purchase a ticket
            		
            		System.out.print("Enter your email address: "); // we assume the user already exists in the database
            		scanner.nextLine();
            		String email = scanner.nextLine();
            		
            		// find UserID from USERS relation
            		String userQuery = "SELECT UserID FROM USERS WHERE EmailAddress='" + email + "'";
                    java.sql.ResultSet result = statement.executeQuery(userQuery);
                    
                    int userid = -1;
                    while (result.next()){
                      userid = result.getInt(1);
                    }
            		
                    if(userid == -1) {
                    	System.out.println("User not found in the database. Please register first!");
                    	continue;
                    }
                    
            		// Event Type
            		System.out.print("Select: 1. Performances    2. Sport Games: ");
            		int eventType = scanner.nextInt();
            		
            		if(eventType != 1 && eventType != 2) {
            			System.out.println("Invalid Option!");
            			break;
            		}
            		
            		// Show the list of available events
            		if(eventType == 1) {
            			printPerformances(statement);
            		}
            		if(eventType == 2) {
            			printSportGames(statement);
            		}
            		
            		// List available events
            		System.out.println("\nSelect EID in which you wish to purchase a ticket: ");
            		int eidPurchase = scanner.nextInt(); // we assume the EID is already correct
            		
            		if(eventType == 1) {
            			String eidQueryp = "SELECT EID FROM PERFORMANCES WHERE EID='" + eidPurchase + "'";
            			java.sql.ResultSet result2 = statement.executeQuery(eidQueryp);
            			eidPurchase = -1;
                        while (result2.next()){
                        	eidPurchase = result2.getInt(1);
                        }
                		
                        if(eidPurchase == -1) {
                        	System.out.println("Performance not found in the database. Please try again!");
                        	continue;
                        }
            		}
            		
            		if(eventType == 2) {
            			String eidQueryp = "SELECT EID FROM SPORTGAMES WHERE EID='" + eidPurchase + "'";
            			java.sql.ResultSet result2 = statement.executeQuery(eidQueryp);
            			eidPurchase = -1;
                        while (result2.next()){
                        	eidPurchase = result2.getInt(1);
                        }
                		
                        if(eidPurchase == -1) {
                        	System.out.println("Sport Game not found in the database. Please try again!");
                        	continue;
                        }
            		}
                    
            		// List of available seats
            		printSeats(statement);
            		
            		System.out.println("\nSelect your section number: ");
            		int secNumber = scanner.nextInt();
            		
        			String sectionQuery = "SELECT SECTIONNUMBER FROM SEATS WHERE SECTIONNUMBER='" + secNumber + "'";
        			java.sql.ResultSet resultSectionQuery = statement.executeQuery(sectionQuery);
        			secNumber = -1;
                    while (resultSectionQuery.next()){
                    	secNumber = resultSectionQuery.getInt(1);
                    }
            		
                    if(secNumber == -1) {
                    	System.out.println("Section Number not found in the database. Please try again!");
                    	continue;
                    }

            		System.out.println("\nSelect your seat number: ");
            		int seatNum = scanner.nextInt();
            		
        			String seatQuery = "SELECT SEATNUMBER FROM SEATS WHERE SEATNUMBER='" + seatNum + "'";
        			java.sql.ResultSet resultSeatQuery = statement.executeQuery(seatQuery);
        			seatNum = -1;
                    while (resultSeatQuery.next()){
                    	seatNum = resultSeatQuery.getInt(1);
                    }
            		
                    if(seatNum == -1) {
                    	System.out.println("Seat Number not found in the database. Please try again!");
                    	continue;
                    }
            		
            		System.out.println("\nSelect your row number: ");
            		int rowNum = scanner.nextInt();
            		
        			String rowQuery = "SELECT ROWNUMBER FROM SEATS WHERE ROWNUMBER='" + rowNum + "'";
        			java.sql.ResultSet resultRowQuery = statement.executeQuery(rowQuery);
        			rowNum = -1;
                    while (resultRowQuery.next()){
                    	rowNum = resultRowQuery.getInt(1);
                    }
            		
                    if(rowNum == -1) {
                    	System.out.println("Row Number not found in the database. Please try again!");
                    	continue;
                    }
            		
                    // Calculating price ticket
            		double highestPrice = 500;
            		
            		double price = highestPrice / rowNum; // As the row increases, the price drops
            		
            		System.out.println("-------------------------------------");
            		System.out.println("Event ID: " + eidPurchase);
            		System.out.println("Section Number: " + secNumber);
            		System.out.println("Seat Number: " + seatNum);
            		System.out.println("Row Number: " + rowNum);
            		System.out.println("Total Price: $" + price);
            		
                    System.out.print("Confirm purchase (yes/no): ");
                    String confirmation = scanner.next();
                    
                    if (confirmation.equalsIgnoreCase("yes")) {
                    	
                    	// generate TicketID
                    	String queryt = "SELECT MAX(TicketID) FROM TICKETS";
                        java.sql.ResultSet rst = statement.executeQuery(queryt);
                        int maxIDt = 0;
                        if (rst.next()) {
                            maxIDt = rst.getInt(1);
                        }
                        int ticketID = maxIDt + 1;
                    	
                        // Get current date
                        long currentTimeMillis = System.currentTimeMillis();
                        java.util.Date currentDate = new java.util.Date(currentTimeMillis);
                        Date sqlDate = new Date(currentDate.getTime()); 
                                                
                        addTicket(statement, ticketID, price, 1, userid, sqlDate, eidPurchase);
                        
                        System.out.println("Purchase successful!");
                        System.out.println("Ticket ID: " + ticketID);
 
                        // Fill the seat so that no one else can purchase that same seat
                        String removeQuery = "DELETE FROM SEATS WHERE SectionNumber='" + secNumber + "'" + " AND SeatNumber='" + seatNum + "'" + " AND RowNumber='" + rowNum + "'";
                        statement.executeUpdate(removeQuery);  
                     
                    } else {
                        System.out.println("Purchase cancelled.");
                        break;
                    }
            		break;
            		
            	case 4: // Add a new sponsor
            		System.out.print("Sponsor Name: ");
            		scanner.nextLine();
             		String sponsorName = scanner.nextLine();
             		
             		System.out.print("Sponsor Type: ");
             		String sponsorType = scanner.nextLine();
            		
                    // generating SID
                    String query4 = "SELECT MAX(SID) FROM SPONSORS";
                    java.sql.ResultSet rs4 = statement.executeQuery(query4);
                    int maxID4 = 0;
                    if (rs4.next()) {
                        maxID4 = rs4.getInt(1);
                    }
                    
                    int sid = maxID4 + 1;
                    
                    System.out.println("EID in which you wish to assign the sponsor: ");
                    
                    int eidRelation = scanner.nextInt();
                    
                    String eidQuery = "SELECT EID FROM EVENTS WHERE EID='" + eidRelation + "'";
        			java.sql.ResultSet resultEidQuery = statement.executeQuery(eidQuery);
        			eidRelation = -1;
                    while (resultEidQuery.next()){
                    	eidRelation = resultEidQuery.getInt(1);
                    }
            		
                    if(eidRelation == -1) {
                    	System.out.println("EID not found in the database. Please try again!");
                    	continue;
                    }

            		addSponsor(statement, sid, sponsorName, sponsorType, eidRelation);
            		break;
            		
            	case 5: // Analytics
            		// Sub Menu
                	System.out.println("\nAnalytics");
                    System.out.println("1. View all events");
                    System.out.println("2. View performances");
                    System.out.println("3. View sport games");
                    System.out.println("4. View all users");
                    System.out.println("5. View registered users");
                    System.out.println("6. View all purchased tickets");
                    System.out.println("7. View purchased tickets of an event");
                    System.out.println("8. View all sponsors");
                    System.out.println("9. Return to main menu");
                    System.out.print("Enter your choice: ");
                    int option2 = 0;
                    try {
                    	option2 = scanner.nextInt();
                    } catch(Exception e) {
                		System.out.println("Invalid option! Try again!");
                        scanner.nextLine();
                        continue;
                    }
                    if(option2 == 1) {
                    	printEvents(statement);
                    }
                    else if(option2 == 2) {
                    	printPerformances(statement);
                    }
                    else if(option2 == 3) {
                    	printSportGames(statement);
                    }
                    else if(option2 == 4) {
                    	printUsers(statement);
                    }
                    else if(option2 == 5) {
                    	printRegisteredUsers(statement);
                    }
                    else if(option2 == 6) {
                    	printTickets(statement);
                    }
                    else if(option2 == 7) {
                    	System.out.println("Select EID: ");
                    	int inputEID = scanner.nextInt();
                    	printTicketsOfEvent(statement, inputEID);
                    }
                    else if(option2 == 8) {
                    	printSponsors(statement);
                    }
                    else if(option2 == 9) {
                    	continue;
                    }
                    else {
                		System.out.println("Invalid option! Try again!");
                		continue;
                    }
                    
            		break;
            		
            	case 6:
            		System.out.println("Bye!");
            		break;
            		
            	default:
            		System.out.println("Invalid option! Please enter a number from 1 to 6.");
            }

        } while(option != 6);
        
  	statement.close();
  	con.close();
    }
    
    
    public static void addEvent(Statement statement, int eid, int ageRestriction, Date startDate, Time startTime, int duration, int typeId) {
    	try{
    	  String insertSQL = "INSERT INTO EVENTS (EID, AgeRestriction, StartDate, StartTime, Duration, TypeID) " +
                    "VALUES (" + eid + "," + ageRestriction + ",'" + startDate + "','" + startTime + "'," + duration + "," + typeId + ")";
          statement.executeUpdate(insertSQL);
    	  System.out.println("Successfully added a new event with EID: " + eid + ", Age Restriction: " + ageRestriction + ", Start Date: " + startDate + ", Start Time: " + startTime + ", Duration: " + duration + ", Type ID: " + typeId);
        }catch(SQLException e){
          int sqlCode = e.getErrorCode(); 
          String sqlState = e.getSQLState();
          System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
          System.out.println(e);
        }    	
    }
    
    public static void addUser(Statement statement, int userID, String emailAddress, Date dob, String username, String password, String creditCard, Date expiryDate, String location, String preferredType, String preferredGenre) {
    	// Adding regular User to the database
    	try{
    		String insertSQL = "INSERT INTO USERS (UserID, EmailAddress, DOB) " +
                    "VALUES (" + userID + ",'" + emailAddress + "','" + dob + "')";

          statement.executeUpdate(insertSQL);
      	  System.out.println("Successfully added a new user with UserID: " + userID + ", Email Address: " + emailAddress + ", DOB: " + dob);
          }catch(SQLException e){
            int sqlCode = e.getErrorCode(); 
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }   
    	
    	// Adding registered user to the database
    	try{
    	  String insertSQL = "INSERT INTO REGISTEREDUSERS (UserID, Username, Password, CreditCard, ExpiryDate, Location, PreferredType, PreferredGenre) " +
                    "VALUES (" + userID + ",'" + username + "','" + password + "','" + creditCard + "','" + expiryDate + "','" + location + "','" + preferredType + "','" + preferredGenre + "')";
    	  statement.executeUpdate(insertSQL);
      	  System.out.println("Successfully added a new user with UserID: " + userID + ", Username: " + username + ", Password: " + password + ", CreditCard: " + creditCard + ", ExpiryDate: " + expiryDate + ", Location: " + location + ", PreferredType: " + preferredType + ", preferredGenre: " + preferredGenre);
          }catch(SQLException e){
            int sqlCode = e.getErrorCode(); 
            String sqlState = e.getSQLState();
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void addTicket(Statement statement, int ticketID, double price, int designatedEntrance, int userID, Date purchaseDate, int eid) {
    	try{
    		String insertSQL = "INSERT INTO Tickets (TicketID, Price, DesignatedEntrance, UserID, PurchaseDate, EID) " +
                    "VALUES ('" + ticketID + "', '" + price + "', '" + designatedEntrance + "', '" + userID + "', '" + purchaseDate + "', '" + eid + "')";
    		statement.executeUpdate(insertSQL);
          	  System.out.println("Successfully added a new ticket with TicketID: " + ticketID);
              }catch(SQLException e){
                int sqlCode = e.getErrorCode(); 
                String sqlState = e.getSQLState();
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                System.out.println(e);
              }

    }
    
    public static void addSponsor(Statement statement, int sid, String sponsorName, String sponsorType, int eid) {
    	// Adding a new sponsor to the database
    	try{
    	  String insertSQL = "INSERT INTO SPONSORS (SID, SponsorName, SponsorType) " +
                    "VALUES (" + sid + ",'" + sponsorName + "','" + sponsorType + "')";
      	  statement.executeUpdate(insertSQL);
        	  System.out.println("Successfully added a new sponsor with SID: " + sid + ", SponsorName: " + sponsorName + ", SponsorType: " + sponsorType);
            }catch(SQLException e){
              int sqlCode = e.getErrorCode(); 
              String sqlState = e.getSQLState();
              System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
              System.out.println(e);
            }
    	
    	// Adding a new Sponsorship relationship
    	try{
      	  String insertSQL = "INSERT INTO SPONSORSHIP (EID, SID) " +
                      "VALUES (" + eid + ",'" + sid + "')";
        	  statement.executeUpdate(insertSQL);
          	  System.out.println("Successfully added a new sponsorship with EID: " + eid + ", SID: " + sid);
              }catch(SQLException e){
                int sqlCode = e.getErrorCode(); 
                String sqlState = e.getSQLState();
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                System.out.println(e);
              }
    }
    
    public static void printEvents(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM EVENTS";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("--------------------------------------------------------------------");
            System.out.println("| EID | AgeRestriction | StartDate | StartTime | Duration | TypeID |");
            System.out.println("--------------------------------------------------------------------");
            
            while (rs.next()){
              int eid = rs.getInt(1);
              int ageRestriction = rs.getInt(2);
              Date startDate = rs.getDate(3);
              Time startTime = rs.getTime(4);
              int duration = rs.getInt(5);
              int typeId = rs.getInt(6);
              
              System.out.printf("| %-4d| %-15d| %-10s| %-10s| %-9d| %-7d|%n", eid, ageRestriction, startDate, startTime, duration, typeId);
            }
            System.out.println("--------------------------------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printPerformances(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM PERFORMANCES";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("---------------------------------------");
            System.out.println("| EID | PerformanceType | Entertainer |");
            System.out.println("---------------------------------------");
            
            while (rs.next()){
              int eid = rs.getInt(1);
              String performanceType = rs.getString(2);
              String entertainer = rs.getString(3);
              
              System.out.printf("| %-4d| %-16s| %-12s|%n", eid, performanceType, entertainer);
            }
            System.out.println("---------------------------------------");

          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printSportGames(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM SportGames";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("--------------------------------------------------------------------");
            System.out.println("| EID |       AwayTeam      |       HomeTeam      |     GameType   |");
            System.out.println("--------------------------------------------------------------------");
            
            while (rs.next()){
              int eid = rs.getInt(1);
              String awayTeam = rs.getString(2);
              String homeTeam = rs.getString(3);
              String gameType = rs.getString(4);

              
              System.out.printf("| %-4d| %-20s| %-20s| %-16s|%n", eid, awayTeam, homeTeam, gameType);
            }
            System.out.println("--------------------------------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printUsers(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM Users";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("--------------------------------------------");
            System.out.println("| UserID |    EmailAddress     |    DOB    |");
            System.out.println("--------------------------------------------");
            
            while (rs.next()){
              int userID = rs.getInt(1);
              String emailAddress = rs.getString(2);
              String dob = rs.getString(3);
              System.out.printf("| %-7d| %-20s| %-10s|%n", userID, emailAddress, dob);
            }
            System.out.println("--------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printRegisteredUsers(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM RegisteredUsers";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println("| UserID | Username | Password | CreditCard | ExpiryDate | Location | PreferredType | PreferredGenre |");
            System.out.println("------------------------------------------------------------------------------------------------------");
            
            while (rs.next()){
              int userID = rs.getInt(1);
              String username = rs.getString(2);
              String password = "*****";
              String creditCard = rs.getString(4).substring(0, 4) + " ****";
              Date expiryDate = rs.getDate(5);
              String location = rs.getString(6);
              String preferredType = rs.getString(7);
              String preferredGenre = rs.getString(8);
              System.out.printf("| %-7d| %-9s| %-9s| %-11s| %-11s| %-9s| %-14s| %-15s|%n", userID, username, password, creditCard, expiryDate, location, preferredType, preferredGenre);
            }
            System.out.println("------------------------------------------------------------------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printSponsors(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM SPONSORS";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("-----------------------------------");
            System.out.println("| SID | SponsorName | SponsorType |");
            System.out.println("-----------------------------------");
            
            while (rs.next()){
              int sid = rs.getInt(1);
              String sponsorName = rs.getString(2);
              String sponsorType = rs.getString(3);
              
              System.out.printf("| %-4d| %-12s| %-12s|%n", sid, sponsorName, sponsorType);
            }
            System.out.println("-----------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printTickets(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM TICKETS";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("| TicketID | Price | DesignatedEntrance | UserID | PurchaseDate | EID |");
            System.out.println("-----------------------------------------------------------------------");
            float totalPrice = 0;
            while (rs.next()){
              int ticketId = rs.getInt(1);
              float price = rs.getFloat(2);
              totalPrice += price;
              int designatedEntrance = rs.getInt(3);
              int userId = rs.getInt(4);
              Date purchaseDate = rs.getDate(5);
              int eid = rs.getInt(6);

              System.out.printf("| %-9d| %-6.2f| %-19d| %-7d| %-13s| %-4d|%n", ticketId, price, designatedEntrance, userId, purchaseDate, eid);
            }
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Total Price: $" + totalPrice);
            System.out.println("-----------------------------------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    public static void printTicketsOfEvent(Statement statement, int inputEID) {
    	try {
            String eidQuery = "SELECT * FROM TICKETS WHERE EID='" + inputEID + "'";
            java.sql.ResultSet rs = statement.executeQuery(eidQuery);
            
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("| TicketID | Price | DesignatedEntrance | UserID | PurchaseDate | EID |");
            System.out.println("-----------------------------------------------------------------------");
            
            while (rs.next()){
              int ticketId = rs.getInt(1);
              float price = rs.getFloat(2);
              int designatedEntrance = rs.getInt(3);
              int userId = rs.getInt(4);
              Date purchaseDate = rs.getDate(5);
              int eid = rs.getInt(6);

              System.out.printf("| %-9d| %-6.2f| %-19d| %-7d| %-13s| %-4d|%n", ticketId, price, designatedEntrance, userId, purchaseDate, eid);
            }
            System.out.println("-----------------------------------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }

    
    public static void printSeats(Statement statement) {
    	try {
            String querySQL = "SELECT * FROM SEATS";
            java.sql.ResultSet rs = statement.executeQuery(querySQL);
            
            System.out.println("--------------------------------------------------");
            System.out.println("| SectionNumber | SeatNumber | RowNumber | Aisle |");
            System.out.println("--------------------------------------------------");
            
            while (rs.next()){
              int sectionNumber = rs.getInt(1);
              int seatNumber = rs.getInt(2);
              int rowNumber = rs.getInt(3);
              String aisle = rs.getString(4);

              System.out.printf("| %-14d| %-11d| %-10d| %-6s|%n", sectionNumber, seatNumber, rowNumber, aisle);
            }
            System.out.println("--------------------------------------------------");
          }catch(SQLException e) {
            int sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
                  
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
          }
    }
    
    
}

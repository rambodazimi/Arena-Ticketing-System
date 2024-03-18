import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATSGui extends JFrame implements ActionListener {
    private JComboBox<String> menuOptions;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextArea outputArea;

    // Events attributes
    private JTextField ageRestrictionField;
    private JTextField startDateField;
    private JTextField startTimeField;
    private JTextField durationField;
    private JTextField typeIdField;
    private JButton addEventButton;

    // RegisteredUsers attributes
    private JTextField emailField;
    private JTextField dobField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField creditCardField;
    private JTextField expiryDateField;
    private JTextField locationField;
    private JTextField preferredTypeField;
    private JTextField preferredGenreField;
    private JButton registerUserButton;
    
    // Tickets attributes
    private JTextField priceField;
    private JTextField designatedEntranceField;
    private JTextField userIDField;
    private JTextField purchaseDateField;
    private JTextField EIDField;
    private JButton addTicketButton;

    // Sponsor attributes
    private JTextField sponsorNameField;
    private JTextField sponsorTypeField;
    private JButton addSponsorButton;


    public ATSGui() {
        setTitle("Arena Ticketing System (ATS)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Menu options
        String[] options = {"Add a new event", "Register a new user", "Purchase a ticket",
                "Add a new sponsor to an event", "Exit"};
        menuOptions = new JComboBox<>(options);
        menuOptions.addActionListener(this);
        panel.add(menuOptions, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // CardLayout for different card panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        panel.add(cardPanel, BorderLayout.SOUTH);

        // Components for adding a new event
        JPanel addEventPanel = new JPanel(new GridLayout(6, 2));
        addEventPanel.add(new JLabel("Age Restriction:"));
        ageRestrictionField = new JTextField();
        addEventPanel.add(ageRestrictionField);

        addEventPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField();
        addEventPanel.add(startDateField);

        addEventPanel.add(new JLabel("Start Time (HH:mm:ss):"));
        startTimeField = new JTextField();
        addEventPanel.add(startTimeField);

        addEventPanel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        addEventPanel.add(durationField);

        addEventPanel.add(new JLabel("Type ID (Layout):"));
        typeIdField = new JTextField();
        addEventPanel.add(typeIdField);

        addEventButton = new JButton("Submit");
        addEventButton.addActionListener(this);
        addEventPanel.add(addEventButton);
        cardPanel.add(addEventPanel, "AddEvent");

        // Components for registering a new user
        JPanel registerUserPanel = new JPanel(new GridLayout(10, 2));
        registerUserPanel.add(new JLabel("Email Address:"));
        emailField = new JTextField();
        registerUserPanel.add(emailField);

        registerUserPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobField = new JTextField();
        registerUserPanel.add(dobField);

        registerUserPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        registerUserPanel.add(usernameField);

        registerUserPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        registerUserPanel.add(passwordField);

        registerUserPanel.add(new JLabel("Credit Card Number:"));
        creditCardField = new JTextField();
        registerUserPanel.add(creditCardField);

        registerUserPanel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        expiryDateField = new JTextField();
        registerUserPanel.add(expiryDateField);

        registerUserPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        registerUserPanel.add(locationField);

        registerUserPanel.add(new JLabel("Preferred Type:"));
        preferredTypeField = new JTextField();
        registerUserPanel.add(preferredTypeField);

        registerUserPanel.add(new JLabel("Preferred Genre:"));
        preferredGenreField = new JTextField();
        registerUserPanel.add(preferredGenreField);

        registerUserButton = new JButton("Submit");
        registerUserButton.addActionListener(this);
        registerUserPanel.add(registerUserButton);
        cardPanel.add(registerUserPanel, "RegisterUser");

        
        // Components for purchasing a new ticket
        JPanel addTicketPanel = new JPanel(new GridLayout(6, 2));
        addTicketPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        addTicketPanel.add(priceField);

        addTicketPanel.add(new JLabel("Designated Entrance:"));
        designatedEntranceField = new JTextField();
        addTicketPanel.add(designatedEntranceField);

        addTicketPanel.add(new JLabel("User ID:"));
        userIDField = new JTextField();
        addTicketPanel.add(userIDField);

        addTicketPanel.add(new JLabel("Purchase Date:"));
        purchaseDateField = new JTextField();
        addTicketPanel.add(purchaseDateField);

        addTicketPanel.add(new JLabel("EID:"));
        EIDField = new JTextField();
        addTicketPanel.add(EIDField);

        addTicketButton = new JButton("Submit");
        addTicketButton.addActionListener(this);
        addTicketPanel.add(addTicketButton);
        cardPanel.add(addTicketPanel, "AddTicket");
        
        // Components for adding a new sponsor
        JPanel addSponsorPanel = new JPanel(new GridLayout(3, 2));
        addSponsorPanel.add(new JLabel("Sponsor Name:"));
        sponsorNameField = new JTextField();
        addSponsorPanel.add(sponsorNameField);

        addSponsorPanel.add(new JLabel("Sponsor Type:"));
        sponsorTypeField = new JTextField();
        addSponsorPanel.add(sponsorTypeField);
        
        addSponsorButton = new JButton("Submit");
        addSponsorButton.addActionListener(this);
        addSponsorPanel.add(addSponsorButton);
        cardPanel.add(addSponsorPanel, "AddSponsor");
        
        // Add panel to frame
        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        String option = (String) menuOptions.getSelectedItem();
        String output = "";

        switch (option) {
            case "Add a new event":
                output = "Option 1: Add a new event - Please fill in the details below:";
                cardLayout.show(cardPanel, "AddEvent");
                break;
            case "Register a new user":
                output = "Option 2: Register a new user - Please fill in the details below:";
                cardLayout.show(cardPanel, "RegisterUser");
                break;
            case "Purchase a ticket":
                output = "Option 3: Purchase a ticket - Please fill in the details below:";
                cardLayout.show(cardPanel, "AddTicket");

                break;
            case "Add a new sponsor to an event":
                output = "Option 4: Add a new sponsor to an event - Please fill in the details below:";
                cardLayout.show(cardPanel, "AddSponsor");

                break;
            case "Exit":
                System.exit(0);
                break;
            case "Submit": // Submit button actions for Add Event and Register User
                if (e.getSource() == addEventButton) {
                    // Submit action for Add Event
                    output = "Adding a new event...\n";
                    // Get values from fields and perform actions accordingly
                    String ageRestriction = ageRestrictionField.getText();
                    String startDate = startDateField.getText();
                    String startTime = startTimeField.getText();
                    String duration = durationField.getText();
                    String typeId = typeIdField.getText();
                    // Perform actions, e.g., call a method to add event to database
                } else if (e.getSource() == registerUserButton) {
                    // Submit action for Register User
                    output = "Registering a new user...\n";
                    // Get values from fields and perform actions accordingly
                    String emailAddress = emailField.getText();
                    String dob = dobField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String creditCard = creditCardField.getText();
                    String expiryDate = expiryDateField.getText();
                    String location = locationField.getText();
                    String preferredType = preferredTypeField.getText();
                    String preferredGenre = preferredGenreField.getText();
                    // Perform actions, e.g., call a method to register user to database
                }
                break;
        }

        outputArea.setText(output + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATSGui gui = new ATSGui();
            gui.setVisible(true);
        });
    }
}

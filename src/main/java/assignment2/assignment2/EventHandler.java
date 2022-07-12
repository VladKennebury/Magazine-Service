package assignment2.assignment2;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import java.io.File;
import javafx.scene.layout.VBox;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - EventHandler.Java
 * 
 * Purpose - Handles events for buttons in the View, Edit and Create scenes.
 * Including creating new windows to display/collect input and pass information
 * to the business logic of the magazine service controller
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class EventHandler {
    
    /// Alertbox for displaying validations/notifications
    private AlertBox alertBox = new AlertBox();
    /// Validates GUI input
    private InputValidation validation = new InputValidation();
    /// Validates magazine logic
    private MagazineValidation logicValidation = new MagazineValidation();
    /// Used in multithreading for billing history
    private BillingHistory billingHistory;
    /// Used in multithreading for billing history payment
    private BillingHistoryPayment billingHistoryPayment;
    
    /**
    * Adds a string branch to another tree item
    * @param info
    * @param parent
    * @return TreeItem<String>
    */
    public TreeItem<String> AddStringBranch(String info, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(info);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
    
    /**
    * Refreshes all trees which store customer information in view and edit scenes
    * @param controller
    * @param item1
    * @param item2
    * @param item3
    */
    public void RefreshCustomerTrees(MagazineServiceController controller, TreeItem<String> item1, TreeItem<String> item2, TreeItem<String> item3){
        item1.getChildren().clear();
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetName(), item1);
        }
        
        item2.getChildren().clear();
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetName(), item2);
        }
        
        item3.getChildren().clear();
    }
    
    /**
    * Refreshes all trees which store supplement information in view and edit scenes
    * @param controller
    * @param item1
    * @param item2
    * @param item3
    * @param item4
    */
    public void RefreshSupplementTrees(MagazineServiceController controller, TreeItem<String> item1, TreeItem<String> item2, 
            TreeItem<String> item3, TreeItem<String> item4){
        
        item1.getChildren().clear();
        for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
            AddStringBranch(controller.GetMagazine().GetSupplements().get(i).GetName(), item1);
        }
        
        item2.getChildren().clear();
        for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
            AddStringBranch(controller.GetMagazine().GetSupplements().get(i).GetName(), item2);
        }
        
        item3.getChildren().clear();
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetName(), item3);
        }
        
        item4.getChildren().clear();
    }
    
    /**
    * Refreshes the tree which stores specified customer supplement information
    * in EditCustomerSupplementWindow
    * @param controller
    * @param name
    * @param item1
    */
    public void RefreshCustomerSupplementTree(MagazineServiceController controller, String name, TreeItem<String> item1){
        item1.getChildren().clear();
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(name)){
                for(int j = 0; j < controller.GetMagazine().GetCustomers().get(i).GetSupplements().size(); j++){
                    AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetName(), item1);
                }
            }
        }
    }
    
    /**
    * Refreshes all trees which store magazine information in view and edit scenes
    * in EditCustomerSupplementWindow
    * @param controller
    * @param item1
    * @param item2
    */
    public void RefreshMagazineTrees(MagazineServiceController controller, TreeItem<String> item1, TreeItem<String> item2){ 
        item1.getChildren().clear();
        AddStringBranch("Weekly Cost - $" + controller.GetMagazine().GetWeeklyCost(), item1);
        
        item2.getChildren().clear();
    }
    
    /**
    * Displays Customer details in the information panel in the view scene
    * @param newVal
    * @param information
    * @param controller
    * @throws java.lang.InterruptedException
    */
    public void ViewCustomerTree(String newVal, TreeItem<String> information, MagazineServiceController controller) throws InterruptedException{
        if(newVal != null){
            information.getChildren().clear();
            for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
                if(controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(newVal)){
                    AddStringBranch("Name - " + controller.GetMagazine().GetCustomers().get(i).GetName(), information);
                    AddStringBranch("Email - " + controller.GetMagazine().GetCustomers().get(i).GetEmail(), information);
                    AddStringBranch("Address - " + controller.GetMagazine().GetCustomers().get(i).GetAddress().GetStreetNumber() + " " + 
                            controller.GetMagazine().GetCustomers().get(i).GetAddress().GetStreetName() + ", " + 
                            controller.GetMagazine().GetCustomers().get(i).GetAddress().GetSuburb() + ", " + 
                            controller.GetMagazine().GetCustomers().get(i).GetAddress().GetPostcode(), information);

                    if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                        AddStringBranch("Status - Paying", information);
                        TreeItem<String> associates = AddStringBranch("Associate Customers: ", information);
                        if(!((PayingCustomer)controller.GetMagazine().GetCustomers().get(i)).GetAssociates().isEmpty()){
                            for(int j = 0; j < ((PayingCustomer)controller.GetMagazine().GetCustomers().get(i)).GetAssociates().size(); j++){
                                AddStringBranch(((PayingCustomer)controller.GetMagazine().GetCustomers().get(i)).GetAssociates().get(j).GetName(), associates);
                            }
                        }
                        else{
                            AddStringBranch("No Associate Customers", associates);
                        }
                        
                        //Multithreadinb Billing History
                        TreeItem<String> billing = AddStringBranch("Billing History: ", information);
                        billingHistory = new BillingHistory(controller, billing, newVal);
                        Thread billingThread1 = new Thread(billingHistory);
                        billingHistoryPayment = new BillingHistoryPayment(controller, billing, newVal);
                        Thread billingThread2 = new Thread(billingHistoryPayment);
                        
                        billingThread1.start();
                        billingThread1.join();
                        // Potenital issue throwing an interruption exception when isAlive() check is not used
                        if(!billingThread1.isAlive()){
                            billingThread2.start();
                        }
                    }
                    else if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.ASSOCIATE){
                        AddStringBranch("Status - Associate", information);
                        TreeItem<String> paying = AddStringBranch("Paying Customer: ", information);
                        AddStringBranch(((AssociateCustomer)controller.GetMagazine().GetCustomers().get(i)).GetPayingCustomer().GetName(), paying);
                    }

                    TreeItem<String> subscriptions = AddStringBranch("Subscribed Supplements: ", information);
                    if(!controller.GetMagazine().GetCustomers().get(i).GetSupplements().isEmpty()){
                        for(int k = 0; k < controller.GetMagazine().GetCustomers().get(i).GetSupplements().size(); k++){
                        
                            if(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(k).GetSubscriptionType() == Supplement.Type.WEEKLY){
                                AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(k).GetName() + " (" + "Weekly" + ")", subscriptions);
                            }

                            if(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(k).GetSubscriptionType() == Supplement.Type.FORTNIGHTLY){
                                AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(k).GetName() + " (" + "Fortnightly" + ")", subscriptions);
                            }
                        }
                    }
                    else{
                        AddStringBranch("No Subscribed Supplements", subscriptions);
                    }
                }
            }
        }
    }
    
    /**
    * Displays Supplement details in the information panel in the view scene
    * @param newVal
    * @param information
    * @param controller
    */
    public void ViewSupplementTree(String newVal, TreeItem<String> information, MagazineServiceController controller){
        
        if(newVal != null){
            information.getChildren().clear();
            for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
                if(controller.GetMagazine().GetSupplements().get(i).GetName().equalsIgnoreCase(newVal)){
                    AddStringBranch("Name - " + controller.GetMagazine().GetSupplements().get(i).GetName(), information);
                    AddStringBranch("Weekly Cost - $" + controller.GetMagazine().GetSupplements().get(i).GetWeeklyCost(), information);
                    TreeItem<String> subscribedCustomers = AddStringBranch("Subscribed Customers: ", information);
                    
                    if(!controller.GetMagazine().GetCustomers().isEmpty()){
                        for(int j = 0; j < controller.GetMagazine().GetCustomers().size(); j++){
                            for(int k = 0; k < controller.GetMagazine().GetCustomers().get(j).GetSupplements().size(); k++){
                                if(controller.GetMagazine().GetCustomers().get(j).GetSupplements().get(k).GetName().equalsIgnoreCase(newVal)){
                                    AddStringBranch(controller.GetMagazine().GetCustomers().get(j).GetName(), subscribedCustomers);
                                }
                            }
                        }
                    }
                    else{
                        AddStringBranch("No Subscribed Customers", subscribedCustomers);
                    }
                    TreeItem<String> subscription = AddStringBranch("Subscription Types: ", information);
                    AddStringBranch("Weekly", subscription);
                    AddStringBranch("Fortnightly", subscription);
                }
            }
            
        }
    }
    
    /**
    * Opens a new window and collects input for adding a new customer 
    * to the magazine service
    * @param controller
    */
    public void AddCustomerWindow(MagazineServiceController controller){
        Stage window = new Stage();
        Scene scene;
        window.initModality(Modality.APPLICATION_MODAL);
        
        //Labels
        window.setTitle("Add Customer");
        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-text-fill: #9370DB");
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: #9370DB");
        Label streetNumberLabel = new Label("Street Number");
        streetNumberLabel.setStyle("-fx-text-fill: #9370DB");
        Label streetNameLabel = new Label("Street Name");
        streetNameLabel.setStyle("-fx-text-fill: #9370DB");
        Label suburbLabel = new Label("Suburb");
        suburbLabel.setStyle("-fx-text-fill: #9370DB");
        Label postcodeLabel = new Label("Postcode");
        postcodeLabel.setStyle("-fx-text-fill: #9370DB");
        Label customerTypeLabel = new Label("Customer Type");
        customerTypeLabel.setStyle("-fx-text-fill: #9370DB");
        
        //Textfields
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField streetNumberField = new TextField();
        TextField streetNameField = new TextField();
        TextField suburbField = new TextField();
        TextField postcodeField = new TextField();
        
        //Choicebox
        ChoiceBox<String> customerTypeBox = new ChoiceBox<>();
        customerTypeBox.getItems().add("Paying");
        customerTypeBox.getItems().add("Associate");
        
        //Buttons
        Button nextButton = new Button("Next");
        nextButton.setOnAction((ActionEvent evt) -> {
            
            if(validation.hasNoNumbers(nameField.getText())){
                if(!logicValidation.isDuplicateCustomerName(controller, nameField.getText())){
                    if(!validation.isEmpty(emailField.getText())){
                        if(!logicValidation.isDuplicateCustomerEmail(controller, emailField.getText())){
                            if(validation.isNumeric(streetNumberField.getText())){
                                if(validation.isPositive(streetNumberField.getText())){
                                    if(validation.hasNoNumbers(streetNameField.getText())){
                                        if(validation.hasNoNumbers(suburbField.getText())){
                                            if(validation.isNumeric(postcodeField.getText())){
                                                if(validation.isPositive(postcodeField.getText())){
                                                    if(customerTypeBox.getSelectionModel().selectedItemProperty().getValue() != null){
                                                        if("Paying".equals(customerTypeBox.getSelectionModel().selectedItemProperty().getValue())){
                                                            AddPayingCustomer(controller, nameField.getText(), emailField.getText(), streetNumberField.getText(), 
                                                                    streetNameField.getText(), suburbField.getText(), postcodeField.getText());
                                                            window.close();
                                                        }
                                                        if(logicValidation.PayingCustomerExists(controller)){
                                                            if("Associate".equals(customerTypeBox.getSelectionModel().selectedItemProperty().getValue())){
                                                                AddAssociateCustomer(controller, nameField.getText(), emailField.getText(), streetNumberField.getText(), 
                                                                        streetNameField.getText(), suburbField.getText(), postcodeField.getText());
                                                                window.close();
                                                            }
                                                        }
                                                        else{
                                                            alertBox.AlertBox("No Paying Customers exist to associate with");
                                                        }
                                                    }
                                                    else{
                                                        alertBox.AlertBox("Please select a Customer Type");
                                                    }
                                                }
                                                else{
                                                    alertBox.AlertBox(postcodeLabel.getText() + " must be positive");
                                                }
                                            }
                                            else{
                                                alertBox.AlertBox("'" + postcodeLabel.getText() + "' field cannot be empty and only contain numbers");
                                            }
                                        }
                                        else{
                                            alertBox.AlertBox("'" + suburbLabel.getText() + "' field cannot be empty or contain numbers");
                                        }
                                    }
                                    else{
                                        alertBox.AlertBox("'" + streetNameLabel.getText() + "' field cannot be empty or contain numbers");
                                    }
                                }
                                else{
                                    alertBox.AlertBox(streetNumberLabel.getText() + " must be positive");
                                }
                            }
                            else{
                                alertBox.AlertBox("'" + streetNumberLabel.getText() + "' field cannot be empty and only contain numbers");
                            }
                        }
                        else{
                            alertBox.AlertBox("Customer email '" + emailField.getText() + "' already exists");
                        }
                    }
                    else{
                        alertBox.AlertBox("'" + emailLabel.getText() + "' field cannot be empty");
                    }
                }
                else{
                    alertBox.AlertBox("Customer named '" + nameField.getText() + "' already exists");
                }
            }
            else{
                alertBox.AlertBox("'" + nameLabel.getText() + "' field cannot be empty or contain numbers");
            }
        });
        
        //Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(emailLabel, 0, 1);
        GridPane.setConstraints(emailField, 1, 1);
        GridPane.setConstraints(streetNumberLabel, 0, 2);
        GridPane.setConstraints(streetNumberField, 1, 2);
        GridPane.setConstraints(streetNameLabel, 0, 3);
        GridPane.setConstraints(streetNameField, 1, 3);
        GridPane.setConstraints(suburbLabel, 0, 4);
        GridPane.setConstraints(suburbField, 1, 4);
        GridPane.setConstraints(postcodeLabel, 0, 5);
        GridPane.setConstraints(postcodeField, 1, 5);
        GridPane.setConstraints(customerTypeLabel, 0, 6);
        GridPane.setConstraints(customerTypeBox, 1, 6);
        GridPane.setConstraints(nextButton, 0, 7);
        
        grid.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, streetNumberLabel, 
                streetNumberField, streetNameLabel, streetNameField, suburbLabel, suburbField,
                postcodeLabel, postcodeField, customerTypeLabel, customerTypeBox, nextButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 300);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for adding a new paying customer 
    * to the magazine service
    * @param controller
    * @param name
    * @param email
    * @param streetNumber
    * @param suburb
    * @param streetName
    * @param postcode
    */
    public void AddPayingCustomer(MagazineServiceController controller, String name, 
            String email, String streetNumber, String streetName, String suburb, String postcode){
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Paying Customer");
        
        Label billingInfoLabel = new Label();
        billingInfoLabel.setStyle("-fx-text-fill: #9370DB");
        TextField billingInfoField = new TextField();
        Label billingTypeLabel = new Label("Billing Type");
        billingTypeLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> billingBox = new ChoiceBox<>();
                
        billingBox.getItems().add("Credit Card");
        billingBox.getItems().add("Direct Debit");
        
        Button addButton = new Button("Add New Paying Customer");
        addButton.setOnAction((ActionEvent evt) -> {
            
            int number = Integer.parseInt(streetNumber);
            int post = Integer.parseInt(postcode);
            
            if(billingBox.getSelectionModel().selectedItemProperty().getValue() != null){
                if(!validation.isEmpty(billingInfoField.getText())){
                    controller.AddPayingCustomer(name, email, number, streetName, suburb, post, 
                        billingBox.getSelectionModel().selectedItemProperty().getValue(), billingInfoField.getText());
                    window.close();
                }
                else{
                    alertBox.AlertBox("'" + billingInfoLabel.getText() + "' field cannot be empty");
                }
            }
            else{
                alertBox.AlertBox("Please select a Billing Type");
            }

        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(billingTypeLabel, 0, 0);
        GridPane.setConstraints(billingBox, 1, 0);
                
        GridPane.setConstraints(billingInfoLabel, 0, 1);
        GridPane.setConstraints(billingInfoField, 1, 1);
        GridPane.setConstraints(addButton, 0, 2);
        
        billingBox.getSelectionModel().selectedItemProperty().addListener( (value, oldValue, newValue) -> {
            if("Credit Card".equals(newValue)){
                billingInfoLabel.setText("Credit Card Number"); 
            }

            if("Credit Card".equals(newValue) && "Direct Debit".equals(oldValue)){
                billingInfoLabel.setText("Credit Card Number");
                billingInfoField.setText("");
                grid.getChildren().remove(billingInfoLabel);
                grid.getChildren().remove(billingInfoField);
            }

            if("Direct Debit".equals(newValue)){
                billingInfoLabel.setText("Bank Account Number");
            }

            if("Direct Debit".equals(newValue) && "Credit Card".equals(oldValue)){
                billingInfoLabel.setText("Bank Account Number");
                billingInfoField.setText("");
                grid.getChildren().remove(billingInfoLabel);
                grid.getChildren().remove(billingInfoField);
            }

            grid.getChildren().addAll(billingInfoLabel, billingInfoField);
        });
        
        grid.getChildren().addAll(billingTypeLabel, billingBox, addButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for adding a new associate customer 
    * to the magazine service
    * @param controller
    * @param name
    * @param email
    * @param streetNumber
    * @param suburb
    * @param streetName
    * @param postcode
    */
    public void AddAssociateCustomer(MagazineServiceController controller, String name, 
            String email, String streetNumber, String streetName, String suburb, String postcode){
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Associate Customer");
        
        Label payerLabel = new Label("Select Paying Customer");
        payerLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> payerBox = new ChoiceBox<>();
                
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                payerBox.getItems().add(controller.GetMagazine().GetCustomers().get(i).GetName());
            }
        }
        
        Button addButton = new Button("Add New Associate Customer");
        addButton.setOnAction((ActionEvent evt) -> {
            
            int number = Integer.parseInt(streetNumber);
            int post = Integer.parseInt(postcode);
            
            if(payerBox.getSelectionModel().selectedItemProperty().getValue() != null){
                controller.AddAssociateCustomer(name, email, number, streetName, suburb, post, 
                    payerBox.getSelectionModel().selectedItemProperty().getValue());
                window.close();
            }
            else{
                alertBox.AlertBox("Please select a Paying Customer");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(payerLabel, 0, 0);
        GridPane.setConstraints(payerBox, 1, 0);
                
        GridPane.setConstraints(addButton, 0, 1);
        
        grid.getChildren().addAll(payerLabel, payerBox, addButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing customer information
    * to the magazine service
    * @param controller
    * @param tree
    */
    public void EditCustomerWindow(MagazineServiceController controller, TreeView<String> tree){
        String name = tree.getSelectionModel().getSelectedItem().getValue();
        
        Stage window = new Stage();
        Scene scene;
        window.initModality(Modality.APPLICATION_MODAL);
        
        window.setTitle("Edit Customer");
        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-text-fill: #9370DB");
        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: #9370DB");
        Label streetNumberLabel = new Label("Street Number");
        streetNumberLabel.setStyle("-fx-text-fill: #9370DB");
        Label streetNameLabel = new Label("Street Name");
        streetNameLabel.setStyle("-fx-text-fill: #9370DB");
        Label suburbLabel = new Label("Suburb");
        suburbLabel.setStyle("-fx-text-fill: #9370DB");
        Label postcodeLabel = new Label("Postcode");
        postcodeLabel.setStyle("-fx-text-fill: #9370DB");
        
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField streetNumberField = new TextField();
        TextField streetNameField = new TextField();
        TextField suburbField = new TextField();
        TextField postcodeField = new TextField();
        
        Button nextButton = new Button("Next");
        nextButton.setOnAction((ActionEvent evt) -> {
            
            if(validation.hasNoNumbers(nameField.getText())){
                if(!logicValidation.isDuplicateCustomerName(controller, name, nameField.getText())){
                    if(!validation.isEmpty(emailField.getText())){
                        if(!logicValidation.isDuplicateCustomerEmail(controller, name, emailField.getText())){
                            if(validation.isNumeric(streetNumberField.getText())){
                                if(validation.isPositive(streetNumberField.getText())){
                                    if(validation.hasNoNumbers(streetNameField.getText())){
                                        if(validation.hasNoNumbers(suburbField.getText())){
                                            if(validation.isNumeric(postcodeField.getText())){
                                                if(validation.isPositive(postcodeField.getText())){
                                                    for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
                                                        if(name.equals(controller.GetMagazine().GetCustomers().get(i).GetName())){
                                                            if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                                                                EditPayingCustomer(controller, name, nameField.getText(), emailField.getText(), streetNumberField.getText(), 
                                                                streetNameField.getText(), suburbField.getText(), postcodeField.getText());
                                                                window.close();
                                                            }
                                                            if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.ASSOCIATE){
                                                                EditAssociateCustomer(controller, name, nameField.getText(), emailField.getText(), streetNumberField.getText(), 
                                                                streetNameField.getText(), suburbField.getText(), postcodeField.getText());
                                                                window.close();
                                                            }
                                                        }
                                                    }
                                                }
                                                else{
                                                    alertBox.AlertBox(postcodeLabel.getText() + " must be positive");
                                                }
                                            }
                                            else{
                                                alertBox.AlertBox("'" + postcodeLabel.getText() + "' field cannot be empty and only contain numbers");
                                            }
                                        }
                                        else{
                                            alertBox.AlertBox("'" + suburbLabel.getText() + "' field cannot be empty or contain numbers");
                                        }
                                    }
                                    else{
                                        alertBox.AlertBox("'" + streetNameLabel.getText() + "' field cannot be empty or contain numbers");
                                    }
                                }
                                else{
                                    alertBox.AlertBox(streetNumberLabel.getText() + " must be positive");
                                }
                            }
                            else{
                                alertBox.AlertBox("'" + streetNumberLabel.getText() + "' field cannot be empty and only contain numbers");
                            }
                        }
                        else{
                            alertBox.AlertBox("Customer email '" + emailField.getText() + "' already exists");
                        }
                    }
                    else{
                        alertBox.AlertBox("'" + emailLabel.getText() + "' field cannot be empty");
                    }
                }
                else{
                    alertBox.AlertBox("Customer named '" + nameField.getText() + "' already exists");
                }
            }
            else{
                alertBox.AlertBox("'" + nameLabel.getText() + "' field cannot be empty or contain numbers");
            }
        });
        
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(name)){
                nameField.setText(controller.GetMagazine().GetCustomers().get(i).GetName());
                emailField.setText(controller.GetMagazine().GetCustomers().get(i).GetEmail());
                streetNumberField.setText(controller.GetMagazine().GetCustomers().get(i).GetAddress().GetStreetNumber() + "");
                streetNameField.setText(controller.GetMagazine().GetCustomers().get(i).GetAddress().GetStreetName());
                suburbField.setText(controller.GetMagazine().GetCustomers().get(i).GetAddress().GetSuburb());
                postcodeField.setText(controller.GetMagazine().GetCustomers().get(i).GetAddress().GetPostcode() + "");
            }
        }
        
        //Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(emailLabel, 0, 1);
        GridPane.setConstraints(emailField, 1, 1);
        GridPane.setConstraints(streetNumberLabel, 0, 2);
        GridPane.setConstraints(streetNumberField, 1, 2);
        GridPane.setConstraints(streetNameLabel, 0, 3);
        GridPane.setConstraints(streetNameField, 1, 3);
        GridPane.setConstraints(suburbLabel, 0, 4);
        GridPane.setConstraints(suburbField, 1, 4);
        GridPane.setConstraints(postcodeLabel, 0, 5);
        GridPane.setConstraints(postcodeField, 1, 5);
        GridPane.setConstraints(nextButton, 0, 6);
        
        grid.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, 
                streetNumberLabel, streetNumberField, streetNameLabel, streetNameField, 
                suburbLabel, suburbField, postcodeLabel, postcodeField, nextButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 300);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing a paying customer 
    * @param controller
    * @param oldName
    * @param newName
    * @param email
    * @param streetNumber
    * @param suburb
    * @param streetName
    * @param postcode
    */
    public void EditPayingCustomer(MagazineServiceController controller, String oldName, String newName, String email, 
            String streetNumber, String streetName, String suburb, String postcode){
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Paying Customer");
        
        Label billingInfoLabel = new Label();
        billingInfoLabel.setStyle("-fx-text-fill: #9370DB");
        TextField billingInfoField = new TextField();
        Label billingTypeLabel = new Label("Billing Type");
        billingTypeLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> billingBox = new ChoiceBox<>();
                
        billingBox.getItems().add("Credit Card");
        billingBox.getItems().add("Direct Debit");
        
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(oldName)){
                if(((PayingCustomer)controller.GetMagazine().GetCustomers().get(i)).GetPaymentMethod().GetPaymentType() == PaymentMethod.Type.CREDITCARD){
                    billingBox.setValue("Credit Card");
                    billingInfoLabel.setText("Credit Card Number");
                }
                if(((PayingCustomer)controller.GetMagazine().GetCustomers().get(i)).GetPaymentMethod().GetPaymentType() == PaymentMethod.Type.DIRECTDEBIT){
                    billingBox.setValue("Direct Debit");
                    billingInfoLabel.setText("Bank Account Number");
                }
                billingInfoField.setText(((PayingCustomer)controller.GetMagazine().GetCustomers().get(i)).GetPaymentMethod().GetPaymentInformation());
            }
        }
        
        Button doneButton = new Button("Finish Editing");
        doneButton.setOnAction((ActionEvent evt) -> {
            
            if(!validation.isEmpty(billingInfoField.getText())){
                int number = Integer.parseInt(streetNumber);
                int post = Integer.parseInt(postcode);
                controller.EditPayingCustomer(oldName, newName, email, number, streetName, suburb, post, 
                    billingBox.getSelectionModel().selectedItemProperty().getValue(), billingInfoField.getText());
                window.close();
            }
            else{
                alertBox.AlertBox("'" + billingInfoLabel.getText() + "' field cannot be empty");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(billingTypeLabel, 0, 0);
        GridPane.setConstraints(billingBox, 1, 0);
                
        GridPane.setConstraints(billingInfoLabel, 0, 1);
        GridPane.setConstraints(billingInfoField, 1, 1);
        GridPane.setConstraints(doneButton, 0, 2);
        grid.getChildren().addAll(billingInfoLabel, billingInfoField);
        grid.getChildren().addAll(billingTypeLabel, billingBox, doneButton);
        
        billingBox.getSelectionModel().selectedItemProperty().addListener( (value, oldValue, newValue) -> {
            if("Credit Card".equals(newValue)){
                billingInfoLabel.setText("Credit Card Number"); 
            }

            if("Credit Card".equals(newValue) && "Direct Debit".equals(oldValue)){
                billingInfoLabel.setText("Credit Card Number");
                billingInfoField.setText("");
                grid.getChildren().remove(billingInfoLabel);
                grid.getChildren().remove(billingInfoField);
            }

            if("Direct Debit".equals(newValue)){
                billingInfoLabel.setText("Bank Account Number");
            }

            if("Direct Debit".equals(newValue) && "Credit Card".equals(oldValue)){
                billingInfoLabel.setText("Bank Account Number");
                billingInfoField.setText("");
                grid.getChildren().remove(billingInfoLabel);
                grid.getChildren().remove(billingInfoField);
            }

            grid.getChildren().addAll(billingInfoLabel, billingInfoField);
        });
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing an associate customer 
    * @param controller
    * @param oldName
    * @param newName
    * @param email
    * @param streetNumber
    * @param suburb
    * @param streetName
    * @param postcode
    */
    public void EditAssociateCustomer(MagazineServiceController controller, String oldName, String newName, 
            String email, String streetNumber, String streetName, String suburb, String postcode){
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Associate Customer");
        
        Label payerLabel = new Label("Select Paying Customer");
        payerLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> payerBox = new ChoiceBox<>();
                
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetType() == Customer.Type.PAYING){
                payerBox.getItems().add(controller.GetMagazine().GetCustomers().get(i).GetName());
            }
            
            if(controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(oldName)){
                payerBox.setValue(((AssociateCustomer)controller.GetMagazine().GetCustomers().get(i)).GetPayingCustomer().GetName());
            }
        }
        
        Button doneButton = new Button("Finish Editing");
        doneButton.setOnAction((ActionEvent evt) -> {
            
            if(payerBox.getSelectionModel().selectedItemProperty().getValue() != null){
                int number = Integer.parseInt(streetNumber);
                int post = Integer.parseInt(postcode);
                controller.EditAssociateCustomer(oldName, newName, email, number, streetName, suburb, 
                        post, payerBox.getSelectionModel().selectedItemProperty().getValue());
                window.close();
            }
            else{
                alertBox.AlertBox("Please select a Paying Customer");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(payerLabel, 0, 0);
        GridPane.setConstraints(payerBox, 1, 0);      
        GridPane.setConstraints(doneButton, 0, 1);
        
        grid.getChildren().addAll(payerLabel, payerBox, doneButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for adding a new supplement 
    * to the magazine service 
    * @param controller
    */
    public void AddSupplementWindow(MagazineServiceController controller){
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Supplement");
        
        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-text-fill: #9370DB");
        Label costLabel = new Label("Weekly Cost");
        costLabel.setStyle("-fx-text-fill: #9370DB");
        
        TextField nameField = new TextField();
        TextField costField = new TextField();
        
        Button addButton = new Button("Add New Supplement");
        addButton.setOnAction((ActionEvent evt) -> {
            
            if(!validation.isEmpty(nameField.getText())){
                if(!logicValidation.isDuplicateSupplementName(controller, nameField.getText())){
                    if(validation.isNumeric(costField.getText())){
                        if(validation.isPositive(costField.getText())){
                            int cost = Integer.parseInt(costField.getText());
                            controller.AddSupplement(nameField.getText(), cost);
                            window.close();
                        }
                        else{
                            alertBox.AlertBox(costLabel.getText() + " must be positive");
                        }
                    }
                    else{
                        alertBox.AlertBox("'" + costLabel.getText() + "' field cannot be empty and only contain numbers");
                    }
                }
                else{
                    alertBox.AlertBox("Supplement '" + nameField.getText() + "' already exists");
                }
            }
            else{
                alertBox.AlertBox("'" + nameLabel.getText() + "' field cannot be empty or contain numbers");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(costLabel, 0, 1);
        GridPane.setConstraints(costField, 1, 1);
        GridPane.setConstraints(addButton, 0, 2);
        
        grid.getChildren().addAll(nameLabel, nameField, costLabel, costField, addButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing a supplement 
    * @param controller
    * @param tree
    */
    public void EditSupplementWindow(MagazineServiceController controller, TreeView<String> tree){
        String name = tree.getSelectionModel().getSelectedItem().getValue();
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Supplement");
        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-text-fill: #9370DB");
        Label costLabel = new Label("Weekly Cost");
        costLabel.setStyle("-fx-text-fill: #9370DB");
        
        TextField nameField = new TextField();
        TextField costField = new TextField();
        
        Button finishButton = new Button("Finish Editting");
        finishButton.setOnAction((ActionEvent evt) -> {
            
            if(!validation.isEmpty(nameField.getText())){
                if(!logicValidation.isDuplicateSupplementName(controller, name, nameField.getText())){
                    if(validation.isNumeric(costField.getText())){
                        if(validation.isPositive(costField.getText())){
                            int cost = Integer.parseInt(costField.getText());
                            controller.EditSupplement(name, nameField.getText(), cost);
                            controller.EditCustomerSupplement(name, nameField.getText(), cost);
                            window.close();
                        }
                        else{
                            alertBox.AlertBox(costLabel.getText() + " must be positive");
                        }
                    }
                    else{
                        alertBox.AlertBox("'" + costLabel.getText() + "' field cannot be empty and only contain numbers");
                    }
                }
                else{
                    alertBox.AlertBox("Supplement Name '" + nameField.getText() + "' already exists");
                }
            }
            else{
                alertBox.AlertBox("'" + nameLabel.getText() + "' field cannot be empty");
            }
        });
        
        for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
            if(controller.GetMagazine().GetSupplements().get(i).GetName().equalsIgnoreCase(name)){
                nameField.setText(controller.GetMagazine().GetSupplements().get(i).GetName());
                costField.setText(controller.GetMagazine().GetSupplements().get(i).GetWeeklyCost() + "");
            }
        }
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(costLabel, 0, 1);
        GridPane.setConstraints(costField, 1, 1);
        GridPane.setConstraints(finishButton, 0, 2);
        
        grid.getChildren().addAll(nameLabel, nameField, costLabel, costField, finishButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing a specified customer 
    * supplement 
    * @param controller
    * @param name
    */
    public void EditCustomerSupplementsWindow(MagazineServiceController controller, String name){
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Customer Supplements");
        
        //Supplement Tree
        TreeView<String> supplementTree;
        TreeItem<String> supplementRoot, supplements;
        supplementRoot = new TreeItem<>();
        supplementRoot.setExpanded(true);
        supplements = AddStringBranch(name + "'s Supplements", supplementRoot);
        
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(controller.GetMagazine().GetCustomers().get(i).GetName().equalsIgnoreCase(name)){
                for(int j = 0; j < controller.GetMagazine().GetCustomers().get(i).GetSupplements().size(); j++){
                    AddStringBranch(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetName(), supplements);
                }
            }
        }
        
        supplementTree = new TreeView<>(supplementRoot);
        supplementTree.setShowRoot(false);
        
        //Buttons
        Button addButton = new Button("Add Supplement");
        addButton.setOnAction((ActionEvent evt) -> {
            if(logicValidation.SupplementsExist(controller)){
                AddCustomerSupplement(controller, name, supplements);
            }
            else{
                alertBox.AlertBox("No Supplements exist to add");
            }
        });
        
        Button deleteButton = new Button("Delete Supplement");
        deleteButton.setOnAction((ActionEvent evt) -> {
            if(supplementTree.getSelectionModel().selectedItemProperty().getValue() != null){
                if(!supplementTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase(name + "'s Supplements")){
                    DeleteCustomerSupplement(controller, name, supplementTree, supplements);
                }
                else{
                    alertBox.AlertBox("Please select a Supplement");
                }
            }
            else{
                alertBox.AlertBox("Please select a Supplement");
            }
        });
        
        Button editButton = new Button("Edit Supplement");
        editButton.setOnAction((ActionEvent evt) -> {
            if(supplementTree.getSelectionModel().selectedItemProperty().getValue() != null){
                if(!supplementTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase(name + "'s Supplements")){
                    EditCustomerSupplement(controller, name, supplementTree, supplements);
                }
                else{
                    alertBox.AlertBox("Please select a Supplement");
                }
            }
            else{
                alertBox.AlertBox("Please select a Supplement");
            }
        });
        
        Button doneButton = new Button("Finish Editing");
        doneButton.setOnAction((ActionEvent evt) -> window.close());
        
        //Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(supplementTree, 0, 0, 2, 1);
        
        GridPane.setConstraints(addButton, 0, 1, 1, 1);
        GridPane.setConstraints(editButton, 1, 1, 1, 1);
        GridPane.setConstraints(deleteButton, 0, 2, 1, 1);
        GridPane.setConstraints(doneButton, 1, 2, 1, 1);
        
        grid.getChildren().addAll(supplementTree, addButton, editButton, deleteButton, doneButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 300);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for adding an existing 
    * supplement for a specified customer 
    * @param controller
    * @param customerName
    * @param supplements
    */
    public void AddCustomerSupplement(MagazineServiceController controller, String customerName, TreeItem<String> supplements){
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Supplement to Customer");
        
        Label supplementLabel = new Label("Select Supplement");
        supplementLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> supplementBox = new ChoiceBox<>();
        
        Label subscriptionLabel = new Label("Subscription Type");
        subscriptionLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> subscriptionBox = new ChoiceBox<>();
                
        for(int i = 0; i < controller.GetMagazine().GetSupplements().size(); i++){
            supplementBox.getItems().add(controller.GetMagazine().GetSupplements().get(i).GetName());
        }
        
        subscriptionBox.getItems().add("Weekly");
        subscriptionBox.getItems().add("Fortnightly");
        
        Button addButton = new Button("Add Supplement to Customer");
        addButton.setOnAction((ActionEvent evt) -> {
            
            if(supplementBox.getSelectionModel().selectedItemProperty().getValue() != null){
                if(!logicValidation.isDuplicateCustomerSupplement(controller, customerName, supplementBox.getSelectionModel().selectedItemProperty().getValue())){
                    if(subscriptionBox.getSelectionModel().selectedItemProperty().getValue() != null){
                        controller.AddSupplementToCustomer(customerName, supplementBox.getSelectionModel().selectedItemProperty().getValue(), 
                        subscriptionBox.getSelectionModel().selectedItemProperty().getValue());
                        RefreshCustomerSupplementTree(controller, customerName, supplements);
                        window.close();
                    }
                    else{
                        alertBox.AlertBox("Please select a Subscription Type");
                    }
                }
                else{
                    alertBox.AlertBox("Customer is already subscribed to '" + supplementBox.getSelectionModel().selectedItemProperty().getValue() + "'");
                }
            }
            else{
                alertBox.AlertBox("Please select a Supplement");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(supplementLabel, 0, 0);
        GridPane.setConstraints(supplementBox, 1, 0);
        
        GridPane.setConstraints(subscriptionLabel, 0, 1);
        GridPane.setConstraints(subscriptionBox, 1, 1);
                
        GridPane.setConstraints(addButton, 0, 2);
        
        grid.getChildren().addAll(supplementLabel, supplementBox, subscriptionLabel, subscriptionBox, addButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing an existing 
    * supplement for a specified customer 
    * @param controller
    * @param customerName
    * @param tree
    * @param supplements
    */
    public void EditCustomerSupplement(MagazineServiceController controller, String customerName, TreeView<String> tree, TreeItem<String> supplements){
        
        String supplementName = tree.getSelectionModel().getSelectedItem().getValue();
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Supplement");
        
        Label subscriptionLabel = new Label("Subscription Type: ");
        subscriptionLabel.setStyle("-fx-text-fill: #9370DB");
        ChoiceBox<String> subscriptionBox = new ChoiceBox<>();
        
        subscriptionBox.getItems().add("Weekly");
        subscriptionBox.getItems().add("Fortnightly");
        
        for(int i = 0; i < controller.GetMagazine().GetCustomers().size(); i++){
            if(customerName.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetName())){
                for(int j = 0; j < controller.GetMagazine().GetCustomers().get(i).GetSupplements().size(); j++){
                    if(supplementName.equalsIgnoreCase(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetName())){
                        
                        if(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetSubscriptionType() == Supplement.Type.WEEKLY){
                            subscriptionBox.setValue("Weekly");
                        }

                        if(controller.GetMagazine().GetCustomers().get(i).GetSupplements().get(j).GetSubscriptionType() == Supplement.Type.FORTNIGHTLY){
                            subscriptionBox.setValue("Fortnightly");
                        }
                    }
                }
            }
        }
        
        Button doneButton = new Button("Finish Editing");
        doneButton.setOnAction((ActionEvent evt) -> {
            if(subscriptionBox.getSelectionModel().selectedItemProperty().getValue() != null){
                controller.EditCustomerSupplement(customerName, supplementName, subscriptionBox.getSelectionModel().selectedItemProperty().getValue());
                RefreshCustomerSupplementTree(controller, customerName, supplements);
                window.close();
            }
            else{
                alertBox.AlertBox("Please select a Subscription Type");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(subscriptionLabel, 0, 0);
        GridPane.setConstraints(subscriptionBox, 1, 0);
                
        GridPane.setConstraints(doneButton, 0, 1);
        
        grid.getChildren().addAll(subscriptionLabel, subscriptionBox, doneButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 200);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for deleting an existing 
    * supplement from a specified customer 
    * @param controller
    * @param customerName
    * @param tree
    * @param supplements
    */
    public void DeleteCustomerSupplement(MagazineServiceController controller, String customerName, TreeView<String> tree, TreeItem<String> supplements){
        String name = tree.getSelectionModel().getSelectedItem().getValue();
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete Supplement");
        
        Label sureLabel = new Label("Delete " + name + "?");
        sureLabel.setStyle("-fx-text-fill: #9370DB");
        
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction((ActionEvent evt) -> {
            controller.RemoveSupplementFromCustomer(customerName, name);
            RefreshCustomerSupplementTree(controller, customerName, supplements);
            window.close();
        });
        noButton.setOnAction((ActionEvent evt) -> window.close());
        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(yesButton, noButton);
        hbox.setStyle("-fx-background-color: #282828");
        
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(sureLabel, hbox);
        box.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(box, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for deleting an existing 
    * specified customer 
    * @param controller
    * @param tree
    */
    public void DeleteCustomerWindow(MagazineServiceController controller, TreeView<String> tree){
        String name = tree.getSelectionModel().getSelectedItem().getValue();
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete Customer");
        
        Label sureLabel = new Label("Delete " + name + "?");
        sureLabel.setStyle("-fx-text-fill: #9370DB");
        
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction((ActionEvent evt) -> {
            controller.RemoveCustomer(name);
            window.close();
        });
        noButton.setOnAction((ActionEvent evt) -> window.close());
        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(yesButton, noButton);
        hbox.setStyle("-fx-background-color: #282828");
        
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(sureLabel, hbox);
        box.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(box, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for deleting an existing 
    * specified supplement 
    * @param controller
    * @param tree
    */
    public void DeleteSupplementWindow(MagazineServiceController controller, TreeView<String> tree){
        String name = tree.getSelectionModel().getSelectedItem().getValue();
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete Supplement");
        
        Label sureLabel = new Label("Delete " + name + "?");
        sureLabel.setStyle("-fx-text-fill: #9370DB");
        
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction((ActionEvent evt) -> {
            controller.RemoveSupplement(name);
            window.close();
        });
        noButton.setOnAction((ActionEvent evt) -> window.close());
        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(yesButton, noButton);
        hbox.setStyle("-fx-background-color: #282828");
        
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(sureLabel, hbox);
        box.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(box, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for editing magazine information
    * @param controller
    */
    public void EditMagazineWindow(MagazineServiceController controller){
        
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Magazine Information");
        
        Label costLabel = new Label("Weekly Cost");
        costLabel.setStyle("-fx-text-fill: #9370DB");
        TextField costField = new TextField();
        
        Button doneButton = new Button("Finish Editing");
        doneButton.setOnAction((ActionEvent evt) -> {
            if(validation.isNumeric(costField.getText())){
                if(validation.isPositive(costField.getText())){
                    int cost = Integer.parseInt(costField.getText());  
                    controller.EditMagazineInformation(cost);
                    window.close();
                }
                else{
                    alertBox.AlertBox(costLabel.getText() + " must be positive");
                }
            }
            else{
                alertBox.AlertBox("'" + costLabel.getText() + "' field cannot be empty and only contain numbers");
            }
        });
        
        costField.setText(controller.GetMagazine().GetWeeklyCost() + "");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GridPane.setConstraints(costLabel, 0, 0, 1, 1);
        GridPane.setConstraints(costField, 1, 0, 1, 1);
        GridPane.setConstraints(doneButton, 0, 1, 1, 1);
        
        grid.getChildren().addAll(costLabel, costField, doneButton);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(grid, 400, 150);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
    * Opens a new window and collects input for saving a magazine 
    * service to file, or confirming that a loaded magazine service is saved
    * @param controller
    */
    public void SaveMagazine(MagazineServiceController controller){
        if(controller.GetIsSaved()){
            controller.SaveMagazine(controller.GetFileName());
            alertBox.AlertBox("Magazine Service '" + controller.GetFileName() + "' saved to file");
        }
        else{
            Stage window = new Stage();
            Scene scene;

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Save Magazine Service");
            
            Label nameLabel = new Label("Magazine Service Name");
            nameLabel.setStyle("-fx-text-fill: #9370DB");
            TextField nameField = new TextField();
            Button saveButton = new Button("Save");
            
            saveButton.setOnAction((ActionEvent evt) -> {
                if(!validation.isEmpty(nameField.getText())){
                    controller.SaveMagazine(nameField.getText());
                    alertBox.AlertBox("Magazine Service '" + nameField.getText() + "' saved to file");
                    window.close();
                }
                else{
                    alertBox.AlertBox("'" + nameLabel.getText() + "' field cannot be empty");
                }
            });

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(10);
            grid.setHgap(10);

            GridPane.setConstraints(nameLabel, 0, 0, 1, 1);
            GridPane.setConstraints(nameField, 1, 0, 1, 1);
            GridPane.setConstraints(saveButton, 0, 1, 1, 1);

            grid.getChildren().addAll(nameLabel, nameField, saveButton);
            grid.setAlignment(Pos.CENTER);
            grid.setStyle("-fx-background-color: #282828");
            
            scene = new Scene(grid, 400, 150);
            window.setScene(scene);
            window.showAndWait();
        }
    }
    
    /**
    * Alerts the user that a new magazine service was created
    * @param controller
    */
    public void CreateMagazine(MagazineServiceController controller){
        controller.CreateNewMagazine();
        alertBox.AlertBox("New Magazine Service created");
    }
    
    /**
    * Opens a file chooser to select a saved magazine service file 
    * and load it into the program
    * @param controller
    * @param stage
    */
    public void LoadMagazine(MagazineServiceController controller, Stage stage){
        
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new ExtensionFilter("BIN Files", "*.bin"));
        File file = chooser.showOpenDialog(stage);
        if(file != null){
            controller.LoadMagazine(file.getName());
            alertBox.AlertBox("'" + file.getName() + "' successfully loaded");
        }
    }
}

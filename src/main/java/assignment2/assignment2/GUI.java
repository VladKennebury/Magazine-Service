package assignment2.assignment2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - GUI.Java
 * 
 * Purpose - Present a graphical user interface for a magazine service
 * system, allowing viewing, editing and creation of customer/supplement
 * data.
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 * Assumptions - Magazines are saved and read in only a .BIN files
 * 
 */
public class GUI extends Application {
    
    /// Tree showing customer list
    private TreeView<String> customerTree;
    /// Root and node for customer tree
    private TreeItem<String> customerRoot, customers;
    /// Tree showing supplement list
    private TreeView<String> supplementTree;
    /// Root and node for supplement tree
    private TreeItem<String> supplementRoot, supplements;
    /// Tree showing information of customers and supplements
    private TreeView<String> informationTree;
    /// Root and node for information tree
    private TreeItem<String> informationRoot, information;
    /// Tree showing magazine information
    private TreeView<String> magazineTree;
    /// Root and node for magazine tree
    private TreeItem<String> magazineRoot, magazineInfo;
    /// Tree showing customer list in edit scene
    private TreeView<String> editCustomerTree;
    /// Root and node for edit customer tree
    private TreeItem<String> editCustomerRoot, editCustomers;
    /// Tree showing supplement list in edit scene
    private TreeView<String> editSupplementTree;
    /// Root and node for edit supplement tree
    private TreeItem<String> editSupplementRoot, editSupplements;
    /// Home, view, edit and create scenes for the system
    private Scene homePageScene, viewScene, editScene, createScene;
    /// Handles events after button presses
    private EventHandler handler = new EventHandler();
    /// Controls magazine service logic
    private MagazineServiceController magazineServiceController = new MagazineServiceController();
    /// Alertbox for displaying validations/notifications
    private AlertBox alertBox = new AlertBox();
    
    /**
    * Load and display the graphical user interface
    * @param primaryStage
    */
    @Override
    public void start(Stage primaryStage) {     
        // Loads a demonstration magazine
        magazineServiceController.Demonstration();
        // Displays student information
        magazineServiceController.DisplayStudentDetails();
        
        //HomePage
        HomePage(primaryStage);
        //View Scene
        View(primaryStage);
        //Edit Scene
        Edit(primaryStage);
        //Create Scene
        Create(primaryStage);
        
        primaryStage.setScene(homePageScene);
        primaryStage.setTitle("Magazine Service");
        primaryStage.show();
   }
    
    /**
    * Used to launch the GUI
    */
    public void Run(){
        launch();
    }
    
    /**
    * Displays HomePage allowing selection in which mode to start the program
    * @param primaryStage
    */
    public void HomePage(Stage primaryStage){
        HBox layout1 = new HBox(20);
        layout1.getChildren().addAll(NavigationPanel(primaryStage));
        layout1.setAlignment(Pos.CENTER);
        layout1.setStyle("-fx-background-color: #282828");
        homePageScene = new Scene(layout1, 1000, 500);
    }
    
    /**
    * Displays list of customers, list of supplements, magazine information 
    * and an information panel
    * @param primaryStage
    */
    public void View(Stage primaryStage){
        //List of Customers
        customerRoot = new TreeItem<>();
        customerRoot.setExpanded(true);
        customers = handler.AddStringBranch("List of Customers", customerRoot);
        
        for(int i = 0; i < magazineServiceController.GetMagazine().GetCustomers().size(); i++){
            handler.AddStringBranch(magazineServiceController.GetMagazine().GetCustomers().get(i).GetName(), customers);
        }
        
        customerTree = new TreeView<>(customerRoot);
        customerTree.setShowRoot(false);
        customerTree.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
            if(!newVal.getValue().equalsIgnoreCase("List of Customers")){
                try {
                    handler.ViewCustomerTree(newVal.getValue(), information, magazineServiceController);
                } catch (InterruptedException ex) {
                }
            }
        });
        
        //List of Supplements
        supplementRoot = new TreeItem<>();
        supplementRoot.setExpanded(true);
        supplements = handler.AddStringBranch("List of Supplements", supplementRoot);
        
        for(int i = 0; i < magazineServiceController.GetMagazine().GetSupplements().size(); i++){
            handler.AddStringBranch(magazineServiceController.GetMagazine().GetSupplements().get(i).GetName(), supplements);
        }
        
        supplementTree = new TreeView<>(supplementRoot);
        supplementTree.setShowRoot(false);
        supplementTree.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
            if(!newVal.getValue().equalsIgnoreCase("List of Supplements")){
                handler.ViewSupplementTree(newVal.getValue(), information, magazineServiceController);
            }
        });
        
        //Information Panel
        informationRoot = new TreeItem<>();
        informationRoot.setExpanded(true);
        information = handler.AddStringBranch("Information Panel", informationRoot);
        
        informationTree = new TreeView<>(informationRoot);
        informationTree.setShowRoot(false);
        
        //Magazine Info
        magazineRoot = new TreeItem<>();
        magazineRoot.setExpanded(true);
        magazineInfo = handler.AddStringBranch("Magazine Information", magazineRoot);
        handler.AddStringBranch("Weekly Cost - $" + magazineServiceController.GetMagazine().GetWeeklyCost(), magazineInfo);
        
        magazineTree = new TreeView<>(magazineRoot);
        magazineTree.setShowRoot(false);
        
        //Grid
        GridPane viewGrid = new GridPane();
        viewGrid.setPadding(new Insets(10, 10, 10, 10));
        viewGrid.setVgap(10);
        viewGrid.setHgap(10);
        
        GridPane.setConstraints(NavigationPanel(primaryStage), 0, 0);
        GridPane.setConstraints(customerTree, 0, 1);
        GridPane.setConstraints(supplementTree, 0, 2);
        GridPane.setConstraints(informationTree, 1, 1, 1, 2);
        GridPane.setConstraints(magazineTree, 2, 1, 1, 1);
        
        viewGrid.getChildren().addAll(customerTree, supplementTree, informationTree, magazineTree);
        viewGrid.setAlignment(Pos.CENTER);
        
        //VBox
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(NavigationPanel(primaryStage), viewGrid);
        box.setStyle("-fx-background-color: #282828");
        viewScene = new Scene(box, 1000, 500);
    }
    
    /**
    * Displays list of customers, list of supplements, magazine information 
    * to add, delete and edit the information
    * @param primaryStage
    */
    public void Edit(Stage primaryStage){
        Button editHomeButton = new Button("Home");
        editHomeButton.setOnAction((ActionEvent evt) -> {
            primaryStage.setScene(homePageScene);
        });
        
        //Edit Customer Tree
        editCustomerRoot = new TreeItem<>();
        editCustomerRoot.setExpanded(true);
        editCustomers = handler.AddStringBranch("Select Customer", editCustomerRoot);
        
        for(int i = 0; i < magazineServiceController.GetMagazine().GetCustomers().size(); i++){
            handler.AddStringBranch(magazineServiceController.GetMagazine().GetCustomers().get(i).GetName(), editCustomers);
        }
        
        editCustomerTree = new TreeView<>(editCustomerRoot);
        editCustomerTree.setShowRoot(false);
        
        //Edit Supplement Tree
        editSupplementRoot = new TreeItem<>();
        editSupplementRoot.setExpanded(true);
        editSupplements = handler.AddStringBranch("Select Supplement", editSupplementRoot);
        
        for(int i = 0; i < magazineServiceController.GetMagazine().GetSupplements().size(); i++){
            handler.AddStringBranch(magazineServiceController.GetMagazine().GetSupplements().get(i).GetName(), editSupplements);
        }
        
        editSupplementTree = new TreeView<>(editSupplementRoot);
        editSupplementTree.setShowRoot(false);
        
        //Magazine Tree
        TreeView<String> editMagazineTree;
        TreeItem<String> editMagazineRoot, editMagazine;
        editMagazineRoot = new TreeItem<>();
        editMagazineRoot.setExpanded(true);
        editMagazine = handler.AddStringBranch("Select Magazine Information", editMagazineRoot);
        
        handler.AddStringBranch("Weekly Magazine Cost", editMagazine);
        
        editMagazineTree = new TreeView<>(editMagazineRoot);
        editMagazineTree.setShowRoot(false);
        
        //Buttons
        Button editCustomerSupplementsButton = new Button("Edit Customer Supplements");
        editCustomerSupplementsButton.setOnAction((ActionEvent evt) -> {
            if(editCustomerTree.getSelectionModel().getSelectedItem() != null){
                if(!editCustomerTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase("Select Customer")){
                    handler.EditCustomerSupplementsWindow(magazineServiceController, editCustomerTree.getSelectionModel().getSelectedItem().getValue());
                    handler.RefreshSupplementTrees(magazineServiceController, editSupplements, supplements, customers, information);
                }
                else{
                    alertBox.AlertBox("Please select a Customer");
                }
            }
            else{
                alertBox.AlertBox("Please select a Customer");
            }
        });
        
        Button addCustomerButton = new Button("Add Customer");
        addCustomerButton.setOnAction((ActionEvent evt) -> {
            handler.AddCustomerWindow(magazineServiceController);
            handler.RefreshCustomerTrees(magazineServiceController, editCustomers, customers, information);
        });
        
        Button deleteCustomerButton = new Button("Delete Customer");
        deleteCustomerButton.setOnAction((ActionEvent evt) -> {
            if(editCustomerTree.getSelectionModel().getSelectedItem() != null){
                if(!editCustomerTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase("Select Customer")){
                    handler.DeleteCustomerWindow(magazineServiceController, editCustomerTree);
                    handler.RefreshCustomerTrees(magazineServiceController, editCustomers, customers, information);
                }
                else{
                    alertBox.AlertBox("Please select a Customer");
                }
            }
            else{
                alertBox.AlertBox("Please select a Customer");
            }
        });
        
        Button editCustomerButton = new Button("Edit Customer");
        editCustomerButton.setOnAction((ActionEvent evt) -> {
            if(editCustomerTree.getSelectionModel().getSelectedItem() != null){
                if(!editCustomerTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase("Select Customer")){
                    handler.EditCustomerWindow(magazineServiceController, editCustomerTree);
                    handler.RefreshCustomerTrees(magazineServiceController, editCustomers, customers, information);
                }
                else{
                    alertBox.AlertBox("Please select a Customer");
                }
            }
            else{
                alertBox.AlertBox("Please select a Customer");
            }
        });
        
        Button addSupplementButton = new Button("Add Supplement");
        addSupplementButton.setOnAction((ActionEvent evt) -> {
            handler.AddSupplementWindow(magazineServiceController);
            handler.RefreshSupplementTrees(magazineServiceController, editSupplements, supplements, customers, information);
        });
        
        Button deleteSupplementButton = new Button("Delete Supplement");
        deleteSupplementButton.setOnAction((ActionEvent evt) -> {
            if(editSupplementTree.getSelectionModel().getSelectedItem() != null){
                if(!editSupplementTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase("Select Supplement")){
                    handler.DeleteSupplementWindow(magazineServiceController, editSupplementTree);
                    handler.RefreshSupplementTrees(magazineServiceController, editSupplements, supplements, customers, information);
                }
                else{
                    alertBox.AlertBox("Please select a Supplement");
                }
            }
            else{
                    alertBox.AlertBox("Please select a Supplement");
            }
        });
        
        Button editSupplementButton = new Button("Edit Supplement");
        editSupplementButton.setOnAction((ActionEvent evt) -> {
            if(editSupplementTree.getSelectionModel().getSelectedItem() != null){
                if(!editSupplementTree.getSelectionModel().getSelectedItem().getValue().equalsIgnoreCase("Select Supplement")){
                    handler.EditSupplementWindow(magazineServiceController, editSupplementTree);
                    handler.RefreshSupplementTrees(magazineServiceController, editSupplements, supplements, customers, information);
                }
                else{
                    alertBox.AlertBox("Please select a Supplement");
                }
            }
            else{
                alertBox.AlertBox("Please select a Supplement");
            }
        });
        
        Button editMagazineButton = new Button("Edit Magazine Information");
        editMagazineButton.setOnAction((ActionEvent evt) -> {
            handler.EditMagazineWindow(magazineServiceController);
            handler.RefreshMagazineTrees(magazineServiceController, magazineInfo, information);
        });
        
        //Grid
        GridPane editGrid = new GridPane();
        editGrid.setPadding(new Insets(10, 10, 10, 10));
        editGrid.setVgap(10);
        editGrid.setHgap(10);
        
        GridPane.setConstraints(NavigationPanel(primaryStage), 0, 0, 2, 1);
        
        GridPane.setConstraints(editCustomerTree, 0, 1, 2, 1);
        GridPane.setConstraints(editSupplementTree, 2, 1, 2, 1);
        
        GridPane.setConstraints(editMagazineTree, 4, 1, 1, 1);
        
        GridPane.setConstraints(editCustomerButton, 0, 2, 1, 1);
        GridPane.setConstraints(deleteCustomerButton, 1, 2, 1, 1);
        
        GridPane.setConstraints(editSupplementButton, 2, 2, 1, 1);
        GridPane.setConstraints(deleteSupplementButton, 3, 2, 1, 1);
        
        GridPane.setConstraints(addCustomerButton, 0, 3, 1, 1);
        GridPane.setConstraints(addSupplementButton, 2, 3, 1, 1);
        
        GridPane.setConstraints(editCustomerSupplementsButton, 1, 3, 1, 1);
        
        GridPane.setConstraints(editMagazineButton, 4, 2, 1, 1);
        
        editGrid.getChildren().addAll(addCustomerButton, deleteCustomerButton, editCustomerButton, 
                addSupplementButton, deleteSupplementButton, editCustomerSupplementsButton, editSupplementButton, 
                editMagazineButton, editCustomerTree, editSupplementTree, editMagazineTree);
        editGrid.setAlignment(Pos.CENTER);

        //VBox
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(NavigationPanel(primaryStage), editGrid);
        box.setStyle("-fx-background-color: #282828");
        editScene = new Scene(box, 1000, 500);
    }
    
    /**
    * Displays scene for the option to create, load and save a magazine service
    * @param primaryStage
    */
    public void Create(Stage primaryStage){
        //Buttons
        Button createHomeButton = new Button("Home");
        createHomeButton.setOnAction((ActionEvent evt) -> {
            primaryStage.setScene(homePageScene);
        });
        
        Button createButton = new Button("Create Magazine");
        createButton.setOnAction((ActionEvent evt) -> {
            handler.CreateMagazine(magazineServiceController);
            handler.RefreshCustomerTrees(magazineServiceController, editCustomers, customers, information);
            handler.RefreshSupplementTrees(magazineServiceController, editSupplements, supplements, customers, information);
            handler.RefreshMagazineTrees(magazineServiceController, magazineInfo, information);
            primaryStage.setScene(editScene);
        });
        
        Button saveButton = new Button("Save Magazine");
        saveButton.setOnAction((ActionEvent evt) -> {
            handler.SaveMagazine(magazineServiceController);
        });
        
        Button loadButton = new Button("Load Magazine");
        loadButton.setOnAction((ActionEvent evt) -> {
            handler.LoadMagazine(magazineServiceController, primaryStage);
            handler.RefreshCustomerTrees(magazineServiceController, editCustomers, customers, information);
            handler.RefreshSupplementTrees(magazineServiceController, editSupplements, supplements, customers, information);
            handler.RefreshMagazineTrees(magazineServiceController, magazineInfo, information);
        });
        
        //Grid
        GridPane createGrid = new GridPane();
        createGrid.setPadding(new Insets(10, 10, 10, 10));
        createGrid.setVgap(20);
        createGrid.setHgap(10);
        
        GridPane.setConstraints(NavigationPanel(primaryStage), 0, 0, 2, 1);
        GridPane.setConstraints(createButton, 0, 1, 1, 1);
        GridPane.setConstraints(saveButton, 0, 2, 1, 1);
        GridPane.setConstraints(loadButton, 0, 3, 1, 1);
        
        createGrid.getChildren().addAll(createButton, saveButton, loadButton);
        createGrid.setAlignment(Pos.CENTER);
        
        //VBox
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(NavigationPanel(primaryStage), createGrid);
        box.setStyle("-fx-background-color: #282828");
        createScene = new Scene(box, 1000, 500);
    }
    
    /**
    * Displays the navigation panel, showing the title, mode and
    * buttons to change scenes
    * @param primaryStage
    */
    public VBox NavigationPanel(Stage primaryStage){
        Label title = new Label("Magazine Service");
        Label mode = new Label();
        
        if(primaryStage.getScene() == homePageScene){
            mode.setText("Home Page");
        }
        else if(primaryStage.getScene() == viewScene){
            mode.setText("View Mode");
        }
        else if(primaryStage.getScene() == editScene){
            mode.setText("Edit Mode");
        }
        else if(primaryStage.getScene() == createScene){
            mode.setText("Create Mode");
        }
        
        //Buttons
        Button viewButton = new Button("View");
        viewButton.setStyle("-fx-background-color: #4169E1; -fx-font-weight: bold;");
        viewButton.setOnAction((ActionEvent evt) -> {
            primaryStage.setScene(viewScene);
        });
        
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #4169E1; -fx-font-weight: bold;");
        editButton.setOnAction((ActionEvent evt) -> {
            primaryStage.setScene(editScene);
        });
        
        Button createButton = new Button("Create");
        createButton.setStyle("-fx-background-color: #4169E1; -fx-font-weight: bold;");
        createButton.setOnAction((ActionEvent evt) -> {
            primaryStage.setScene(createScene);
        });
        
        //HBox
        HBox layout1 = new HBox(20);
        layout1.getChildren().addAll(viewButton, createButton, editButton);
        layout1.setAlignment(Pos.CENTER);
        
        //VBox
        VBox layout2 = new VBox(10);
        title.setStyle("-fx-text-fill: #8A2BE2; -fx-font-size: 16px; -fx-font-weight: bold;");
        mode.setStyle("-fx-text-fill: #9370DB");
        layout2.getChildren().addAll(title, mode, layout1);
        layout2.setAlignment(Pos.CENTER);
        
        return layout2;
    }
}

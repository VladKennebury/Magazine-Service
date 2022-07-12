package assignment2.assignment2;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;

/**
 * Title - ICT373 Assignment 2
 * 
 * File Name - AlertBox.Java
 * 
 * Purpose - Displays a specified message in a new window as a notification
 * 
 * Date - 29/5/2022
 * 
 * @author Vladislav Kennebury
 * 
 */
public class AlertBox {
    
    /**
    * Creates a new window to alert the user via a specified message 
    * depending on the alert
    * @param information
    */
    public void AlertBox(String information){
        Stage window = new Stage();
        Scene scene;
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert");
        
        Label alertLabel = new Label(information);
        alertLabel.setStyle("-fx-text-fill: #DC143C; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        Button okButton = new Button("Okay");
        okButton.setOnAction((ActionEvent evt) -> window.close());
        
        VBox box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(alertLabel, okButton);
        box.setStyle("-fx-background-color: #282828");
        
        scene = new Scene(box, 500, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}

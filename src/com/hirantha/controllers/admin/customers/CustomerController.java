package com.hirantha.controllers.admin.customers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    public AnchorPane basePane;
    @FXML
    private Button btnBtn;

    @FXML
    private ScrollPane customerRowScrollPane;

    @FXML
    private VBox rowContainer;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        customerRowScrollPane.

        btnBtn.setOnMouseClicked(e -> {
            try {
                AnchorPane row = FXMLLoader.load(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customer_row.fxml"));
                rowContainer.getChildren().add(row);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}

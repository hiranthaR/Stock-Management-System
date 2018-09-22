package com.hirantha.controllers.admin.customers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private VBox rowsContainer;

    @FXML
    private AnchorPane profileContainer;

    private CustomerProfileController customerProfileController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //load and get the controller for profile showing pane
        FXMLLoader profileFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customer_profile.fxml"));
        customerProfileController = profileFxmlLoader.getController();
        try {
            profileContainer.getChildren().add(profileFxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnBtn.setOnMouseClicked(e -> {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customer_row.fxml"));
                AnchorPane row = fxmlLoader.load();
                CustomerRowController rowController = fxmlLoader.getController();
                rowController.setParentController(this);
                rowsContainer.getChildren().add(row);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public CustomerProfileController getCustomerProfileController() {
        return customerProfileController;
    }
}

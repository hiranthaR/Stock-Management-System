package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInRight;
import com.hirantha.controllers.admin.DashboardController;
import com.hirantha.models.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private DashboardController parentController;

    public AnchorPane basePane;
    @FXML
    private Button btnBtn;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane profileContainer;


    @FXML
    private Button btnNewCustomer;


    private CustomerProfileController customerProfileController;
    private NewCustomerController newCustomerController;
    private AnchorPane newCustomerPane;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //load and get the controller for profile showing pane
        FXMLLoader profileFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customer_profile.fxml"));
        customerProfileController = profileFxmlLoader.getController();

        FXMLLoader newCustomerFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/costomers/new_customer.fxml"));
        newCustomerController = newCustomerFxmlLoader.getController();


        try {
            profileContainer.getChildren().add(profileFxmlLoader.load());
            newCustomerPane = newCustomerFxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnBtn.setOnMouseClicked(e -> {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customer_row.fxml"));
                AnchorPane row = fxmlLoader.load();
                rowsContainer.getChildren().add(row);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        btnNewCustomer.setOnMouseClicked(e -> {


            if (!((StackPane) basePane.getParent()).getChildren().contains(newCustomerPane)) {
                ((StackPane) basePane.getParent()).getChildren().add(newCustomerPane);
            } else {
                System.out.println("already");
            }
            newCustomerPane.toFront();
            FadeIn animation = new FadeIn(newCustomerPane);
            animation.setSpeed(3);
//            animation.getTimeline().setOnFinished(ex -> ((StackPane)basePane.getParent()).getChildren().remove(basePane));
            animation.play();

//            slideOutRight.play();


        });

    }

}

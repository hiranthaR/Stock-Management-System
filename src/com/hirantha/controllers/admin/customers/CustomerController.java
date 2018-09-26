package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeIn;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.models.data.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    public AnchorPane basePane;
    @FXML
    private Button btnBtn;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane profileContainer;


    @FXML
    private Button btnNewCustomer;


    private AnchorPane newCustomerPane;
    private CustomerProfileController customerProfileController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            //load and get the controller for profile showing pane
            FXMLLoader profileFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/customer_profile.fxml"));
            profileContainer.getChildren().add(profileFxmlLoader.load());
            customerProfileController = profileFxmlLoader.getController();

            //new customer view
            FXMLLoader newCustomerFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/new_customer.fxml"));
            newCustomerPane = newCustomerFxmlLoader.load();

            //add rows
            readRows();

        } catch (IOException e1) {
            e1.printStackTrace();
        }


        btnBtn.setOnMouseClicked(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/customer_row.fxml"));
                AnchorPane row = fxmlLoader.load();
                fxmlLoader.<CustomerRowController>getController().init(new Customer("cs1222", true, "Mr", "Hirantha Rathnayake", "ginipenda,Kalugamuwa.", "0716203812", 1), customerProfileController);
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

    void readRows() throws IOException {
        List<Customer> customers = CustomerQueries.getInstance().getCustomers();

        for (Customer customer : customers) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/customer_row.fxml"));
            AnchorPane row = fxmlLoader.load();
            fxmlLoader.<CustomerRowController>getController().init(customer, customerProfileController);
            rowsContainer.getChildren().add(row);
        }


    }

}

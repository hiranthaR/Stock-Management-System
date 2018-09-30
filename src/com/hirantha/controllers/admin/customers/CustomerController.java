package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeIn;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.models.data.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private TextField txtSearch;

    @FXML
    private Button btnNewCustomer;

    private List<Customer> customers;


    private AnchorPane newCustomerPane;
    private AnchorPane profilePane;
    private CustomerProfileController customerProfileController;
    private NewCustomerController newCustomerController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            //load and get the controller for profile showing pane
            FXMLLoader profileFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/customer_profile.fxml"));
            profilePane = profileFxmlLoader.load();
            profileContainer.getChildren().add(profilePane);
            customerProfileController = profileFxmlLoader.getController();
            customerProfileController.setCustomerController(CustomerController.this);

            //new customer view
            FXMLLoader newCustomerFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/new_customer.fxml"));
            newCustomerPane = newCustomerFxmlLoader.load();
            newCustomerController = newCustomerFxmlLoader.getController();
            newCustomerController.setCustomerController(CustomerController.this);

            //add rows
            customers = readRows();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        txtSearch.setOnKeyReleased(keyEvent -> {

            List<Customer> temp = new ArrayList<>();
            for (Customer customer : customers)
                if (customer.getName().contains(txtSearch.getText())) temp.add(customer);
            try {
                setRowViews(temp);
                if (txtSearch.getText().isEmpty()) readRows();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

        btnNewCustomer.setOnMouseClicked(e -> showNewCustomer());
    }

    List<Customer> readRows() throws IOException {

        List<Customer> customers = CustomerQueries.getInstance().getCustomers();
        setRowViews(customers);

        return customers;
    }

    void setRowViews(List<Customer> customers) throws IOException {
        rowsContainer.getChildren().clear();
        for (Customer customer : customers) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/customers/customer_row.fxml"));
            AnchorPane row = fxmlLoader.load();
            fxmlLoader.<CustomerRowController>getController().init(customer, customerProfileController);
            rowsContainer.getChildren().add(row);
        }

        if (customers.size() == 0) {
            profileContainer.getChildren().clear();
        } else {
            profileContainer.getChildren().clear();
            profileContainer.getChildren().add(profilePane);
            customerProfileController.init(customers.get(0));
        }
    }

    void showNewCustomer() {
        if (!((StackPane) basePane.getParent()).getChildren().contains(newCustomerPane)) {
            ((StackPane) basePane.getParent()).getChildren().add(newCustomerPane);
        }
        newCustomerPane.toFront();
        FadeIn animation = new FadeIn(newCustomerPane);
        animation.setSpeed(3);
        animation.play();
    }

    void showUpdateCustomer(Customer customer) {
        showNewCustomer();
        newCustomerController.initToUpdate(customer);
    }
}

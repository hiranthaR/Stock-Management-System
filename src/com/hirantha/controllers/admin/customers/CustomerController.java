package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeIn;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.fxmls.FXMLS;
import com.hirantha.models.data.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    public AnchorPane basePane;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane profileContainer;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label btnNewCustomer;

    private List<Customer> customers;


    private AnchorPane newCustomerPane;
    private AnchorPane profilePane;
    private CustomerProfileController customerProfileController;
    private NewCustomerController newCustomerController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            //load and get the controller for profile showing pane
            FXMLLoader profileFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Customers.CUSTOMER_PROFILE));
            profilePane = profileFxmlLoader.load();
            profileContainer.getChildren().add(profilePane);
            customerProfileController = profileFxmlLoader.getController();
            customerProfileController.setCustomerController(CustomerController.this);

            //new customer view
            FXMLLoader newCustomerFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Customers.NEW_CUSTOMER));
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

        btnNewCustomer.setOnMouseClicked(e -> showNewCustomer());
    }

    List<Customer> readRows() throws IOException {

        List<Customer> customers = CustomerQueries.getInstance().getCustomers();
        setRowViews(customers);

        return customers;
    }

    private void setRowViews(List<Customer> customers) throws IOException {
        rowsContainer.getChildren().clear();
        for (Customer customer : customers) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Customers.CUSTOMER_ROW));
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

    private void showNewCustomer() {
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

package com.hirantha.controllers.admin.customers;

import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.models.data.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerProfileController implements Initializable {

    @FXML
    private Label btnEdit;

    @FXML
    private Label btnDelete;

    @FXML
    private ImageView imgProfilePhote;

    @FXML
    private Label tvName;

    @FXML
    private Label tvId;

    @FXML
    private Label tvRank;

    @FXML
    private Label tvAddress;

    @FXML
    private Label tvTelephone;

    private Customer customer;

    private CustomerController customerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnEdit.setOnMouseClicked(e -> customerController.showUpdateCustomer(customer));
        btnDelete.setOnMouseClicked(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + customer.getTitle() + customer.getName() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                CustomerQueries.getInstance().deleteCustomer(customer);
                try {
                    customerController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void init(Customer customer) {
        this.customer = customer;
        imgProfilePhote.setImage(new Image(customer.getImageUrl()));
        tvAddress.setText(customer.getAddress());
        tvName.setText(customer.getTitle() + " " + customer.getName());
        tvId.setText(customer.getId());
        tvRank.setText(String.valueOf(customer.getRank()));
        tvTelephone.setText(customer.getTelephone());
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }
}

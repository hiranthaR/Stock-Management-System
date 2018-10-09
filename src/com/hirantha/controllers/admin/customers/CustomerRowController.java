package com.hirantha.controllers.admin.customers;

import com.hirantha.models.data.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerRowController implements Initializable {

    @FXML
    private AnchorPane customerRow;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Label tvName;

    @FXML
    private Label tvAddress;


    @FXML
    private Label tvId;

    @FXML
    private Label tvRank;

    private CustomerProfileController customerProfileController;
    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRow.setOnMouseClicked(e -> customerProfileController.init(customer));
    }

    public void init(Customer customer, CustomerProfileController customerProfileController) {
        imgPhoto.setImage(new Image(customer.getImageUrl()));
        tvAddress.setText(customer.getAddress());
        tvName.setText(customer.getTitle() + " " + customer.getName());
        tvId.setText(customer.getId());
        tvRank.setText(String.valueOf(customer.getRank()));
        this.customerProfileController = customerProfileController;
        this.customer = customer;
    }
}

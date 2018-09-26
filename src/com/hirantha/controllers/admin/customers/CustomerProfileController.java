package com.hirantha.controllers.admin.customers;

import com.hirantha.models.data.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerProfileController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init(Customer customer) {
        imgProfilePhote.setImage(new Image(customer.getImageUrl()));
        tvAddress.setText(customer.getAddress());
        tvName.setText(customer.getTitle() + ". " + customer.getName());
        tvId.setText(customer.getId());
        tvRank.setText(String.valueOf(customer.getRank()));
        tvTelephone.setText(customer.getTelephone());
    }
}

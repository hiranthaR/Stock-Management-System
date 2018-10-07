package com.hirantha.controllers.admin.items;

import com.hirantha.controllers.admin.customers.CustomerProfileController;
import com.hirantha.models.data.customer.Customer;
import com.hirantha.models.data.item.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemRowController implements Initializable {

    @FXML
    private Label tvName;

    @FXML
    private Label tvCategory;

    @FXML
    private Label tvId;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init(Item item) {
        tvName.setText(item.getName());
        tvCategory.setText(item.getCategory());
        tvId.setText(item.getItemCode());
    }

}

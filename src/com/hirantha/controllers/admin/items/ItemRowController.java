package com.hirantha.controllers.admin.items;

import com.hirantha.controllers.admin.customers.CustomerProfileController;
import com.hirantha.models.data.customer.Customer;
import com.hirantha.models.data.item.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemRowController implements Initializable {

    @FXML
    private AnchorPane itemRow;


    @FXML
    private Label tvName;

    @FXML
    private Label tvCategory;

    @FXML
    private Label tvId;

    private Item item;
    private ItemsFullViewController itemsFullViewController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemRow.setOnMouseClicked(e -> itemsFullViewController.init(item));
    }

    public void init(Item item, ItemsFullViewController itemsFullViewController) {
        tvName.setText(item.getName());
        tvCategory.setText(item.getCategory());
        tvId.setText(item.getItemCode());
        this.item = item;
        this.itemsFullViewController = itemsFullViewController;
    }
}

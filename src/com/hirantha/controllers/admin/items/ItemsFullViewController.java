package com.hirantha.controllers.admin.items;

import com.hirantha.database.items.ItemQueries;
import com.hirantha.models.data.item.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemsFullViewController implements Initializable {

    @FXML
    private Label tvItemName;

    @FXML
    private Label tvId;

    @FXML
    private Label tvCategory;

    @FXML
    private Label tvUnit;

    @FXML
    private Label tvReceiptPrice;

    @FXML
    private Label tvMarkedPrice;

    @FXML
    private Label tvSellingPrice;

    @FXML
    private Label tvDiscountRank1;

    @FXML
    private Label tvDiscountRank2;

    @FXML
    private Label tvDiscountRank3;

    @FXML
    private Label btnEdit;

    @FXML
    private Label btnDelete;

    private ItemsController itemsController;
    private Item item;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnEdit.setOnMouseClicked(e -> itemsController.showUpdateItemView(item));
        btnDelete.setOnMouseClicked(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + item.getName() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                ItemQueries.getInstance().deleteItem(item);
                try {
                    itemsController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void init(Item item) {
        this.item = item;

        tvItemName.setText(item.getName());
        tvCategory.setText(item.getCategory());
        tvId.setText(item.getItemCode());
        tvUnit.setText(item.getUnit());

        tvReceiptPrice.setText(String.valueOf(item.getReceiptPrice()) + "/=");
        tvMarkedPrice.setText(String.valueOf(item.getMarkedPrice()) + "/=");
        tvSellingPrice.setText(String.valueOf(item.getSellingPrice()) + "/=");

        String postFix = item.isPercentage() ? "%" : "/=";
        tvDiscountRank1.setText(String.valueOf(item.getRank1()) + postFix);
        tvDiscountRank2.setText(String.valueOf(item.getRank2()) + postFix);
        tvDiscountRank3.setText(String.valueOf(item.getRank3()) + postFix);
    }

    public void setItemsController(ItemsController itemsController) {
        this.itemsController = itemsController;
    }
}

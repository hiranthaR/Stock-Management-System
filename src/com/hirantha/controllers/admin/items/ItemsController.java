package com.hirantha.controllers.admin.items;

import animatefx.animation.FadeIn;
import com.hirantha.controllers.admin.customers.CustomerRowController;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.database.items.ItemQueries;
import com.hirantha.fxmls.FXMLS;
import com.hirantha.models.data.customer.Customer;
import com.hirantha.models.data.item.Item;
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
import java.util.ResourceBundle;

public class ItemsController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private Button btnNewItem;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane itemContainer;

    @FXML
    private TextField txtSearch;

    private AnchorPane newItemView;
    private NewItemController newItemController;

    private AnchorPane itemFullViewPane;
    private ItemsFullViewController itemsFullViewController;

    private List<Item> items;
    List<Item> temp = new ArrayList<>();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader itemFullViewFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_FULL_VIEW));
        FXMLLoader newItemFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.NEW_ITEM_VIEW));
        try {


            itemFullViewPane = itemFullViewFxmlLoader.load();
            itemContainer.getChildren().add(itemFullViewPane);
            itemsFullViewController = itemFullViewFxmlLoader.getController();
            itemsFullViewController.setItemsController(ItemsController.this);

            //new items
            newItemView = newItemFxmlLoader.load();
            newItemController = newItemFxmlLoader.getController();
            newItemController.setItemsController(ItemsController.this);

            items = readRows();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnNewItem.setOnMouseClicked(e -> showNewItemView());

        txtSearch.setOnKeyReleased(keyEvent -> {

            temp.clear();
            for (Item item : items)
                if (item.getName().toLowerCase().contains(txtSearch.getText().toLowerCase())) temp.add(item);
            try {
                setRowViews(temp);
                if (txtSearch.getText().isEmpty()) readRows();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    List<Item> readRows() throws IOException {

        List<Item> items = ItemQueries.getInstance().getItems();
        setRowViews(items);

        return items;
    }

    private void setRowViews(List<Item> items) throws IOException {
        rowsContainer.getChildren().clear();
        for (Item item : items) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_ROW));
            AnchorPane row = fxmlLoader.load();
            fxmlLoader.<ItemRowController>getController().init(item, itemsFullViewController);
            rowsContainer.getChildren().add(row);
        }

        if (items.size() == 0) {
            itemContainer.getChildren().clear();
        } else {
            itemContainer.getChildren().clear();
            itemContainer.getChildren().add(itemFullViewPane);
            itemsFullViewController.init(items.get(0));
        }
    }

    public void showUpdateItemView(Item item) {
        showNewItemView();
        newItemController.initToUpdate(item);
    }

    public void showNewItemView() {
        if (!((StackPane) basePane.getParent()).getChildren().contains(newItemView)) {
            ((StackPane) basePane.getParent()).getChildren().add(newItemView);
        }
        newItemView.toFront();
        newItemController.loadUnitsAndCategories();
        FadeIn animation = new FadeIn(newItemView);
        animation.setSpeed(3);
        animation.play();
    }
}

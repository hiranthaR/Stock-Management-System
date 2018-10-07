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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private Button btnOpen;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane itemContainer;

    private AnchorPane newItemView;

    private NewItemController newItemController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_FULL_VIEW));
        FXMLLoader newItemFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.NEW_ITEM_VIEW));
        try {
            itemContainer.getChildren().add(fxmlLoader1.load());
            newItemView = newItemFxmlLoader.load();
            newItemController = newItemFxmlLoader.getController();
            newItemController.setItemsController(ItemsController.this);

            readRows();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnOpen.setOnMouseClicked(e -> {
            if (!((StackPane) basePane.getParent()).getChildren().contains(newItemView)) {
                ((StackPane) basePane.getParent()).getChildren().add(newItemView);
            }
            newItemView.toFront();
            newItemController.loadUnitsAndCategories();
            FadeIn animation = new FadeIn(newItemView);
            animation.setSpeed(3);
            animation.play();
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
            fxmlLoader.<ItemRowController>getController().init(item);
            rowsContainer.getChildren().add(row);
        }

//        if (items.size() == 0) {
//            profileContainer.getChildren().clear();
//        } else {
//            profileContainer.getChildren().clear();
//            profileContainer.getChildren().add(profilePane);
//            customerProfileController.init(customers.get(0));
//        }
    }
}

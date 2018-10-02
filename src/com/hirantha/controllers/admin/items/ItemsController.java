package com.hirantha.controllers.admin.items;

import com.hirantha.fxmls.FXMLS;
import com.hirantha.models.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane itemContainer;
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_ROW));
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_FULL_VIEW));
        try {
            rowsContainer.getChildren().add(fxmlLoader.load());
            itemContainer.getChildren().add(fxmlLoader1.load());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

package com.hirantha.controllers.admin.items;

import animatefx.animation.FadeIn;
import com.hirantha.fxmls.FXMLS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_ROW));
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_FULL_VIEW));
        FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.NEW_ITEM_VIEW));
        try {
            rowsContainer.getChildren().add(fxmlLoader.load());
            itemContainer.getChildren().add(fxmlLoader1.load());
            newItemView = fxmlLoader3.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnOpen.setOnMouseClicked(e -> {
            if (!((StackPane) basePane.getParent()).getChildren().contains(newItemView)) {
                ((StackPane) basePane.getParent()).getChildren().add(newItemView);
            }
            newItemView.toFront();
            FadeIn animation = new FadeIn(newItemView);
            animation.setSpeed(3);
            animation.play();
        });

    }
}

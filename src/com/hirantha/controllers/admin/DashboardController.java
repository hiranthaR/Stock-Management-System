package com.hirantha.controllers.admin;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private HBox topnavbar;

    @FXML
    private Button btnMinimize;


    @FXML
    private Button btnClose;

    @FXML
    private HBox menuCustomers;

    @FXML
    private HBox menuItems;

    @FXML
    private HBox menuStocks;

    @FXML
    private HBox menuFinancial;

    @FXML
    private StackPane panesContainer;


    private double x;
    private double y;
    private AnchorPane customersPane;
    private AnchorPane itemsPane;
    private AnchorPane stocksPane;
    private AnchorPane financialPane;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customersPane = FXMLLoader.load(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customers.fxml"));
            itemsPane = FXMLLoader.load(getClass().getResource("/com/hirantha/fxmls/admin/items.fxml"));
            stocksPane = FXMLLoader.load(getClass().getResource("/com/hirantha/fxmls/admin/stocks.fxml"));
            financialPane = FXMLLoader.load(getClass().getResource("/com/hirantha/fxmls/admin/financial.fxml"));

            panesContainer.getChildren().add(customersPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        topnavbar.setOnMouseClicked(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });


        topnavbar.setOnMouseDragged(e -> {
            Stage stage = (Stage) topnavbar.getScene().getWindow();
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });

        btnClose.setOnMouseClicked(e -> btnClose.getScene().getWindow().hide());

        menuCustomers.setOnMouseClicked(event -> changePane(customersPane));

        menuItems.setOnMouseClicked(event -> changePane(itemsPane));

        menuStocks.setOnMouseClicked(event -> changePane(stocksPane));


        menuFinancial.setOnMouseClicked(event -> changePane(financialPane));

        btnMinimize.setOnMouseClicked(e -> ((Stage) btnMinimize.getScene().getWindow()).setIconified(true));

    }

    private void changePane(AnchorPane pane) {
        if (!panesContainer.getChildren().contains(pane)) {
            panesContainer.getChildren().add(pane);
            FadeIn animation = new FadeIn(pane);
            animation.setSpeed(3);
            animation.getTimeline().setOnFinished(e -> panesContainer.getChildren().remove(0));
            animation.play();

        }
    }
}

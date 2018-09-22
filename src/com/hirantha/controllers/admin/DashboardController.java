package com.hirantha.controllers.admin;

import animatefx.animation.FadeIn;
import com.hirantha.models.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Controller {

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

    public StackPane getPanesContainer() {
        return panesContainer;
    }

    @FXML
    private StackPane panesContainer;


    private double x;
    private double y;
    private FXMLLoader customersFxmlLoader;
    private FXMLLoader itemsFxmlLoader;
    private FXMLLoader stocksFxmlLoader;
    private FXMLLoader financialFxmlLoader;
    private AnchorPane customersPane;
    private AnchorPane itemsPane;
    private AnchorPane stocksPane;
    private AnchorPane financialPane;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customersFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/costomers/customers.fxml"));
            itemsFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/items.fxml"));
            stocksFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/stocks.fxml"));
            financialFxmlLoader = new FXMLLoader(getClass().getResource("/com/hirantha/fxmls/admin/financial.fxml"));

            customersPane = customersFxmlLoader.load();
            itemsPane = itemsFxmlLoader.load();
            stocksPane = stocksFxmlLoader.load();
            financialPane = financialFxmlLoader.load();

            changePane(customersPane, customersFxmlLoader.getController());
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

        menuCustomers.setOnMouseClicked(event -> changePane(customersPane, customersFxmlLoader.getController()));

        menuItems.setOnMouseClicked(event -> changePane(itemsPane, itemsFxmlLoader.getController()));

        menuStocks.setOnMouseClicked(event -> changePane(stocksPane, stocksFxmlLoader.getController()));

        menuFinancial.setOnMouseClicked(event -> changePane(financialPane, financialFxmlLoader.getController()));

        btnMinimize.setOnMouseClicked(e -> ((Stage) btnMinimize.getScene().getWindow()).setIconified(true));

    }

    private void changePane(AnchorPane pane, Controller controller) {
        if (!panesContainer.getChildren().contains(pane)) {
            controller.setParentController(DashboardController.this);
            panesContainer.getChildren().add(pane);
            FadeIn animation = new FadeIn(pane);
            animation.setSpeed(3);
            animation.getTimeline().setOnFinished(e -> {
                if (panesContainer.getChildren().size() > 1) panesContainer.getChildren().remove(0);
            });
            animation.play();

        }
    }

    @Override
    public void setParentController(Controller controller) {

    }

    @Override
    public Controller getParentController() {
        return null;
    }

    public AnchorPane getCustomersPane() {
        return customersPane;
    }
}

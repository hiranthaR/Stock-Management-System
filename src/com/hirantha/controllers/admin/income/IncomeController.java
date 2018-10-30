package com.hirantha.controllers.admin.income;

import animatefx.animation.FadeIn;
import com.hirantha.controllers.admin.items.ItemsController;
import com.hirantha.fxmls.FXMLS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IncomeController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane invoiceContainer;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label btnNewInvoice;

    private NewInvoiceController newInvoiceController;
    private AnchorPane newInvoiceView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        FXMLLoader itemFullViewFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_FULL_VIEW));
        FXMLLoader newInvoiceFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Income.NEW_INVOICE));
        try {
//            itemFullViewPane = itemFullViewFxmlLoader.load();
//            invoiceContainer.getChildren().add(itemFullViewPane);
//            itemsFullViewController = itemFullViewFxmlLoader.getController();
//            itemsFullViewController.setItemsController(ItemsController.this);

            //new items
            newInvoiceView = newInvoiceFxmlLoader.load();
            newInvoiceController = newInvoiceFxmlLoader.getController();
//            newInvoiceController.setItemsController(ItemsController.this);

//            items = readRows();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnNewInvoice.setOnMouseClicked(e -> showNewItemView());

    }

    public void showNewItemView() {
        if (!((StackPane) basePane.getParent()).getChildren().contains(newInvoiceView)) {
            ((StackPane) basePane.getParent()).getChildren().add(newInvoiceView);
        }
        newInvoiceView.toFront();
        newInvoiceController.loadData();
        FadeIn animation = new FadeIn(newInvoiceView);
        animation.setSpeed(3);
        animation.play();
    }
}

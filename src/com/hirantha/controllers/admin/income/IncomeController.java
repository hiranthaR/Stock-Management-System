package com.hirantha.controllers.admin.income;

import animatefx.animation.FadeIn;
import com.hirantha.controllers.admin.items.ItemsController;
import com.hirantha.database.invoice.InvoiceQueries;
import com.hirantha.fxmls.FXMLS;
import com.hirantha.models.data.invoice.Invoice;
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
import java.util.ArrayList;
import java.util.List;
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

    private List<Invoice> invoices;
    List<Invoice> tempInvoices = new ArrayList<>();

    private NewInvoiceController newInvoiceController;
    private AnchorPane newInvoiceView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
//        FXMLLoader itemFullViewFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Items.ITEM_FULL_VIEW));
//            itemFullViewPane = itemFullViewFxmlLoader.load();
//            invoiceContainer.getChildren().add(itemFullViewPane);
//            itemsFullViewController = itemFullViewFxmlLoader.getController();
//            itemsFullViewController.setItemsController(ItemsController.this);

            //new items
            FXMLLoader newInvoiceFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Income.NEW_INVOICE));
            newInvoiceView = newInvoiceFxmlLoader.load();
            newInvoiceController = newInvoiceFxmlLoader.getController();
            newInvoiceController.setIncomeController(IncomeController.this);

            invoices = readRows();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnNewInvoice.setOnMouseClicked(e -> showNewItemView());

    }

    List<Invoice> readRows() throws IOException {

        invoices = InvoiceQueries.getInstance().getInvoices();
        setRowViews(invoices);

        return invoices;
    }

    private void setRowViews(List<Invoice> customers) throws IOException {
        rowsContainer.getChildren().clear();
        for (Invoice invoice : invoices) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Income.INVOICE_ROW));
            AnchorPane row = fxmlLoader.load();
            fxmlLoader.<InvoiceRowController>getController().init(invoice, null);
            rowsContainer.getChildren().add(row);
        }

//        if (customers.size() == 0) {
//            profileContainer.getChildren().clear();
//        } else {
//            profileContainer.getChildren().clear();
//            profileContainer.getChildren().add(profilePane);
//            customerProfileController.init(customers.get(0));
//        }
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

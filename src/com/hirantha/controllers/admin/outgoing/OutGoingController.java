package com.hirantha.controllers.admin.outgoing;

import animatefx.animation.FadeIn;
import com.hirantha.fxmls.FXMLS;
import com.hirantha.models.data.outgoing.Bill;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OutGoingController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private ScrollPane customerRowScrollPane;

    @FXML
    private VBox rowsContainer;

    @FXML
    private AnchorPane invoiceContainer;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label btnNewInvoice;

    private List<Bill> bills;

    //TODO: implement search
    List<Bill> tempInvoices = new ArrayList<>(); //for search

    private NewOutGoingInvoiceController newOutGoingInvoiceController;
    private AnchorPane newOutGoingView;

    private AnchorPane outgoingFullViewPane;
    private OutGoingInvoiceFullViewController outGoingInvoiceFullViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader outgoingFullViewFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Outgoing.OUTGOING_BILL_FULL_VIEW));
            outgoingFullViewPane = outgoingFullViewFxmlLoader.load();
            invoiceContainer.getChildren().add(outgoingFullViewPane);
            outGoingInvoiceFullViewController = outgoingFullViewFxmlLoader.getController();
//            newOutGoingInvoiceController.setIncomeController(IncomeController.this);

            //new items
            FXMLLoader newInvoiceFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.Outgoing.NEW_BILL));
            newOutGoingView = newInvoiceFxmlLoader.load();
            newOutGoingInvoiceController = newInvoiceFxmlLoader.getController();
            newOutGoingInvoiceController.setOutGoingController(OutGoingController.this);

//            bills = readRows();

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnNewInvoice.setOnMouseClicked(e -> showNewItemView());
    }

//    List<Bill> readRows() throws IOException {

//        bills = InvoiceQueries.getInstance().getInvoices();
//        setRowViews(invoices);

//        return invoices;
//    }

    public void showNewItemView() {
        if (!((StackPane) basePane.getParent()).getChildren().contains(newOutGoingView)) {
            ((StackPane) basePane.getParent()).getChildren().add(newOutGoingView);
        }
        newOutGoingView.toFront();
        newOutGoingInvoiceController.loadData();
        FadeIn animation = new FadeIn(newOutGoingView);
        animation.setSpeed(3);
        animation.play();
    }

}

package com.hirantha.controllers.admin.outgoing;

import com.hirantha.models.data.outgoing.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private AnchorPane newOuGoingView;

    private AnchorPane outgoingFullViewPane;
    private OutGoingInvoiceFullViewController outGoingInvoiceFullViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}

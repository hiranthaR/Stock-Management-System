package com.hirantha.controllers.admin.outgoing;

import com.hirantha.models.data.outgoing.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class OutGoingInvoiceRowController implements Initializable {

    @FXML
    private AnchorPane itemRow;

    @FXML
    private Label tvInvoiceTitle;

    @FXML
    private Label tvInvoiceNo;

    @FXML
    private Label tvAdmin;

    @FXML
    private Label tvTotalPrice;

    private OutGoingInvoiceFullViewController outGoingInvoiceFullViewController;
    private Bill bill;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemRow.setOnMouseClicked(e -> outGoingInvoiceFullViewController.init(bill));
    }

    public void init(Bill bill, OutGoingInvoiceFullViewController outGoingInvoiceFullViewController) {

        tvInvoiceTitle.setText(bill.getCustomerName() + " @ " + simpleDateFormat.format(bill.getDate()));
        tvInvoiceNo.setText(bill.get_id());
        tvAdmin.setText(bill.getAcceptedAdminName());
        tvTotalPrice.setText("Rs. " + String.valueOf(bill.getTotalBillCost()));

        this.outGoingInvoiceFullViewController = outGoingInvoiceFullViewController;
        this.bill = bill;
    }
}

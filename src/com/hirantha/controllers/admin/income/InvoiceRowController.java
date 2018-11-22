package com.hirantha.controllers.admin.income;

import com.hirantha.models.data.invoice.Invoice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class InvoiceRowController implements Initializable {


    @FXML
    private AnchorPane itemRow;

    @FXML
    private Label tvInvoiceTitle;

    @FXML
    private Label tvSupplierInvoiceNo;

    @FXML
    private Label tvCompanyInvoiceNo;

    @FXML
    private Label tvTotalPrice;

    private InvoiceFullViewController invoiceFullViewController;
    private Invoice invoice;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemRow.setOnMouseClicked(e -> invoiceFullViewController.init(invoice));
    }

    public void init(Invoice invoice, InvoiceFullViewController invoiceFullViewController) {

        tvInvoiceTitle.setText(invoice.getName() + " @ " + simpleDateFormat.format(invoice.getDate()));
        tvSupplierInvoiceNo.setText(invoice.getInvoiceNumber());
        tvCompanyInvoiceNo.setText(invoice.get_id());
        tvTotalPrice.setText("Rs. " + String.valueOf(invoice.getBillCost()));

        this.invoiceFullViewController = invoiceFullViewController;
        this.invoice = invoice;
    }
}

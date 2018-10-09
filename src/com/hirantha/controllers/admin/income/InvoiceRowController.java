package com.hirantha.controllers.admin.income;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceRowController implements Initializable {


    @FXML
    private AnchorPane itemRow;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Label tvInvoiceTitle;

    @FXML
    private Label tvSupplierInvoiceNo;

    @FXML
    private Label tvCompanyInvoiceNo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

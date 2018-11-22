package com.hirantha.controllers.admin.income;

import com.hirantha.database.invoice.InvoiceQueries;
import com.hirantha.models.data.invoice.Invoice;
import com.hirantha.models.data.item.InvoiceTableItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class InvoiceFullViewController implements Initializable {


    @FXML
    private Label tvInvoiceNumber;

    @FXML
    private Label tvDate;

    @FXML
    private Label tvSupplierInvoiceNumber;

    @FXML
    private Label tvSupplierName;

    @FXML
    private Label tvSupplierAddress;

    @FXML
    private TableView<InvoiceTableItem> table;

    @FXML
    private TableColumn<InvoiceTableItem, String> clmnCode;

    @FXML
    private TableColumn<InvoiceTableItem, String> clmnName;

    @FXML
    private TableColumn<InvoiceTableItem, String> clmnUnit;

    @FXML
    private TableColumn<InvoiceTableItem, Double> clmnCostPerItem;

    @FXML
    private TableColumn<InvoiceTableItem, Integer> clmnQuantity;

    @FXML
    private Label tvTotalCost;

    @FXML
    private HBox rowChequeDetails;

    @FXML
    private HBox rowBank;

    @FXML
    private Label tvBank;

    @FXML
    private HBox rowBranch;

    @FXML
    private Label tvBranch;

    @FXML
    private HBox rowChequeDate;

    @FXML
    private Label tvChequeDate;

    @FXML
    private HBox rowAmount;

    @FXML
    private Label tvAmount;

    @FXML
    private Label tvPreparedByAdmin;

    @FXML
    private Label tvCheckedByAdmin;

    @FXML
    private Label tvAcceptedByAdmin;

    @FXML
    private Label btnEdit;

    @FXML
    private Label btnDelete;

    private IncomeController incomeController;
    private Invoice invoice;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnEdit.setOnMouseClicked(e -> incomeController.showUpdateInvoice(invoice));
        btnDelete.setOnMouseClicked(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete invoice " + invoice.get_id() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                InvoiceQueries.getInstance().deleteInvoice(invoice);
                try {
                    incomeController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        clmnCode.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        clmnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmnUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        clmnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmnCostPerItem.setCellValueFactory(new PropertyValueFactory<>("costPerItem"));

    }

    public void init(Invoice invoice) {
        this.invoice = invoice;
        tvInvoiceNumber.setText(invoice.get_id());
        tvDate.setText(simpleDateFormat.format(invoice.getDate()));
        tvSupplierInvoiceNumber.setText(invoice.getInvoiceNumber());
        tvSupplierName.setText(invoice.getName());
        tvSupplierAddress.setText(invoice.getAddress());

        table.getItems().clear();
        table.getItems().addAll(invoice.getInvoiceTableItems());

        tvTotalCost.setText(String.valueOf(invoice.getBillCost()));

        if (invoice.isCash()) {
            rowBank.setManaged(false);
            rowBank.setVisible(false);
            rowBranch.setManaged(false);
            rowBranch.setVisible(false);
            rowAmount.setManaged(false);
            rowAmount.setVisible(false);
            rowChequeDate.setManaged(false);
            rowChequeDate.setVisible(false);
            rowChequeDetails.setManaged(false);
            rowChequeDetails.setVisible(false);
        } else {
            rowBank.setManaged(true);
            rowBank.setVisible(true);
            rowBranch.setManaged(true);
            rowBranch.setVisible(true);
            rowAmount.setManaged(true);
            rowAmount.setVisible(true);
            rowChequeDate.setManaged(true);
            rowChequeDate.setVisible(true);
            rowChequeDetails.setManaged(true);
            rowChequeDetails.setVisible(true);

            tvBank.setText(invoice.getBank());
            tvBranch.setText(invoice.getBranch());
            tvAmount.setText(String.valueOf(invoice.getAmount()));
            tvChequeDate.setText(simpleDateFormat.format(invoice.getChequeDate()));
        }

        tvPreparedByAdmin.setText(invoice.getPreparedAdminName());
        tvAcceptedByAdmin.setText(invoice.getAcceptedAdminName());
        tvCheckedByAdmin.setText(invoice.getCheckedAdminName());
    }

    public InvoiceFullViewController setIncomeController(IncomeController incomeController) {
        this.incomeController = incomeController;
        return this;
    }
}

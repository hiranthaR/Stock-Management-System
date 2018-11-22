package com.hirantha.controllers.admin.outgoing;

import com.hirantha.database.outgoing.OutgoingQueries;
import com.hirantha.models.data.item.BillTableItem;
import com.hirantha.models.data.outgoing.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class OutGoingInvoiceFullViewController implements Initializable {

    @FXML
    private Label tvInvoiceNumber;

    @FXML
    private Label tvDate;

    @FXML
    private Label tvSupplierName;

    @FXML
    private Label tvSupplierAddress;

    @FXML
    private Label tvRank;

    @FXML
    private TableView<BillTableItem> table;

    @FXML
    private TableColumn<BillTableItem, String> clmnCode;

    @FXML
    private TableColumn<BillTableItem, String> clmnName;

    @FXML
    private TableColumn<BillTableItem, String> clmnUnit;

    @FXML
    private TableColumn<BillTableItem, Double> clmnCostPerItem;

    @FXML
    private TableColumn<BillTableItem, Integer> clmnQuantity;

    @FXML
    private TableColumn<BillTableItem, Double> clmnDiscount;

    @FXML
    private Label tvTotalCost;

    @FXML
    private Label tvPreparedByAdmin;

    @FXML
    private Label tvCheckedByAdmin;

    @FXML
    private Label tvAcceptedByAdmin;

    @FXML
    private Label tvVehicleNumber;

    @FXML
    private Label btnEdit;

    @FXML
    private Label btnDelete;

    private OutGoingController outGoingController;
    private Bill bill;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEdit.setOnMouseClicked(e -> outGoingController.showUpdateInvoice(bill));
        btnDelete.setOnMouseClicked(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete bill " + bill.get_id() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                OutgoingQueries.getInstance().deleteBill(bill);
                try {
                    outGoingController.readRows();
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
        clmnDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

    }

    public void init(Bill bill) {
        this.bill = bill;
        tvInvoiceNumber.setText(bill.get_id());
        tvDate.setText(simpleDateFormat.format(bill.getDate()));
        tvSupplierName.setText(bill.getCustomerName());
        tvSupplierAddress.setText(bill.getCustomerAddress());
        tvRank.setText(String.valueOf(bill.getCustomerRank()));

        table.getItems().clear();
        table.getItems().addAll(bill.getTableItems());

        tvTotalCost.setText(String.valueOf(bill.getTotalBillCost()));

        tvPreparedByAdmin.setText(bill.getPreparedAdminName());
        tvAcceptedByAdmin.setText(bill.getAcceptedAdminName());
        tvCheckedByAdmin.setText(bill.getCheckedAdminName());
        tvVehicleNumber.setText(bill.getVehicleNumber());
    }

    public OutGoingInvoiceFullViewController setOutGoingController(OutGoingController outGoingController) {
        this.outGoingController = outGoingController;
        return this;
    }
}

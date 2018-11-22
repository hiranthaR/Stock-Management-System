package com.hirantha.controllers.admin.stock;

import com.hirantha.database.invoice.InvoiceQueries;
import com.hirantha.models.data.item.BillTableItem;
import com.hirantha.models.data.item.InvoiceTableItem;
import com.hirantha.models.data.item.StockItem;
import com.hirantha.models.data.outgoing.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class StocksController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<StockItem> table;

    @FXML
    private TableColumn<StockItem, String> clmnCode;

    @FXML
    private TableColumn<StockItem, String> clmnName;

    @FXML
    private TableColumn<StockItem, String> clmnUnit;

    @FXML
    private TableColumn<StockItem, Integer> clmnQuantity;

    Map<String, StockItem> stockItemMap = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //table configurations
        clmnCode.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        clmnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmnUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        clmnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        new Thread(this::setStockData).start();
    }

    public void setStockData() {
        List<InvoiceTableItem> invoiceTableItems = InvoiceQueries.getInstance().getInvoiceTableItems();
        for (InvoiceTableItem invoiceTableItem : invoiceTableItems) {
            if (stockItemMap.containsKey(invoiceTableItem.getItemId())) {
                StockItem stockItem = stockItemMap.get(invoiceTableItem.getItemId());
                stockItem.setQuantity(stockItem.getQuantity() + invoiceTableItem.getQuantity());
            } else {
                stockItemMap.put(invoiceTableItem.getItemId(), new StockItem(invoiceTableItem.getItemId(), invoiceTableItem.getName(), invoiceTableItem.getUnit(), invoiceTableItem.getQuantity()));
            }
        }
//        List<BillTableItem> billTableItems = new ArrayList<>();
//        for (BillTableItem billTableItem:billTableItems){
//            if(stockItemMap.containsKey(billTableItem.getItemId())){
//                StockItem stockItem = stockItemMap.get(billTableItem.getItemId());
//                stockItem.setQuantity(stockItem.getQuantity() - billTableItem.getQuantity());
//            } else {
//                stockItemMap.put(billTableItem.getItemId(),new StockItem(billTableItem.getItemId(),billTableItem.getName(),billTableItem.getUnit(),billTableItem.getQuantity() * -1));
//            }
//        }

        table.getItems().addAll(stockItemMap.values());
    }
}

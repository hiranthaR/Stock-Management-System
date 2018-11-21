package com.hirantha.controllers.admin.outgoing;

import animatefx.animation.FadeOut;
import com.hirantha.admins.CurrentAdmin;
import com.hirantha.admins.Permissions;
import com.hirantha.database.admins.AdminQueries;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.database.invoice.InvoiceQueries;
import com.hirantha.database.items.ItemQueries;
import com.hirantha.models.data.admins.Admin;
import com.hirantha.models.data.customer.Customer;
import com.hirantha.models.data.invoice.Supplier;
import com.hirantha.models.data.item.BillTableItem;
import com.hirantha.models.data.item.Item;
import com.hirantha.models.data.item.TableItem;
import com.hirantha.models.data.outgoing.Bill;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.apache.commons.lang3.text.WordUtils;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NewOutGoingInvoiceController implements Initializable {

    @FXML
    private Label btnCancel;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtRank;

    @FXML
    private ComboBox<Item> cmbItemCode;

    @FXML
    private ComboBox<Item> cmbItemName;

    @FXML
    private Label tvUnit;

    @FXML
    private TextField txtQuanitiy;

    @FXML
    private TextField txtCostPerItem;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNewItem;

    @FXML
    private Button btnRemove;

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
    private TextField txtBillCost;

    @FXML
    private ComboBox<Admin> cmbPrepared;

    @FXML
    private ComboBox<Admin> cmbchecked;

    @FXML
    private ComboBox<Admin> cmbAccepted;

    @FXML
    private TextField txtVehicleNumber;

    @FXML
    private Button btnSave;

    private List<Item> items;
    private Item selectedItem;
    private List<Admin> admins;
    private boolean goingToUpdate;
    private Bill bill;
    private OutGoingController outGoingController;
    private Set<Customer> customers;
    private Customer selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtName.requestFocus());

        //loading items
        loadData();

        dpDate.getEditor().textProperty().addListener((observableValue, s, t1) -> {
            String date = dpDate.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
            dpDate.getEditor().setText(date);
        });
        dpDate.setValue(LocalDate.now());

        txtName.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });

        txtName.textProperty().addListener((observableValue, s, t1) -> {
            txtName.setText(WordUtils.capitalize(t1));
            Customer temp = customers.stream().filter(i -> i.getName().equalsIgnoreCase(t1)).findFirst().orElse(null);
            if (temp != null) {
                txtAddress.setText(temp.getAddress());
                txtRank.setText(String.valueOf(temp.getRank()));
            }
        });

        //item code settings
        cmbItemCode.setCellFactory(itemListView -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getItemCode());
                }
            }
        });
        cmbItemCode.setConverter(new StringConverter<Item>() {
            @Override
            public String toString(Item item) {
                if (item == null) {
                    return null;
                } else {
                    return item.getItemCode();
                }
            }

            @Override
            public Item fromString(String s) {
                return items.stream().filter(i -> i.getItemCode().equalsIgnoreCase(s)).findFirst().orElse(null);
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, item, t1) -> {
            if (t1 != null) {
                selectedItem = t1;
                cmbItemName.setValue(t1);
                tvUnit.setText(t1.getUnit());
                txtCostPerItem.setText(String.valueOf(t1.getReceiptPrice()));
            }
        });
        cmbItemCode.getItems().setAll(items);

        //item name settings
        cmbItemName.setCellFactory(itemListView -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        cmbItemName.setConverter(new StringConverter<Item>() {
            @Override
            public String toString(Item item) {
                return item == null ? null : item.getName();
            }

            @Override
            public Item fromString(String s) {
                return items.stream().filter(i -> i.getName().equalsIgnoreCase(s)).findFirst().orElse(null);
            }
        });

        cmbItemName.getSelectionModel().selectedItemProperty().addListener((observableValue, item, t1) -> {
            if (t1 != null) {
                cmbItemCode.setValue(t1);
                tvUnit.setText(t1.getUnit());
                selectedItem = t1;
                txtCostPerItem.setText(String.valueOf(t1.getReceiptPrice()));
            }
        });
        cmbItemName.getItems().setAll(items);

        txtQuanitiy.setOnKeyTyped(e -> {
            if (!Character.isDigit(e.getCharacter().charAt(0))) e.consume();
        });
        txtCostPerItem.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        //table configurations
        clmnCode.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        clmnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmnUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        clmnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmnCostPerItem.setCellValueFactory(new PropertyValueFactory<>("costPerItem"));
        clmnDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        btnAdd.setOnMouseClicked(e -> addItemToTable());
        btnAdd.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addItemToTable();
            }
        });

        btnRemove.setOnMouseClicked(e -> {
            BillTableItem billTableItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(table.getSelectionModel().getSelectedIndex());
            if (txtBillCost.getText().isEmpty() || txtBillCost.getText().equals("0")) {
                txtBillCost.setText(String.valueOf(billTableItem.getCostPerItem() * billTableItem.getQuantity()));
            } else {
                txtBillCost.setText(String.valueOf(Double.parseDouble(txtBillCost.getText()) - billTableItem.getCostPerItem() * billTableItem.getQuantity()));
            }

            if (Double.parseDouble(txtBillCost.getText()) < 0) txtBillCost.setText("0");
        });

        if (!Permissions.checkPermission(CurrentAdmin.getInstance().getCurrentAdmin().getLevel(), Permissions.ADD_ITEM))
            btnNewItem.setVisible(false);
        //TODO: code for new item button

        txtBillCost.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        cmbPrepared.setCellFactory(adminListView -> new ListCell<Admin>() {
            @Override
            protected void updateItem(Admin item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        cmbPrepared.setItems(FXCollections.observableArrayList(admins));
        cmbPrepared.setConverter(new StringConverter<Admin>() {
            @Override
            public String toString(Admin admin) {
                if (admin == null) {
                    return null;
                } else {
                    return admin.getName();
                }
            }

            @Override
            public Admin fromString(String s) {
                return null;
            }
        });

        cmbAccepted.setCellFactory(adminListView -> new ListCell<Admin>() {
            @Override
            protected void updateItem(Admin item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        cmbAccepted.setItems(FXCollections.observableArrayList(admins));
        cmbAccepted.setConverter(new StringConverter<Admin>() {
            @Override
            public String toString(Admin admin) {
                if (admin == null) {
                    return null;
                } else {
                    return admin.getName();
                }
            }

            @Override
            public Admin fromString(String s) {
                return null;
            }
        });

        cmbchecked.setCellFactory(adminListView -> new ListCell<Admin>() {
            @Override
            protected void updateItem(Admin item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        cmbchecked.setItems(FXCollections.observableArrayList(admins));
        cmbchecked.setConverter(new StringConverter<Admin>() {
            @Override
            public String toString(Admin admin) {
                if (admin == null) {
                    return null;
                } else {
                    return admin.getName();
                }
            }

            @Override
            public Admin fromString(String s) {
                return null;
            }
        });

        cmbchecked.setValue(CurrentAdmin.getInstance().getCurrentAdmin());
        cmbPrepared.setValue(CurrentAdmin.getInstance().getCurrentAdmin());
        cmbAccepted.setValue(CurrentAdmin.getInstance().getCurrentAdmin());

        btnSave.setOnMouseClicked(e -> {
//            if (checkInvoiceItemProperties()) {
//                if (goingToUpdate) {
//                    InvoiceQueries.getInstance().updateInvoice(createInvoice());
//                    goingToUpdate = false;
//                } else {
//                    InvoiceQueries.getInstance().insertInvoice(createInvoice());
//                }
//                try {
//                    incomeController.readRows();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                goBack();
//            }
        });
    }

    private boolean checkTableItemProperties() {
        boolean status = true;
        if (selectedItem == null || cmbItemCode.getValue() == null || cmbItemName.getValue() == null) {
            new Alert(Alert.AlertType.INFORMATION, "Select an Item!").showAndWait();
            status = false;
        }

        if (txtQuanitiy.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Enter a Quantity!").showAndWait();
            status = false;
        }

        if (txtCostPerItem.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Cost per item is empty!").showAndWait();
            status = false;
        }
        return status;
    }

    private void addItemToTable() {
        if (checkTableItemProperties()) {
            BillTableItem tableItem = createTableItem();
            table.getItems().add(tableItem);
            cmbItemCode.requestFocus();
            cmbItemCode.setValue(null);
            cmbItemName.setValue(null);
            txtQuanitiy.setText("");
            txtCostPerItem.setText("");
            tvUnit.setText("");

            if (txtBillCost.getText().isEmpty() || txtBillCost.getText().equals("0")) {
                txtBillCost.setText(String.valueOf((tableItem.getCostPerItem() * tableItem.getQuantity()) - (discount(tableItem.getCostPerItem()) * tableItem.getQuantity())));
            } else {
                txtBillCost.setText(String.valueOf(Double.parseDouble(txtBillCost.getText()) + (tableItem.getCostPerItem() * tableItem.getQuantity()) - (discount(tableItem.getCostPerItem()) * tableItem.getQuantity())));
            }

        }
    }

    private BillTableItem createTableItem() {
        String itemCode = selectedItem.getItemCode();
        String itemName = selectedItem.getName();
        String unit = selectedItem.getUnit();
        int quantity = Integer.parseInt(txtQuanitiy.getText());
        double costPerItem = Double.parseDouble(txtCostPerItem.getText());
        boolean percentage = selectedItem.isPercentage();
        double discount = discount(costPerItem) * quantity;

        return new BillTableItem(itemCode, itemName, unit, quantity, costPerItem, discount, percentage);
    }

    private double discount(double itemCost) {
        double discount;
        if (selectedItem.isPercentage()) {
            discount = itemCost * selectedItem.getDiscountAccoringToRank(selectedCustomer == null ? 0 : selectedCustomer.getRank()) / 100;
        } else {
            discount = itemCost - selectedItem.getDiscountAccoringToRank(selectedCustomer == null ? 0 : selectedCustomer.getRank());
        }
        return discount;
    }

    private void goBack() {
        FadeOut fadeOut = new FadeOut(btnCancel.getParent());
        fadeOut.setSpeed(3);
        fadeOut.play();
        fadeOut.getTimeline().setOnFinished(ex -> {
            ((StackPane) btnCancel.getParent().getParent()).getChildren().remove(btnCancel.getParent());
//            clearFields();
        });
//        try {
//            outGoingController.readRows();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void loadData() {
        items = ItemQueries.getInstance().getItems();
        TextFields.bindAutoCompletion(cmbItemCode.getEditor(), cmbItemCode.getItems().stream().map(Item::getItemCode).collect(Collectors.toList()));
        cmbItemName.getItems().clear();
        cmbItemName.getItems().addAll(items);
        cmbItemCode.getItems().clear();
        cmbItemCode.getItems().addAll(items);
        admins = AdminQueries.getInstance().getAdmins();
        customers = new HashSet<>(CustomerQueries.getInstance().getCustomers());
        TextFields.bindAutoCompletion(txtName, customers.stream().map(Customer::getName).collect(Collectors.toList()));
    }

    public NewOutGoingInvoiceController setOutGoingController(OutGoingController outGoingController) {
        this.outGoingController = outGoingController;
        return this;
    }
}

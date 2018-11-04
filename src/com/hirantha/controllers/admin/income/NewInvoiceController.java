package com.hirantha.controllers.admin.income;

import animatefx.animation.FadeOut;
import com.hirantha.admins.CurrentAdmin;
import com.hirantha.admins.Permissions;
import com.hirantha.controllers.admin.auth.LoginController;
import com.hirantha.database.admins.AdminQueries;
import com.hirantha.database.invoice.InvoiceQueries;
import com.hirantha.database.items.ItemQueries;
import com.hirantha.models.data.admins.Admin;
import com.hirantha.models.data.invoice.Invoice;
import com.hirantha.models.data.invoice.Supplier;
import com.hirantha.models.data.item.Item;
import com.hirantha.models.data.item.TableItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.commons.lang3.text.WordUtils;
import org.bson.types.ObjectId;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NewInvoiceController implements Initializable {

    @FXML
    private Label btnCancel;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField txtInvoiceNumber;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

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
    private TableView<TableItem> table;

    @FXML
    private TableColumn<TableItem, String> clmnCode;

    @FXML
    private TableColumn<TableItem, String> clmnName;

    @FXML
    private TableColumn<TableItem, String> clmnUnit;

    @FXML
    private TableColumn<TableItem, Double> clmnCostPerItem;

    @FXML
    private TableColumn<TableItem, Integer> clmnQuantity;

    @FXML
    private TextField txtBillCost;

    @FXML
    private RadioButton radioCash;

    @FXML
    private ToggleGroup cashOrCheck;

    @FXML
    private RadioButton radioCheque;

    @FXML
    private TextField txtBank;

    @FXML
    private TextField txtBranch;

    @FXML
    private DatePicker dpChequeDate;

    @FXML
    private TextField txtAmount;

    @FXML
    private ComboBox<Admin> cmbPrepared;

    @FXML
    private ComboBox<Admin> cmbchecked;

    @FXML
    private ComboBox<Admin> cmbAccepted;

    @FXML
    private Button btnSave;

    private List<Item> items;
    private Item selectedItem;
    private List<Admin> admins;
    private boolean goingToUpdate;
    private Invoice invoice;
    private IncomeController incomeController;
    private Set<Supplier> suppliers;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> txtInvoiceNumber.requestFocus());

        //loading items
        loadData();

        dpDate.getEditor().textProperty().addListener((observableValue, s, t1) -> {
            String date = dpDate.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
            dpDate.getEditor().setText(date);
        });
        dpDate.setValue(LocalDate.now());


        //add auto complete to name field
        txtName.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });

        txtName.textProperty().addListener((observableValue, s, t1) -> {
            txtName.setText(WordUtils.capitalize(t1));
            Supplier temp = suppliers.stream().filter(i -> i.getName().equalsIgnoreCase(t1)).findFirst().orElse(null);
            if (temp != null) txtAddress.setText(temp.getAddress());
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

        btnAdd.setOnMouseClicked(e -> addItemToTable());
        btnAdd.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addItemToTable();
            }
        });

        btnRemove.setOnMouseClicked(e -> {
            TableItem tableItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(table.getSelectionModel().getSelectedIndex());
            if (txtBillCost.getText().isEmpty() || txtBillCost.getText().equals("0")) {
                txtBillCost.setText(String.valueOf(tableItem.getCostPerItem() * tableItem.getQuantity()));
            } else {
                txtBillCost.setText(String.valueOf(Double.parseDouble(txtBillCost.getText()) - tableItem.getCostPerItem() * tableItem.getQuantity()));
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

//settings if radio buttons
        radioCash.setUserData(true);
        radioCheque.setUserData(false);
        txtBank.setDisable(true);
        txtBranch.setDisable(true);
        dpChequeDate.setDisable(true);
        txtAmount.setDisable(true);
        cashOrCheck.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (cashOrCheck.getSelectedToggle() != null) {
                if (!(boolean) cashOrCheck.getSelectedToggle().getUserData()) {
                    txtBank.setDisable(false);
                    txtBranch.setDisable(false);
                    dpChequeDate.setDisable(false);
                    txtAmount.setDisable(false);
                } else {
                    txtBank.setDisable(true);
                    txtBranch.setDisable(true);
                    dpChequeDate.setDisable(true);
                    txtAmount.setDisable(true);
                }
            }
        });


        dpChequeDate.getEditor().textProperty().addListener((observableValue, s, t1) -> {
            String date = dpChequeDate.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
            dpChequeDate.getEditor().setText(date);
        });
        dpChequeDate.setValue(LocalDate.now());

        txtBank.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });
        txtBranch.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });

        txtAmount.setTextFormatter(new TextFormatter<>(change -> {
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
            if (checkInvoiceItemProperties()) {
                if (goingToUpdate) {
                    InvoiceQueries.getInstance().updateInvoice(createInvoice());
                    goingToUpdate = false;
                } else {
                    InvoiceQueries.getInstance().insertInvoice(createInvoice());
                }
                try {
                    incomeController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                goBack();
            }
        });
    }

    private void addItemToTable() {
        if (checkTableItemProperties()) {
            TableItem tableItem = createTableItem();
            table.getItems().add(tableItem);
            cmbItemCode.requestFocus();
            cmbItemCode.setValue(null);
            cmbItemName.setValue(null);
            txtQuanitiy.setText("");
            txtCostPerItem.setText("");
            tvUnit.setText("");

            if (txtBillCost.getText().isEmpty() || txtBillCost.getText().equals("0")) {
                txtBillCost.setText(String.valueOf(tableItem.getCostPerItem() * tableItem.getQuantity()));
            } else {
                txtBillCost.setText(String.valueOf(Double.parseDouble(txtBillCost.getText()) + tableItem.getCostPerItem() * tableItem.getQuantity()));
            }

        }
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

    private TableItem createTableItem() {
        String itemCode = selectedItem.getItemCode();
        String itemName = selectedItem.getName();
        String unit = selectedItem.getUnit();
        int quantity = Integer.parseInt(txtQuanitiy.getText());
        double costPerItem = Double.parseDouble(txtCostPerItem.getText());

        return new TableItem(itemCode, itemName, unit, quantity, costPerItem);
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private boolean checkInvoiceItemProperties() {
        boolean status = true;
        if (txtInvoiceNumber.getText().isEmpty()) {
            alert.setContentText("Supplier Invoice number is empty!");
            alert.showAndWait();
            status = false;
        }
        if (txtName.getText().isEmpty()) {
            alert.setContentText("Supplier name is empty!");
            alert.showAndWait();
            status = false;
        }

        if (txtAddress.getText().isEmpty()) {
            alert.setContentText("Supplier address is empty!");
            alert.showAndWait();
            status = false;
        }

        if (table.getItems().size() < 1) {
            alert.setContentText("No items added yet!");
            alert.showAndWait();
            status = false;
        }

        if (txtBillCost.getText().isEmpty()) {
            alert.setContentText("Total bill cost is empty!");
            alert.showAndWait();
            status = false;
        }

        if (!(boolean) cashOrCheck.getSelectedToggle().getUserData()) {
            if (txtBank.getText().isEmpty()) {
                alert.setContentText("Bank is empty!");
                alert.showAndWait();
                status = false;
            }

            if (txtBranch.getText().isEmpty()) {
                alert.setContentText("Branch is empty!");
                alert.showAndWait();
                status = false;
            }

            if (txtAmount.getText().isEmpty()) {
                alert.setContentText("Amount is empty!");
                alert.showAndWait();
                status = false;
            }
        }

        return status;
    }

    private Invoice createInvoice() {
        Date date = java.sql.Date.valueOf(dpDate.getValue());
        String invoiceNumber = txtInvoiceNumber.getText();
        String supplierName = txtName.getText();
        String supplierAddress = txtAddress.getText();
        List<TableItem> tableItems = new ArrayList<>(table.getItems());
        double billCost = Double.parseDouble(txtBillCost.getText());
        boolean cash = (boolean) cashOrCheck.getSelectedToggle().getUserData();

        String bank = null, branch = null;
        Date chequeDate = null;
        double amount = 0;

        if (!cash) {
            bank = txtBank.getText();
            branch = txtBranch.getText();
            chequeDate = java.sql.Date.valueOf(dpChequeDate.getValue());
            amount = Double.parseDouble(txtAmount.getText());
        }

        String preparedAdminName = cmbPrepared.getValue().getName();
        String preparedAdminId = cmbPrepared.getValue().getId();
        String acceptedAdminName = cmbAccepted.getValue().getName();
        String acceptedAdminId = cmbAccepted.getValue().getId();
        String checkedAdminName = cmbchecked.getValue().getName();
        String checkedAdminId = cmbchecked.getValue().getId();


        return new Invoice(goingToUpdate ? invoice.get_id() : "", date, invoiceNumber, supplierName, supplierAddress, tableItems, billCost, cash, bank, branch, chequeDate, amount, preparedAdminName, preparedAdminId, checkedAdminName, checkedAdminId, acceptedAdminName, acceptedAdminId);
    }

    private void clearFields() {
        dpDate.setValue(LocalDate.now());
        txtInvoiceNumber.setText("");
        txtName.setText("");
        txtAddress.setText("");
        cmbItemCode.setValue(null);
        cmbItemName.setValue(null);
        txtQuanitiy.setText("");
        tvUnit.setText("");
        txtCostPerItem.setText("");
        table.getItems().clear();
        txtBillCost.setText("0");
        radioCash.setSelected(true);
        txtBank.setText("");
        txtBranch.setText("");
        txtAmount.setText("");
        dpChequeDate.setValue(LocalDate.now());
        cmbPrepared.setValue(CurrentAdmin.getInstance().getCurrentAdmin());
        cmbAccepted.setValue(CurrentAdmin.getInstance().getCurrentAdmin());
        cmbchecked.setValue(CurrentAdmin.getInstance().getCurrentAdmin());
    }

    public NewInvoiceController setIncomeController(IncomeController incomeController) {
        this.incomeController = incomeController;
        return this;
    }

    private void goBack() {
        FadeOut fadeOut = new FadeOut(btnCancel.getParent());
        fadeOut.setSpeed(3);
        fadeOut.play();
        fadeOut.getTimeline().setOnFinished(ex -> {
            ((StackPane) btnCancel.getParent().getParent()).getChildren().remove(btnCancel.getParent());
            clearFields();
        });
        try {
            incomeController.readRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initToUpdate(Invoice invoice) {

        System.out.println(invoice.getPreparedAdminId().toString());
        this.invoice = invoice;
        this.goingToUpdate = true;
        dpDate.setValue(new java.sql.Date(invoice.getDate().getTime()).toLocalDate());
        txtName.setText(invoice.getName());
        txtAddress.setText(invoice.getAddress());
        txtInvoiceNumber.setText(invoice.getInvoiceNumber());
        table.getItems().addAll(invoice.getTableItems());
        txtBillCost.setText(String.valueOf(invoice.getBillCost()));
        if (invoice.isCash()) {
            radioCash.setSelected(true);
        } else {
            radioCheque.setSelected(true);
            txtBank.setText(invoice.getBank());
            txtBranch.setText(invoice.getBranch());
            txtAmount.setText(String.valueOf(invoice.getAmount()));
            dpChequeDate.setValue(new java.sql.Date(invoice.getChequeDate().getTime()).toLocalDate());

            System.out.println(invoice.getPreparedAdminId().toString());
            cmbPrepared.setValue(AdminQueries.getInstance().getAdmin(invoice.getPreparedAdminId()));
            cmbAccepted.setValue(AdminQueries.getInstance().getAdmin(invoice.getAcceptedAdminId()));
            cmbchecked.setValue(AdminQueries.getInstance().getAdmin(invoice.getCheckedAdminId()));
        }


    }

    public void loadData() {
        items = ItemQueries.getInstance().getItems();
        TextFields.bindAutoCompletion(cmbItemCode.getEditor(), cmbItemCode.getItems().stream().map(Item::getItemCode).collect(Collectors.toList()));
        cmbItemCode.getItems().clear();
        cmbItemName.getItems().clear();
        cmbItemName.getItems().addAll(items);
        cmbItemCode.getItems().addAll(items);
        admins = AdminQueries.getInstance().getAdmins();
        suppliers = new HashSet<>(InvoiceQueries.getInstance().getSuppliers());
        TextFields.bindAutoCompletion(txtName, suppliers.stream().map(Supplier::getName).collect(Collectors.toList()));
    }
}

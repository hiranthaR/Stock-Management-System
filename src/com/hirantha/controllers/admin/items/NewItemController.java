package com.hirantha.controllers.admin.items;

import animatefx.animation.FadeOut;
import com.hirantha.database.items.ItemQueries;
import com.hirantha.models.data.item.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NewItemController implements Initializable {

    @FXML
    private Label btnCancel;

    @FXML
    private TextField txtItemName;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<String> cmbUnit;

    @FXML
    private TextField txtReceiptPrice;

    @FXML
    private TextField txtMarkPrice;

    @FXML
    private TextField txtSellingPrice;

    @FXML
    private RadioButton radioAmount;

    @FXML
    private ToggleGroup discountToggleGroup;

    @FXML
    private RadioButton radioPercentage;


    @FXML
    private TextField txtDiscountRank1;

    @FXML
    private Label lblDiscount1;

    @FXML
    private TextField txtDiscountRank2;

    @FXML
    private Label lblDiscount2;

    @FXML
    private TextField txtDiscountRank3;

    @FXML
    private Label lblDiscount3;


    @FXML
    private Label btnSave;

    private ItemsController itemsController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadUnitsAndCategories();

        txtDiscountRank1.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        txtDiscountRank2.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        txtDiscountRank3.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        txtMarkPrice.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        txtReceiptPrice.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        txtSellingPrice.setTextFormatter(new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        }));

        cmbCategory.getEditor().textProperty().addListener((observableValue, s, t1) -> cmbCategory.getEditor().setText(WordUtils.capitalize(t1)));
        cmbCategory.getEditor().setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });

        cmbUnit.getEditor().setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0))))
                e.consume();
        });

        txtItemName.textProperty().addListener((observableValue, s, t1) -> txtItemName.setText(WordUtils.capitalize(t1)));

        //setting up radio buttons
        radioPercentage.setUserData(true);
        radioAmount.setUserData(false);
        discountToggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (discountToggleGroup.getSelectedToggle() != null) {
                txtDiscountRank1.setText("");
                txtDiscountRank2.setText("");
                txtDiscountRank3.setText("");
                if ((boolean) discountToggleGroup.getSelectedToggle().getUserData()) {
                    lblDiscount1.setText("%");
                    lblDiscount2.setText("%");
                    lblDiscount3.setText("%");
                } else {
                    lblDiscount1.setText("/=");
                    lblDiscount2.setText("/=");
                    lblDiscount3.setText("/=");
                }
            }
        });

        btnSave.setOnMouseClicked(e -> {
            if (valid()) {
                ItemQueries.getInstance().insertItem(createItem());
            }
        });

        btnCancel.setOnMouseClicked(e -> goBack());

    }

    public void loadUnitsAndCategories() {
        cmbUnit.setItems(FXCollections.observableArrayList(ItemQueries.getInstance().getUnits()));
        cmbCategory.setItems(FXCollections.observableArrayList(ItemQueries.getInstance().getCategories()));
    }

    private Item createItem() {

        String name = txtItemName.getText().trim();
        String category = cmbCategory.getEditor().getText().trim();
        String unit = cmbUnit.getEditor().getText().trim();
        double receiptPrice = Double.parseDouble(txtReceiptPrice.getText());
        double markedPrice = Double.parseDouble(txtMarkPrice.getText());
        double sellingPrice = Double.parseDouble(txtSellingPrice.getText());
        double discountRank1 = Double.parseDouble(txtDiscountRank1.getText());
        double discountRank2 = Double.parseDouble(txtDiscountRank2.getText());
        double discountRank3 = Double.parseDouble(txtDiscountRank3.getText());
        boolean percentage = (boolean) discountToggleGroup.getSelectedToggle().getUserData();

        return new Item("", name, category, unit, receiptPrice, markedPrice, sellingPrice, percentage, discountRank1, discountRank2, discountRank3);
    }

    private boolean valid() {

        boolean status = true;

        if (cmbCategory.getEditor().getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Category is empty!").showAndWait();
            status = false;
        }

        if (cmbUnit.getEditor().getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Unit is empty!").showAndWait();
            status = false;
        }

        if (txtReceiptPrice.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Receipt Price is empty!").showAndWait();
            status = false;
        }

        if (txtMarkPrice.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Marked Price is empty!").showAndWait();
            status = false;
        }

        if (txtSellingPrice.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Selling Price is empty!").showAndWait();
            status = false;
        }


        if (txtDiscountRank1.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Discount Rank 1 is empty!").showAndWait();
            status = false;
        }

        if (txtDiscountRank2.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Discount Rank 2 is empty!").showAndWait();
            status = false;
        }


        if (txtDiscountRank3.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Discount Rank 3 is empty!").showAndWait();
            status = false;
        }

        return status;
    }

    private void clearFields() {
        txtItemName.setText("");

        cmbCategory.getEditor().setText("");
        cmbUnit.getEditor().setText("");

        txtSellingPrice.setText("");
        txtReceiptPrice.setText("");
        txtMarkPrice.setText("");

        txtDiscountRank1.setText("");
        txtDiscountRank2.setText("");
        txtDiscountRank3.setText("");

        discountToggleGroup.selectToggle(radioAmount);
    }

    public void setItemsController(ItemsController itemsController) {
        this.itemsController = itemsController;
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
            itemsController.readRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

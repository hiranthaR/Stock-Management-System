package com.hirantha.controllers.admin.items;

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
    private Label btnEdit;

    @FXML
    private Label btnDelete;

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cmbCategory.setItems(FXCollections.observableArrayList("sex1", "sex2", "sex3"));

        btnEdit.setOnMouseClicked(e -> System.out.println(cmbCategory.editorProperty().get().getText()));

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


        //setting up radio buttons
        radioPercentage.setUserData(false);
        radioAmount.setUserData(true);
        discountToggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (discountToggleGroup.getSelectedToggle() != null) {
                txtDiscountRank1.setText("");
                txtDiscountRank2.setText("");
                txtDiscountRank3.setText("");
                if ((boolean) discountToggleGroup.getSelectedToggle().getUserData()) {
                    lblDiscount1.setText("/=");
                    lblDiscount2.setText("/=");
                    lblDiscount3.setText("/=");
                } else {
                    lblDiscount1.setText("%");
                    lblDiscount2.setText("%");
                    lblDiscount3.setText("%");
                }
            }
        });

    }
}

package com.hirantha.controllers.admin.items;

import com.hirantha.database.items.ItemQueries;
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
import org.apache.commons.lang3.text.WordUtils;

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

    TextFormatter decimalFormatter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        decimalFormatter = new TextFormatter<>(change -> {
            if (Pattern.compile("-?\\d*(\\.\\d{0,2})?").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });

        cmbUnit.setItems(FXCollections.observableArrayList(ItemQueries.getInstance().getUnits()));
        cmbCategory.setItems(FXCollections.observableArrayList(ItemQueries.getInstance().getCategories()));

        txtDiscountRank1.setTextFormatter(decimalFormatter);

        txtDiscountRank2.setTextFormatter(decimalFormatter);

        txtDiscountRank3.setTextFormatter(decimalFormatter);

        txtMarkPrice.setTextFormatter(decimalFormatter);

        txtReceiptPrice.setTextFormatter(decimalFormatter);

        txtSellingPrice.setTextFormatter(decimalFormatter);

        cmbCategory.getEditor().textProperty().addListener((observableValue, s, t1) -> cmbCategory.getEditor().setText(WordUtils.capitalize(t1)));
        cmbCategory.getEditor().setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });

        cmbUnit.getEditor().setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0))))
                e.consume();
        });

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

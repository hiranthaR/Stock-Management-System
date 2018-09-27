package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeOut;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.models.data.customer.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    @FXML
    private ImageView imgProfilePhoto;
    private Image manImage = new Image(Customer.manUrl);
    private Image womanImage = new Image(Customer.womanUrl);

    @FXML
    private Spinner<String> spinnerTitles;

    @FXML
    private TextField txtName;

    @FXML
    private Spinner<Integer> spinnerRank;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtTelephone;

    @FXML
    private Label btnSave;

    @FXML
    private Label btnCancel;

    private CustomerController customerController;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //button go back
        btnCancel.setOnMouseClicked(e -> goBack());

        //setting telephone text only numbers and max 10 characters
        txtTelephone.setOnKeyTyped(e -> {
            if (!Character.isDigit(e.getCharacter().charAt(0))) e.consume();
            if (txtTelephone.getText().length() > 9) e.consume();
        });

        //setting up name only for letters
        txtName.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });

        //setting up rank values
        spinnerRank.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1));

        //setting titles
        spinnerTitles.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList("Mr.", "Miss.", "Mrs.")));

        //save button
        btnSave.setOnMouseClicked(e -> {
            if (isCorrect()) {
                CustomerQueries.getInstance().insertCustomer(createCustomer());
                try {
                    customerController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                goBack();
            }
        });

        spinnerTitles.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("Mr.")) imgProfilePhoto.setImage(manImage);
            else imgProfilePhoto.setImage(womanImage);
        });
    }

    private boolean isCorrect() {

        boolean status = true;

        if (txtName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name is empty!").showAndWait();
            status = false;
        }

        if (txtAddress.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Address is empty!").showAndWait();
            status = false;
        }
        if (txtTelephone.getText().length() < 10) {
            new Alert(Alert.AlertType.ERROR, "Telephone must Contain 10 numbers!").showAndWait();
            status = false;
        }

        return status;
    }

    private Customer createCustomer() {
        boolean man = spinnerTitles.getValue().equals("Mr.");
        String title = spinnerTitles.getValue();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String telephone = txtTelephone.getText();
        int rank = spinnerRank.getValue();

        return new Customer("0", man, title, name, address, telephone, rank);
    }

    private void goBack() {
        FadeOut fadeOut = new FadeOut(btnCancel.getParent());
        fadeOut.setSpeed(3);
        fadeOut.play();
        fadeOut.getTimeline().setOnFinished(ex -> ((StackPane) btnCancel.getParent().getParent()).getChildren().remove(btnCancel.getParent()));
        try {
            customerController.readRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }
}

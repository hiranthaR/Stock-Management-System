package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeOut;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.models.data.customer.Customer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.net.URL;
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
    private boolean goingToUpdate = false;
    private Customer customer;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        spinnerTitles.requestFocus();

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

        //setting name,address in capital letters
        txtName.textProperty().addListener((observableValue, s, t1) -> txtName.setText(WordUtils.capitalize(t1)));
        txtAddress.textProperty().addListener((observableValue, s, t1) -> txtAddress.setText(WordUtils.capitalize(t1)));

        //setting up rank values
        spinnerRank.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1));

        //setting titles
        spinnerTitles.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList("Mr.", "Miss.", "Mrs.")));

        //save button
        btnSave.setOnMouseClicked(e -> {
            if (isCorrect()) {
                if (goingToUpdate) {
                    CustomerQueries.getInstance().updateCustomer(createCustomer());
                    goingToUpdate = false;
                } else {
                    CustomerQueries.getInstance().insertCustomer(createCustomer());
                }
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
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        String telephone = txtTelephone.getText();
        int rank = spinnerRank.getValue();

        String id;
        if (goingToUpdate) id = customer.getId();
        else id = "0";

        return new Customer(id, man, title, name, address, telephone, rank);
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
            customerController.readRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initToUpdate(Customer customer) {
        goingToUpdate = true;
        this.customer = customer;
        if (customer.getTitle().equals("Mr.")) imgProfilePhoto.setImage(manImage);
        else imgProfilePhoto.setImage(womanImage);
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        spinnerRank.getValueFactory().setValue(customer.getRank());
        spinnerTitles.getValueFactory().setValue(customer.getTitle());
        txtTelephone.setText(customer.getTelephone());
    }

    void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    private void clearFields() {
        imgProfilePhoto.setImage(manImage);
        txtName.setText("");
        txtAddress.setText("");
        spinnerRank.getValueFactory().setValue(1);
        spinnerTitles.getValueFactory().setValue("Mr.");
        txtTelephone.setText("");
    }
}

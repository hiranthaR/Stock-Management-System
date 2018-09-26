package com.hirantha.controllers.admin.customers;

import animatefx.animation.FadeOut;
import com.hirantha.database.customers.CustomerQueries;
import com.hirantha.models.data.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    @FXML
    private ImageView imgProfilePhoto;

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

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btnCancel.setOnMouseClicked(e -> {
            FadeOut fadeOut = new FadeOut(btnCancel.getParent());
            fadeOut.setSpeed(3);
            fadeOut.play();
            fadeOut.getTimeline().setOnFinished(ex -> ((StackPane) btnCancel.getParent().getParent()).getChildren().remove(btnCancel.getParent()));
        });

        txtTelephone.setOnKeyTyped(e -> {
            if (!Character.isDigit(e.getCharacter().charAt(0))) e.consume();
            if (txtTelephone.getText().length() > 9) e.consume();
        });

        spinnerRank.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1));

        spinnerTitles.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList("Mr.", "Miss.", "Mrs.")));

        btnSave.setOnMouseClicked(e -> {
            CustomerQueries.getInstance().insertCustomer(new Customer("cs1222", true, "Mr", "Hirantha Rathnayake", "ginipenda,Kalugamuwa.", "0716203812", 1));
        });


    }


}

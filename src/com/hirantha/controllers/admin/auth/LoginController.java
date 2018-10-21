package com.hirantha.controllers.admin.auth;

import com.hirantha.admins.CurrentAdmin;
import com.hirantha.fxmls.FXMLS;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private HBox topnavbar;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnClose;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    private double x;
    private double y;
    private FXMLLoader dashboardFxmlLoader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dashboardFxmlLoader = new FXMLLoader(getClass().getResource(FXMLS.Admin.MAIN_DASHBOARD));

        topnavbar.setOnMouseClicked(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });


        topnavbar.setOnMouseDragged(e -> {
            Stage stage = (Stage) topnavbar.getScene().getWindow();
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });

        txtPassword.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) login();
        });

        txtUsername.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isDigit(e.getCharacter().charAt(0))))
                e.consume();
        });

        btnClose.setOnMouseClicked(e -> btnClose.getScene().getWindow().hide());

        btnMinimize.setOnMouseClicked(e -> ((Stage) btnMinimize.getScene().getWindow()).setIconified(true));

        btnLogin.setOnMouseClicked(e -> login());

        Platform.runLater(() -> {
            txtUsername.requestFocus();

            //temporary pass the login
            txtUsername.setText("hirantha");
            txtPassword.setText("hiruu");
            login();
        });

    }

    private void login() {

        String username = txtUsername.getText().toLowerCase();
        String password = txtPassword.getText();

        if (!CurrentAdmin.getInstance().logginAdmin(username, password)) {
            new Alert(Alert.AlertType.ERROR, "Username or Password incorrect!").showAndWait();
            txtPassword.setText("");
            txtPassword.requestFocus();
        } else {
            btnLogin.getScene().getWindow().hide();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            try {
                stage.setScene(new Scene(dashboardFxmlLoader.load()));
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

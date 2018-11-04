package com.hirantha.controllers.admin.admins;

import animatefx.animation.FadeOut;
import com.hirantha.admins.Permissions;
import com.hirantha.database.admins.AdminQueries;
import com.hirantha.models.data.admins.Admin;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.apache.commons.lang3.text.WordUtils;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.hirantha.models.data.admins.Admin.*;

public class NewAdminController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUsername;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private CheckBox chkCustomer;

    @FXML
    private CheckBox chkItems;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label btnSave;

    @FXML
    private Label btnCancel;

    private boolean goingToUpdate = false;
    private AdminsController adminsController;
    private Admin admin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //button go back
        btnCancel.setOnMouseClicked(e -> goBack());

        txtName.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isSpaceChar(e.getCharacter().charAt(0))))
                e.consume();
        });
        txtName.textProperty().addListener((observableValue, s, t1) -> txtName.setText(WordUtils.capitalize(t1)));

        txtUsername.setOnKeyTyped(e -> {
            if (!(Character.isAlphabetic(e.getCharacter().charAt(0)) || Character.isDigit(e.getCharacter().charAt(0))))
                e.consume();
        });

        cmbRole.setItems(FXCollections.observableArrayList(ROLE_ADMIN, ROLE_INPUT, ROLE_OUTPUT, ROLE_INPUT_OUTPUT));


        cmbRole.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case ROLE_ADMIN:
                    chkItems.setSelected(true);
                    chkItems.setDisable(true);
                    chkCustomer.setSelected(true);
                    chkCustomer.setDisable(true);
                    break;
                case ROLE_INPUT:
                    chkItems.setSelected(true);
                    chkItems.setDisable(false);
                    chkCustomer.setSelected(false);
                    chkCustomer.setDisable(true);
                    break;
                case ROLE_OUTPUT:
                    chkItems.setSelected(false);
                    chkItems.setDisable(true);
                    chkCustomer.setSelected(true);
                    chkCustomer.setDisable(false);
                    break;
                case ROLE_INPUT_OUTPUT:
                    chkItems.setSelected(true);
                    chkItems.setDisable(false);
                    chkCustomer.setSelected(true);
                    chkCustomer.setDisable(false);
                    break;
            }

        });

        cmbRole.valueProperty().set(ROLE_ADMIN);

        //save button
        btnSave.setOnMouseClicked(e -> {
            if (valid()) {
                if (goingToUpdate) {
                    AdminQueries.getInstance().updateAdmin(createAdmin());
                    goingToUpdate = false;
                } else {
                    AdminQueries.getInstance().insertAdmin(createAdmin());
                }
                try {
                    adminsController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                goBack();
            }
        });
    }

    private boolean valid() {

        boolean status = true;

        boolean usernameExistence = false;
        for (Admin admin : AdminQueries.getInstance().getAdmins()) {
            if (admin.getUsername().equals(txtUsername.getText())) {
                usernameExistence = true;
                break;
            }
        }

        if (txtName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name is empty!").showAndWait();
            status = false;
        }

        //check username existence if not going to update
        if (!goingToUpdate && usernameExistence) {
            new Alert(Alert.AlertType.ERROR, "Username already Exist!").showAndWait();
            status = false;
        }
        if (txtUsername.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username is empty!").showAndWait();
            status = false;
        } else if (txtUsername.getText().length() < 4) {
            new Alert(Alert.AlertType.ERROR, "Username must have more than 4 characters!").showAndWait();
            status = false;
        }
        if (txtPassword.getText().length() < 5) {
            new Alert(Alert.AlertType.ERROR, "Password must have more than 5 characters!").showAndWait();
            status = false;
        }

        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Passwords are not matching!").showAndWait();
            status = false;
        }

        return status;
    }

    private Admin createAdmin() {

        String name = txtName.getText();
        String username = txtUsername.getText().toLowerCase();
        String password = txtPassword.getText();
        int level = 0;

        switch (cmbRole.getValue()) {
            case ROLE_ADMIN:
                level += Permissions.ROLE_ADMIN;
                break;
            case ROLE_INPUT:
                level += Permissions.ROLE_INPUT;
                break;
            case ROLE_OUTPUT:
                level += Permissions.ROLE_OUTPUT;
                break;
            case ROLE_INPUT_OUTPUT:
                level += Permissions.ROLE_INPUT;
                level += Permissions.ROLE_OUTPUT;
                break;
        }

        if (chkCustomer.isSelected()) level += Permissions.ADD_CUSTOMER;
        if (chkItems.isSelected()) level += Permissions.ADD_ITEM;

        String id;
        if (goingToUpdate) id = admin.getId();
        else id = "";

        return new Admin(id, name, username, password, level);
    }


    void initToUpdate(Admin admin) {
        goingToUpdate = true;
        this.admin = admin;
        txtName.setText(admin.getName());
        txtUsername.setText(admin.getUsername());
        cmbRole.valueProperty().set(Permissions.getRole(admin.getLevel()));
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
            adminsController.readRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }

    private void clearFields() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        chkCustomer.setSelected(false);
        chkItems.setSelected(false);
        cmbRole.valueProperty().set(Admin.ROLE_ADMIN);
    }
}

package com.hirantha.controllers.admin.admins;

import com.hirantha.admins.Permissions;
import com.hirantha.database.admins.AdminQueries;
import com.hirantha.models.data.admins.Admin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminProfileController implements Initializable {

    @FXML
    private Label tvName;

    @FXML
    private Label tvUsername;

    @FXML
    private Label tvRole;

    @FXML
    private Label btnEdit;

    @FXML
    private Label btnDelete;

    private AdminsController adminsController;
    private Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnEdit.setOnMouseClicked(e -> adminsController.showUpdateAdmin(admin));
        btnDelete.setOnMouseClicked(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + admin.getName() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                AdminQueries.getInstance().deleteAdmin(admin);
                try {
                    adminsController.readRows();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void init(Admin admin) {
        this.admin = admin;
        tvName.setText(admin.getName());
        tvUsername.setText(admin.getUsername());
        tvRole.setText(Permissions.getRole(admin.getLevel()));
    }

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }
}
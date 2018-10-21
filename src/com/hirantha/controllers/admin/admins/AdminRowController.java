package com.hirantha.controllers.admin.admins;

import com.hirantha.admins.Permissions;
import com.hirantha.models.data.admins.Admin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminRowController implements Initializable {

    @FXML
    private AnchorPane customerRow;

    @FXML
    private Label tvName;

    @FXML
    private Label tvUsername;

    @FXML
    private Label tvRole;

    private AdminProfileController adminProfileController;
    private Admin admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRow.setOnMouseClicked(e -> adminProfileController.init(admin));
    }

    public void init(Admin admin, AdminProfileController adminProfileController) {
        tvName.setText(admin.getName());
        tvRole.setText(Permissions.getRole(admin.getLevel()));
        tvUsername.setText(admin.getUsername());
        this.adminProfileController = adminProfileController;
        this.admin = admin;
    }
}

package com.hirantha.controllers.admin.customers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerRowController implements Initializable {

    private CustomerController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setParentController(CustomerController parentController) {
        this.parentController = parentController;
    }
}

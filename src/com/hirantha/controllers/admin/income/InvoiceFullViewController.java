package com.hirantha.controllers.admin.income;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceFullViewController implements Initializable {

    private IncomeController incomeController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public InvoiceFullViewController setIncomeController(IncomeController incomeController) {
        this.incomeController = incomeController;
        return this;
    }
}

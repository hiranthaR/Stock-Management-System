package com.hirantha.models;

import javafx.fxml.Initializable;

public interface Controller extends Initializable {

    void setParentController(Controller controller);

    Controller getParentController();

}

package com.hirantha;

import com.hirantha.database.items.ItemQueries;
import com.hirantha.fxmls.FXMLS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.function.Consumer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(FXMLS.Admin.MAIN_DASHBOARD));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Werellagama Hardware");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
//        ItemQueries.getInstance().insertItem(null);
        launch(args);
    }
}

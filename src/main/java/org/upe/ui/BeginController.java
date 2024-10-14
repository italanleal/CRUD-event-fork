package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BeginController {
    @FXML
    Button subscriptionButton;

    @FXML
    StackPane beginPage;

    @FXML
    Button LogOutButton;

    @FXML
    private void handleSubscription() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaInscricoes.fxml"));
        StackPane screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage) beginPage.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Even2");
    }

    @FXML
    private void logOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-app.fxml"));
        StackPane screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage) beginPage.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Even2");
    }
}

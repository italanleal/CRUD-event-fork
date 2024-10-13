package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.upe.controllers.AuthController;
import org.upe.persistence.interfaces.UserInterface;

import java.io.IOException;

public class LoginController {

    @FXML
    StackPane loginPage;

    @FXML
    Button signInButton;

    @FXML
    TextField cpfField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorMessage;

    @FXML
    Button signUpButton;

   @FXML
    private void handleLogin() throws IOException {
       String cpf = cpfField.getText();
       String password = passwordField.getText();

        UserInterface isLogged = AuthController.loginUser(cpf, password);

        if(isLogged == null) {
            errorMessage.setText("Credenciais erradas ou não cadastradas");
            errorMessage.setVisible(true);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaInicio.fxml"));
            StackPane screen = loader.load();
            Scene scene = new Scene(screen);
            Stage stage = (Stage) loginPage.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Even2");
        }
    }

    @FXML
    private void handleSignUp() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaCadastro.fxml"));
        StackPane screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage) loginPage.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Even2");
    }
}

package org.upe.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.upe.controllers.EventController;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.utils.SceneLoader;
import org.upe.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {
    @FXML
    StackPane schedulePage;

    EventController eventController = new EventController();

    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox mainContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showEvents();
    }

    @FXML
    private void moveToHomeScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInicio.fxml", "Home", schedulePage);
    }

    @FXML
    private void moveToRegisterInScreen() throws IOException {
        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Inscrições", schedulePage);
    }

//    private void moveToSubmissionsScreen() throws IOException {
//        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Submissões", schedulePage);
//    }

//    private void moveToCertificatesScreen() throws IOException {
//        SceneLoader.loadScene("/org/upe/ui/telaInscricoes.fxml", "Certificados", schedulePage);
//    }

    private void showEvents() {

        List<EventInterface> events = eventController.getAllEvents();


        scrollPane.setStyle("-fx-background: rgba(63, 63, 70, 0.3); -fx-background-color: rgba(63, 63, 70, 0.3);");
        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setAlignment(Pos.CENTER);

        events.forEach(event -> {
            VBox eventContainer = new VBox();
            eventContainer.setPrefWidth(490);
            eventContainer.setPrefHeight(300);
            eventContainer.setStyle("-fx-background-color: rgba(217, 217, 217, 0.1); -fx-background-radius: 25px;");
            eventContainer.setSpacing(20);
            eventContainer.setPadding(new Insets(20, 20, 20, 20));

            Label title = new Label(event.getName());
            title.setFont(Font.font("Arial", 18));
            title.setTextFill(Color.WHITE);
            title.setAlignment(Pos.TOP_CENTER);
            VBox.setVgrow(title, Priority.ALWAYS);
            title.setMaxWidth(Double.MAX_VALUE);
            title.setAlignment(Pos.CENTER);


            Label description = new Label(event.getDescription());
            description.setFont(Font.font("Arial", 14));
            description.setTextFill(Color.WHITE);
            description.setWrapText(true);


            Label dateLabel = new Label(event.getDate());
            dateLabel.setFont(Font.font("Arial", 14));
            dateLabel.setTextFill(Color.WHITE);


            Button signUpButton = new Button("Realizar Inscrição");
            signUpButton.setStyle("-fx-background-radius: 25; -fx-background-color: #2DD4BF; -fx-text-fill: WHITE;");
            signUpButton.setFont(Font.font("Arial", 14));
            signUpButton.setOnAction(e -> signUpEvent(event));


            Label locationLabel = new Label(event.getLocal());
            locationLabel.setFont(Font.font("Arial", 14));
            locationLabel.setTextFill(Color.WHITE);

            eventContainer.getChildren().addAll(title, description, dateLabel, locationLabel, signUpButton);
            mainContainer.getChildren().add(eventContainer);
        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        }
    private void signUpEvent(EventInterface event) {
        UserSession userSession = UserSession.getInstance();
        eventController.addAttendeeOnList(userSession.getCurrentUser(), event);

    }
}
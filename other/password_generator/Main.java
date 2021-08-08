import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Contains the class that holds the information for
 * designing my password generating program.
 *
 * @author Connor Rosenberg
 * @version 1.0
 */
public class Main extends Application {
    /** Instance variable for the custom font I imported from online. */
    private Font customHeaderFont;
    /** Instance variable for the main BorderPane for the program. */
    private BorderPane backgroundBP = new BorderPane();
    /** Instance variable for the main StackPane for the program. */
    private StackPane myPane = new StackPane();
    /** Instance variable for the main scene of the program. */
    private Scene mainBackground = new Scene(myPane, 800, 300);
    /** Instance variable for the generator button in the program. */
    private Button generatorButton = new Button("CLICK ME TO GENERATE PASSWORD!");
    /** Instance variable for the textfield of the generated password. */
    private TextField generatedText = new TextField();
    /** Instance variable for the length of the password the user wants to generate. */
    private int numOfCharacters;
    
    /**
    * Imports the custom font from the web.
    */
    public void createFont() {
        String currentFontFile = "ADAM.CG PRO.otf";
        InputStream fontStream = Main.class.getResourceAsStream(currentFontFile);
        customHeaderFont = Font.loadFont(fontStream, 23);
        try {
            fontStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
    * Styles the 'generate password' button.
    */
    public void styleButton() {
        generatorButton.setMinHeight(50);
        generatorButton.setMinWidth(100);
    }

    /**
    * Styles the textfield for the password.
    */
    public void styleTextField() {
        generatedText.setMinHeight(50);
        generatedText.setMinWidth(400);
        generatedText.setPromptText("No passwords generated yet");
        generatedText.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
    }

    /**
    * Produces the random string of characters for the password.
    * @return Returns the string of the randomized characters (ASCII values 33 - 126).
    */
    public String randomizationProducer() {
        String fullStr = "";
        for (int i = 0; i < numOfCharacters; i++) {
            int num = 33 + (int)(Math.random() * ((126-33) + 1));
            char numToChar = (char) num;
            fullStr = fullStr + numToChar;
        }
        return fullStr;
    }

    /**
    * Code used to create the header for the code.
    */
    public void createHeader() {
        Label header = new Label("Welcome to Connor Rosenberg's Password Generator!");
        createFont();
        header.setFont(customHeaderFont);
        header.setTextFill(Color.WHITE);
        StackPane headerSP = new StackPane();
        headerSP.getChildren().add(header);
        headerSP.setAlignment(Pos.CENTER);
        headerSP.setPadding(new Insets(60, 20, 0, 20));
        backgroundBP.setTop(headerSP);
    }

    /**
    * Code used to generate the body of the program (all the buttons and textfields and stuff).
    */
    public void createBody() {
        // Create the body for the stuff to go inside of
        VBox centerVB = new VBox();
        backgroundBP.setCenter(centerVB);
        centerVB.setAlignment(Pos.CENTER_LEFT);
        centerVB.setPadding(new Insets(30, 30, 30, 30));
        centerVB.setSpacing(30);

        // Creating the checkbox to determine whether or not the password field is editable
        CheckBox cb1 = new CheckBox("Do you want to make the password? field editable?");
        centerVB.getChildren().add(cb1);
        cb1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cb1.isSelected()) {
                    generatedText.setDisable(true);
                } else {
                    generatedText.setDisable(false);
                }
            }
        });

        // Creating the HBOX to hold the checkbox aspect
        HBox numHB = new HBox();
        numHB.setSpacing(30);
        numHB.setPadding(new Insets(0, 20, 20, 0));
        centerVB.getChildren().add(numHB);

        // Creating the text field for the textfield and button
        TextField numberTextField = new TextField();
        numberTextField.setMinHeight(50);
        numberTextField.setMinWidth(400);
        numberTextField.setPromptText("How many characters would you like your password to be?");
        numberTextField.setStyle("-fx-text-fill: black; -fx-font-size: 13px;");
        numHB.getChildren().add(numberTextField);

        // Creating the button to confirm the number of characters for the password, as well as see whether or not
            // it is in the range of the textfield or not
        Button confirmButton = new Button("Confirm?");
        confirmButton.setOnAction(e -> {
            if (numberTextField.getText().trim().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Confirmation Box");
                a.setHeaderText("Please enter a number between 1 and 20");
                a.show();
            } else {
                String str = numberTextField.getText();
                int tempNumOfCharacters = Integer.parseInt(str);
                if (tempNumOfCharacters < 1 || tempNumOfCharacters > 20) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Confirmation Box");
                    a.setHeaderText("Please enter a number between 1 and 20");
                    a.show();
                } else {
                    numOfCharacters = tempNumOfCharacters;
                    generatedText.setPromptText("All right! Password will now be " + numOfCharacters
                        + " characters long!");
                }
            }
        });
        confirmButton.setMinHeight(50);
        confirmButton.setMinWidth(100);

        numHB.getChildren().add(confirmButton);

        // Creating the HBOX for the generation button and text field
        HBox centerHB = new HBox();
        centerHB.setAlignment(Pos.CENTER_LEFT);
        centerHB.setPadding(new Insets(0, 30, 30, 0));
        centerHB.setSpacing(30);
        centerVB.getChildren().add(centerHB);

        // Adding the button and textfield, as well as styling them
        centerHB.getChildren().add(generatorButton);
        styleButton();
        centerHB.getChildren().add(generatedText);
        styleTextField();

        // Add functionality to the generation button
        generatorButton.setOnAction(e -> {
            String randomString = randomizationProducer();
            generatedText.setText(randomString);
        });
    }

    @Override
    public void start(Stage primaryStage) {

        // Initialize all the starter things for the window
        primaryStage.setScene(mainBackground);
        primaryStage.setTitle("Connor Rosenberg's Password Generator");
        primaryStage.setResizable(false);
        backgroundBP.setStyle("-fx-background-color:#bebebe;");
        myPane.getChildren().add(backgroundBP);

        // Create a header for the program
        createHeader();
        // Create the body of the program (text field generator + button)
        createBody();

        primaryStage.show();
    }

    /**
    * Main method for driving the program.
    * @param args For launching the program.
    */
    public static void main(String[] args) {
        launch(args);
    }
}

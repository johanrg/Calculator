/**
 * Calculator. A simple expression solver.
 *
 * @author Johan Gustafsson
 * @version 0.1
 * @see README.md in the root folder for more info
 */
/**
 * Created by Johan on 2016-05-27.
 */
package com.github.johanrg.calculator;

import com.github.johanrg.expressionengine.Expression;
import com.github.johanrg.expressionengine.ParserException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application {

    private TextArea expressionTextArea;
    private TextArea resultTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        expressionTextArea = new TextArea();
        expressionTextArea.setPrefHeight(400);
        expressionTextArea.setPrefWidth(400);

        resultTextArea = new TextArea();
        resultTextArea.setPrefHeight(400);
        resultTextArea.setPrefWidth(200);

        resultTextArea.setEditable(false);

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(0.6);
        splitPane.getItems().addAll(expressionTextArea, resultTextArea);

        Button calculateButton = new Button();
        calculateButton.setText("Calculate");
        calculateButton.setOnAction(e -> onCalculateButtonClick());

        Button resetButton = new Button();
        resetButton.setText("Clear");
        resetButton.setOnAction(e -> onClearButtonClick());

        HBox bottomPane = new HBox();
        bottomPane.setPrefHeight(60);
        bottomPane.setPrefWidth(600);
        bottomPane.setStyle("-fx-background-color: darkcyan");
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        bottomPane.setSpacing(10);
        bottomPane.setPadding(new Insets(0, 10, 0, 0));
        bottomPane.getChildren().addAll(resetButton, calculateButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(bottomPane);
        borderPane.setCenter(splitPane);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.show();

        expressionTextArea.requestFocus();
    }

    private void onCalculateButtonClick() {
        try {
            Expression expression = new Expression(expressionTextArea.getText());
        } catch(ParserException e) {
            resultTextArea.insertText(resultTextArea.getLength(), e.getMessage() + "\n");
        }
    }

    private void onClearButtonClick() {
        expressionTextArea.setText("");
        resultTextArea.setText("");
    }
}

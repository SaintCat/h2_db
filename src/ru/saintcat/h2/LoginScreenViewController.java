package ru.saintcat.h2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginScreenViewController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField pwField;
	@FXML
	private Label infoLabel;
	@FXML
	private ComboBox<String> choiceBox;

	private MainApp mainapp;

	@FXML
	void handleLogin(ActionEvent event) {

		if (inputDataIsValid()) {
			mainapp.loadTables();
			mainapp.showRootLayout();
			mainapp.showOwnerTable();
		} else {
			infoLabel.setText("Wrong username or password");
			infoLabel.setTextFill(Color.RED);
		}
	}

	@FXML
	void initialize() {
		choiceBox.getSelectionModel().select(0);
		choiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				if (arg2.equals("Guest")) {
					nameField.setDisable(true);
					pwField.setDisable(true);
				} else {
					nameField.setDisable(false);
					pwField.setDisable(false);
				}

			}
		});

	}

	private boolean inputDataIsValid() {
		if (nameField.getText().equalsIgnoreCase("sa")
				&& pwField.getText().equals("")) {
			return true;
		} else
			return false;
	}

	public void setMainApp(MainApp app) {
		this.mainapp = app;
	}

}

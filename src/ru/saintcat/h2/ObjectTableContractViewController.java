package ru.saintcat.h2;

import java.util.List;
import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.Client;
import ru.saintcat.h2.model.Contract;
import ru.saintcat.h2.model.Worker;
import ru.saintcat.h2.util.CalendarUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ObjectTableContractViewController {
	@FXML
	private ComboBox<Client> clientField;
	@FXML
	private ComboBox<Worker> workerField;
	@FXML
	private TextField objectsField;
	@FXML
	private TextField totalField;
	@FXML
	private TextField dateField;

	private Stage dialogStage;
	private Contract contract;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
		updateComboBoxes();
	}
	
	public void updateComboBoxes() {
		List<Client> clients = Factory.getInstance().getClientDAO().getAllClients();
		clientField.setItems(FXCollections.observableList(clients));
		List<Worker> workers = Factory.getInstance().getWorkerDAO().getAllWorkers();
		workerField.setItems(FXCollections.observableList(workers));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setContract(Contract contract, List<Long> objects) {
		this.contract = contract;
		clientField.getSelectionModel().select(null);
		workerField.getSelectionModel().select(null);	
		objectsField.setText(objects.toString());
		totalField.setText(String.valueOf(contract.getSum()));
		dateField.setText(ru.saintcat.h2.util.CalendarUtil.format(contract.getDate()));
		dateField.setPromptText("yyyy-mm-dd");
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			contract.setIdClient(clientField.getSelectionModel().getSelectedItem().getId());
			contract.setIdWorker(workerField.getSelectionModel().getSelectedItem().getId());
			contract.setDate(CalendarUtil.parse(dateField.getText()));
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (clientField.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "No valid id client!\n"; 
		}
		if (workerField.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "No valid id worker!\n"; 
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else{
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}
}

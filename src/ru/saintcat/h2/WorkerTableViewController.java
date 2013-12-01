package ru.saintcat.h2;

import java.util.List;

import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.Worker;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class WorkerTableViewController {
	@FXML
	private TableView<Worker> workerTable;
	@FXML
	private TableColumn<Worker, Integer> idColumn;
	@FXML
	private TableColumn<Worker, String> nameColumn;
	@FXML
	private TableColumn<Worker, Integer> telephoneColumn;
	@FXML
	private TextField nameField;
	@FXML
	private TextField telephoneField;
	@FXML
	private TextField seachField;
	
	private ObservableList<Worker> workersData = FXCollections.observableArrayList();
	private MainApp mainApp;

	public WorkerTableViewController() {
		
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<Worker, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("name"));
		telephoneColumn.setCellValueFactory(new PropertyValueFactory<Worker, Integer>("telephone"));
		//WorkerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	
		showWorkerDetails(null);
		refreshWorkerTable();

		workerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Worker>() {
			@Override
			public void changed(ObservableValue<? extends Worker> observable,
					Worker oldValue, Worker newValue) {
				showWorkerDetails(newValue);
			}
		});
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void showWorkerDetails(Worker worker) {
		if (worker != null) {
			nameField.setText(worker.getName());
			telephoneField.setText(worker.getTelephone().toString());
			
		} else {
			nameField.setText("");
			telephoneField.setText("");
		}
	}
	
	@FXML
	private void handleSeach(){
		refreshWorkerTable();
	}
	
	@FXML
	private void handleDeleteWorker() {
		int selectedIndex = workerTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
		Worker tmpWorker = workerTable.getSelectionModel().getSelectedItem();
		Factory.getInstance().getWorkerDAO().deleteWorker(tmpWorker);
		showWorkerDetails(null);
		refreshWorkerTable();	
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a Worker in the table.",
					"No Worker Selected", "No Selection");
		}
	}

	@FXML
	private void handleNewWorker() {
		Worker tempWorker = new Worker();
		tempWorker.setName(nameField.getText());
		tempWorker.setTelephone(Long.valueOf(telephoneField.getText()));
		Factory.getInstance().getWorkerDAO().addWorker(tempWorker);
		refreshWorkerTable();
	}

	@FXML
	private void handleEditWorker() {
		Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
		if (selectedWorker != null) {
			selectedWorker.setName(nameField.getText());
			selectedWorker.setTelephone(Long.valueOf(telephoneField.getText()));
			Factory.getInstance().getWorkerDAO().updateWorker(selectedWorker);
			refreshWorkerTable();
			showWorkerDetails(selectedWorker);
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a person in the table.",
					"No Person Selected", "No Selection");
		}
	}
	
	private void refreshWorkerTable() {
		int selectedIndex = workerTable.getSelectionModel().getSelectedIndex();
		workerTable.setItems(null);
		workerTable.layout();
		workersData.clear();
		List<Worker> workers;
		String seachText = seachField.getText();
		if(seachText.equals("") || seachField.getText()==null){
			workers = Factory.getInstance().getWorkerDAO().getAllWorkers();
		}
		else{
			workers = Factory.getInstance().getWorkerDAO().getWorkerByName(seachText);
		}
		
		for(Worker worker: workers){
			workersData.add(worker);
		}
		workerTable.setItems(workersData);
		workerTable.getSelectionModel().select(selectedIndex);
	}
}
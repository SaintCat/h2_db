package ru.saintcat.h2;

import java.util.List;

import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.Contract;

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

public class ContractTableViewController {
	@FXML
	private TableView<Contract> contractTable;
	@FXML
	private TableColumn<Contract, String> dateColumn;
	@FXML
	private TableColumn<Contract, Integer> idClientColumn;
	@FXML
	private TableColumn<Contract, Integer> idColumn;
	@FXML
	private TableColumn<Contract, Integer> idWorkerColumn;
	@FXML
	private TableColumn<Contract, Integer> sumColumn;
	@FXML
	private TextField idField;
	@FXML
	private TextField clientField;
	@FXML
	private TextField workerField;
	@FXML
	private TextField totalField;
	@FXML
	private TextField dateField;
	@FXML
	private TextField seachField;

	private ObservableList<Contract> contractsData = FXCollections
			.observableArrayList();
	private MainApp mainApp;

	public ContractTableViewController() {

	}

	@FXML
	private void initialize(){
		idColumn.setCellValueFactory(new PropertyValueFactory<Contract, Integer>("id"));
		idClientColumn.setCellValueFactory(new PropertyValueFactory<Contract, Integer>("idClient"));
		idWorkerColumn.setCellValueFactory(new PropertyValueFactory<Contract, Integer>("idWorker"));
		sumColumn.setCellValueFactory(new PropertyValueFactory<Contract, Integer>("sum"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("date2"));
		//contractTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		showContractDetails(null);
		refreshContractTable();
		contractTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contract>() {
			@Override
			public void changed( ObservableValue<? extends Contract> observable, Contract oldValue, Contract newValue) {
						showContractDetails(newValue);
			}
		});

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private void showContractDetails(Contract contract) {
		if (contract != null) {
			idField.setText(String.valueOf(contract.getId()));
			clientField.setText(String.valueOf(contract.getIdClient()));
			workerField.setText(String.valueOf(contract.getIdWorker()));
			totalField.setText(String.valueOf(contract.getSum()));
			dateField.setText(contract.date2Property().getValue());

		} else {
			idField.setText("");
			clientField.setText("");
			workerField.setText("");
			totalField.setText("");
			dateField.setText("");
		}
	}

	@FXML
	private void handleSeach() {
		refreshContractTable();
	}

	@FXML
	private void handleDeleteContract() {
		int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Contract tmpContract = contractTable.getSelectionModel().getSelectedItem();
			Factory.getInstance().getContractDAO().deleteContract(tmpContract);
			showContractDetails(null);
			refreshContractTable();
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a Contract in the table.", "No Contract Selected",
					"No Selection");
		}
	}

	@FXML
	private void handleRefresh() {
		refreshContractTable();
	}

	public void refreshContractTable() {
		int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();
		contractTable.setItems(null);
		contractTable.layout();
		contractsData.clear();
		List<Contract> contracts;
		String seachText = seachField.getText();
		if (seachText.equals("") || seachField.getText() == null) {
			contracts = Factory.getInstance().getContractDAO().getAllContracts();
		} else {
			contracts = Factory.getInstance().getContractDAO().getContractByName(seachText);
		}

		for (Contract contract : contracts) {
			contractsData.add(contract);
		}
		contractTable.setItems(contractsData);
		contractTable.getSelectionModel().select(selectedIndex);
	}
}
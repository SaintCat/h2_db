package ru.saintcat.h2;


import java.sql.SQLException;
import java.util.List;

import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.Client;


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


public class ClientTableViewController {
	@FXML
	private TableView<Client> clientTable;
	@FXML
	private TableColumn<Client, Integer> idColumn;
	@FXML
	private TableColumn<Client, String> nameColumn;
	@FXML
	private TableColumn<Client, Integer> telephoneColumn;
	@FXML
	private TextField nameField;
	@FXML
	private TextField telephoneField;
	@FXML
	private TextField seachField;
	
	private ObservableList<Client> clientsData = FXCollections.observableArrayList();
	private MainApp mainApp;

	public ClientTableViewController() {
		
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
		telephoneColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("telephone"));
		//ClientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		showClientDetails(null);
		refreshClientTable();

		clientTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
			@Override
			public void changed(ObservableValue<? extends Client> observable,
					Client oldValue, Client newValue) {
				showClientDetails(newValue);
			}
		});
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void showClientDetails(Client client) {
		if (client != null) {
			nameField.setText(client.getName());
			telephoneField.setText(client.getTelephone().toString());
			
		} else {
			nameField.setText("");
			telephoneField.setText("");
		}
	}
	
	@FXML
	private void handleSeach() {
		refreshClientTable();
	}
	
	@FXML
	private void handleDeleteClient() {
		int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Client tmpClient = clientTable.getSelectionModel().getSelectedItem();
			Factory.getInstance().getClientDAO().deleteClient(tmpClient);
			showClientDetails(null);
			refreshClientTable();
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a Client in the table.",
					"No Client Selected", "No Selection");
		}
	}
	

	@FXML
	private void handleNewClient() throws SQLException {
		Client tempClient = new Client();
		tempClient.setName(nameField.getText());
		tempClient.setTelephone(Long.valueOf(telephoneField.getText()));
		Factory.getInstance().getClientDAO().addClient(tempClient);
		refreshClientTable();
	}

	@FXML
	private void handleEditClient() throws SQLException {
		Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
		if (selectedClient != null) {
			selectedClient.setName(nameField.getText());
			selectedClient.setTelephone(Long.valueOf(telephoneField.getText()));
			Factory.getInstance().getClientDAO().updateClient(selectedClient);
			refreshClientTable();
			showClientDetails(selectedClient);
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a person in the table.",
					"No Person Selected", "No Selection");
		}
	}
	
	private void refreshClientTable(){
		int selectedIndex = clientTable.getSelectionModel()
				.getSelectedIndex();
		clientTable.setItems(null);
		clientTable.layout();
		clientsData.clear();
		List<Client> clients;
		String seachText = seachField.getText();
		if (seachText.equals("") || seachField.getText() == null) {
			clients = Factory.getInstance().getClientDAO().getAllClients();
		} else {
			clients = Factory.getInstance().getClientDAO()
					.getClientByName(seachText);
		}

		for (Client client : clients) {
			clientsData.add(client);
		}
		clientTable.setItems(clientsData);
		clientTable.getSelectionModel().select(selectedIndex);
	}
}
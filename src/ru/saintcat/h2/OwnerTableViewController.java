package ru.saintcat.h2;


import java.util.List;

import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.Owner;


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


public class OwnerTableViewController {
	@FXML
	private TableView<Owner> ownerTable;
	@FXML
	private TableColumn<Owner, Integer> idColumn;
	@FXML
	private TableColumn<Owner, String> nameColumn;
	@FXML
	private TableColumn<Owner, String> emailColumn;

	@FXML
	private TextField nameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField seachField;
	
	private ObservableList<Owner> ownersData = FXCollections.observableArrayList();
	private MainApp mainApp;

	public OwnerTableViewController() {
		
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<Owner, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Owner, String>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Owner, String>("email"));
		//ownerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	
		showOwnerDetails(null);
		refreshOwnerTable();
		
		ownerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Owner>() {
			@Override
			public void changed(ObservableValue<? extends Owner> observable,
					Owner oldValue, Owner newValue) {
				showOwnerDetails(newValue);
			}
		});
		
		
		
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void showOwnerDetails(Owner Owner) {
		if (Owner != null) {
			nameField.setText(Owner.getName());
			emailField.setText(Owner.getEmail());
		} else {
			nameField.setText("");
			emailField.setText("");
		}
	}
	
	@FXML
	private void handleSeach(){
		refreshOwnerTable();
	}

	@FXML
	private void handleDeleteOwner(){
		int selectedIndex = ownerTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
		Owner tmpOwner = ownerTable.getSelectionModel().getSelectedItem();
		Factory.getInstance().getOwnerDAO().deleteOwner(tmpOwner);
		showOwnerDetails(null);
		refreshOwnerTable();
		mainApp.getObjectTableController().updateComboBoxes();	
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a Owner in the table.",
					"No Owner Selected", "No Selection");
		}
	}

	@FXML
	private void handleNewOwner() {
		Owner tempOwner = new Owner();
		tempOwner.setName(nameField.getText());
		tempOwner.setEmail(emailField.getText());
		Factory.getInstance().getOwnerDAO().addOwner(tempOwner);
		refreshOwnerTable();
		mainApp.getObjectTableController().updateComboBoxes();
	}

	@FXML
	private void handleEditOwner() {
		Owner selectedOwner = ownerTable.getSelectionModel().getSelectedItem();
		selectedOwner.setName(nameField.getText());
		selectedOwner.setEmail(emailField.getText());
		
		Factory.getInstance().getOwnerDAO().updateOwner(selectedOwner);
		refreshOwnerTable();
		mainApp.getObjectTableController().updateComboBoxes();
		showOwnerDetails(selectedOwner);
	}
	
	private void refreshOwnerTable() {
		int selectedIndex = ownerTable.getSelectionModel().getSelectedIndex();
		ownerTable.setItems(null);
		ownerTable.layout();
		ownersData.clear();
		List<Owner> owners;
		String seachText = seachField.getText();
		if(seachText.equals("") || seachField.getText()==null){
			owners = Factory.getInstance().getOwnerDAO().getAllOwners();
		}
		else{
			owners = Factory.getInstance().getOwnerDAO().getOwnerByName(seachText);
		}
		for(Owner owner: owners){
			ownersData.add(owner);
		}
		ownerTable.setItems(ownersData);
		ownerTable.getSelectionModel().select(selectedIndex);
	}
}
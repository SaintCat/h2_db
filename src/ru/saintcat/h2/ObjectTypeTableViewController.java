package ru.saintcat.h2;


import java.util.List;

import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.ObjectType;
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


public class ObjectTypeTableViewController {
	@FXML
	private TableView<ObjectType> objectTypeTable;
	@FXML
	private TableColumn<ObjectType, Integer> idColumn;
	@FXML
	private TableColumn<ObjectType, String> typeColumn;
	@FXML
	private TableColumn<ObjectType, String> descriptionColumn;

	@FXML
	private TextField idField;
	@FXML
	private TextField typeField;
	@FXML
	private TextField descriptionField;

	private MainApp mainApp;
	private ObservableList<ObjectType> objectTypesData = FXCollections.observableArrayList();

	public ObjectTypeTableViewController() {
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<ObjectType, Integer>("id"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<ObjectType, String>("type"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<ObjectType, String>("description"));
		//ownerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		showObjectTypeDetails(null);
		
		refreshObjectTypeTable();

		objectTypeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ObjectType>() {

			@Override
			public void changed(ObservableValue<? extends ObjectType> observable,
					ObjectType oldValue, ObjectType newValue) {
				showObjectTypeDetails(newValue);
			}
		});
	}


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void showObjectTypeDetails(ObjectType object) {
		if (object != null) {
			idField.setText(object.getId().toString());
			typeField.setText(object.getType());
			descriptionField.setText(object.getDescription());
			
		} else {
			idField.setText("");
			typeField.setText("");
			descriptionField.setText("");
		}
	}
	

	@FXML
	private void handleDeleteObjectType() {
		int selectedIndex = objectTypeTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
		ObjectType tmpObject = objectTypeTable.getSelectionModel().getSelectedItem();
		Factory.getInstance().getObjectTypeDAO().deleteObjectType(tmpObject);
		showObjectTypeDetails(null);
		refreshObjectTypeTable();
		mainApp.getObjectTableController().updateComboBoxes();
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a Type in the table.",
					"No Type Selected", "No Selection");
		}
	}
	
	
	@FXML
	private void handleNewObjectType() {
		ObjectType tempType = new ObjectType();
		tempType.setType(typeField.getText());
		tempType.setDescription(descriptionField.getText());
		Factory.getInstance().getObjectTypeDAO().addObjectType(tempType);
		refreshObjectTypeTable();
		mainApp.getObjectTableController().updateComboBoxes();
		
	}

	@FXML
	private void handleEditObjectType() {
		ObjectType selectedObjectType = objectTypeTable.getSelectionModel().getSelectedItem();
		if (selectedObjectType != null) {
			selectedObjectType.setType(typeField.getText());
			selectedObjectType.setDescription(descriptionField.getText());
			Factory.getInstance().getObjectTypeDAO().updateObjectType(selectedObjectType);
			refreshObjectTypeTable();
			mainApp.getObjectTableController().updateComboBoxes();
			showObjectTypeDetails(selectedObjectType);
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a type in the table.",
					"No Type Selected", "No Selection");
		}
	}
	
	private void refreshObjectTypeTable() {
		int selectedIndex = objectTypeTable.getSelectionModel().getSelectedIndex();
		objectTypeTable.setItems(null);
		objectTypeTable.layout();
		objectTypesData.clear();
		
		List<ObjectType> objectTypes = Factory.getInstance().getObjectTypeDAO().getAllObjectTypes();
		for(ObjectType objectType: objectTypes){
			objectTypesData.add(objectType);
		}
		objectTypeTable.setItems(objectTypesData);
		objectTypeTable.getSelectionModel().select(selectedIndex);
	}
}
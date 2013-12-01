package ru.saintcat.h2;


import java.util.ArrayList;
import java.util.List;

import ru.saintcat.h2.dao.Factory;
import ru.saintcat.h2.model.Contract;
import ru.saintcat.h2.model.ObjectType;
import ru.saintcat.h2.model.Owner;
import ru.saintcat.h2.model.SaleObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class ObjectTableViewController {
	@FXML
	private static TableView<SaleObject> objectTable;
	@FXML
	private TableColumn<SaleObject, Integer> idColumn;
	@FXML
	private TableColumn<SaleObject, String> nameColumn;
	@FXML
	private TableColumn<SaleObject, Integer> priceColumn;
	@FXML
	private TableColumn<SaleObject, Integer> idObjectTypeColumn;
	@FXML
	private TableColumn<SaleObject, Boolean> checkColumn;
	@FXML
	private TableColumn<SaleObject, Integer> idContractColumn;

	@FXML
	private TextField nameField;
	@FXML
	private TextField priceField;
	@FXML
	private ComboBox<ObjectType> typeField;
	@FXML
	private ComboBox<Owner> ownerField;
	@FXML
	private static Label sumLabel;
	@FXML
	private TextField seachField;
	@FXML
	private TextField lowPriceField;
	@FXML
	private TextField highPriceField;
	@FXML
	private CheckBox onlyFreeCheckBox;
	
	@FXML
	private Button newContractButton;

	private MainApp mainApp;
	private ObservableList<SaleObject> objectsData = FXCollections.observableArrayList();
	private static List<SaleObject> objectsForSale = new ArrayList<SaleObject>();

	public ObjectTableViewController() {
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<SaleObject, Integer>("id"));
		idObjectTypeColumn.setCellValueFactory(new PropertyValueFactory<SaleObject, Integer>("idObjectType"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<SaleObject, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<SaleObject, Integer>("price"));
		checkColumn.setCellValueFactory(new PropertyValueFactory<SaleObject, Boolean>("selected"));
		idContractColumn.setCellValueFactory(new PropertyValueFactory<SaleObject, Integer>("idContract"));
		//objectTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		refreshObjectTable();
		
		checkColumn.setCellFactory(new Callback<TableColumn<SaleObject, Boolean>, TableCell<SaleObject, Boolean>>() {
            public TableCell<SaleObject, Boolean> call(TableColumn<SaleObject, Boolean> p) {
                return new CheckBoxTableCell<SaleObject, Boolean>();
            }
        });
		
		updateComboBoxes();
		showObjectDetails(null);

		objectTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleObject>() {
			@Override
			public void changed(ObservableValue<? extends SaleObject> observable, SaleObject oldValue, SaleObject newValue) {
				showObjectDetails(newValue);
			}
		});
		
	}
	
	
	
	public void updateComboBoxes() {
		List<ObjectType> types = Factory.getInstance().getObjectTypeDAO().getAllObjectTypes();
		typeField.setItems(FXCollections.observableList(types));
		
		List<Owner> owners = Factory.getInstance().getOwnerDAO().getAllOwners();
		ownerField.setItems(FXCollections.observableList(owners));
	}
	
	private static void updateCount(int index, Boolean s) {
		SaleObject tmp = objectTable.getItems().get(index);

		Long sum = Long.valueOf(sumLabel.getText());
		if (s == true) {
			sum += tmp.getPrice();
			objectsForSale.add(tmp);
		} else {
			sum -= tmp.getPrice();
			objectsForSale.remove(tmp);
		}
		if(objectsForSale.isEmpty()){
		}
		sumLabel.setText(String.valueOf(sum));
	}

	private static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
        private final CheckBox checkBox;
        private ObservableValue<T> ov;
 
        public CheckBoxTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
            checkBox.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					 SaleObject tmp = objectTable.getItems().get(getIndex());
					 if(tmp.getIdContract() != null) {
						 checkBox.setDisable(true); 
						 checkBox.setSelected(false);
					 }
					 else
						 updateCount(getIndex(), (Boolean) ov.getValue());
				}
            });
            
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        } 
        
        @Override public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                	
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
        	
        }
    }
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void showObjectDetails(SaleObject object) {
		if (object != null){
		
			for(ObjectType t: typeField.getItems()){
				if(t.getId() == object.getIdObjectType()){
					typeField.getSelectionModel().select(t);
					break;
				}
			}
			
			for(Owner w: ownerField.getItems()){
				if(w.getId() == object.getIdOwner()){
					ownerField.getSelectionModel().select(w);
					break;
				}
			}
			nameField.setText(object.getName());
			priceField.setText(object.getPrice().toString());
			
		} else {
			
			typeField.getSelectionModel().select(null);
			nameField.setText("");
			priceField.setText("");
			ownerField.getSelectionModel().select(null);
		}
	}
	

	@FXML
	private void handleDeleteObject() {
		int selectedIndex = objectTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
		SaleObject tmpObject = objectTable.getSelectionModel().getSelectedItem();
		Factory.getInstance().getSaleObjectDAO().deleteSaleObject(tmpObject);
		showObjectDetails(null);
		refreshObjectTable();
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a Owner in the table.",
					"No Owner Selected", "No Selection");
		}
	}
	
	
	@FXML
	private void handleNewObject() {
		SaleObject tempObject = new SaleObject();
		
		tempObject.setIdObjectType(typeField.getSelectionModel().getSelectedItem().getId());
		tempObject.setIdOwner(ownerField.getSelectionModel().getSelectedItem().getId());
		tempObject.setName(nameField.getText());
		tempObject.setPrice(Long.valueOf(priceField.getText()));
		
		Factory.getInstance().getSaleObjectDAO().addSaleObject(tempObject);
		refreshObjectTable();
		
	}

	@FXML
	private void handleEditObject() {
		SaleObject selectedObject = objectTable.getSelectionModel().getSelectedItem();
		if (selectedObject != null) {
			
			selectedObject.setIdObjectType(typeField.getSelectionModel().getSelectedItem().getId());
			selectedObject.setIdOwner(ownerField.getSelectionModel().getSelectedItem().getId());
			selectedObject.setName(nameField.getText());
			selectedObject.setPrice(Long.valueOf(priceField.getText()));
			
			Factory.getInstance().getSaleObjectDAO().updateSaleObject(selectedObject);
			refreshObjectTable();
			showObjectDetails(selectedObject);
		} else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a person in the table.",
					"No Person Selected", "No Selection");
		}
	}
	@FXML
	private void handleNewContract() {
		if (objectsForSale.isEmpty() == false) {
			List<Long> objectIds = new ArrayList<Long>();
			for (SaleObject object : objectsForSale) {
				objectIds.add(object.getId());
			}

			Contract contract = new Contract();
			contract.setSum(Long.valueOf(sumLabel.getText()));

			boolean okClicked = mainApp.loadObjectTableContractView(contract,
					objectIds);
			if (okClicked) {
				Factory.getInstance().getContractDAO().addContract(contract);
				for (SaleObject object : objectsForSale) {
					object.setIdContract(contract.getId());
					Factory.getInstance().getSaleObjectDAO().updateSaleObject(object);
					mainApp.getContractTableController().refreshContractTable();
				}
				refreshObjectTable();
			} 
		}else {
			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
					"Please select a nosale object in the table.",
					"No Object Selected", "No Selection");
		}
	}
	
	@FXML
	private void handleRefresh() {
		refreshObjectTable();
	}
	
	private void refreshObjectTable() {
		int selectedIndex = objectTable.getSelectionModel().getSelectedIndex();
		objectTable.setItems(null);
		objectTable.layout();
		sumLabel.setText("0");
		objectsForSale.clear();
		
		objectsData.clear();
		List<SaleObject> objects;
		String seachText;
		boolean onlyFree = onlyFreeCheckBox.isSelected();
		Long lowPrice=null;
		Long highPrice=null;
		try {
			lowPriceField.setStyle("-fx-text-fill: black;");
			lowPrice = (lowPriceField.getText() == null || lowPriceField
					.getText().equals("")) ? Long.MIN_VALUE : Long
					.valueOf(lowPriceField.getText());

		} catch (NumberFormatException ex) {
			lowPriceField.setStyle("-fx-text-fill: red;");
		}

		try {
			highPriceField.setStyle("-fx-text-fill: black;");
			highPrice = (highPriceField.getText() == null || highPriceField
					.getText().equals("")) ? Long.MAX_VALUE : Long
					.valueOf(highPriceField.getText());
		} catch (NumberFormatException ex) {
			highPriceField.setStyle("-fx-text-fill: red;");
		}
		
		seachText = (seachField.getText().equals("") || seachField.getText()==null) ? "" : seachField.getText();
			
	    objects = Factory.getInstance().getSaleObjectDAO().getSaleObjectByParam(seachText, onlyFree, lowPrice, highPrice);
		
		
		for(SaleObject object: objects){
			objectsData.add(object);
		}
		objectTable.setItems(objectsData);
		objectTable.getSelectionModel().select(selectedIndex);
	}
}
package ru.saintcat.h2;


import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class RootLayoutViewController {

    @FXML
    private ToggleGroup sd;

    @FXML
    void handle1(MouseEvent event) {
    	mainApp.showOwnerTable();
    }

    @FXML
    void handle2(MouseEvent event) {
    	mainApp.showObjectTable();
    }
    
    @FXML
    void handle3(MouseEvent event) {
    	mainApp.showLoginScreen();
    }
    @FXML
    void handle4(MouseEvent event) {
    	mainApp.getPrimaryStage().close();
    }
    @FXML
    void handleObjectTypes(MouseEvent event){
    	mainApp.showObjectTypeTable();
    }
    @FXML
    void handleClients(MouseEvent event){
    	mainApp.showClientTable();
    }
    @FXML
    void handleWorkers(MouseEvent event){
    	mainApp.showWorkerTable();
    }
    @FXML
    void handleContracts(MouseEvent event){
    	mainApp.showContractTable();
    }
    
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleAbout() {
		Dialogs.showInformationDialog(mainApp.getPrimaryStage(), "Author: Roman Chernishev", "Database", "About");
	}
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}
	
}


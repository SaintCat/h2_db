package ru.saintcat.h2;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.saintcat.h2.model.Contract;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private Scene loginScreenScene;
	private Scene rootLayoutScene;
	private AnchorPane ownerTablePane;
	private AnchorPane objectTablePane;
	private AnchorPane objectTypeTablePane;
	private AnchorPane clientTablePane;
	private AnchorPane workerTablePane;
	private AnchorPane contractTablePane;
	private ObjectTableViewController objectTableController;
	private ContractTableViewController contractTableController;
	private static final String windowsTitle = "Торговля недвижимостью";
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		this.primaryStage.setTitle(windowsTitle);
		loadLoginScreenView();
		showLoginScreen();
		primaryStage.show();
	}
	
	public void loadTables(){
		loadRootLayoutView();
		loadOwnerTableView();
		
		loadObjectTypeTableView();
		loadClientTableView();
		loadWorkerTableView();
		loadContractTableView();
		loadObjectTableView();
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void  showOwnerTable(){
		rootLayout.setCenter(ownerTablePane);
	}
	public void showObjectTable(){
		rootLayout.setCenter(objectTablePane);
	}
	public void showObjectTypeTable(){
		rootLayout.setCenter(objectTypeTablePane);
	}
	public void showClientTable(){
		rootLayout.setCenter(clientTablePane);
	}
	public void showWorkerTable(){
		rootLayout.setCenter(workerTablePane);
	}
	public void showContractTable(){
		rootLayout.setCenter(contractTablePane);
	}
	public void showLoginScreen(){
		primaryStage.setScene(loginScreenScene);
		primaryStage.centerOnScreen();
	}
	public void showRootLayout(){
		primaryStage.setScene(rootLayoutScene);
		primaryStage.centerOnScreen();
	}

	private void loadRootLayoutView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayoutView.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayoutScene = new Scene(rootLayout);
			RootLayoutViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	

	private void loadOwnerTableView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/OwnerTableView.fxml"));
			ownerTablePane = (AnchorPane) loader.load();
			OwnerTableViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadObjectTableView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ObjectTableView.fxml"));
			objectTablePane = (AnchorPane) loader.load();
			ObjectTableViewController controller = loader.getController();
			this.objectTableController = controller;
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadLoginScreenView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/LoginView.fxml"));
			loginScreenScene = new Scene((Parent) loader.load());
			LoginScreenViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
	
	private void loadObjectTypeTableView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ObjectTypeTableView.fxml"));
			objectTypeTablePane = (AnchorPane) loader.load();
			ObjectTypeTableViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadContractTableView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ContractTableView.fxml"));
			contractTablePane = (AnchorPane) loader.load();
			ContractTableViewController controller = loader.getController();
			this.contractTableController = controller;
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void loadClientTableView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ClientTableView.fxml"));
			clientTablePane = (AnchorPane) loader.load();
			ClientTableViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadWorkerTableView() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/WorkerTableView.fxml"));
			workerTablePane = (AnchorPane) loader.load();
			WorkerTableViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean loadObjectTableContractView(Contract conract, List<Long> objects) {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ObjectTableContractView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Conract");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			ObjectTableContractViewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setContract(conract, objects);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ObjectTableViewController getObjectTableController(){
		return objectTableController;
	}
	
	public ContractTableViewController getContractTableController(){
		return contractTableController;
	}

}

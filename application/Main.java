package application;
import application.SettingPage;
import java.lang.*;	
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application implements Initializable{
	Stage newstage;
	
	public void start(Stage primaryStage) throws IOException 
	{
	
		newstage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}
	
	@FXML
	private ComboBox<String> combobox , combobox2;
	@FXML
	private Button b1;
	@FXML
	private Button b2;
	
	
	
	ObservableList<String> list = FXCollections.observableArrayList("2 Player", "3 Player", "4 Player", "5 Player", "6 Player", "7 Player", "8 Player");
	ObservableList<String> list2 = FXCollections.observableArrayList("9 x 6", "15 x 12");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combobox.setItems(list);
		combobox2.setItems(list2);
		
		Color c = Color.WHITE;
		for(int i = 0 ; i < 8 ; i ++){
			SettingPage.playerColor.add(c);
		}
	}

	static String noOfPlayers ;
	static String gridSize;
	
	@FXML
	private void test(ActionEvent event){
		noOfPlayers = combobox.getValue();
	}
	
	@FXML
	private void test2(ActionEvent event){
		gridSize = combobox2.getValue();
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		
		Parent root;
		root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
 		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML 
	private void handleNewGame(ActionEvent event) throws IOException{
		
		Grid96 g= new Grid96();
		Scene scene_grid= g.makeSceneGrid();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene_grid);
		stage.show();
	}
	public static void main(String[] args) {
		
		launch(args);
		
		//System.out.println(noOfPlayers);
		
	}
}


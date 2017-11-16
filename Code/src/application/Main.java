package application;
//import application.SettingPage;
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
	static Stage newstage;
	
	public void start(Stage primaryStage) throws IOException 
	{
		newstage = primaryStage;
		Scene scene_main=main_page();
		primaryStage.setScene(scene_main);
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}
	
	public Scene main_page() throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	
	@FXML
	private ComboBox<String> combobox , combobox2;
	@FXML
	private Button b1;
	@FXML
	private Button b2;
	
	ObservableList<String> list = FXCollections.observableArrayList("2 Player", "3 Player", "4 Player", "5 Player", "6 Player", "7 Player", "8 Player");
	ObservableList<String> list2 = FXCollections.observableArrayList("9 x 6", "15 x 10");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combobox.setItems(list);
		combobox2.setItems(list2);
		
		Color c = Color.WHITE;
		SettingPage.playerColor.add(Color.RED);
		SettingPage.playerColor.add(Color.BLUE);
		for(int i = 2 ; i < 8 ; i ++){
			SettingPage.playerColor.add(c);
		}
	}

	static String noOfPlayers ;
	static String gridSize;
	
	@FXML
	private void test(ActionEvent event){
		noOfPlayers = combobox.getValue();
		combobox.setPromptText(noOfPlayers);
	}
	
	@FXML
	private void test2(ActionEvent event){
		gridSize = combobox2.getValue();
		combobox2.setPromptText(gridSize);
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		
		Parent root;
		root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
 		Scene scene = new Scene(root);
		newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newstage.setScene(scene);
		newstage.show();
	}
	
	@FXML 
	private void handleNewGame(ActionEvent event) throws IOException{
		
		Grid96 g= new Grid96();
		Scene scene_grid= g.makeSceneGrid();
		newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newstage.setScene(scene_grid);
		newstage.show();
	}
	public static void main(String[] args) {
		
		launch(args);
		
		//System.out.println(noOfPlayers.charAt(0));
		
	}
}


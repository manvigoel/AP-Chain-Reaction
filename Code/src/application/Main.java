package application;
//import application.SettingPage;
import java.lang.*;
import java.io.File;
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

/**
 * Date : November 18, 2017
 * Main class implements an application to start the main page of the game
 *  
 * @author manvigoel, arpitbhatia
 */
public class Main extends Application implements Initializable{
	/**
	 * newstage variable of type stage
	 */
	static Stage newstage;
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	public void start(Stage primaryStage) throws IOException 
	{
		newstage = primaryStage;
		Scene scene_main=main_page();
		primaryStage.setScene(scene_main);
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}
	/**
	 * creates the scene for the main page
	 * @return scene of the main page
	 * @throws IOException
	 */
	public Scene main_page() throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	/**
	 * combobox1, and combobo2 of type combobox for input from user
	 */
	@FXML
	private ComboBox<String> combobox , combobox2;
	
	/**
	 * button to direct to direct page
	 */
	@FXML
	private Button b1;
	
	/**
	 * button to direct to new game
	 */
	@FXML
	private Button b2;
	
	/**
	 * button for undo
	 */
	@FXML
	private Button b3;
	
	/**
	 * list to store values to select number of players
	 */
	ObservableList<String> list = FXCollections.observableArrayList("2 Player", "3 Player", "4 Player", "5 Player", "6 Player", "7 Player", "8 Player");
	
	/**
	 * stores values to select the grid size
	 */

	ObservableList<String> list2 = FXCollections.observableArrayList("9 x 6", "15 x 10");
	
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combobox.setItems(list);
		combobox2.setItems(list2);
		
		//Color c = Color.WHITE;
		SettingPage.playerColor.add(Color.RED);
		SettingPage.playerColor.add(Color.GREEN);
		SettingPage.playerColor.add(Color.BLUE);
		SettingPage.playerColor.add(Color.YELLOW);
		SettingPage.playerColor.add(Color.PURPLE);
		SettingPage.playerColor.add(Color.TURQUOISE);
		SettingPage.playerColor.add(Color.ORANGE);
		SettingPage.playerColor.add(Color.WHITE);
		
		boolean show = true;
		
		File file = new File(".");
		File files[] = file.listFiles();
		for(File f : files){
			if(f.getName().equals("out.txt") || f.getName().equals("out2.txt")){
				b3.setDisable(false);
				show = false;
			}
			if(show == true){
				b3.setDisable(true);
			}
			
		}
	}

	
	/**
	 * stores the number of players in the game
	 */
	static String noOfPlayers ;
	
	/**
	 * stores gridsize 
	 */
	static String gridSize;
	
	
	/**
	 * event handler to store number of players 
	 * @param event
	 * 
	 */
	@FXML
	private void test(ActionEvent event){
		noOfPlayers = combobox.getValue();
		combobox.setPromptText(noOfPlayers);
	}
	
	/**
	 * event handler to store grid size 
	 * @param event
	 */
	@FXML
	private void test2(ActionEvent event){
		gridSize = combobox2.getValue();
		combobox2.setPromptText(gridSize);
	}
	
	/**
	 * event handler to load the settings page 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Parent root;
		root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
 		Scene scene = new Scene(root);
		newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newstage.setScene(scene);
		newstage.show();
	}
	
	/**
	 * event handler to load a new page
	 * @param event
	 * @throws IOException
	 */
	@FXML 
	private void handleNewGame(ActionEvent event) throws IOException{
		
		Grid96 g= new Grid96();
		Scene scene_grid= g.makeSceneGrid();
		newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newstage.setScene(scene_grid);
		newstage.show();
	}
	
	/**
	 * event handler to resume last scene
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleResumeButton(ActionEvent event) throws IOException{
		Grid96 g = new Grid96();
		Scene scene_grid= g.resumeSceneGrid();
		newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newstage.setScene(scene_grid);
		newstage.show();
	}
	
	
	
	/**
	 * main method of this application
	 * @param args array of string arguments
	 */
	public static void main(String[] args) {
		launch(args);
		//System.out.println(noOfPlayers.charAt(0));
		
	}
}


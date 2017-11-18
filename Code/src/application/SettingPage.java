package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Date : November 18, 2017
 * The SettingPage class implements an application that stores the color of each player as entered by the user.
 * It implements the initializable interface.
 * 
 * @author manvigoel, arpitbhatia
 */

public class SettingPage extends Application implements Initializable{
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
	
		Stage newstage = new Stage();
		Parent anotherroot = FXMLLoader.load(getClass().getResource("/application/Settings.fxml"));
		Scene newscene = new Scene(anotherroot);
		newstage.setScene(newscene);
		newstage.show();
			
		}
	/**
	 * button to direct to home page
	 */
	@FXML
	private Button b2;
	
	/**
	 * araylist of type color to store color of each player
	 */
	static ArrayList<Color> playerColor = new ArrayList<Color>();
	
	/**
	 * colorpicker to store color of player 1
	 */
	@FXML
	private ColorPicker cp1;
	/**
	 * colorpicker to store color of player 2
	 */
	@FXML
	private ColorPicker cp2;
	/**
	 * colorpicker to store color of player 3
	 */
	@FXML
	private ColorPicker cp3;
	/**
	 * colorpicker to store color of player 4
	 */
	@FXML
	private ColorPicker cp4;
	/**
	 * colorpicker to store color of player 5
	 */
	@FXML
	private ColorPicker cp5;
	/**
	 * colorpicker to store color of player 6
	 */
	@FXML
	private ColorPicker cp6;
	/**
	 * colorpicker to store color of player 7
	 */
	@FXML
	private ColorPicker cp7;
	/**
	 * colorpicker to store color of player 8
	 */
	@FXML
	private ColorPicker cp8;
	
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		totalPlayers = Character.getNumericValue(Main.noOfPlayers.charAt(0));
		
	}

	/**
	 * directs to the home page
	 * @param event b2 button on clicking directs to the home page 
	 * @throws IOException
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		
		Parent root;
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
 		
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	
	}
	
	/**
	 * sets the color for the player1 as selected  
	 * @param event 
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker1(ActionEvent event) throws IOException{
		playerColor.set(0, cp1.getValue());
	
	}
	
	/**
	 * int value to store the total number of players in the game
	 */
	int totalPlayers;
	
	
	/**
	 *  sets the color for the player2 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker2(ActionEvent event) throws IOException{
		
		if(cp2.getValue().equals(cp1.getValue())){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
				
		
		playerColor.set(1, cp2.getValue());
		//System.out.println("player2 " + cp2.getValue());
		
	}
	
	
	/**
	 *  sets the color for the player3 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker3(ActionEvent event) throws IOException{
		
		if(cp3.getValue().equals(cp1.getValue()) || cp3.getValue().equals(cp2.getValue())){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
		playerColor.set(2, cp3.getValue());
		System.out.println("player3 " + cp3.getValue());
		
	}
	
	/**
	 * sets the color for the player 4 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker4(ActionEvent event) throws IOException{
		if(cp4.getValue().equals(cp1.getValue()) || cp4.getValue().equals(cp2.getValue()) || cp4.getValue().equals(cp3.getValue())){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
		
		playerColor.set(3, cp4.getValue());
	//	System.out.println("player4 " + cp4.getValue());
		
	}
	
	
	/**
	 *  sets the color for the player5 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker5(ActionEvent event) throws IOException{
		if(cp5.getValue().equals(cp1.getValue()) || cp5.getValue().equals(cp2.getValue()) || cp5.getValue().equals(cp3.getValue()) || cp5.getValue().equals(cp4.getValue())){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
		
		playerColor.set(4, cp5.getValue());
	//	System.out.println("player5 " + cp5.getValue());
		
	}
	
	
	/**
	 *  sets the color for the player6 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker6(ActionEvent event) throws IOException{
		if(cp6.getValue().equals(cp1.getValue()) || cp6.getValue().equals(cp2.getValue()) || cp6.getValue().equals(cp3.getValue()) || cp6.getValue().equals(cp4.getValue())  || cp6.getValue().equals(cp5.getValue()) ){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
		playerColor.set(5, cp6.getValue());
	//	System.out.println("player6 " + cp6.getValue());
		
	}
	
	/**
	 *  sets the color for the player7 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker7(ActionEvent event) throws IOException{
		if(cp7.getValue().equals(cp1.getValue()) || cp7.getValue().equals(cp2.getValue()) || cp7.getValue().equals(cp3.getValue()) || cp7.getValue().equals(cp4.getValue())  || cp7.getValue().equals(cp5.getValue()) || cp7.getValue().equals(cp6.getValue()) ){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
		playerColor.set(6, cp7.getValue());
	//	System.out.println("player7 " + cp7.getValue());
		
	}
	
	/**
	 * sets the color for the player 8 as selected
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlecolorPicker8(ActionEvent event) throws IOException{
		if(cp8.getValue().equals(cp1.getValue()) || cp8.getValue().equals(cp2.getValue()) || cp8.getValue().equals(cp3.getValue()) || cp8.getValue().equals(cp4.getValue())  || cp8.getValue().equals(cp5.getValue()) || cp8.getValue().equals(cp6.getValue()) || cp8.getValue().equals(cp7.getValue())){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Color already taken! Choose another one");
			alert.showAndWait();
		}
		playerColor.set(7, cp8.getValue());
	//	System.out.println("player8 " + cp8.getValue());
	//	
	}
	
	
	/**
	 * @return arraylist of player's colors
	 */
	public static ArrayList<Color> getColor1(){
		SettingPage ob1 = null ;
		return ob1.playerColor;
	}
	
	/**
	 * main method of this application
	 * @param args array of string arguments
	 */
	public static void main(String[] args) {
		
		launch(args);
	}
}

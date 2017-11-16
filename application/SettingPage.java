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
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SettingPage extends Application implements Initializable{
	@Override
	public void start(Stage primaryStage) throws IOException {
	
		Stage newstage = new Stage();
		Parent anotherroot = FXMLLoader.load(getClass().getResource("/application/Settings.fxml"));
		Scene newscene = new Scene(anotherroot);
		newstage.setScene(newscene);
		newstage.show();
			
		}
	@FXML
	private Button b2;
	
	static ArrayList<Color> playerColor = new ArrayList<Color>();
	
	
	@FXML
	private ColorPicker cp1;
	@FXML
	private ColorPicker cp2;
	@FXML
	private ColorPicker cp3;
	@FXML
	private ColorPicker cp4;
	@FXML
	private ColorPicker cp5;
	@FXML
	private ColorPicker cp6;
	@FXML
	private ColorPicker cp7;
	@FXML
	private ColorPicker cp8;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		
		Parent root;
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
 		
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handlecolorPicker1(ActionEvent event) throws IOException{
		playerColor.set(0, cp1.getValue());
		System.out.println("player1 " + cp1.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker2(ActionEvent event) throws IOException{
		playerColor.set(1, cp1.getValue());
		System.out.println("player2 " + cp1.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker3(ActionEvent event) throws IOException{
		playerColor.set(2, cp1.getValue());
		System.out.println("player3 " + cp3.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker4(ActionEvent event) throws IOException{
		playerColor.set(3, cp1.getValue());
		System.out.println("player4 " + cp4.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker5(ActionEvent event) throws IOException{
		playerColor.set(4, cp1.getValue());
		System.out.println("player5 " + cp5.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker6(ActionEvent event) throws IOException{
		playerColor.set(5, cp1.getValue());
		System.out.println("player6 " + cp6.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker7(ActionEvent event) throws IOException{
		playerColor.set(6, cp1.getValue());
		System.out.println("player7 " + cp7.getValue());
		
	}
	
	@FXML
	private void handlecolorPicker8(ActionEvent event) throws IOException{
		playerColor.set(7, cp1.getValue());
		System.out.println("player8 " + cp8.getValue());
		
	}
	
	
	public static ArrayList<Color> getColor1(){
		SettingPage ob1 = null ;
		return ob1.playerColor;
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}

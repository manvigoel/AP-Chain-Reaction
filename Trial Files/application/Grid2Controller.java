package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class Grid2Controller  {

	
	@FXML
	
	public ChoiceBox choicebox2;
	
	ObservableList<String> box2 = FXCollections.observableArrayList("Settings", "New Game");
	
	
	
	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		choicebox2.setItems(box2);

	}

}

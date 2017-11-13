package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class GridBox  {

	
	@FXML
	
	public ChoiceBox choicebox;
	
	ObservableList<String> choicebox1 = FXCollections.observableArrayList("Settings", "New Game");
	
	
	
	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		choicebox.setItems(choicebox1);

	}

}

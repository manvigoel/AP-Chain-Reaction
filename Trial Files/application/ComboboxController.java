package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
 
public class ComboboxController implements Initializable {

	@FXML
	public ComboBox<String> combobox , combobox2;
	@FXML
	private Button b1;
	
	ObservableList<String> list = FXCollections.observableArrayList("2 Player", "3 Player", "4 Player", "5 Player", "6 Player", "7 Player", "8 Player");
	ObservableList<String> list2 = FXCollections.observableArrayList("9 x 6", "15 x 12");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combobox.setItems(list);
		combobox2.setItems(list2);
		
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
}

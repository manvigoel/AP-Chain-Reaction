package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
 
public class ComboboxController implements Initializable {

	@FXML
	public ComboBox<String> combobox , combobox2;
	
	ObservableList<String> list = FXCollections.observableArrayList("2 Player", "3 Player", "4 Player", "5 Player", "6 Player", "7 Player", "8 Player");
	ObservableList<String> list2 = FXCollections.observableArrayList("9 x 6", "15 x 12");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combobox.setItems(list);
		combobox2.setItems(list2);
		
	}

}

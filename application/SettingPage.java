package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingPage extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
	
			Stage newstage = new Stage();
			Parent anotherroot = FXMLLoader.load(getClass().getResource("/application/Settings.fxml"));
			Scene newscene = new Scene(anotherroot);
			newstage.setScene(newscene);
			newstage.show();
		}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}

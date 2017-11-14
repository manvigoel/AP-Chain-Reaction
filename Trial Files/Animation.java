import java.sql.Time;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animation extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root, 200, 200);
		root.setTranslateX(100);
		root.setTranslateY(100);
		primaryStage.setScene(scene);
		createScene(root);
		primaryStage.show();
	}

	private void createScene(Group branchy) {
		// Create a timeline for rotation and add keyframes
		Group branch=new Group();
		Group branch2=new Group();
		
		branchy.getChildren().add(branch);
		branchy.getChildren().add(branch2);
		
		
		Timeline rot = new Timeline();
		rot.setRate(2);
		rot.setCycleCount(Timeline.INDEFINITE);
		KeyFrame startk = new KeyFrame(Duration.ZERO, new KeyValue(branch.rotateProperty(), 0));
		KeyFrame endk = new KeyFrame(Duration.seconds(5), new KeyValue(branch.rotateProperty(), 360));
		rot.getKeyFrames().add(startk);
		rot.getKeyFrames().add(endk);
		rot.playFromStart();
		
		Timeline rot2 = new Timeline();
		rot2.setRate(1);
		rot2.setCycleCount(Timeline.INDEFINITE);
		KeyFrame startk2 = new KeyFrame(Duration.ZERO, new KeyValue(branch2.rotateProperty(), 0));
		KeyFrame endk2 = new KeyFrame(Duration.seconds(5), new KeyValue(branch2.rotateProperty(), 360));
		rot2.getKeyFrames().add(startk2);
		rot2.getKeyFrames().add(endk2);
		rot2.playFromStart();

		// Make spheres
		Material kMaterial = new PhongMaterial();
		((PhongMaterial) kMaterial).setDiffuseColor(Color.RED);
		Sphere r = new Sphere(10);
		r.setMaterial(kMaterial);
		branch.getChildren().add(r);
		Sphere c = new Sphere(10);
		r.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			c.setMaterial(kMaterial);
			branch.getChildren().add(c);
			c.setTranslateX(-10);
		});
		Sphere d = new Sphere(10);
		c.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			d.setMaterial(kMaterial);
			branch.getChildren().add(d);
			d.setTranslateY(-10);
		});
		
		// Make side waale sphere
		
		Material kMaterial2 = new PhongMaterial();
		((PhongMaterial) kMaterial2).setDiffuseColor(Color.GREEN);
		Sphere r2 = new Sphere(10);
		r2.setMaterial(kMaterial2);
		r2.setTranslateX(-50);
		branch2.getChildren().add(r2);
		Sphere c2 = new Sphere(10);
		r2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			c2.setMaterial(kMaterial2);
			branch2.getChildren().add(c2);
			c2.setTranslateX(-60);
		});
		Sphere d2 = new Sphere(10);
		c2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			d2.setMaterial(kMaterial2);
			branch.getChildren().add(d2);
			d2.setTranslateY(-10);
		});
		
		
		d.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			rot.stop();
			branch.setRotate(0);
			;
			TranslateTransition rt = new TranslateTransition(Duration.seconds(0.5), r);
			rt.setFromX(0);
			rt.setToX(r.getLayoutX() + 40);
			TranslateTransition ct = new TranslateTransition(Duration.seconds(0.5), c);
			ct.setFromX(0);
			ct.setToX(c.getLayoutX() - 40);
			TranslateTransition dt = new TranslateTransition(Duration.seconds(0.5), d);
			dt.setFromY(0);
			dt.setToY(d.getLayoutY() + 40);
			Sphere f=new Sphere(10);
			f.setMaterial(kMaterial);
			branch.getChildren().add(f);
			TranslateTransition ft = new TranslateTransition(Duration.seconds(0.5), f);
			ft.setFromY(0);
			ft.setToY(f.getLayoutY() - 40);
			ct.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					branch.getChildren().remove(c);
					Sphere r3 = new Sphere(10);
					r3.setMaterial(kMaterial);
					r3.setTranslateX(-40);
					branch2.getChildren().add(r3);
					r2.setMaterial(kMaterial);
					d2.setMaterial(kMaterial);
				}
			});
			ft.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					//branch.getChildren().remove(f);
				}
			});
			ft.play();
			rt.play();
			ct.play();
			//r2.setMaterial(kMaterial);
			//d2.setMaterial(kMaterial);
			dt.play();
		});
	}

}

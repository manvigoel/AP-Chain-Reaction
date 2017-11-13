package application;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Grid3D extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
        Scene scene = new Scene(root,500, 650);
        
        
        Line line1 ;
        
        //gridsmall
        for(int i = 150 ; i < 650 ; i = i + 50){
        	line1 = new Line(100, i, 400, i);
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        }
        
        for(int i = 100 ; i < 450 ; i = i + 50){
        	line1 = new Line( i, 150 , i , 600 );
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        }
        
        //gridbig
        
        for(int i = 132 ; i < 630 ; i = i + 54){
        	line1 = new Line(88, i, 412, i);
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        }
        
        for(int i = 88 ; i < 420 ; i = i + 54){
        	line1 = new Line( i, 132 , i , 618 );
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        }
        
        //nestedloop
        int x1 = 88;
        int y1 = 132;
        int x2 = 100;
        int y2 = 150;
        
        for(int i = 1; i <= 10 ; i ++){
        	x1 = 88;
        	x2 = 100;
        	for(int j = 1; j < 8 ; j ++){
        		line1 = new Line(x1, y1, x2, y2);
        		root.getChildren().add(line1);
        		line1.setStroke(Color.RED);
        		x1 = x1 + 54;
        		x2 = x2 + 50;
        	}
        	y1 = y1 + 54;
        	y2 = y2 + 50;
        }
        
       
        
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	

}

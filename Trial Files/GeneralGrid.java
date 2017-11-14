import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GeneralGrid extends Application{
	
	static int row = 0, col = 0;
	static float cell_small = 0 , cell_big = 0;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group root = new Group();
        Scene scene = new Scene(root,500, 650);
        Line line1 ;
        
        //gridsmall
        //horizontal lines
        float i = 150;
        for(int m = 0 ; m <= row ; m ++){
        	line1 = new Line(100, i, 400, i);
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        	i = i + cell_small;
        }
        
        //vertical lines
        i = 100;
        for(int m = 0 ; m <= col ; m ++){
        	line1 = new Line( i, 150 , i , 600 );
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        	i = i + cell_small;
        }
        
        //gridbig
        i = 132;
        for(int m = 0 ; m <= row ; m ++){
        	line1 = new Line(88, i, 412, i);
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        	i = i + cell_big;
        }
        i = 88;
        for(int m = 0 ; m <= col ;  m ++){
        	line1 = new Line( i, 132 , i , 618 );
        	line1.setStroke(Color.RED);
        	root.getChildren().add(line1);
        	i = i + cell_big;
        }
        
        //nestedloop
        float x1 = 88;
        float y1 = 132;
        float x2 = 100;
        float y2 = 150;
        
        for(i = 1; i <= row + 1 ; i ++){
        	x1 = 88;
        	x2 = 100;
        	for(int j = 1; j <= col + 1 ; j ++){
        		line1 = new Line(x1, y1, x2, y2);
        		root.getChildren().add(line1);
        		line1.setStroke(Color.RED);
        		x1 = x1 + cell_big;
        		x2 = x2 + cell_small;
        	}
        	y1 = y1 + cell_big;
        	y2 = y2 + cell_small;
        }
        
       
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public static void main (String[] args) {
	        Scanner sc = new Scanner(System.in);
		System.out.println("Enter grid size");
	        int x = sc.nextInt();
	        int y = sc.nextInt();
	        
	        if(x == 9 && y == 6){
	        	row = 9;
	        	col = 6;
	        	cell_small = 50;
	        	cell_big = 54;
	        }
	        else{
	        	row = 15;
	        	col = 10;
	        	cell_small = 30;
	        	cell_big = (float) 32.4;
	        }
	        
		launch(args);
	}

	

}

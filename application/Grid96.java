package application;



import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import application.Cell;
import application.Coordinate;
import application.Player;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Grid96 extends Application {
	static Cell array[][];
	static int rows , col;
	static float cell_size = 40;
	int xc, yc;
	int noOfPlayers = 0;
	String size = ("9 x 6");
	int turncounter=0;
	ArrayList<Player> players = new ArrayList<Player>();
	static Player current;
	//int currentx;
	//int currenty;

	Grid96(){
		Main ob = new Main();
		this.noOfPlayers = Character.getNumericValue(Main.noOfPlayers.charAt(0));
		
		System.out.println(this.noOfPlayers);
		for(int i = 0 ;i < this.noOfPlayers ; i ++){
			Player p = new Player("Player " + String.valueOf(i+1) , SettingPage.playerColor.get(i));
			players.add(p);
			
		}
		size = Main.gridSize;
		if(size.equalsIgnoreCase("9 x 6")){
			rows = 9;
			col = 6;
			cell_size = 55;
		}
		else{
			rows = 15;
			col = 10;
			cell_size = 45;
		}
	}

	public static void cgc(Rectangle[][] vis, Player p) {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				vis[i][j].setStroke(p.color);
			}
			}
	}
	
	public Scene makeSceneGrid()
	{
		current = players.get(0);
		Player other = players.get(1);
		Group root = new Group();
		Scene scene = new Scene(root, 550, 800, Color.BLACK);
		array = new Cell[rows + 1][col + 1];
		int noOfPlayers = 2;
		int startx = 0, starty = 0;
		
		if(rows == 15){
			startx = (int) cell_size;
			starty = (int) 100;
			
		}
		else{
			startx = (int) cell_size + 40;
			starty = (int) 200;
			
		}
			
		Button btn1 = new Button();
		btn1.setText("UNDO");
		btn1.setLayoutX(80);
		btn1.setLayoutY(40);
		
		
		
		Button btn2 = new Button();
		btn2.setText("NEW GAME");
		btn2.setLayoutX(200);
		btn2.setLayoutY(40);
		btn2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				Grid96 g= new Grid96();
				Scene scene_grid= g.makeSceneGrid();
				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Main.newstage.setScene(scene_grid);
				Main.newstage.show();
			
			}
		});
		
		
		
		
		Button btn3 = new Button();
		btn3.setText("MAIN MENU");
		btn3.setLayoutX(350);
		btn3.setLayoutY(40);
		btn3.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				Parent root = null ;
				try {
					root = FXMLLoader.load(getClass().getResource("Main.fxml"));
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
		 		Scene scene = new Scene(root);
		 		Main.newstage.setScene(scene);
		 		Main.newstage.show();
			
			}
		});
	
		
		
		root.getChildren().addAll(btn1, btn2, btn3);
		
		Rectangle[][] vis=new Rectangle[rows][col];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				array[i][j] = new Cell(i, j);
				array[i][j].findCriticalMass(i, j);
				array[i][j].branch.setLayoutX(startx+25);
				array[i][j].branch.setLayoutY(starty+25);
				root.getChildren().add(array[i][j].branch);
				Integer I = new Integer(i);
				Integer J = new Integer(j);
				vis[i][j] = new Rectangle(cell_size, cell_size, Color.TRANSPARENT);
				vis[i][j].setStroke(current.color);
				root.getChildren().add(vis[i][j]);
				vis[i][j].setLayoutX(startx);
				vis[i][j].setLayoutY(starty);
				vis[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					if(array[I][J].owner==null || array[I][J].owner==current) {
					/*if(turncounter+1>3) {
						if(players.size()==0) {
						System.out.println(current.name+" wins");
						System.exit(0);
					}
					}*/
					turncounter++;
					makeMove (array[I][J], current, players);
					cgc(vis,current);
					}
				});
				startx += cell_size;
			}
			starty += cell_size;
			if(rows == 9)
				startx = (int) cell_size + 40;
			else
				startx = (int) cell_size ;
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				array[i][j].findNeighbours(array);
			}
		}
		
		return scene;

	}
	
	public Scene returnMainMenu(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
 		Scene scene1 = new Scene(root);
		return scene1;
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene= makeSceneGrid();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public void explode(Player p, Cell c, ArrayList<Player> list) {
			c.owner=null;
			c.orbNumber=0;
			c.rot.stop();
			c.branch.getChildren().add(c.balls.get(c.criticalMass-1));
			c.branch.setRotate(0);
			for(int i=0;i<4;i++) {
				c.balls.get(i).setTranslateX(0);
				c.balls.get(i).setTranslateY(0);
			}
			TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.4), c.balls.get(0));
			t1.setFromX(0);
			t1.setToX(50);
			t1.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					c.reset();
					c.createballs();
					makeMove (array[c.x+1][c.y],p,list);
				}
			});
			TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.4), c.balls.get(1));
			t2.setFromY(0);
			t2.setToY(50);
			t2.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					makeMove (array[c.x][c.y+1],p,list);
				}
			});
			TranslateTransition t3 = new TranslateTransition(Duration.seconds(0.4), c.balls.get(2));
			t3.setFromX(0);
			t3.setToX(-50);
			t3.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					makeMove (array[c.x-1][c.y],p,list);
				}
			});
			TranslateTransition t4 = new TranslateTransition(Duration.seconds(0.4), c.balls.get(3));
			t4.setFromY(0);
			t4.setToY(-50);
			t4.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					makeMove (array[c.x][c.y-1],p,list);
				}
			});
			for(int i=0;i<c.neighbours.size();i++) {
				if(c.neighbours.get(i).x>c.x) {
					t1.play();
				}
				else if(c.neighbours.get(i).x<c.x) {
					t3.play();
				}
				else if(c.neighbours.get(i).y>c.y) {
					t2.play();
				}
				else if(c.neighbours.get(i).y<c.y) {
					t4.play();
				}
			}
	}
	public void makeMove (Cell c, Player p, ArrayList<Player> list){
		Grid96 g = new Grid96();
		if(!(c.owner==null || c.owner==p)) {
			c.switcho(p);
		}
		int x=c.x;
		int y=c.y;
		if(array[x][y].getorbs() < array[x][y].criticalMass-1){
		if(array[x][y].getorbs() == 0){
		array[x][y].setOwner(p);
		//System.out.println(x+" "+y);
		p.addCell();
		}
		array[x][y].addOrb();
		
		}
		else{
		p.subCell();
		explode(p,c,list);
		}
		players.add(current);
		System.out.println(players.size());
		System.out.println(current.name+" has no of cells= "+current.getCells());
		current=players.get(0);
		for(int o=0;o<players.size();o++) {
			if(players.get(o).noOfCells==0) {
				players.remove(0);
			}
		}
		players.remove(0);
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
package application;



import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// import application.Cell;
// import application.Coordinate;
// import application.Player;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Grid96 extends Application {
	static Cell array[][];
	static int rows , col;
	static float cell_small = 50, cell_big = 54;
	int xc, yc;
	int noOfPlayers = 0;
	String size;
	static int turncounter=0;
	static ArrayList<Player> players ;
	static Player current;
	//int currentx;
	//int currenty;

	public Grid96(){
		Main ob = new Main();
		//this.noOfPlayers = Main.noOfPlayers.charAt(0);
		//System.out.println( SettingPage.playerColor.get(0));
//		for(int i = 0 ;i < this.noOfPlayers ; i ++){
//			Player p = new Player("Player " + String.valueOf(i+1) , SettingPage.playerColor.get(i));
//			players.add(p);
//		}
		//size = Main.gridSize;
		size ="9 * 6";
		if(size.equalsIgnoreCase("9 * 6")){
			rows = 9;
			col = 6;
		}
		else{
			rows = 16;
			col = 10;
		}
		
	}

	public static void cgc(Rectangle[][] vis, Player p) {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				vis[i][j].setStroke(p.color);
			}
			}
	}
	
	public static Scene makeSceneGrid()
	{
		current=new Player("p1",Color.RED);
		Player other=new Player("p2",Color.BLUE);
		players = new ArrayList<Player>();
		players.add(other);
		Group root = new Group();
		Scene scene = new Scene(root, 400,550, Color.BLACK);
		array = new Cell[rows + 1][col + 1];
		int noOfPlayers = 2;
		int startx=50;
		int starty=50;
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
				vis[i][j]=new Rectangle(50,50,Color.TRANSPARENT);
				vis[i][j].setStroke(current.color);
				root.getChildren().add(vis[i][j]);
				vis[i][j].setLayoutX(startx);
				vis[i][j].setLayoutY(starty);
				vis[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					if(current.getCells()!=0 || turncounter<3) {
					//System.out.println(players.size());
					//System.out.println(current.name+" has no of cells= "+current.getCells());
					if(array[I][J].owner==null || array[I][J].owner==current) {
						System.out.println(array[I][J].x+" "+array[I][J].y);
						turncounter++;
					makeMove (array[I][J], current, players);
					players.add(current);
					if(turncounter>2) {
						for(int k=0;k<players.size();k++) {
							if(players.get(k).noOfCells==0) {
								players.remove(k);
							}
						}
						}
					if(players.size()==1) {
						System.out.println(players.get(0).name+" wins");
						System.exit(0);
					}
					current=players.get(0);
					players.remove(0);
					cgc(vis,current);
					}
					}
					else if(current.getCells()==0 && turncounter>2){
						current=players.get(0);
					}
				});
				//System.out.print(array[i][j].criticalMass+" ");
				startx += 50;
			}
			//System.out.println("");
			starty += 50;
			startx = 50;
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
		array[i][j].findNeighbours(array);
			}}
		
		return scene;

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene= makeSceneGrid();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void explode(Player p, Cell c, ArrayList<Player> list) {
			c.owner=null;
			c.orbNumber=0;
			c.rot.stop();
			c.branch.getChildren().add(c.balls.get(c.criticalMass-1));
			c.branch.setRotate(0);
			for(int i=0;i<c.balls.size();i++) {
				c.balls.get(i).setTranslateX(0);
				c.balls.get(i).setTranslateY(0);
			}
			TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.4), c.r1);
			t1.setFromX(0);
			t1.setToX(50);
			t1.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					c.reset();
					//System.out.println("called");
					c.createballs();
					if(c.y+1<c.rows);
					makeMove (array[c.x][c.y+1],p,list);
				}
			});
			TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.4), c.r2);
			t2.setFromY(0);
			t2.setToY(50);
			t2.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if(c.x+1<c.cols) {
					makeMove (array[c.x+1][c.y],p,list);
					}
				}
			});
			TranslateTransition t3 = new TranslateTransition(Duration.seconds(0.4), c.r3);
			t3.setFromX(0);
			t3.setToX(-50);
			t3.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if(c.y-1>-1) {
					makeMove (array[c.x][c.y-1],p,list);
					}
				}
			});
			TranslateTransition t4 = new TranslateTransition(Duration.seconds(0.4), c.r4);
			t4.setFromY(0);
			t4.setToY(-50);
			t4.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if(c.x-1>-1) {
					makeMove (array[c.x-1][c.y],p,list);
					}
				}
			});
			System.out.println("start");
			for(int i=0;i<c.neighbours.size();i++) {
				System.out.println(c.neighbours.get(i).x+" "+c.neighbours.get(i).y);
			}
			t1.play();
			t2.play();
			t3.play();
			t4.play();
			
	}
	public static void makeMove (Cell c, Player p, ArrayList<Player> list){
		//System.out.println("called");
		if(!(c.owner==null || c.owner==p)) {
			c.switcho(p);
		}
		int x=c.x;
		int y=c.y;
		if(array[x][y].getorbs() < array[x][y].criticalMass-1){
		if(array[x][y].getorbs() == 0){
		array[x][y].setOwner(p);
		p.addCell();
		}
		System.out.println("gcgc");
		array[x][y].addOrb();
		}
		else{
		p.subCell();
		explode(p,c,list);
		}
		
		}
	public static void main(String[] args) {
		launch(args);
	}

}
package main;

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

import game_elements.Cell;
import game_elements.Coordinate;
import game_elements.Player;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Grid96 extends Application {
	static Cell array[][];
	static int rows = 9, col = 6;
	static float cell_small = 50, cell_big = 54;
	int xc, yc;
	int turncounter=0;
	static ArrayList<Player> players ;
	static Player current;
	//int currentx;
	//int currenty;
	public static void cgc(Rectangle[][] vis, Player p) {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				vis[i][j].setStroke(p.color);
			}
			}
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		current=new Player("p1",Color.RED);
		Player other=new Player("p2",Color.BLUE);
		players = new ArrayList<Player>();
		players.add(other);
		Group root = new Group();
		Scene scene = new Scene(root, 400,550, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.show();
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
				startx += 50;
			}
			starty += 50;
			startx = 50;
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
		array[i][j].findNeighbours(array);
			}}

	}

	public static void explode(Player p, Cell c, ArrayList<Player> list) {
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
	public static void makeMove (Cell c, Player p, ArrayList<Player> list){
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
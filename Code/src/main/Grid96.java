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
	ArrayList<Player> players ;
	//int currentx;
	//int currenty;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Player current=new Player("Hello",Color.RED);
		players = new ArrayList<Player>();
		Group root = new Group();
		Scene scene = new Scene(root, 400,550, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.show();
		array = new Cell[rows + 1][col + 1];
		int noOfPlayers = 2;
		int startx=50;
		int starty=50;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				array[i][j] = new Cell(i, j);
				array[i][j].findCriticalMass(i, j);
				array[i][j].branch.setLayoutX(startx+25);
				array[i][j].branch.setLayoutY(starty+25);
				root.getChildren().add(array[i][j].branch);
				Integer I = new Integer(i);
				Integer J = new Integer(j);
				Rectangle a=new Rectangle(50,50,Color.TRANSPARENT);
				a.setStroke(Color.BLUEVIOLET);
				root.getChildren().add(a);
				a.setLayoutX(startx);
				a.setLayoutY(starty);
				a.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					turncounter++;
					makeMove (array[I][J], current, players);
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
		// Remove once settings is integrated
		/*String name;
		for (int i = 0; i < noOfPlayers; i++) {
			//System.out.println("Enter player " + (i + 1) + "'s name and color");
			name = "hello";
			Color color = Color.RED;
			players.add(new Player(name, color));
		}
		int turn=0;
		while (players.size() != 1) {
			for (int j = 0; j < players.size(); j++) {
				current=players.get(j);
				//System.out.println(turncounter);
				/*while(true) {
					if(turncounter>turn) {
					makeMove (array[currentx][currenty], current, players);
					System.out.println(turncounter);
					turn++;
					break;
					}
				}*/
				/*
				for (int a = 0; a < players.size(); a++) {
					if (players.get(a).noOfCells == 0) {
						System.out.println(players.get(a).name + players.get(a).noOfCells);
						players.remove(a);
						a = 0;
					}
				}
			}
		}*/

		// System.out.println(players.get(0).name + " wins!!" );
		// System.exit(0);

	}

	public static void explode(Player p, Cell c, ArrayList<Player> list) {
		if(c.owner.equals(p)) {
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
			
			
			///////// SEE THIS VVVIMP
			/*for (int i = 0; i < c.criticalMass; i++) {
				t.get(i).play();
			}*/
		}
		
		//System.out.println("vgvg");
		/*if (c.getOwner() != null) {
			System.out.println("vgvg");
			int j = 0;
			while (j < list.size()) {
				if (list.get(j).equals(c.getOwner())) {
					list.get(j).subCell();
					break;
				}
				j++;
			}
			c.rot.stop();
			c.branch.setRotate(0);
			Material kMaterial = new PhongMaterial();
			TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.5), c.balls.get(0));
			TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5), c.balls.get(1));
			TranslateTransition t3 = new TranslateTransition(Duration.seconds(0.5), c.balls.get(2));
			TranslateTransition t4 = new TranslateTransition(Duration.seconds(0.5), c.balls.get(3));
			ArrayList<TranslateTransition> t = new ArrayList<TranslateTransition>();
			t.add(t1);
			t.add(t2);
			t.add(t3);
			t.add(t4);

			for (int i = 0; i < c.neighbours.size(); i++) {
				//System.out.println(c.neighbours.get(0).orbNumber);
				if (c.neighbours.get(i).x > c.x) {
					t.get(i).setFromX(c.branch.getLayoutX());
					t.get(i).setToX(c.branch.getLayoutX() + 50);
					Integer I=new Integer(i);
					t.get(i).setOnFinished(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							makeMove (c.neighbours.get(I), p,list);
						}
					});
				} else if (c.neighbours.get(i).x < c.x) {
					t.get(i).setFromX(c.branch.getLayoutX());
					t.get(i).setToX(c.branch.getLayoutX() - 50);
				} else if (c.neighbours.get(i).y < c.y) {
					t.get(i).setFromY(c.branch.getLayoutY());
					t.get(i).setToY(c.branch.getLayoutY() - 50);
				} else if (c.neighbours.get(i).y > c.y) {
					t.get(i).setFromY(c.branch.getLayoutY());
					t.get(i).setToY(c.branch.getLayoutY() + 50);
				}
			}
			((PhongMaterial) kMaterial).setDiffuseColor(p.color);
			for (int i = 0; i < 4; i++) {
				c.balls.get(i).setMaterial(kMaterial);
			}
			c.branch.getChildren().add(c.balls.get(c.criticalMass - 1));
			///////// SEE THIS VVVIMP
			for (int i = 0; i <= c.criticalMass; i++) {
				t.get(i).play();
			}
		}*/
	}
	public static void makeMove (Cell c, Player p, ArrayList<Player> list){
		int x=c.x;
		int y=c.y;
		//System.out.println(x+" "+y);
		if(array[x][y].getorbs() < array[x][y].criticalMass-1){
		if(array[x][y].getorbs() == 0){
		array[x][y].setOwner(p);
		p.addCell();
		}
		array[x][y].addOrb();
		array[x][y].setOwner(p);

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
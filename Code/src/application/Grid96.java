package application;

import javafx.scene.*;
import java.applet.*;
import java.net.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.*;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;

/**
* Class Grid96 implements an application to start the game
* Date : November 18, 2017
* 
* @author manvigoel, arpitbhatia
*
*/
public class Grid96 extends Application {
	/**
	 * 2d array of cell 
	 */
	static Cell array[][];
	/**
	 * int to store rows, columns
	 */
	static int rows , col, game_end = 0;
	/**
	 * 2d array of rectangles
	 */
	static Rectangle[][] vis;
	/**
	 * float value to store cell size
	 */
	static float cell_size = 40;
	int xc, yc;
	/**
	 * int value to check if a player has been removed
	 */
	static int rem = 0;
	/**
	 * initialise the number of players in the game
	 */
	static int noOfPlayers = 2;
	/**
	 * string value to store the grid size 
	 */
	String size = ("9 x 6");
	/**
	 * int value to store the number of turns
	 */
	static int turncounter = 0;
	/**
	 * arraylist of players 
	 */
	static ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * stores the current and previous player(Player class)
	 */
	static Player current, previous;
	
	//int currentx;
	//int currenty;
	private Animation clip;

	/**
	 * Constructor for the class to initialise the rows and columns
	 * Also initialise the number of plyers, and initialise players in the arraylist  
	 */
	Grid96(){
		Main ob = new Main();
		if(Main.noOfPlayers == null){
			this.noOfPlayers = 2;
			System.out.println("players null");
			Player p1 = new Player("Player 1" , Color.RED);
			Player p2 = new Player("Player 2" , Color.BLUE);
			players.add(p1);
			players.add(p2);
		}
		
		if(Main.gridSize == null){
			rows = 9;
			col = 6;
			cell_size = 55;
			
		}
		if(Main.noOfPlayers != null){
			this.noOfPlayers = Character.getNumericValue(Main.noOfPlayers.charAt(0));
		
		//System.out.println(this.noOfPlayers);
		for(int i = 0 ; i < this.noOfPlayers ; i ++){
			
			Player p = new Player("Player " + String.valueOf(i+1) , SettingPage.playerColor.get(i));
			players.add(p);
			
			}
		}
		if(Main.gridSize != null){
			
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
		try {
			AudioClip clip = Applet.newAudioClip(new URL("/pop.wav"));
			//clip.play();
			} catch (MalformedURLException murle) {
			System.out.println(murle);
			}
	}
	
	/**
	 * sets the color of each rectangle according to the plauer color
	 * @param vis 2d grid of rectangle
	 * @param p player to make move
	 */
	public static void cgc(Rectangle[][] vis, Player p) {
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < col ; j++) {
				vis[i][j].setStroke(p.color);
			}
		}
	}
	
	
	/**
	 * serialise the player the scell class
	 * @throws IOException
	 */
	public static void serialize() throws IOException {
		ObjectOutputStream out = null;
		//ObjectOutputStream out2 = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("out.txt"));
			for(int i=0;i<rows;i++) {
				for(int j=0;j<col;j++) {
					//System.out.print(array[i][j].sc.orbNumber+" ");
					out.writeObject(array[i][j].sc);
				}
				//System.out.println("");
			}
//			out2 = new ObjectOutputStream(new FileOutputStream("out2.txt"));
//			System.out.println("ser "+players.size());
//			for(int i=0;i<players.size();i++) {
//				out2.writeObject(players.get(i));
//			}
		} finally {
			out.close();
			//out2.close();
		}
	}
	
	
	/**
	 * serialise the attributes of the player class
	 * @throws IOException
	 */
	public static void serialize2() throws IOException {
		ObjectOutputStream out2 = null;
		try {
			out2 = new ObjectOutputStream(new FileOutputStream("out2.txt"));
			//System.out.println("ser "+players.size());
			for(int i=0;i<players.size();i++) {
				out2.writeObject(players.get(i));
			}
		} finally {
			out2.close();
		}
	}
	//public static Cell[][] deserialize() throws IOException,ClassNotFoundException {
//	public static void deserialize() throws IOException,ClassNotFoundException {
//			ObjectInputStream in = null;
//			Cell[][] ret=new Cell[rows][col];
//			try {
//				in = new ObjectInputStream(new FileInputStream("out.txt"));
//				for(int i=0;i<rows;i++) {
//					for(int j=0;j<col;j++) {
//						sCell rr=(sCell) in.readObject();
//						//System.out.println(rr.owner);
//						//System.out.print(rr.orbNumber+" "+rr.owner+" ");
//						System.out.print(rr.orbNumber+" ");
//						array[i][j].reset();
//						array[i][j]=null;
//						array[i][j]=new Cell(rr);
//					}
//					System.out.println("");
//				}
//			} finally {
//				System.out.println("");
//				in.close();
//				//System.out.println("des");
//				//return ret;
//			}
//		}
	
	/**
	 * desrialises all the data from the files stored before
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void deserialize() throws IOException,ClassNotFoundException {
		System.out.println("des called");
		ObjectInputStream in = null;
		ObjectInputStream in2 = null;
		//sCell[][] scc=new sCell[rows][col];
		int ss=players.size();
		System.out.println(ss);
		players.clear();
		try {
			in = new ObjectInputStream(new FileInputStream("out.txt"));
			in2 = new ObjectInputStream(new FileInputStream("out2.txt"));
			for(int i=0;i<rows;i++) {
				for(int j=0;j<col;j++) {
					sCell rr=(sCell) in.readObject();
//					//System.out.println(rr.owner);
					array[i][j]=new Cell(rr);
					//System.out.print(array[i][j].orbNumber+" ");
				}
				//System.out.println("");
			}
			for(int i=0;i<ss;i++) {
				Player y=(Player) in2.readObject();
				System.out.println(y.name);
				y.setcol();
				players.add(y);
			}
		} finally {
			in.close();
			//return scc;
		}
	}
	
	
	/**
	 * creates the scene for the grid by adding various attributes to the scene
	 * @return scene
	 */
	public Scene makeSceneGrid()
	{
		current = players.get(0);
		//Player other = players.get(1);
		Group root = new Group();
		Scene scene = new Scene(root, 550, 800, Color.BLACK);
		array = new Cell[rows + 1][col + 1];
		//int noOfPlayers = 0;
		int startx = 0;
		int starty = 0;
		vis = new Rectangle[rows][col];
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
		btn1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				//Grid96 g= new Grid96();
				Scene scene_grid= resumeSceneGrid();
				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Main.newstage.setScene(scene_grid);
				Main.newstage.show();
			
			}
		});
		
		ObservableList<String> options = FXCollections.observableArrayList( "NEW GAME", "MAIN MENU");
		
		ComboBox<String> combobox = new ComboBox(options);
		combobox.setPromptText("OPTIONS");
		combobox.setItems(options);
		combobox.setLayoutX(300);
		combobox.setLayoutY(40);
		
		combobox.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(combobox.getSelectionModel().getSelectedItem().equals("NEW GAME")){
					Grid96 g= new Grid96();
					Scene scene_grid= g.makeSceneGrid();
					Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					Main.newstage.setScene(scene_grid);
					Main.newstage.show();
				}
				else{
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
			}
		});
		
		root.getChildren().addAll(combobox, btn1);
		
		for(int i = 0 ; i < rows ; i++) {
			
			for(int j = 0 ; j < col ; j++) {
				
				array[i][j] = new Cell(i, j);
				array[i][j].findCriticalMass(i, j);
				array[i][j].branch.setLayoutX(startx + 25);
				array[i][j].branch.setLayoutY(starty + 25);
				
				root.getChildren().add(array[i][j].branch);
				Integer I = new Integer(i);
				Integer J = new Integer(j);
				
				vis[i][j] = new Rectangle(cell_size, cell_size, Color.TRANSPARENT);
				vis[i][j].setStroke(current.color);
				root.getChildren().add(vis[i][j]);
				vis[i][j].setLayoutX(startx);
				vis[i][j].setLayoutY(starty);
				vis[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					try {
						serialize();
						//System.out.println("serialised");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(current.getCells() != 0 || turncounter < noOfPlayers + 1) {
					
						if(array[I][J].owner == null || array[I][J].owner == current) {
							
							
							turncounter++;
							makeMove (array[I][J], current, players);
							
							players.add(current);
							previous = current;
							players.remove(0);
							System.out.println("current : " + current.name );
							try {
								serialize2();
								//System.out.println("serialised");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							current = players.get(0);
							System.out.println("Current after move " + current.name );
							cgc(vis,current);
							
							for(int x = 0 ; x < players.size() ; x ++){
								System.out.println(players.get(x).name + " " + players.get(x).noOfCells);
							}
						}
					}
					else if(current.getCells() == 0){
						
						for(int k = 0 ; k < players.size() ; k++) {
							
							if(players.get(k).noOfCells==0) {
								players.remove(k);
							}
						}
						
//						if(players.size() == 1) {
//							System.out.println("2 " + players.get(0).name+" wins");
//							game_end = 1;
//							ButtonType b1 = new ButtonType("Main Menu");
//							ButtonType b2 = new ButtonType("New Game");
//							ButtonType b3 = new ButtonType("Exit");
//							
//							Alert alert = new Alert(AlertType.CONFIRMATION);
//							alert.setTitle("Congratulations!");
//							alert.setContentText(players.get(0).name+" wins");
//							alert.getButtonTypes().setAll(b1, b2, b3);
//							alert.showAndWait();
//							
//							if(alert.getResult() == b1){
//								mainMenu();
//							}
//							else if(alert.getResult() == b2){
//								Grid96 g= new Grid96();
//								Scene scene_grid= g.makeSceneGrid();
//							//	Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//								Main.newstage.setScene(scene_grid);
//								Main.newstage.show();
//							
//							}
//							else if (alert.getResult() == b3){
//								System.exit(0);
//							}
//							
//						}
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
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < col ; j++) {
				array[i][j].findNeighbours(array);
			}
		}
		return scene;

	}
	/**
	 * creates the scene from the resumed condition by adding the respective attributes 
	 * according to the last saved data
	 * @return scene
	 */
	public Scene resumeSceneGrid()
	{
		turncounter--;
        System.out.println(players.size());
		Group root = new Group();
		Scene scene = new Scene(root, 550, 800, Color.BLACK);
		array = new Cell[rows + 1][col + 1];
		 try {
	            deserialize();
	        } catch (ClassNotFoundException e2) {
	            // TODO Auto-generated catch block
	            e2.printStackTrace();
	        } catch (IOException e2) {
	            // TODO Auto-generated catch block
	            e2.printStackTrace();
	        }
		//int noOfPlayers = 0;
		int startx = 0;
		int starty = 0;
		vis = new Rectangle[rows][col];
		current = players.get(players.size()-1);
		players.add(0,current);
		players.remove(players.size()-1);
		//System.out.println(current.color);
		//System.out.println("Current " + current.name);
		//cgc(vis,current);
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
		btn1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				//Grid96 g= new Grid96();
				Scene scene_grid= resumeSceneGrid();
				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Main.newstage.setScene(scene_grid);
				Main.newstage.show();
			
			}
		});
		
		ObservableList<String> options = FXCollections.observableArrayList( "NEW GAME", "MAIN MENU");
		
		ComboBox<String> combobox = new ComboBox(options);
		combobox.setPromptText("OPTIONS");
		combobox.setItems(options);
		combobox.setLayoutX(300);
		combobox.setLayoutY(40);
		
		combobox.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(combobox.getSelectionModel().getSelectedItem().equals("NEW GAME")){
					Grid96 g= new Grid96();
					Scene scene_grid= g.makeSceneGrid();
					Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					Main.newstage.setScene(scene_grid);
					Main.newstage.show();
				}
				else{
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
			}
		});
		
		root.getChildren().addAll(combobox, btn1);
		
		for(int i = 0 ; i < rows ; i++) {
			
			for(int j = 0 ; j < col ; j++) {
				
				array[i][j] = new Cell(i, j);
				array[i][j].findCriticalMass(i, j);
				array[i][j].branch.setLayoutX(startx + 25);
				array[i][j].branch.setLayoutY(starty + 25);
				int ol=array[i][j].orbNumber;
	            array[i][j].orbNumber=0;
	            
	            for(int q=0;q<ol;q++){
	            	
	            	array[i][j].addOrb();
	            }
				root.getChildren().add(array[i][j].branch);
				Integer I = new Integer(i);
				Integer J = new Integer(j);
				
				vis[i][j] = new Rectangle(cell_size, cell_size, Color.TRANSPARENT);
				vis[i][j].setStroke(current.color);
				root.getChildren().add(vis[i][j]);
				vis[i][j].setLayoutX(startx);
				vis[i][j].setLayoutY(starty);
				vis[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					
					if(current.getCells() != 0 || turncounter < noOfPlayers + 1) {
					
						if(array[I][J].owner == null || array[I][J].owner.name.equals(current.name)) {
							try {
								serialize();
								//System.out.println("serialised");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//System.out.println(array[I][J].x+" "+array[I][J].y);
							
							
							turncounter++;
							makeMove (array[I][J], current, players);
							if(game_end == 1 || players.size() == 1){
								
								ButtonType b1 = new ButtonType("Main Menu");
								ButtonType b2 = new ButtonType("New Game");
								ButtonType b3 = new ButtonType("Exit");
								
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Congratulations!");
								alert.setContentText(players.get(0).name+" wins");
								alert.getButtonTypes().setAll(b1, b2, b3);
								alert.showAndWait();
								
								if(alert.getResult() == b1){
									mainMenu();
								}
								else if(alert.getResult() == b2){
									Grid96 g= new Grid96();
									Scene scene_grid= g.makeSceneGrid();
								//	Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
									Main.newstage.setScene(scene_grid);
									Main.newstage.show();
								}
								else if(alert.getResult() == b3){
									System.exit(0);
								}
								
							}
							
							players.add(current);
							previous = current;
							players.remove(0);
							try {
								serialize2();
								//System.out.println("serialised");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							current = players.get(0);
							//System.out.println("Current " + current.name);
							cgc(vis,current);
						}
					}
					else if(current.getCells() == 0){
						
						for(int k = 0 ; k < players.size() ; k++) {
							
							if(players.get(k).noOfCells==0) {
								players.remove(k);
							}
						}
						
						
						if(players.size() == 1) {
							System.out.println("2 " + players.get(0).name+" wins");
							game_end = 1;
							ButtonType b1 = new ButtonType("Main Menu");
							ButtonType b2 = new ButtonType("New Game");
							ButtonType b3 = new ButtonType("Exit");
							
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Congratulations!");
							alert.setContentText(players.get(0).name+" wins");
							alert.getButtonTypes().setAll(b1, b2, b3);
							alert.showAndWait();
							
							if(alert.getResult() == b1){
								mainMenu();
							}
							else if(alert.getResult() == b2){
								Grid96 g= new Grid96();
								Scene scene_grid= g.makeSceneGrid();
							//	Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
								Main.newstage.setScene(scene_grid);
								Main.newstage.show();
							
							}
							else if (alert.getResult() == b3){
								System.exit(0);
							}
							
						}
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
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < col ; j++) {
				array[i][j].findNeighbours(array);
			}
		}
		return scene;

	}
	/**
	 * loads the window of the main page
	 */
	public void mainMenu(){
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
	
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene= makeSceneGrid();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	/**
	 * recursive function to perform explosion on each cell if the number of balls is equal to the critical mass 
	 * @param p player to take over the cell
	 * @param c cell on which explosion is to be performed
	 * @param list arraylist of players
	 */
	public void explode(Player p, Cell c, ArrayList<Player> list) {
			c.owner = null;
			c.orbNumber = 0;
			c.rot.stop();
			c.branch.getChildren().add(c.balls.get(c.criticalMass-1));
			c.branch.setRotate(0);
			for(int i = 0 ; i < c.balls.size() ; i++) {
				c.balls.get(i).setTranslateX(0);
				c.balls.get(i).setTranslateY(0);
			}
			clip.play();
			TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.4), c.r1);
			t1.setFromX(0);
			t1.setToX(50);
			t1.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					c.reset();
					c.createballs();
					if(c.y+1 < c.rows){
						System.out.println("y+1");
						makeMove (array[c.x][c.y+1],p,list);
						if(turncounter > noOfPlayers) {
							System.out.println( "size " + players.size());
							
							for(int k = 0 ; k < players.size() ; k++) {
								
								if(players.get(k).noOfCells==0) {
									System.out.println("remove" + players.get(k).name);
									players.remove(k);
									rem = 1;
									for(int a = 0 ; a < players.size() ; a ++){
										System.out.println(players.get(a).name);
									}
								}
								
								if(rem == 1){
									current = players.get(0);
									System.out.println("Current " + current.name);
									cgc(vis,current);
									
								}
								rem = 0;
							}
						}
					}
				}
			});
			TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.4), c.r2);
			t2.setFromY(0);
			t2.setToY(50);
			t2.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					c.reset();
					c.createballs();
					
					if(c.x+1<c.cols) {
						System.out.println("x+1");
						makeMove (array[c.x+1][c.y],p,list);
						
						if(turncounter > noOfPlayers) {
							System.out.println( "size " + players.size());
							for(int k = 0 ; k < players.size() ; k++) {
								
								if(players.get(k).noOfCells==0) {
									System.out.println("remove" + players.get(k).name);
									players.remove(k);
									rem = 1;
									for(int a = 0 ; a < players.size() ; a ++){
										System.out.println(players.get(a).name);
									}
								}
								if(rem == 1){
									current = players.get(0);
									System.out.println("Current " + current.name);
									cgc(vis,current);
								}
								rem = 0;
							}
						}
					}
				}
			});
			TranslateTransition t3 = new TranslateTransition(Duration.seconds(0.4), c.r3);
			t3.setFromX(0);
			t3.setToX(-50);
			t3.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					c.reset();
					c.createballs();
					
					if(c.y-1>-1) {
						System.out.println("y-1");
						makeMove (array[c.x][c.y-1],p,list);
						
						if(turncounter > noOfPlayers) {
							System.out.println( "size " + players.size());
							for(int k = 0 ; k < players.size() ; k++) {
								
								if(players.get(k).noOfCells==0) {
									System.out.println("remove" + players.get(k).name);
									players.remove(k);
									rem = 1;
									for(int a = 0 ; a < players.size() ; a ++){
										System.out.println(players.get(a).name);
									}
								}
								if(rem == 1){
									//players.remove(0);
									current = players.get(0);
									System.out.println("Current " + current.name);
									cgc(vis,current);
									
								}
								rem = 0;
							}
						}
					}
				}
			});
			TranslateTransition t4 = new TranslateTransition(Duration.seconds(0.4), c.r4);
			t4.setFromY(0);
			t4.setToY(-50);
			t4.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					c.reset();
					c.createballs();
					
					if(c.x-1>-1) {
						System.out.println("x-1");
						makeMove (array[c.x-1][c.y],p,list);
						if(turncounter > noOfPlayers) {
							System.out.println( "size " + players.size());
							for(int k = 0 ; k < players.size() ; k++) {
								
								if(players.get(k).noOfCells==0) {
									System.out.println("remove" + players.get(k).name);
									players.remove(k);
									rem = 1;
									for(int a = 0 ; a < players.size() ; a ++){
										System.out.println(players.get(a).name);
									}
								}
								if(rem == 1){
								//	players.remove(0);
									current = players.get(0);
									System.out.println("Current " + current.name);
									cgc(vis,current);
									
								}
								rem = 0;
							}
						}
					}
					if(players.size() == 1) {
						
						System.out.println("3 " + players.get(0).name+" wins");
//						ButtonType b1 = new ButtonType("Main Menu");
//						ButtonType b2 = new ButtonType("New Game");
//						ButtonType b3 = new ButtonType("Exit");
//						
//						Alert alert = new Alert(AlertType.CONFIRMATION);
//						alert.setTitle("Congratulations!");
//						alert.setContentText(players.get(0).name+" wins");
//						alert.getButtonTypes().setAll(b1, b2, b3);
//						alert.show();
//						
//						if(alert.getResult() == b1){
//							mainMenu();
//						}
//						else if(alert.getResult() == b2){
//							Grid96 g= new Grid96();
//							Scene scene_grid= g.makeSceneGrid();
//						//	Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//							Main.newstage.setScene(scene_grid);
//							Main.newstage.show();
//						}
//						else if (alert.getResult() == b3){
//							System.exit(0);
//						}
//						game_end = 1;
//						
					}
				}
			});
			
		
			t1.play();
			t2.play();
			t3.play();
			t4.play();
			
	}
	/**
	 * makes a move on each cell, adds the balls according to the critical mass of each cell
	 * @param c cell on which to make move
	 * @param p player to make the move
	 * @param list arraylist of players
	 */
	public  void makeMove (Cell c, Player p, ArrayList<Player> list){
		
		if(!(c.owner == null || c.getOwner().equals(p))) {
			c.switcho(p);
		}
		//System.out.println("after switch " + p.name + " " + p.getCells());
		int x = c.x;
		int y = c.y;
		if(array[x][y].getorbs() < array[x][y].criticalMass - 1){
			
			if(array[x][y].getorbs() == 0){
				array[x][y].setOwner(p);
				array[x][y].sc.owner=p;
				p.addCell();
			}
			
			array[x][y].addOrb();
			array[x][y].setOwner(p);
		}
		else{
			p.subCell();
			explode(p, c, list);
		}
		
	}
	/**
	 * main method of this application
	 * @param args array of string arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
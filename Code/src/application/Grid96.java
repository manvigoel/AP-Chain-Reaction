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
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
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


public class Grid96 extends Application {
	static Cell array[][];
	static int rows , col, game_end = 0;
	static Rectangle[][] vis;
	static float cell_size = 40;
	int xc, yc;
	static int rem = 0;
	static int noOfPlayers = 2;
	String size = ("9 x 6");
	static int turncounter = 0;
	static ArrayList<Player> players = new ArrayList<Player>();
	
	static Player current, previous;
	String winner;
	//int currentx;
	//int currenty;

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
		
	}
	
	public static void cgc(Rectangle[][] vis, Player p) {
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < col ; j++) {
				vis[i][j].setStroke(p.color);
			}
		}
	}
	
	
	public static void serialize() throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("out.txt"));
			for(int i=0;i<rows;i++) {
				for(int j=0;j<col;j++) {
					//System.out.print(array[i][j].sc.orbNumber+" ");
					out.writeObject(array[i][j].sc);
				}
				//System.out.println("");
			}
		} finally {
			out.close();
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
	public static void deserialize() throws IOException,ClassNotFoundException {
		ObjectInputStream in = null;
		//sCell[][] scc=new sCell[rows][col];
		try {
			in = new ObjectInputStream(new FileInputStream("out.txt"));
			for(int i=0;i<rows;i++) {
				for(int j=0;j<col;j++) {
					sCell rr=(sCell) in.readObject();
//					//System.out.println(rr.owner);
					array[i][j]=new Cell(rr);
					//System.out.print(array[i][j].orbNumber+" ");
				}
				//System.out.println("");
			}
		} finally {
			in.close();
			//return scc;
		}
	}
	
	
	public Scene makeSceneGrid()
	{
		current = players.get(0);
		Player other = players.get(1);
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
				Grid96 g= new Grid96();
				Scene scene_grid= g.resumeSceneGrid();
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
					
					if(current.getCells() != 0 || turncounter < noOfPlayers + 1) {
					
						if(array[I][J].owner == null || array[I][J].owner == current) {
							
							//System.out.println(array[I][J].x+" "+array[I][J].y);
							try {
								serialize();
								//System.out.println("serialised");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(turncounter!=0) {
								System.out.println(current.name+" has no of cells= "+previous.getCells());
							}
							
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
							current = players.get(0);
							System.out.println("Current " + current.name);
							cgc(vis,current);
						}
					}
					else if(current.getCells() == 0){
						
						for(int k = 0 ; k < players.size() ; k++) {
							
							if(players.get(k).noOfCells==0) {
								players.remove(k);
							}
						}
						
						for(int x = 0 ; x < players.size() ; x ++)
							System.out.println(players.get(x).name + " " + players.get(x).noOfCells);
						
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
	public Scene resumeSceneGrid() 
	{
		players.add(0,current);
		current=previous;
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
//		btn1.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				Grid96 g= new Grid96();
//		//		Scene scene_grid= g.resumeSceneGrid();
//				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//				Main.newstage.setScene(scene_grid);
//				Main.newstage.show();
//			
//			}
//		});
		
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
		try {
			deserialize();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for(int i = 0 ; i < rows ; i++) {
			
			for(int j = 0 ; j < col ; j++) {
				
				//array[i][j] = new Cell(dsc[i][j]);
				array[i][j].findCriticalMass(i, j);
				array[i][j].branch.setLayoutX(startx + 25);
				array[i][j].branch.setLayoutY(starty + 25);
				int ol=array[i][j].orbNumber;
				array[i][j].orbNumber=0;
				for(int q=0;q<ol;q++){
					array[i][j].addOrb();
				}
//				if(array[i][j].orbNumber==0) {
//				System.out.print(array[i][j].orbNumber+" ");
//				}
//				else {
//					System.out.print(array[i][j].sc.owner.r+" "+array[i][j].sc.owner.g+array[i][j].sc.owner.b+" ");
//				}
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
					
						if(array[I][J].owner == null || array[I][J].owner == current) {
							
							//System.out.println(array[I][J].x+" "+array[I][J].y);
							try {
								serialize();
								//System.out.println("serialised");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(turncounter!=0) {
								System.out.println(current.name+" has no of cells= "+previous.getCells());
							}
							
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
							current = players.get(0);
							System.out.println("Current " + current.name);
							cgc(vis,current);
						}
					}
					else if(current.getCells() == 0){
						
						for(int k = 0 ; k < players.size() ; k++) {
							
							if(players.get(k).noOfCells==0) {
								players.remove(k);
							}
						}
						
						for(int x = 0 ; x < players.size() ; x ++)
							System.out.println(players.get(x).name + " " + players.get(x).noOfCells);
						
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
			System.out.println("");
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
	//public Scene resumeSceneGrid()
//	{
//		current = players.get(0);
//		Group root = new Group();
//		Scene scene = new Scene(root, 550, 800, Color.BLACK);
//		array = new Cell[rows + 1][col + 1];
//		//int noOfPlayers = 0;
//		int startx = 0;
//		int starty = 0;
//		
//		if(rows == 15){
//			startx = (int) cell_size;
//			starty = (int) 100;
//			
//		}
//		else{
//			startx = (int) cell_size + 40;
//			starty = (int) 200;
//		}
//			
//		Button btn1 = new Button();
//		btn1.setText("UNDO");
//		btn1.setLayoutX(80);
//		btn1.setLayoutY(40);
//		btn1.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				Grid96 g= new Grid96();
//				Scene scene_grid= g.resumeSceneGrid();
//				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//				Main.newstage.setScene(scene_grid);
//				Main.newstage.show();
//			
//			}
//		});
//		
//		Button btn2 = new Button();
//		btn2.setText("NEW GAME");
//		btn2.setLayoutX(200);
//		btn2.setLayoutY(40);
//		btn2.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				Grid96 g= new Grid96();
//				Scene scene_grid= g.makeSceneGrid();
//				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//				Main.newstage.setScene(scene_grid);
//				Main.newstage.show();
//			
//			}
//		});
//		
//		Button btn3 = new Button();
//		btn3.setText("MAIN MENU");
//		btn3.setLayoutX(350);
//		btn3.setLayoutY(40);
//		btn3.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				Parent root = null ;
//				try {
//					root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//				} 
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//		 		Scene scene = new Scene(root);
//		 		Main.newstage.setScene(scene);
//		 		Main.newstage.show();
//			}
//		});
//	
//		root.getChildren().addAll(btn1, btn2, btn3);
//		
//		
//		vis = new Rectangle[rows][col];
//		for(int i = 0 ; i < rows ; i++) {
//			
//			for(int j = 0 ; j < col ; j++) {
//				
//				array[i][j] = new Cell(i, j);
//				//array[i][j].findCriticalMass(i, j);
//				array[i][j].branch.setLayoutX(startx + 25);
//				array[i][j].branch.setLayoutY(starty + 25);
//				
//				root.getChildren().add(array[i][j].branch);
//				Integer I = new Integer(i);
//				Integer J = new Integer(j);
//				
//				vis[i][j] = new Rectangle(cell_size, cell_size, Color.TRANSPARENT);
//				vis[i][j].setStroke(current.color);
//				root.getChildren().add(vis[i][j]);
//				vis[i][j].setLayoutX(startx);
//				vis[i][j].setLayoutY(starty);
//				vis[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//					//System.out.println(players.size());
//						if(array[I][J].owner == null || array[I][J].owner == current) {
//							//System.out.println(array[I][J].x+" "+array[I][J].y);
//							try {
//								serialize();
//								//System.out.println("serialised");
//							} catch (IOException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							if(turncounter!=0) {
//								System.out.println(current.name+" has no of cells= "+previous.getCells());
//							}
//							turncounter++;
//							makeMove (array[I][J], current, players);
//							if(rec==0) {
//								if(rec==0) {
//									players.add(current);
//									if(turncounter>2) {
//										for(int k=0;k<players.size();k++) {
//											if(players.get(k).noOfCells==0) {
//												players.remove(k);
//											}
//										}
//									}
//									if(players.size() == 1) {
//										System.out.println(players.get(0).name+" wins");
//										System.exit(0);
//									}
//									previous=current;
//									current=players.get(0);
//									players.remove(0);
//								}
//								cgc(vis,current);
//							}
//							}
//				});
//				//System.out.print(array[i][j].criticalMass+" ");
//				startx += cell_size;
//			}
//			//System.out.println("");
//			starty += cell_size;
//			if(rows == 9)
//				startx = (int) cell_size + 40;
//			else
//				startx = (int) cell_size ;
//			
//		}
//		for(int i=0;i<rows;i++) {
//			for(int j=0;j<col;j++) {
//				array[i][j].findNeighbours(array);
//			}
//		}
//		
//		return scene;
//
//	}
	
	
	/*public Scene resumeSceneGrid()
	{
		players.add(0,current);
		current=previous;
		//current = players.get(0);
		//Player other = players.get(1);
		//current=new Player("p1",Color.RED);
		//Player other=new Player("p2",Color.BLUE);
		//players.add(other);
		Group root = new Group();
		Scene scene = new Scene(root, 550, 800, Color.BLACK);
		//array = new Cell[rows + 1][col + 1];
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
				Grid96 g= new Grid96();
				Scene scene_grid= g.resumeSceneGrid();
				Main.newstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Main.newstage.setScene(scene_grid);
				Main.newstage.show();
			
			}
		});
		
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
		
		
		Rectangle[][] vis = new Rectangle[rows][col];
		for(int i = 0 ; i < rows ; i++) {
			
			for(int j = 0 ; j < col ; j++) {
				
				//array[i][j] = new Cell(i, j);
				//array[i][j].findCriticalMass(i, j);
				array[i][j].branch.setLayoutX(startx + 25);
				array[i][j].branch.setLayoutY(starty + 25);
				//System.out.print(array[i][j].branch.getChildren().size()+" ");
				Integer I = new Integer(i);
				Integer J = new Integer(j);
				int ol=array[i][j].getorbs();
				//System.out.print(array[i][j].getorbs()+" ");
				array[i][j].orbNumber=0;
				array[i][j].sc.orbNumber=0;
				for(int a=0;a<ol;a++) {
					array[i][j].addOrb();
					//System.out.print(" added ");
				}
				System.out.print(array[i][j].getorbs()+" ");
				vis[i][j] = new Rectangle(cell_size, cell_size, Color.TRANSPARENT);
				vis[i][j].setStroke(current.color);
				root.getChildren().add(vis[i][j]);
				root.getChildren().add(array[i][j].branch);
				vis[i][j].setLayoutX(startx);
				vis[i][j].setLayoutY(starty);
				vis[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					//System.out.println(players.size());
					if(array[I][J].owner == null || array[I][J].owner == current) {
						//System.out.println(array[I][J].x+" "+array[I][J].y);
						try {
							serialize();
							//System.out.println("serialised");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(turncounter!=0) {
							System.out.println(current.name+" has no of cells= "+previous.getCells());
						}
						turncounter++;
						makeMove (array[I][J], current, players);
						if(rec==0) {
							if(rec==0) {
								players.add(current);
								if(turncounter>2) {
									for(int k=0;k<players.size();k++) {
										if(players.get(k).noOfCells==0) {
											players.remove(k);
										}
									}
								}
								if(players.size() == 1) {
									System.out.println(players.get(0).name+" wins");
									System.exit(0);
								}
								previous=current;
								current=players.get(0);
								players.remove(0);
							}
							cgc(vis,current);
						}
						}
				});
				//System.out.print(array[i][j].criticalMass+" ");
				startx += cell_size;
			}
			System.out.println("");
			starty += cell_size;
			if(rows == 9)
				startx = (int) cell_size + 40;
			else
				startx = (int) cell_size ;
			
		}
		System.out.println("");
		for(int i=0;i<rows;i++) {
			for(int j=0;j<col;j++) {
				array[i][j].findNeighbours(array);
			}
		}
		
		return scene;

	}*/
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene= makeSceneGrid();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public  void explode(Player p, Cell c, ArrayList<Player> list) {
			c.owner = null;
			c.orbNumber = 0;
			c.rot.stop();
			c.branch.getChildren().add(c.balls.get(c.criticalMass-1));
			c.branch.setRotate(0);
			for(int i = 0 ; i < c.balls.size() ; i++) {
				c.balls.get(i).setTranslateX(0);
				c.balls.get(i).setTranslateY(0);
			}
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
						ButtonType b1 = new ButtonType("Main Menu");
						ButtonType b2 = new ButtonType("New Game");
						ButtonType b3 = new ButtonType("Exit");
						
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Congratulations!");
						alert.setContentText(players.get(0).name+" wins");
						alert.getButtonTypes().setAll(b1, b2, b3);
						alert.show();
						
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
						game_end = 1;
						
					}
				}
			});
			
			
			for(int i = 0 ; i < c.neighbours.size() ; i++) {
				System.out.println(c.neighbours.get(i).x + " " + c.neighbours.get(i).y);
			}
			t1.play();
			t2.play();
			t3.play();
			t4.play();
			
	}
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
	
	public static void main(String[] args) {
		launch(args);
	}

}
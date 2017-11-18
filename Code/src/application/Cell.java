package application;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;

/**
 * Date : November 18, 2017
 * Cell class implements an application that creates the state of each cell with all the various attributes associated with it
 * 
 * @author manvigoel, arpitbhatia
 */
	public class Cell{
	int oe = 1;
	/**
	 * initialise the object of class sCell
	 */
	public sCell sc=new sCell();
	
	/**
	 *int value to store the value of each cell's x coordinate 
	 */
	public int x=sc.x ;
	
	/**
	 * int value to store the value of each cell's y coordinate
	 */
	public int y=sc.y ;
	
	/**
	 * int value to store the critical mass
	 */
	public int criticalMass=sc.criticalMass;
	
	/**
	 * int value to store the cell's orbnumber
	 */
	public int orbNumber=sc.orbNumber;
	
	/**
	 * attribute of type group to store all nodes together
	 */
	public Group branch = new Group();
	
	/**
	 * player owner to store the owner of scell's player
	 */
	public Player owner = sc.owner;
	
	/**
	 * object of type Timeline to ensure sequential execution
	 */
	public Timeline rot = new Timeline();
	
	/**
	 * arralist of spheres to store balls
	 */
	public ArrayList <Sphere> balls = new ArrayList<Sphere>();
	
	/**
	 * arraylist of cells consisting of neighbours of each cell
	 */
	public ArrayList <Cell> neighbours = new ArrayList<Cell>();
	
	/**
	 * 4 spheres for each neighbour
	 */
	public Sphere r1,r2,r3,r4;
	String size = ("9 x 6");
	
	
	/**
	 * creates balls for each cell
	 */
	public void createballs() {
		balls.clear();
		
		Random ran = new Random(System.currentTimeMillis());
		int Low = 5;
		int High = 10;
		
		r1=new Sphere(10);
		r2=new Sphere(10);
		r2.setTranslateX(0);
		r2.setTranslateY(0);
		r3=new Sphere(10);
		r3.setTranslateX(0);
		r3.setTranslateY(0);
		r4=new Sphere(10);
		if(x+1 < cols) {
			balls.add(r2);
		}
		if(x-1 > -1) {
			balls.add(r4);
		}
		if(y-1 > -1) {
			balls.add(r3);
		}
		if(y+1 < rows) {
			balls.add(r1);
		}
		/*balls.add(r1);
		balls.add(r2);
		balls.add(r3);
		balls.add(r4);*/
	}
	
	
	/**
	 * Constructor to initilise frames and call creat ball
	 * @param x coordinate of each cell
	 * @param y coordinate of each cell
	 */
	public Cell(int x, int y){
		
		size = Main.gridSize;
		if(size.equalsIgnoreCase("9 x 6")){
			rows = 6;
			cols = 9;
			
		}
		else{
			rows = 10;
			cols = 15;
			
		}
		
		this.x = x;
		this.sc.x=x;
		this.y = y;
		this.sc.y=y;
		findCriticalMass(this.x,this.y);
		rot.setRate(2);
		rot.setCycleCount(Timeline.INDEFINITE);
		KeyFrame startk = new KeyFrame(Duration.ZERO, new KeyValue(branch.rotateProperty(), 0));
		KeyFrame endk = new KeyFrame(Duration.seconds(5), new KeyValue(branch.rotateProperty(), 360));
		rot.getKeyFrames().add(startk);
		rot.getKeyFrames().add(endk);
		rot.playFromStart();
		orbNumber=0;
		createballs();
		//branch.getChildren().add(r);
	}
	
	
	/**
	 * Constructor to store value from a previously stored state 
	 * @param sc variable of type sCell
	 */
	public Cell(sCell sc){
		
		this.sc=sc;
		if(Main.gridSize != null){
			size = Main.gridSize;
			if(size.equalsIgnoreCase("9 x 6")){
				rows = 6;
				cols = 9;
				
			}
			else{
				rows = 10;
				cols = 15;
				
			}
		}
		else{
			rows = 6;
			cols = 9;
		}
		this.x = sc.x;
		this.y = sc.y;
		findCriticalMass(this.x,this.y);
		rot.setRate(2);
		rot.setCycleCount(Timeline.INDEFINITE);
		KeyFrame startk = new KeyFrame(Duration.ZERO, new KeyValue(branch.rotateProperty(), 0));
		KeyFrame endk = new KeyFrame(Duration.seconds(5), new KeyValue(branch.rotateProperty(), 360));
		rot.getKeyFrames().add(startk);
		rot.getKeyFrames().add(endk);
		rot.playFromStart();
		if(sc.owner!=null) {
		sc.owner.setcol();
		}
		owner=sc.owner;
		createballs();
		orbNumber=sc.orbNumber;
		//System.out.print(" "+orbNumber+" ");
		//branch.getChildren().add(r);
	}
	
	
	/**
	 * sets translation of all balls to 0
	 */
	public void reset() {
		orbNumber=0;
		this.branch.getChildren().clear();
		r1.setTranslateX(0);
		r1.setTranslateY(0);
		r2.setTranslateX(0);
		r2.setTranslateY(0);
		r3.setTranslateX(0);
		r3.setTranslateY(0);
		r4.setTranslateX(0);
		r4.setTranslateY(0);
		rot.playFromStart();
	}
	/**
	 * initialise value of rows and columns
	 */
	static int rows = 6, cols = 9;
	
	/**
	 * calculates critical mass for each cell
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 */
	public void findCriticalMass(int x, int y){
		if(x==0 || x==cols-1) {
			if(y==0 || y==rows-1) {
				this.criticalMass=2;
				this.sc.criticalMass=2;
			}
			else {
				this.criticalMass=3;
				this.sc.criticalMass=3;
			}
		}
		else if(y==0 || y==rows-1) {
			if(x==cols-1) {
				this.criticalMass=2;
				this.sc.criticalMass=2;
			}
			else {
				this.criticalMass=3;
				this.sc.criticalMass=3;
			}
		}
		else if(x==cols-1 && y==rows-1) {
			this.criticalMass=2;
			this.sc.criticalMass=2;
		}
		else {
			this.criticalMass=4;
			this.sc.criticalMass=4;
		}
	}
	
	
	/**
	 * finds the neighbours of each cell and adds to an arraylist
	 * @param array 2d array of cell
	 */
	public void findNeighbours(Cell[][] array){
		if(x + 1 < cols) {
			neighbours.add(array[this.x+1][this.y]);
		}
		if(x - 1 > -1) {
			neighbours.add(array[this.x-1][this.y]);
		}
		if(y - 1 > -1) {
			neighbours.add(array[this.x][this.y-1]);
		}
		if(y + 1 < rows) {
			neighbours.add(array[this.x][this.y+1]);
		}
		
	}
	
	/**
	 * @return arraylist of neighbours
	 */
	public ArrayList<Cell> getNeighbours(){
		return (this.neighbours);
	}
	
	/**
	 * @param x xcoordinate
	 * @param y ycoordinate
	 * @return critical mass of each cell
	 */
	public int getCriticalMass(int x, int y){
		return (this.criticalMass);
	}
	
	/**
	 * adds an orb to each cell with proper animation
	 */
	public void addOrb(){
		oe++;
		Random ran = new Random(System.currentTimeMillis());
		if(oe%2==0) {
			//balls.get(orbNumber).setLayoutX((ran.nextInt(High-Low) + Low));
			balls.get(orbNumber).setLayoutX(8);
		}
		else {
		//balls.get(orbNumber).setLayoutY((ran.nextInt(High-Low) + Low));
			balls.get(orbNumber).setLayoutY(8);
		}
		if(orbNumber==0) {
			Material kMaterial = new PhongMaterial();
			//System.out.println(owner.color);
			((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
			//((PhongMaterial) kMaterial).setDiffuseColor(Color.GREEN);
			for(int i=0;i<balls.size();i++) {
				balls.get(i).setMaterial(kMaterial);
			}
			balls.get(orbNumber).setLayoutX(0);
			balls.get(orbNumber).setLayoutY(0);
		}
		branch.getChildren().add(balls.get(orbNumber));
		//Material kMaterial = new PhongMaterial();
		//((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
		//((PhongMaterial) kMaterial).setDiffuseColor(Color.GREEN);
		//Sphere r = new Sphere(10);
		//r.setMaterial(kMaterial);
		orbNumber++;
		this.sc.orbNumber=this.orbNumber;
	}
	
	/**
	 * change the owner of a cell when occupied by another player
	 * @param n new owner of the cell
	 * 
	 */
	public void switcho(Player n) {
		this.owner.subCell();
		this.sc.owner.subCell();
		this.owner=n;
		this.sc.owner=n;
		this.owner.addCell();
		this.sc.owner.addCell();
		Material kMaterial = new PhongMaterial();
		((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
		for(int i=0;i<balls.size();i++) {
			balls.get(i).setMaterial(kMaterial);
		}
	}
	
	
	/**
	 * @return number of orbs of each player
	 */
	public int getorbs(){
		return this.orbNumber;
	}
	
	/**
	 * sets the owner of each cell
	 * @param owner owner of type player
	 */
	public void setOwner(Player owner){
		this.owner = owner;
		this.sc.owner=owner;
	}
	
	/**
	 * @return owner of each cell
	 */
	public Player getOwner(){
		return this.owner;
	}
}
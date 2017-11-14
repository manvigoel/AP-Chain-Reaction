package game_elements;

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

public class Cell{
	public int x ;
	public int y ;
	public int criticalMass;
	public int orbNumber;
	public Group branch=new Group();
	Player owner;
	public Timeline rot;
	public ArrayList <Sphere> balls = new ArrayList<Sphere>();
	public ArrayList <Cell> neighbours = new ArrayList<Cell>();
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		Timeline rot = new Timeline();
		rot.setRate(2);
		rot.setCycleCount(Timeline.INDEFINITE);
		KeyFrame startk = new KeyFrame(Duration.ZERO, new KeyValue(branch.rotateProperty(), 0));
		KeyFrame endk = new KeyFrame(Duration.seconds(5), new KeyValue(branch.rotateProperty(), 360));
		rot.getKeyFrames().add(startk);
		rot.getKeyFrames().add(endk);
		rot.playFromStart();
		orbNumber=0;
		
		Random ran = new Random(System.currentTimeMillis());
		int Low = 5;
		int High = 10;
		
		Sphere r1=new Sphere(10);
		Sphere r2=new Sphere(10);
		r2.setTranslateX(-1*(ran.nextInt(High-Low) + Low));
		r2.setTranslateY(-1*(ran.nextInt(High-Low) + Low));
		Sphere r3=new Sphere(10);
		r3.setTranslateX(ran.nextInt(High-Low) + Low);
		r3.setTranslateY(ran.nextInt(High-Low) + Low);
		Sphere r4=new Sphere(10);
		balls.add(r1);
		balls.add(r2);
		balls.add(r3);
		balls.add(r4);
		//branch.getChildren().add(r);
	}
	
	Logic l = new Logic();
	public void findCriticalMass(int x, int y){
		if(x == 1 || x == l.rows ){
			if((y == 1) || (y == l.col)){
				this.criticalMass = 2; 
			}
			else{
				this.criticalMass = 3;
			}
		}
		
		else if(y == 1 || y == l.col)
			this.criticalMass = 3;
		
		else
			this.criticalMass = 4;
		
	}
	public void findNeighbours(){
		if (this.x - 1 != 0)
			neighbours.add(new Cell(this.x - 1, this.y));
		if(this.x + 1 != l.col)	
			neighbours.add(new Cell(this.x + 1, this.y));
		if (this.y - 1 != 0 )
			neighbours.add(new Cell(this.x, this.y - 1));
		if (this.y + 1 != l.rows)
			neighbours.add(new Cell(this.x, this.y + 1));
	}
	
	public ArrayList<Cell> getNeighbours(){
		return (this.neighbours);
	}
	
	public int getCriticalMass(int x, int y){
		return (this.criticalMass);
	}
	
	public void addOrb(){
		branch.getChildren().add(balls.get(orbNumber));
		Material kMaterial = new PhongMaterial();
		((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
		Sphere r = new Sphere(10);
		r.setMaterial(kMaterial);
		this.orbNumber+= 1;
	}
	
	
	public int getorbs(){
		return this.orbNumber;
	}
	
	public void setOwner(Player owner){
		this.owner = owner;
	}
	
	public Player getOwner(){
		return this.owner;
	}
}

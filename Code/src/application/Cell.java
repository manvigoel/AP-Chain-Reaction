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

public class Cell{
	public int x ;
	public int y ;
	public int criticalMass;
	public int orbNumber;
	public Group branch=new Group();
	public Player owner;
	public Timeline rot=new Timeline();;
	public ArrayList <Sphere> balls = new ArrayList<Sphere>();
	public ArrayList <Cell> neighbours = new ArrayList<Cell>();
	public static Sphere r1,r2,r3,r4;
	public void createballs() {
		balls.clear();
		
		Random ran = new Random(System.currentTimeMillis());
		int Low = 5;
		int High = 10;
		
		r1=new Sphere(10);
		r2=new Sphere(10);
		r2.setTranslateX(-1*(ran.nextInt(High-Low) + Low));
		r2.setTranslateY((ran.nextInt(High-Low) + Low));
		r3=new Sphere(10);
		r3.setTranslateX(ran.nextInt(High-Low) + Low);
		r3.setTranslateY(ran.nextInt(High-Low) + Low);
		r4=new Sphere(10);
		if(x+1<cols) {
			balls.add(r2);
		}
		if(x-1>-1) {
			balls.add(r4);
		}
		if(y-1>-1) {
			balls.add(r3);
		}
		if(y+1<rows) {
			balls.add(r1);
		}
		/*balls.add(r1);
		balls.add(r2);
		balls.add(r3);
		balls.add(r4);*/
	}
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
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
	public void reset() {
		orbNumber=0;
		this.branch.getChildren().clear();
		Random ran = new Random(System.currentTimeMillis());
		int Low = 5;
		int High = 10;
		r1.setTranslateX(0);
		r1.setTranslateY(0);
		r2.setTranslateX(-1*(ran.nextInt(High-Low) + Low));
		r2.setTranslateY((ran.nextInt(High-Low) + Low));
		r3.setTranslateX(ran.nextInt(High-Low) + Low);
		r3.setTranslateY(ran.nextInt(High-Low) + Low);
		r4.setTranslateX(0);
		r4.setTranslateY(0);
		rot.playFromStart();
	}
	static int rows=6,cols=9;
	public void findCriticalMass(int x, int y){
		if(x==0 || x==cols-1) {
			if(y==0 || y==rows-1) {
				this.criticalMass=2;
			}
			else {
				this.criticalMass=3;
			}
		}
		else if(y==0 || y==rows-1) {
			if(x==cols-1) {
				this.criticalMass=2;
			}
			else {
				this.criticalMass=3;
			}
		}
		else if(x==cols-1 && y==rows-1) {
			this.criticalMass=2;
		}
		else {
			this.criticalMass=4;
		}
	}
	public void findNeighbours(Cell[][] array){
		if(x+1<cols) {
			neighbours.add(array[this.x+1][this.y]);
		}
		if(x-1>-1) {
			neighbours.add(array[this.x-1][this.y]);
		}
		if(y-1>-1) {
			neighbours.add(array[this.x][this.y-1]);
		}
		if(y+1<rows) {
			neighbours.add(array[this.x][this.y+1]);
		}
		/*
		try {
			this.neighbours.add(array[this.x+1][this.y]);
		}catch(ArrayIndexOutOfBoundsException e) {
			
		}
		finally{
			//System.out.println("Added");
		}
		try {
			this.neighbours.add(array[this.x][this.y+1]);
		}catch(ArrayIndexOutOfBoundsException e) {
			
		}
		finally{
			
		}
		try {
			this.neighbours.add(array[this.x-1][this.y]);
		}catch(ArrayIndexOutOfBoundsException e) {
			
		}
		finally{
			
		}
		try {
			this.neighbours.add(array[this.x][this.y-1]);
		}catch(ArrayIndexOutOfBoundsException e) {
			
		}
		finally{
			
		}*/
	}
	
	public ArrayList<Cell> getNeighbours(){
		return (this.neighbours);
	}
	
	public int getCriticalMass(int x, int y){
		return (this.criticalMass);
	}
	
	public void addOrb(){
		if(orbNumber==0) {
			Material kMaterial = new PhongMaterial();
			((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
			for(int i=0;i<balls.size();i++) {
				balls.get(i).setMaterial(kMaterial);
			}
		}
		branch.getChildren().add(balls.get(orbNumber));
		Material kMaterial = new PhongMaterial();
		((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
		Sphere r = new Sphere(10);
		r.setMaterial(kMaterial);
		this.orbNumber+= 1;
	}
	public void switcho(Player n) {
		this.owner.subCell();
		this.owner=n;
		this.owner.addCell();
		Material kMaterial = new PhongMaterial();
		((PhongMaterial) kMaterial).setDiffuseColor(owner.color);
		for(int i=0;i<balls.size();i++) {
			balls.get(i).setMaterial(kMaterial);
		}
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

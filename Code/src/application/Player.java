package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.paint.Color;

public class Player implements Serializable {
	public transient Color color;
	public String name;
	public int noOfCells;
	public double r;
	public double g;
	public double b;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		this.noOfCells = 0;
		r=color.getRed();
		g=color.getGreen();
		b=color.getBlue();
	}
	public void setInts() {
		r=color.getRed();
		g=color.getGreen();
		b=color.getBlue();
	}
	public void setcol() {
		color=new Color(r,g,b,1.0);
	}
	public Color getPlayerColor() {
		return this.color;
	}

	public String getPlayerName() {
		return this.name;
	}

	public void setPlayerColor(Color color) {
		this.color = color;
		r=color.getRed();
		g=color.getGreen();
		b=color.getBlue();
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public void subCell() {

		this.noOfCells--;
	}

	public void addCell() {
		this.noOfCells++;
	}

	public int getCells() {
		return this.noOfCells;
	}
}
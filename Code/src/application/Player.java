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
	public String Colors;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		this.noOfCells = 0;
	}

	public Color getPlayerColor() {
		return this.color;
	}

	public String getPlayerName() {
		return this.name;
	}

	public void setPlayerColor(Color color) {
		this.color = color;
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
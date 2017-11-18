package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 * Date : November 18, 2017 
 * The Player class implements an application that stores the various attributes
 * of a player in the current game(Chain reaction).
 * It implements the serializable interface to read and store data for later use.
 * 
 * @author manvigoel, arpitbhatia  
 */

	public class Player implements Serializable {
		
	/**
	 * Color value for each player
	 */
	public transient Color color;
	
	/**
	 * string value for the name of each player
	 */
	public String name;
	
	/**
	 * int value to store number of cells of each player 
	 */
	public int noOfCells;
	
	/**
	 * double value to extract the "red" component of the color
	 */
	public double r;
	
	/**
	 * double value to extract the "green" component of the color
	 */
	public double g;
	/**
	 * double value to extract the "blue" component of the color
	 */
	public double b;

	/**
	 * Constructor function to initialise various attributes of the player.
	 * @param name Player name
	 * @param color	Player color
	 */
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		this.noOfCells = 0;
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
	}
	
	/**
	 * Extract Red, Green, and Blue components from the hexadecimal color 
	 */
	public void setInts() {
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
	}
	
	/**
	 * set the color from the red, blue, and green components
	 */
	public void setcol() {
		color=new Color(r,g,b,1.0);
	}
	
	/**
	 * @return color of the current player
	 */
	public Color getPlayerColor() {
		return this.color;
	}

	/**
	 * @return name of current player
	 */
	public String getPlayerName() {
		return this.name;
	}

	/**
	 * sets the player color
	 * @param color color to set 
	 */
	public void setPlayerColor(Color color) {
		this.color = color;
		r=color.getRed();
		g=color.getGreen();
		b=color.getBlue();
	}

	/**
	 * sets the player name
	 * @param name name to set 
	 */
	public void setPlayerName(String name) {
		this.name = name;
	}

	/**
	 * subtract the number of cells of each player by 1
	 */
	public void subCell() {
		this.noOfCells--;
	}

	/**
	 *add the number of cells of each player by 1 
	 */
	public void addCell() {
		this.noOfCells++;
	}

	/**
	 * @return number of cells of each player
	 */
	public int getCells() {
		return this.noOfCells;
	}
}
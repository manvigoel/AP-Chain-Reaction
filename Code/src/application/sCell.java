package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.shape.Sphere;

/**
 * Date : November 18, 2017
 * sCell stores all the serializable attributes associated with the player
 * 
 * @author manvigoel, arpitbhatia
 */
public class sCell implements Serializable {
	/**
	 * int value to store the x coordinate 
	 */
	public int x;
	
	/**
	 * int value to store the y coordinate
	 */
	public int y;
	
	/**
	 * int value to store the critical mass of each cell
	 */
	public int criticalMass;
	
	/**
	 * int value to store the number of orbs in each cell
	 */
	public int orbNumber;
	
	/**
	 * stores the owner(Player class) of each cell
	 */
	public Player owner;
}
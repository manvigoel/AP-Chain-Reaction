package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.shape.Sphere;

public class sCell implements Serializable {
	public int x;
	public int y;
	public int criticalMass;
	public int orbNumber;
	public Player owner;
}
package game_elements;

import java.util.*;
public class Logic {
	static int rows, col;
	static Cell array[][];
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter dimension (9, 6 or 10, 15)");
		rows = sc.nextInt();
		col = sc.nextInt();
		array = new Cell[rows + 1][col + 1];
		int noOfPlayers = 0;
		//Logic l = new Logic();
		
		//defining critical mass for all cells in the grid
		for(int i = 1 ; i <= rows ; i ++ ){
			for(int j = 1 ; j <= col ; j ++){
				array[i][j] = new Cell(i, j);
				array[i][j].findCriticalMass(i, j);
			}
		}
		
		//initialising orb number = 0 in all cells
		for(int i = 1 ; i <= rows ; i ++ ){
			for(int j = 1 ; j <= col ; j ++){
				array[i][j].orbNumber = 0;
			}
		}
		ArrayList<Player>players= new ArrayList <Player>();
		String name, color;
		for(int i = 0; i < noOfPlayers ; i ++){
			System.out.println("Enter player " +  (i + 1) + "'s name and color");
			name = sc.next();
			color = sc.next();
			//players.add(new Player(name , color));
			
		}
		
		
		//play game
		int xc, yc;
		for(int i = 0 ; i < 100 ; i ++){
			for(int j = 0 ; j < players.size() ; j ++){
				System.out.println(players.get(j).name + " Please enter x and y coordinates");
				xc = sc.nextInt();
				yc = sc.nextInt();
				//l.makeMove(xc, yc, players.get(j), players);
				//l.displayGrid();
				
				
				if(i != 0){
					for(int a = 0 ; a < players.size(); a ++){
						if(players.get(a).noOfCells == 0){
							System.out.println(players.get(a).name + players.get(a).noOfCells);
							players.remove(a);
							a = 0;
						}
					}
				}
//				for(int x = 0 ; x < players.size() ; x ++)
//					System.out.println(players.get(x).name + " " + players.get(x).noOfCells);
				
				if(players.size() == 1){
					System.out.println(players.get(0).name + " wins!!" );
					System.exit(0);
				}
			}
		}
			
		}
	
	
	public void checkWinner(ArrayList<Player> list){
		for(int i = 0 ; i < list.size(); i ++){
			if(list.get(i).noOfCells == 0)
				System.out.println("yes");
				list.remove(i);
		}
	}
	
	
	
	public void makeMove (int x, int y, Player p, ArrayList<Player> list){
		
		if(array[x][y].getorbs() < array[x][y].criticalMass - 1){
			if(array[x][y].getorbs() == 0){
				p.addCell();
				array[x][y].setOwner(p.name);
			}
			array[x][y].addOrb();
			array[x][y].setOwner(p.name);
			
		}
		else{
			array[x][y].orbNumber = 0;
			p.subCell();
			array[x][y].setOwner(null);
			if(x + 1 <= rows){
				if (array[x + 1][y].getorbs() != 0)
					p.addCell();
				
				Player original; 
				if(array[x + 1][y].getOwner()!= null && !array[x + 1][y].getOwner().equals(p.name)){
					int j = 0 ;
					while(j < list.size()){
						if (list.get(j).name.equals(array[x + 1][y].getOwner())){
							original = new Player(list.get(j).name, list.get(j).color);
							list.get(j).subCell();
							break;
						}
						j ++;
					}
				}
				makeMove(x + 1, y, p, list);
				//System.out.println(array[x + 1][y].x + " " + array[x + 1][y].y + " " + array[x + 1][y].owner);
				//array[x + 1][y].setOwner(p.name);
			}
			if(x - 1!= 0){
				if (array[x - 1][y].getorbs() != 0)
					p.addCell();
				
				Player original; 
				if(array[x - 1][y].getOwner()!= null && !array[x - 1][y].getOwner().equals(p.name)){
					int j = 0 ;
					while(j < list.size()){
						if (list.get(j).name.equalsIgnoreCase(array[x - 1][y].getOwner())){
							original = new Player(list.get(j).name, list.get(j).color);
							list.get(j).subCell();
							break;
						}
						j ++;
					}
				}
				makeMove(x - 1, y, p, list);
				//System.out.println(array[x - 1][y].x + " " + array[x - 1][y].y + " " + array[x - 1][y].owner);
				//array[x - 1][y].setOwner(p.name);
			}
			if(y + 1 <= col){
				
				if (array[x][y + 1].getorbs() != 0)
					p.addCell();
				
				Player original; 
				
				if(array[x][y + 1].getOwner() != null && !array[x][y + 1].getOwner().equals(p.name) ){
					int j = 0 ;
					while(j < list.size()){
						if (list.get(j).name.equals(array[x][y + 1].getOwner())){
							original = new Player(list.get(j).name, list.get(j).color);
							list.get(j).subCell();
							break;
						}
						j ++;
					}
				}
				makeMove(x, y + 1, p, list);
				//System.out.println(array[x][y + 1].x + " " + array[x][y + 1].y + " " + array[x][y + 1].owner);
				//array[x][y + 1].setOwner(p.name);
				
			}
			if(y - 1 != 0 ){
				if (array[x][y - 1].getorbs() != 0)
					p.addCell();
				
				Player original; 
				if(array[x][y - 1].getOwner()!= null && !array[x][y - 1].getOwner().equals(p.name)){
					int j = 0 ;
					while(j < list.size()){
						if (list.get(j).name.equalsIgnoreCase(array[x][y - 1].getOwner())){
							original = new Player(list.get(j).name, list.get(j).color);
							list.get(j).subCell();
							break;
						}
						j ++;
					}
				}
				makeMove(x, y - 1, p, list);
				//System.out.println(array[x][y - 1].x + " " + array[x][y - 1].y + " " + array[x][y - 1].owner);
				//array[x][y - 1].setOwner(p.name);
			}
			
		}
	}
	
	public void displayGrid(){
		for(int i = 1 ; i <= rows ; i ++ ){
			for(int j = 1 ; j <= col ; j ++){
				System.out.print(array[i][j].getOwner() + " ");
			}
			System.out.println( " ");
		}
	}
	
	
	
	public void displayCriticalMass(){
		for(int i = 1 ; i <= rows ; i ++ ){
			for(int j = 1 ; j <= col ; j ++){
				System.out.print(array[i][j].getCriticalMass(i, j) + " ");
			}
			System.out.println( " ");
		}
	}
	
	public void displayOrbs(){
		for(int i = 1 ; i <= rows ; i ++ ){
			for(int j = 1 ; j <= col ; j ++){
				System.out.print(array[i][j].getorbs() + " ");
			}
			System.out.println( " ");
		}
	}

}

/*
	Andy Chrzanowski 5/27/2020
	This class will be used as a node for the A* algorithim
*/

import java.util.ArrayList;

public class Node{
    //node fields
    private boolean traversable;
    private int x, y, gCost, hCost;
    private ArrayList<Node> neighbors;
    private Node parent;

	//constructor to initialize values
    public Node(boolean traverse, int xPos, int yPos){
        traversable = traverse;
        x = xPos;
        y = yPos;
    }

	//getter for traversablility
    public boolean isTraversable(){
        return traversable;
    }

	//find and get the fcost
    public int fCost(){
        return gCost+hCost;
    }

	//getter/setter for gCost
    public int getGCost(){
        return gCost;
    }
    public void setGCost(int g){
        gCost = g;
    }

	//getter/setter for gCost
    public int getHCost() {
        return hCost;
    }
    public void setHCost(int h) {
        hCost = h;
    }

	//setter/getter for neighbors
    public void setNeighbors(ArrayList<Node> n){
        neighbors = n;
    }
    public ArrayList<Node> getNeighbors(){
        return neighbors;
    }

	//getters for x and y coordinantes
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Node getParent(){
        return parent;
    }
    public void setParent(Node n){
        parent = n;
    }
}
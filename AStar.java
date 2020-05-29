/*
	This class performs the AStar pathfinding algorithim
	on map.txt file, a square grid of characters:
	x for impassable nodes
	S for start node
	E for end node
	* for empty node

	it returns the completed map as a character array
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class AStar{

	//AStar method, returns a 2D character array
    public char[][] doAStar() throws IOException{

		//filereading setup
        FileReader fr = new FileReader("Map.txt");
        Scanner sc = new Scanner(fr);

		//get length of square
		int l = sc.nextInt();
		//grid for visuals and for nodes
        char[][] grid = new char[l][l];
        Node[][] nodeGrid = new Node[l][l];

		//open and closed node arraylists
        ArrayList<Node> open = new ArrayList<Node>();
        ArrayList<Node> closed = new ArrayList<Node>();
		//initialize start/end node so java doesnt get mad
		Node startNode = new Node(true, 0, 0);
		Node endNode = new Node(false, 0, 0);

		sc.nextLine();
        //read in file
        for (int i = 0; i < l; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < l; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        //initialize nodeGrid based off file
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {

				//check if it is traversable
                boolean t = grid[i][j] != 'x';
                nodeGrid[i][j] = new Node(t, i, j);

                //find start/end nodes
                if (grid[i][j] == 'S')
                    startNode = nodeGrid[i][j];
                if (grid[i][j] == 'E')
                    endNode = nodeGrid[i][j];
            }
        }

        //add inital node
        open.add(startNode);
		//continue A* until the open list is empy
        while (!open.isEmpty()) {
            //get currentNode
            Node currentNode = open.get(0);

            //get node with the lowest fCost and hCost
            for (int i = 1; i < open.size(); i++) {
                if ((open.get(i).fCost() < currentNode.fCost())
                        || (open.get(i).fCost() == currentNode.fCost() && open.get(i).getHCost() < currentNode.getHCost())) {
                    currentNode = open.get(i);
                }
            }

			//move current node from open to closed list
            open.remove(currentNode);
            closed.add(currentNode);

			//if the path has been found
            if (currentNode == endNode) {
				//create a node for tracing path
				Node traceNode = endNode.getParent();

				//trace path util start node is reached
				while(traceNode != startNode){
					//set grid to show the path
					grid[traceNode.getX()][traceNode.getY()] = 'P';
					//set trace node to the next node in the path
					traceNode = traceNode.getParent();
				}

				//return the grid
				return grid;
            }

			//if the path has not been found yet, get the neighbors of the node
            getNeighbors(currentNode, nodeGrid);
            ArrayList<Node> currentNeighbors = currentNode.getNeighbors();

            //loop over all neighbor nodes
            for(int i = 0; i < currentNeighbors.size(); i++) {
                Node currentNeighbor = currentNeighbors.get(i);

				//if the node has already been checked or is not transversable continue
                if (!currentNeighbor.isTraversable() || closed.contains(currentNeighbor)) {
                    continue;
                }

				//find the new cost to the node
                int newMovementCost = currentNode.getGCost() + getDistance(currentNode, currentNeighbor);

                //if a better path has been found or it is not in open list
                if(newMovementCost < currentNeighbor.getGCost() || !open.contains(currentNeighbor)) {

					//set hCost, gCost, and the parent
                    currentNeighbor.setGCost(newMovementCost);
                    currentNeighbor.setHCost(getDistance(currentNeighbor, endNode));
                    currentNeighbor.setParent(currentNode);

					//add the node to the open list
                    if(!open.contains(currentNeighbor)){
                        open.add(currentNeighbor);
                    }
                }
            }
            //set the grid to display that the node was checked
            if(grid[currentNode.getX()][currentNode.getY()] != 'S')
         		grid[currentNode.getX()][currentNode.getY()] = 'C';
        }
        return grid;
    }

	//takes in a node and a node array to find the given nodes neighbors
	//set the nodes "neighbors" arraylist to the found neighbors
    public void getNeighbors(Node n, Node[][] NG){
        ArrayList<Node> neighbors = new ArrayList<Node>();
        //iterate in a 3x3 block around current node
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                //do not do anything when on current node
                if (x == 0 && y == 0)
                    continue;
                //get position to check
                int checkX = n.getX() + x;
                int checkY = n.getY() + y;

                //check if the position is in the bounds
                if ((checkX >= 0 && checkX < NG.length) && (checkY >= 0 && checkY < NG.length)) {
                    //if so add to the neighbors
                    neighbors.add(NG[checkX][checkY]);
                }
            }
        }
        //set the nodes neighbors
        n.setNeighbors(neighbors);
    }

	//finds the distance between two nodes
    public int getDistance(Node A, Node B){
        //distance in x and y direction
        int distanceX = Math.abs(A.getX() - B.getX());
        int distanceY = Math.abs(A.getY() - B.getY());

		//find if you need to move diagonally int X or Y
        if(distanceX > distanceY) {
            return 14*distanceY + 10*(distanceX-distanceY);
        } else{
            return 14*distanceX + 10*(distanceY-distanceX);
        }
    }
}
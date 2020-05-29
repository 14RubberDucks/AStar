/*
	This program will use the AStar class to display
	the found path in a javafx window
*/

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.*;

public class Main extends Application {
	//start method for javafx
	public void start(Stage stage) throws IOException{
		//group and AStar objects
		Group root = new Group();
		AStar star = new AStar();

		//get the completed map from the AStar class
		char[][] map = star.doAStar();
		int l = map.length;

		//loop over the map and decide on colors
		for(int i = 0; i < l; i++){
			for(int j = 0; j < l; j++){
				//create the a rectangle and set the border to be black
				Rectangle rect = new Rectangle(j*(500/l), i*(500/l), 500/l, 500/l);
				rect.setStroke(Color.BLACK);

				//decide on the color based on the character
				switch(map[i][j]){
					//unimportant node
					case '*':
						rect.setFill(Color.WHITE);
						break;
					//start/end node
					case 'S':
					case 'E':
						rect.setFill(Color.DARKSLATEBLUE);
						break;
					//nodes that are part of the path
					case 'P':
						rect.setFill(Color.GREEN);
						break;
					//impassable nodes
					case 'x':
						rect.setFill(Color.BLACK);
						break;
					//nodes that have been checked, but not used for path
					case 'C':
						rect.setFill(Color.RED);
						break;
				}
			//add the final rectangle to the group
			root.getChildren().add(rect);
			}
		}

		//javafx window setup
		//								(500/l)*l to correct for int division
		Scene scene = new Scene(root, (500/l)*l, (500/l)*l);
		stage.setTitle("A*");
		stage.setScene(scene);
		stage.show();
   }
   public static void main(String args[]){
		launch();
   }
}
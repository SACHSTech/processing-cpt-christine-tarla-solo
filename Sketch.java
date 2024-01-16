/** Edit Ideas 
 * Add a starting and ending menu (space to start game) 
 * Change sprites with falling speed 
 * Add life and scroe boosters 
 * Add score in top left corner 
 */

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
  // Declaring Image Variables 
  PImage fallingPuff;
  PImage divingPuff;
  PImage glidingPuff;
  PImage happyPuff;
  PImage holdingPuff;
  PImage multiplier;
  PImage heart;
  PImage background;
  // Obstacle Location Variables 
  // *DELETE AFTER* Maybe have more obstacles closer together if its too easy 
  float[] fltObstacleY = {70, 140, 210, 280, 350, 420, 490, 560, 630, 700};
  float[] fltObstacleX = new float[10];
  int intObstacleSpeed = 2;
  // Player Movement Variables 
  int intPlayerX = 163;
  int intPlayerY = 313;
  boolean blnUp = false;
  boolean blnLeft = false;
  boolean blnDown = false;
  boolean blnRight = false;
  // Player Lives Variables 
  int intLives = 3;
  boolean blnPlayerAlive = true;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(400, 700);
    // Assign images 
    fallingPuff = loadImage("/CPT Images/Falling Purple Puff.png");
    divingPuff = loadImage("/CPT Images/Diving Purple Puff Sprite.png");
    glidingPuff = loadImage("/CPT Images/Gliding Purple Puff Sprite.png");
    happyPuff = loadImage("/CPT Images/Happy Purple Puff Sprite.png");
    holdingPuff = loadImage("/CPT Images/Purple Puff Sprite Holding.png");
    multiplier = loadImage("/CPT Images/Green X.png");
    heart = loadImage("/CPT Images/Pixel Heart.png");
    background = loadImage("/CPT Images/Yellow Cloud Background.png");
  }

  /** 
   * Called once at the beginning of execution. Add initial set up values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background.resize(400, 700);
    image(background, 0, 0);
    for (int i = 0; i < fltObstacleX.length; i++){
      // *DELETE AFTER* Potentially make every other X on the same side (use modulus; % = 0 is even, % = 1 is odd) to have a more even play 
      fltObstacleX[i] = random(0, 325);
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw(){
    if (blnPlayerAlive == true){
      background.resize(400, 700);
      image(background, 0, 0);

      // Draw Player Lives 
      stroke(205, 25, 130);
      fill(205, 25, 130);
      if (intLives == 3){
        rect(355, 5, 10, 10);
        rect(370, 5, 10, 10);
        rect(385, 5, 10, 10);
      } else if (intLives == 2){
        rect(570, 5, 10, 10);
        rect(585, 5, 10, 10);
      } else if (intLives == 1){
        rect(585, 5, 10, 10);
      }

      fallingPuff.resize(75, 75);
      image(fallingPuff, intPlayerX, intPlayerY);

      stroke(160, 0, 165);
      fill(160, 0, 165);
      for (int i = 0; i < fltObstacleY.length; i++){
        rect(fltObstacleX[i], fltObstacleY[i], 75, 15);
        playerCollision();
        fltObstacleY[i] -= intObstacleSpeed;

        if (fltObstacleY[i] < 0) {
          fltObstacleY[i] = 700;
        }
      }

      playerMovement();
    } else {
      // End Game 
      // EDIT 
      background(255);
    }
  }

  /**
   * A method that changes the x and y value of the provided variables based on the conditions of the user keyboard input 
   */
  public void playerMovement(){
    if (blnUp == true && intPlayerY >= 25){
      intPlayerY -= 3;
    } else if (blnDown == true && intPlayerY <= 725){
      intPlayerY += 3;
    } else if (blnLeft == true && intPlayerX >= 25){
      intPlayerX -= 3;
    } else if (blnRight == true && intPlayerX <= 375){
      intPlayerX += 3;
    }
  }

  /**
   * A method that reads the user keyboard input and changes values according to the pressed keys 
   */
  public void keyPressed(){
    if (key == 'W' || key == 'w'){
      blnUp = true;
    } else if (key == 'A' || key == 'a'){
      blnLeft = true;
    } else if (key == 'S' || key == 's'){
      blnDown = true;
    } else if (key == 'D' || key == 'd'){
      blnRight = true;
    } else if (key == CODED){
      if (keyCode == UP){
        intObstacleSpeed = 1;
      } else if (keyCode == DOWN){
        intObstacleSpeed = 4;
      }
    }
  }

  /**
   * A method that stops the user keyaord input and resets values according to the pressed keys 
   */
  public void keyReleased(){
    if (key == 'W' || key == 'w'){
      blnUp = false;
    } else if (key == 'A' || key == 'a'){
      blnLeft = false;
    } else if (key == 'S' || key == 's'){
      blnDown = false;
    } else if (key == 'D' || key == 'd'){
      blnRight = false;
    } else if (key == CODED){
      if (keyCode == UP){
        intObstacleSpeed = 2;
      } else if (keyCode == DOWN){
        intObstacleSpeed = 2;
      }
    }
  }

/**
   * A method that detects if the player circle collides with the snow, and deducts a life accordingly 
   */
  public void playerCollision(){
    for (int i = 0; i < fltObstacleY.length; i++){
      if (intPlayerY - 38 < fltObstacleY[i] + 15 && intPlayerY + 38 > fltObstacleY[i]){
        if (intPlayerX - 38 < fltObstacleX[i] + 25 && intPlayerX + 38 > fltObstacleX[i] - 25){        
          fltObstacleY[i] -= 80;
          intLives --;
          updateLives();
        }
      }
    }
  }

  /**
   * A method that checks if the player has more than 0 lives and is still alive 
   */
  public void updateLives(){
    if (intLives == 0){
      blnPlayerAlive = false;
    }
  }
}

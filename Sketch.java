/** Edit Ideas 
 * Add a starting and ending menu (space to start game) 
 * Add life and score boosters 
 * Add score in top left corner 
 */

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
  // Declaring Image Variables 
  PImage playerPuff;
  PImage fallingPuff;
  PImage divingPuff;
  PImage glidingPuff;
  PImage happyPuff;
  PImage holdingPuff;
  PImage multiplier;
  PImage heart;
  PImage background;
  // Obstacle Location Variables 
  float[] fltObstacleY = new float[10];
  float[] fltObstacleX = new float[10];
  int intObsCounter = 700;
  int intMovingSpeed = 2;
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
  // Booster Variables 
  int intBoosterTiming = 0;
  boolean blnShowLifeBooster = false;
  float fltLifeBoosterX = random(0, 375);
  int intLifeBoosterY = 700;
  boolean blnShowScoreBooster = false;
  float fltScoreBoosterX = random(0, 375);
  int intScoreBoosterY = 700;
  boolean blnScoreBoost;
  int intScoreBoostTime = 0;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(400, 700);
    // Assign images 
    fallingPuff = loadImage("/CPT Images/Falling Purple Puff.png");
    fallingPuff.resize(75, 75);
    divingPuff = loadImage("/CPT Images/Diving Purple Puff Sprite.png");
    glidingPuff = loadImage("/CPT Images/Gliding Purple Puff Sprite.png");
    happyPuff = loadImage("/CPT Images/Happy Purple Puff Sprite.png");
    holdingPuff = loadImage("/CPT Images/Purple Puff Sprite Holding.png");
    multiplier = loadImage("/CPT Images/Green X.png");
    multiplier.resize(25, 25);
    heart = loadImage("/CPT Images/Pixel Heart.png");
    heart.resize(25, 25);
    background = loadImage("/CPT Images/Yellow Cloud Background.png");
    //background = loadImage("/CPT Images/Purple Moon Background.jpg");
    playerPuff = fallingPuff;
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

    for (int j = 0; j < fltObstacleY.length; j++){
      fltObstacleY[j] = j * 70 + 700;
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw(){
    background.resize(400, 700);
    image(background, 0, 0);

    image(playerPuff, intPlayerX, intPlayerY);
    playerMovement();

    stroke(160, 0, 165);
    fill(160, 0, 165);
    for (int i = 0; i < fltObstacleY.length; i++){
      rect(fltObstacleX[i], fltObstacleY[i], 75, 15);
      //playerCollision();
      fltObstacleY[i] -= intMovingSpeed;

      if (fltObstacleY[i] < 0) {
        fltObstacleY[i] = 700;
      }
    }

    // Draw Player Lives 
    stroke(205, 25, 130);
    fill(205, 25, 130);
    if (intLives == 3){
      rect(355, 5, 10, 10);
      rect(370, 5, 10, 10);
      rect(385, 5, 10, 10);
    } else if (intLives == 2){
      rect(370, 5, 10, 10);
      rect(385, 5, 10, 10);
    } else if (intLives == 1){
      rect(385, 5, 10, 10);
    }

    showBoosters();

    if (blnPlayerAlive == false){
      noLoop();
    }
  }

  /**
   * A method that changes the x and y value of the provided variables based on the conditions of the user keyboard input 
   */
  public void playerMovement(){
    if (blnUp == true){
      intMovingSpeed = 1;
      playerPuff = glidingPuff;
      playerPuff.resize(65, 65);
    } else if (blnDown == true){
      intMovingSpeed = 4;
      playerPuff = divingPuff;
      playerPuff.resize(75, 60);
    } 
    
    if (blnLeft == true && intPlayerX >= -25){
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
    } 
  }

  /**
   * A method that stops the user keyaord input and resets values according to the pressed keys 
   */
  public void keyReleased(){
    if (key == 'W' || key == 'w'){
      blnUp = false;
      intMovingSpeed = 2;
      playerPuff = fallingPuff;
    } else if (key == 'A' || key == 'a'){
      blnLeft = false;
    } else if (key == 'S' || key == 's'){
      blnDown = false;
      intMovingSpeed = 2;
      playerPuff = fallingPuff;
    } else if (key == 'D' || key == 'd'){
      blnRight = false;
    } 
  }

  /**
   * A method that detects if the player circle collides with the snow, and deducts a life accordingly 
   */
  public void playerCollision(){
    for (int i = 0; i < fltObstacleY.length; i++){
      if (intPlayerY + 5 < fltObstacleY[i] + 15 && intPlayerY + 70 > fltObstacleY[i]){
        if (intPlayerX + 5 < fltObstacleX[i] + 75 && intPlayerX + 70 > fltObstacleX[i]){        
          fltObstacleY[i] = 700;
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

  /**
   * A method that draws boosts  
   */
  public void showBoosters(){
    intBoosterTiming += intMovingSpeed;

    if (intBoosterTiming >= 1500 && intBoosterTiming <= 1510){
      blnShowScoreBooster = true;
    } else if (intBoosterTiming >= 2500 && intBoosterTiming <= 2510){
      blnShowLifeBooster = true;
      intBoosterTiming = 0;
    }

    if (blnShowScoreBooster == true){
      image(multiplier, fltScoreBoosterX, intScoreBoosterY);
      intScoreBoosterY -= intMovingSpeed;
      if (intScoreBoosterY < -25){
        blnShowScoreBooster = false;
      }
    }

    if (blnShowLifeBooster == true){
      image(heart, fltLifeBoosterX, intLifeBoosterY);
      intLifeBoosterY -= intMovingSpeed;
      if (intLifeBoosterY < - 25){
        blnShowLifeBooster = false;
      }
    }

  }

  /**
   * A method that enales the effects of boosters when touched by the player 
   */
  public void enableBoosters(){

  }
}

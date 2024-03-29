import processing.core.PApplet;
import processing.core.PImage;

/**
 * A program that lets the user play a dropper game where they can move left and right, as well as change their speed of descent, 
 * and collect points and boosters
 * @author: C. Tarla
 */

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
  PImage life;
  PImage background;
  // Start Menu Variables 
  boolean blnStartGame = false;
  // Obstacle Location Variables 
  float[] fltObstacleY = new float[10];
  float[] fltObstacleX = new float[10];
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
  // Score Variables 
  int intScore = 0;
  int intScoreIncrease = intMovingSpeed/2;
  boolean blnScoreBoost = false;
  double dblScoreBoostTime = 0;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
    size(400, 700);
    // Assign and resize images 
    fallingPuff = loadImage("/CPT Images/Falling Purple Puff.png");
    fallingPuff.resize(75, 75);
    divingPuff = loadImage("/CPT Images/Diving Purple Puff Sprite.png");
    divingPuff.resize(75, 75);
    glidingPuff = loadImage("/CPT Images/Gliding Purple Puff Sprite.png");
    glidingPuff.resize(75, 75);
    happyPuff = loadImage("/CPT Images/Happy Purple Puff Sprite.png");
    happyPuff.resize(75, 75);
    holdingPuff = loadImage("/CPT Images/Purple Puff Sprite Holding.png");
    holdingPuff.resize(75, 75);
    multiplier = loadImage("/CPT Images/Green X.png");
    multiplier.resize(25, 25);
    heart = loadImage("/CPT Images/Pixel Heart.png");
    heart.resize(25, 25);
    life = loadImage("/CPT Images/Pixel Heart.png");
    life.resize(15, 15);
    background = loadImage("/CPT Images/Yellow Cloud Background.png");
    background.resize(400, 700);
    playerPuff = fallingPuff;
  }

  /** 
   * Called once at the beginning of execution. Add initial set up values here i.e background, stroke, fill etc.
   */
  public void setup() {
    image(background, 0, 0);
    // Set up obstacle x and y values 
    for (int i = 0; i < fltObstacleX.length; i++){
      // Make every other X on the same side for a more even play 
      if (i % 2 == 0){
        fltObstacleX[i] = random(0, 175);
      } else if (i % 2 == 1){
        fltObstacleX[i] = random(225, 325);
      }
    }

    for (int j = 0; j < fltObstacleY.length; j++){
      fltObstacleY[j] = j * 70 + 700;
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw(){
    // Print starting menu 
    fill(160, 0, 165);
    textSize(30);
    text("Press ENTER to start!", 55, 350);
    if (blnStartGame == true){
      image(background, 0, 0);

      // Print player image and call movement 
      image(playerPuff, intPlayerX, intPlayerY);
      playerMovement();

      // Print and animate obstacles 
      stroke(160, 0, 165);
      fill(160, 0, 165);
      for (int i = 0; i < fltObstacleY.length; i++){
        rect(fltObstacleX[i], fltObstacleY[i], 75, 15);
        playerCollision();
        fltObstacleY[i] -= intMovingSpeed;

        // Reset obstacles and their x position once they reach the top of the screen 
        if (fltObstacleY[i] < -15) {
          fltObstacleY[i] = 700;
          if (i % 2 == 0){
            fltObstacleX[i] = random(0, 175);
          } else if (i % 2 == 1){
            fltObstacleX[i] = random(225, 325);
          }
        }
      }

      // Draw Player Lives 
      stroke(205, 25, 130);
      fill(205, 25, 130);
      if (intLives == 3){
        image(life, 355, 5);
        image(life, 370, 5);
        image(life, 385, 5);
      } else if (intLives == 2){
        image(life, 370, 5);
        image(life, 385, 5);
      } else if (intLives == 1){
        image(life, 385, 5);
      }

      // Call booster printing and functions 
      showBoosters();
      enableBoosters();

      // Calculate and show score 
      intScore += (intScoreIncrease);
      textSize(12);
      text(intScore, 5, 15);

      if (blnPlayerAlive == false){
        // Stop game and print end menu 
        image(background, 0, 0);
        endMenu();
        noLoop();
      }
    }
  }
  
  /**
   * A method that changes the player x coordinate and obstacle speed based on the conditions of the user keyboard input 
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
    } else if (blnRight == true && intPlayerX <= 350){
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
    } else if (key == ENTER){
      blnStartGame = true;
    }
  }

  /**
   * A method that stops the user keyboard input and resets values according to the pressed keys 
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
   * A method that detects if the player image collides with the obstacles, and deducts a life accordingly 
   */
  public void playerCollision(){
    for (int i = 0; i < fltObstacleY.length; i++){
      if (intPlayerY + 5 < fltObstacleY[i] + 15 && intPlayerY + 60 > fltObstacleY[i]){
        if (intPlayerX + 10 < fltObstacleX[i] + 75 && intPlayerX + 65 > fltObstacleX[i]){        
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
   * A method that draws and animates boosts based on appropriate timing 
   */
  public void showBoosters(){
    intBoosterTiming += intMovingSpeed;

    // Determine timing of when to show each booster 
    if (intBoosterTiming >= 1500 && intBoosterTiming <= 1510){
      blnShowScoreBooster = true;
    } else if (intBoosterTiming >= 2500){
      blnShowLifeBooster = true;
      intBoosterTiming = 0;
    }

    // Draw, animate, and reset score boosters 
    if (blnShowScoreBooster == true){
      image(multiplier, fltScoreBoosterX, intScoreBoosterY);
      intScoreBoosterY -= intMovingSpeed;
      if (intScoreBoosterY < -25){
        blnShowScoreBooster = false;
        intScoreBoosterY = 700;
        fltScoreBoosterX = random(0, 375);
      }
    }

    // Draw, animate, and reset life boosters 
    if (blnShowLifeBooster == true){
      image(heart, fltLifeBoosterX, intLifeBoosterY);
      intLifeBoosterY -= intMovingSpeed;
      if (intLifeBoosterY < - 25){
        blnShowLifeBooster = false;
        intLifeBoosterY = 700;
        fltLifeBoosterX = random(0, 375);
      }
    }

  }

  /**
   * A method that enables the effects of boosters when touched by the player 
   */
  public void enableBoosters(){
    // Detect collision between player and life booster 
    if (intPlayerY < intLifeBoosterY + 25 && intPlayerY + 75 > intLifeBoosterY){
      if (intPlayerX < fltLifeBoosterX + 25 && intPlayerX + 75 > fltLifeBoosterX){        
        if (intLives < 3 && intLives > 0){
          blnShowLifeBooster = false;
          intLifeBoosterY = 700;
          intLives ++;
        }
      }
    }

    // Detect collision between player and score booster 
    if (intPlayerY < intScoreBoosterY + 25 && intPlayerY + 75 > intScoreBoosterY){
      if (intPlayerX < fltScoreBoosterX + 25 && intPlayerX + 75 > fltScoreBoosterX){        
        blnShowScoreBooster = false;
        intScoreBoosterY = 700;
        blnScoreBoost = true;
      }
    }

    // Change score increase while score booster applies 
    if (blnScoreBoost == true){
      dblScoreBoostTime += 0.0167;
      intScoreIncrease = intMovingSpeed;
      if (dblScoreBoostTime >= 3){
        dblScoreBoostTime = 0;
        blnScoreBoost = false;
        intScoreIncrease = intMovingSpeed/2;
      }
    }
  }

  /**
   * A method that displays an end menu when the player dies 
   */
  public void endMenu(){
    if (intScore < 10000){
      fill(160, 0, 165);
      textSize(40);
      text("Game Over!", 95, 350);
      textSize(20);
      text("Final Score: " + intScore, 125, 375);
    } else {
      fill(160, 0, 165);
      textSize(40);
      text("You Win!", 115, 350);
      textSize(20);
      text("Final Score: " + intScore, 110, 375);
    }
  }
}

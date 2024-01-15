import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
  PImage fallingPuff;
  PImage divingPuff;
  PImage glidingPuff;
  PImage happyPuff;
  PImage holdingPuff;
  PImage multiplier;
  PImage heart;
  PImage background;
  // *DELETE AFTER* Maybe have more obstacles closer together if its too easy 
  float[] fltObstacleY = {70, 140, 210, 280, 350, 420, 490, 560, 630, 700};
  float[] fltObstacleX = new float[10];
  int intObstacleSpeed = 2;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(400, 700);
    // Assign images 
    fallingPuff = loadImage("Comp Sci CPT - Falling Purple Puff.png");
    divingPuff = loadImage("Comp Sci CPT - Diving Purple Puff Sprite.png");
    glidingPuff = loadImage("Comp Sci CPT - Gliding Purple Puff Sprite.png");
    happyPuff = loadImage("Comp Sci CPT - Happy Purple Puff Sprite.png");
    holdingPuff = loadImage("Comp Sci CPT - Purple Puff Sprite Holding.png");
    multiplier = loadImage("Green X.png");
    heart = loadImage("Pixel Heart.png");
  }

  /** 
   * Called once at the beginning of execution. Add initial set up values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(210, 255, 173);
    for (int i = 0; i < fltObstacleX.length; i++){
      // *DELETE AFTER* Potentially make every other X on the same side (use modulus; % = 0 is even, % = 1 is odd) to have a more even play 
      fltObstacleX[i] = random(0, 325);
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw(){
	  background(210, 255, 173);
    //image(background, 0, 0);
    //fallingPuff.resize(20, 20);
    //image(fallingPuff, 200, 350);
    for (int i = 0; i < fltObstacleY.length; i++){
      rect(fltObstacleX[i], fltObstacleY[i], 75, 15);
      fltObstacleY[i] -= intObstacleSpeed;

      if (fltObstacleY[i] < 0) {
        fltObstacleY[i] = 700;
      }
    }
  }
}

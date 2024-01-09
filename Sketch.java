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
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw(){
	  //image(background, 0, 0);
    happyPuff.resize(20, 20);
    image(happyPuff, 200, 350);
  }
}

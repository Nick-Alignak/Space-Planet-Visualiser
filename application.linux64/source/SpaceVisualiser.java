import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SpaceVisualiser extends PApplet {

// Space music visualiser

/*
 * Uses FFT to decide how fast the planets revolve around the center,
 * Speed is decided by the average of a group of 20 bands,
 * Can be paused and resumed easily using the space bar,
*/
 
 
int numberOfPlanets = 6;
float total=0;
planet[] planets = new planet[numberOfPlanets];
boolean input;



Minim minim;
AudioPlayer player;
AudioInput in;
FFT ourFFT;

public void setup() {
  size(750, 750, P3D);
  minim=new Minim(this);
  player=minim.loadFile("Witchcraft.mp3");
  //player=minim.loadFile("Levels.mp3");
  
  player.play();
  ourFFT = new FFT(player.bufferSize(), player.sampleRate());
  background(50);

  planets[0] = new planet(3, 200, 50, 255, 153, 125);
  planets[1] = new planet(1, 100, 50, 125, 255, 153);
  planets[2] = new planet(2, 150, 50, 153, 125, 125);
  planets[3] = new planet(4, 250, 50, 255, 255, 125);
  planets[4] = new planet(5, 300, 50, 125, 255, 255);
  planets[5] = new planet(6, 350, 50, 153, 225, 125);
}

public void draw() {
  fill(10, 100);
  rect(0, 0, width, height);
  line(0, 250, width, 250);
  ourFFT.forward(player.mix);
  for (int i=0; i<ourFFT.specSize (); i++) {
    stroke(255, 0, 0);
    strokeWeight(5);
    total = total + (ourFFT.getBand(i)*8);
    if (i%20 == 19) {
      //line(i, height, i, height-(total/10));
      noStroke();
      fill(255, 153, 0, 50);
      ellipse(width/2, height/2, total/20, total/20);
      total=0;
    }
  }
  for (int i=0; i<numberOfPlanets; i++) {
    planets[i].calcAv();
    planets[i].move();
    planets[i].paint();
  }
}

public void keyReleased() {
  if (key==' ') {
    if (player.isPlaying()) {
      player.pause();
    } else {
      player.play();
    }
  }
}

class planet {
  int id;
  float distance;
  float radius;
  float velocity;
  float myTimer;
  int colorR;
  int colorG;
  int colorB;
  float xPos, yPos, yTemp, xTemp;
  float average;
  float direction;

  planet(int idIn, float distIn, float radIn, int rIn, int gIn, int bIn) {
    id = idIn;
    if (id%2==1) {
      direction=-1;
    } else {
      direction=1;
    }
    distance = distIn;
    radius = radIn;
    colorR = rIn;
    colorG = gIn;
    colorB = bIn;
    myTimer = PApplet.parseInt(random(2000));
  }

  public void calcAv() {
    int i = (id+1)*50;
    for (int j=id; j<id*25; j++) {
      average = average+player.left.get(j)*100;
    }
    average = average/(id-(id*25));
  }

  public void move() {
    for (int i=0*id; i<21*id; i++) {
      total = total + (ourFFT.getBand(i));
    }
    myTimer=myTimer+(total/40);
    myTimer=myTimer+1;
    total = 0;
    yTemp = sin((myTimer/id*direction)/360) * distance;
    xTemp = cos((myTimer/id*direction)/360) * distance;
    xPos = width/2 +xTemp;
    yPos = height/2 +yTemp;
  }

  public void paint() {
    noStroke();
    fill(colorR, colorG, colorB);
    ellipse(xPos, yPos, (radius/2)+(abs(average)/2), (radius/2)+(abs(average)/2));
    println(radius);
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SpaceVisualiser" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

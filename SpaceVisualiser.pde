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
import ddf.minim.*;
import ddf.minim.analysis.*;

Minim minim;
AudioPlayer player;
AudioInput in;
FFT ourFFT;

void setup() {
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

void draw() {
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

void keyReleased() {
  if (key==' ') {
    if (player.isPlaying()) {
      player.pause();
    } else {
      player.play();
    }
  }
}


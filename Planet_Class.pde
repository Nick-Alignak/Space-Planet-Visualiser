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
    myTimer = int(random(2000));
  }

  void calcAv() {
    int i = (id+1)*50;
    for (int j=id; j<id*25; j++) {
      average = average+player.left.get(j)*100;
    }
    average = average/(id-(id*25));
  }

  void move() {
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

  void paint() {
    noStroke();
    fill(colorR, colorG, colorB);
    ellipse(xPos, yPos, (radius/2)+(abs(average)/2), (radius/2)+(abs(average)/2));
    println(radius);
  }
}


class Plant {

  float xpos, ypos, diameter;
  int life=1;
  color col;
  boolean yummy=false;

  Plant(float x, float y, float _diameter, color _col) {
    xpos = x;//xrange/2+random(-x+_diameter/2, x-_diameter/2);
    ypos = y;//yrange/2+random(-y+_diameter/2, y-_diameter/2);
    diameter = _diameter;
    col = _col;
  }

  Plant(float x, float y, float _diameter, color _col, boolean _yummy) {
    xpos = x;
    ypos = y;
    diameter = _diameter;
    col = _col;
    yummy=_yummy;
    if (xpos<=_diameter) xpos=_diameter;
    if (ypos<=_diameter) ypos=_diameter;
    if (xpos>=xrange) xpos=xrange;
    if (ypos>=yrange) ypos=yrange;
  }

  void update() {
    display();
  }

  void display() {
    stroke(col);
    strokeWeight(2);
    line(xpos, ypos, xpos-diameter, ypos-diameter);
    line(xpos, ypos, xpos+diameter, ypos-diameter);
    stroke(0);
    strokeWeight(1);
  }

  boolean dead() {
     if (yummy == true) return true;
    if (life<=0) return true;
    else       return false;
  }
}
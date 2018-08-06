class Dung extends Plant {

  int fTime;
  Dung(float x, float y, float _diameter, color _col, boolean _yummy) {
    super(x, y, _diameter, _col, _yummy);
    fTime=frameCount-1;
  }

  void update() {
    time();
    display(yummy);
  }

  void time() {
    //一定時間経ったか
    if ((frameCount-fTime)%480==0) {
      //植物化
     int rand=(int)random(5);
     if(rand>0){
      for (int i = 1; i<=4; i++) {
        if (i==1) {
          plants.add(new Plant(xpos+diameter/2, ypos+diameter/2, diameter, color(0, 255, 0), false));
        } else if (i==2) {
          plants.add(new Plant(xpos-diameter/2, ypos+diameter/2, diameter, color(0, 255, 0), false));
        } else if (i==3) {
          plants.add(new Plant(xpos-diameter/2, ypos-diameter/2, diameter, color(0, 255, 0), false));
        } else if (i==4) {
          plants.add(new Plant(xpos+diameter/2, ypos-diameter/2, diameter, color(0, 255, 0), false));
        }
      }
     }else{
     }
      life=0;
    } 

    
  }




  void display(boolean _yummy) {
    fill(col);
    noStroke();
    //食われたか
    if (yummy) {
      rect(xpos, ypos, diameter/2, diameter/2);//フン
    } else {       
      ellipse(xpos, ypos, diameter, diameter);//死がい
    }
    stroke(0);
  }
  boolean dead() {

    if (life<=0) return true;
    else       return false;
  }
}

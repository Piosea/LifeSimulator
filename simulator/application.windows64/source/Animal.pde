class Animal {

  float vx, vy, xpos, ypos; //x方向の速度、y方向の速度、位置
  float diameter;           //動物の大きさ
  int life=0, food=0;//寿命、捕食回数
  boolean paku = false;     //true＝捕食された
  color col;//動物の色

  //setup内で呼ばれるコンストラクタ
  Animal(float x, float y, float _diameter, color _col, int _life) {
    xpos = x;
    ypos = y;
    diameter = _diameter;
    col = _col;
    life = _life;
  }
  //draw内で呼ばれるコンストラクタ
  Animal(float x, float y, float _diameter, color _col, int _life, boolean _breed) {
    xpos = x-_diameter;
    ypos = y;
    diameter = _diameter;
    col = _col;
    life = _life;
    //エリア外に出ないようにする
    if (xpos<=_diameter) xpos=_diameter;
    if (ypos<=_diameter) ypos=_diameter;
  }
  //ランダム時間たったら進む方向を変え寿命を減らす 
  void move() {
    if (frameCount%(60*(int)random(1, 3))==0) {
      life--;

      vx=random(-2, 2);
      vy=random(-2, 2);
    }

    //位置の更新
    xpos+=vx;
    ypos+=vy;
  }
  void edge() {
    if (xpos < diameter/2 || xpos > xrange-diameter/2) {
      vx*=-2;
    }
    if (ypos < diameter/2 || ypos > yrange-diameter/2) {
      vy*=-2;
    }
  }
  void display() {
    fill(col);
    noStroke();
    ellipse(xpos, ypos, diameter, diameter);
    stroke(0);
  }
  //検死関数 
  boolean dead(float _xpos, float _ypos, float _diameter, boolean _paku, int _life) {
    //食われたか
    if (_paku == true) return true;
    //寿命が来たか
    if (_life<=0) {
      //死がい化黒丸になる
      if (nightmode) {
        dungs.add(new Dung(_xpos, _ypos, _diameter, color(255, 150, 220), false));
      } else {
        dungs.add(new Dung(_xpos, _ypos, _diameter, color(0, 0, 0), false));
      }
      return true;
    }
    //生存
    else  return false;
  }
}

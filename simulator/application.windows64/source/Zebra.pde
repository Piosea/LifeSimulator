class Zebra extends Animal {
  //Animalクラスのコンストラクタを継承
  Zebra(float x, float y, float _diameter, color _col, int _life) {
    super(x, y, _diameter, _col, _life);
  }
  Zebra(float x, float y, float _diameter, color _col, int _life, boolean _breed) {
    super(x, y, _diameter, _col, _life, _breed);
  }

  void update() {
    move();
    eat();
    edge();
    display();
  }

  void move() {
    if (frameCount%(60*(int)random(1, 3))==0) {
      life--;

      vx=random(-zebraMaxspeed, zebraMaxspeed);
      vy=random(-zebraMaxspeed, zebraMaxspeed);
    }

    //位置の更新
    xpos+=vx;
    ypos+=vy;
  }

  void eat() {
    for (int i = plants.size()-1; i>=0; i--) {
      Plant p = plants.get(i);
      if (dist(xpos, ypos, p.xpos, p.ypos)<=(p.diameter/2+diameter)) {
        food++;
        life++;
        //捕食回数が一定値を超えたか
        if (food%zebraborn==0) {
          //誕生
          zebras.add(new Zebra(xpos, ypos, diameter, color(0, 0, 255), 15, true));
        }
        if (food%zebradung==0) {
          dungs.add(new Dung(xpos, ypos, p.diameter, color(160, 0, 0), true));
        }
        //捕食したのでその位置にフンをする
        p.yummy=true;
      }
      //zebraが一定範囲内にいて寿命が迫っているか
      if (dist(xpos, ypos, p.xpos, p.ypos)<=(zebravision*diameter/2)) {
        if (life<=zebrasearchHP) {
          //食べに向かう
          if (nightmode) {
            stroke(255);
          } else {
            stroke(0);
          }
          strokeWeight(0.5);
          line(xpos, ypos, p.xpos, p.ypos);
          vx=(p.xpos-xpos)/(1.5*diameter);
          vy=(p.ypos-ypos)/(1.5*diameter);
          break;
        }
      }
    }
  }

  void edge() {
    super.edge();
  }

  void display() {
    super.display();
  }

  boolean dead() {
    return super.dead(xpos, ypos, diameter, paku, life);
  }
}

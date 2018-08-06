class Lion extends Animal {
  //Animalクラスのコンストラクタを継承
  Lion(float x, float y, float _diameter, color _col, int _life) {
    super(x, y, _diameter, _col, _life);
  }
  Lion(float x, float y, float _diameter, color _col, int _life, boolean _breed) {
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

      vx=random(-lionMaxspeed, lionMaxspeed);
      vy=random(-lionMaxspeed, lionMaxspeed);
    }

    //位置の更新
    xpos+=vx;
    ypos+=vy;
  }

  void eat() {
    for (int i = zebras.size()-1; i>=0; i--) {
      Zebra z = zebras.get(i);
      //zebraとlionの距離が0か
      if (dist(xpos, ypos, z.xpos, z.ypos)<=(z.diameter/2+diameter)) {
        food++;
        life++;
        //捕食回数が一定値を超えたか
        if (food%lionborn==0) {
          //ライオン誕生
          lions.add(new Lion(xpos, ypos, diameter, color(255, 0, 0), 15, true));
        }
        if (food%liondung==0) {
          dungs.add(new Dung(xpos, ypos, z.diameter, color(160, 0, 0), true));
        }
        //捕食したのでその位置にフンをする
        z.paku=true;
      }
      //zebraが一定範囲内にいて寿命が迫っているか
      if (dist(xpos, ypos, z.xpos, z.ypos)<=(lionvision*diameter/2)) {
        if (life<=lionsearchHP) {
          //食べに向かう
          if (nightmode) {
            stroke(255);
          } else {
            stroke(0);
          }
          strokeWeight(0.5);
          line(xpos, ypos, z.xpos, z.ypos);
          vx=(z.xpos-xpos)*2/(1.5*diameter);
          vy=(z.ypos-ypos)*2/(1.5*diameter);
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
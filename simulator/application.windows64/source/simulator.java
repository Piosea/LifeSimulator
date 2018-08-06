import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class simulator extends PApplet {

Camera worldCamera;

ArrayList<Plant> plants = new ArrayList<Plant>();
ArrayList<Lion> lions   = new ArrayList<Lion>();
ArrayList<Zebra> zebras = new ArrayList<Zebra>();
ArrayList<Dung> dungs   = new ArrayList<Dung>();
float total;

float xrange=800, yrange=800;
int syoki=300;
int zebraHP=15, zebrasearchHP=5, zebraborn=5, zebravision=15, zebradung=5;
int lionHP=15, lionsearchHP=5, lionborn=10, lionvision=17, liondung=1;
float zebraMaxspeed=2.0f, lionMaxspeed=2.0f;


float scale=1;

PImage pencil, eraser, graph;
int scene=0;
int menupage=0;
int selectitem=0;
boolean nightmode=false;

int msecond=0;
int second=0;
int minute=0;

int wait;

boolean gameover=false;

Title title;
Setting setting;
button quit;
public void setup() {
  
  title=new Title();
  setting=new Setting();
  worldCamera = new Camera();
  

  pencil=loadImage("enpitsu.png");
  eraser=loadImage("keshigomu.png");
  graph=loadImage("oresen.png");

  quit=new button("Quit", 1150, 750, 50, 50);

  PFont pf=createFont("SansSerif", 50);
  textFont(pf);
}
public void draw() {
  switch (scene) {
  case 0:
    title.draw();
    break;
  case 1:
    setting.draw();
    break;
  case 2:
    pushMatrix();
    translate(-worldCamera.pos.x, -worldCamera.pos.y);
    worldCamera.draw();
    scale(scale);
    if (nightmode) {
      fill(0, 2);
      noStroke();
      rect(400,400,800,800);
    } else {
      background(255);
    }
    //for文は要素を削除する場合があれば逆方向に
    for (int i = plants.size ()-1; i>=0; i--) {
      Plant pla = plants.get(i);
      pla.update();
      //生存確認
      if (pla.dead()) plants.remove(i);
    }
    for (int i = zebras.size ()-1; i>=0; i--) {
      Zebra z = zebras.get(i);
      z.update();
      if (z.dead()) zebras.remove(i);
    }
    for (int i = lions.size ()-1; i>=0; i--) {
      Lion l = lions.get(i);
      l.update();

      if (l.dead()) lions.remove(i);
    }
    for (int i = dungs.size ()-1; i>=0; i--) {
      Dung d = dungs.get(i);
      d.update();  
      if (d.dead()) dungs.remove(i);
    }
    if (nightmode) {

    }
    popMatrix();
    menudisplay();
    judgegameover();
    break;
  }
}
public void menudisplay() {
  rectMode(CORNER);
  strokeWeight(5);
  if (nightmode) {
    stroke(255, 150, 220);
    fill(0);
  } else {
    stroke(0);
    fill(255);
  }

  rect(800, 0, 400, 800);
  image(pencil, 800, 0, 100, 100);
  image(eraser, 900, 0, 100, 100);
  image(graph, 1000, 0, 100, 100);
  noFill();
  strokeWeight(2);
  rect(800, 0, 100, 100);
  rect(900, 0, 100, 100);
  rect(1000, 0, 100, 100);
  textSize(50);

  if (menupage!=0) {
    stroke(255, 0, 0);
    strokeWeight(5);
    noFill();
    rect(800+(menupage-1)*100, 0, 100, 100);
    textAlign( CENTER );
    switch (menupage) {
    case 1:
      if (nightmode) {
        fill(255, 150, 220);
      } else {
        fill(0);
      }
      text("生成モード", 950, 170);
      if (nightmode) {
        stroke(255, 150, 220);
      } else {
        stroke(0);
      }

      noFill();
      for (int i=0; i<3; i++) {
        rect(800, 200+i*100, 300, 100);
      }
      if (selectitem!=0) {
        stroke(255, 0, 0);
        strokeWeight(5);
        noFill();
        rect(800, 200+(selectitem-1)*100, 300, 100);
      }
      noStroke();
      fill(255, 0, 0);
      ellipse(850, 250, 80, 80);
      text("肉食", 1000, 270);


      fill(0, 0, 255);
      ellipse(850, 350, 80, 80);
      text("草食", 1000, 370);

      stroke(0, 255, 0);
      fill(0, 255, 0);
      strokeWeight(4);
      line(850, 475, 810, 440);
      line(850, 475, 890, 440);
      text("草", 1000, 470);

      break;

    case 2 :
      if (nightmode) {
        fill(255, 150, 220);
      } else {
        fill(0);
      }
      text("削除モード", 950, 170);
      break;
    case 3 :
      if (nightmode) {
        fill(255, 150, 220);
      } else {
        fill(0);
      }
      text("グラフモード", 950, 170);
      rectMode(CENTER);
      noStroke();
      total=lions.size()+zebras.size()+plants.size();

      fill(255, 0, 0);
      rect(1000, 250, lions.size()/total*400, 80);
      fill(0, 0, 255);
      rect(1000, 350, zebras.size()/total*400, 80);
      fill(0, 255, 0);
      rect(1000, 450, plants.size()/total*400, 80);

      break;
    default:
      break;
    }
  }

  if (gameover==false) {  //絶滅したらタイマーが止まる

    msecond++;
    if (msecond>=100) {
      second++;
      msecond=0;
    }
    if (second>=60) {
      minute++;
      second=0;
    }
    if (nightmode) {
      fill(255, 150, 220);
    } else {
      fill(0);
    }
  } else {
    fill(255, 0, 0);

    plants = new ArrayList<Plant>();
    lions   = new ArrayList<Lion>();
    zebras = new ArrayList<Zebra>();
    dungs   = new ArrayList<Dung>();
    for (int i=0; i<syoki; i++) {   
      float r = random(1);
      if (r<0.85f) {
        plants.add(new Plant(xrange/2+random(-xrange/2+5, xrange/2-5), yrange/2+random(-yrange/2+5, yrange/2-5), 10, color(0, 255, 0)));
      } else if (r<0.97f) {
        zebras.add(new Zebra(xrange/2+random(-xrange/2+6, xrange/2-6), yrange/2+random(-yrange/2+6, yrange/2-6), 12, color(0, 0, 255), zebraHP));
      } else {
        lions.add(new Lion(xrange/2+random(-xrange/2+7, xrange/2-7), yrange/2+random(-yrange/2+7, yrange/2-7), 14, color(255, 0, 0), lionHP));
      }
    }
    msecond=0;
    second=0;
    minute=0;
    gameover=false;
    
  }
  textAlign( CORNER );

  text("Time:"+minute+"分"+second+"秒", 810, 775);
  quit.display();
}

public void mousePressed() {
  switch(scene) {
  case 0:
    title.mousePressed();
    break;
  case 1:
    setting.mousePressed();
    break;

  case 2:
    if (mouseX>=800) {
      for (int i=1; i<5; i++) {
        if (mouseX>=800+(i-1)*100&&mouseX<=800+i*100&&mouseY>=0&&mouseY<=100) {
          menupage=i;
          if (menupage==4) {
            nightmode=!nightmode;
          }
          selectitem=0;
        }
      }
      if (menupage==1) {
        for (int i=0; i<3; i++) {
          if (mouseY>=200+i*100&&mouseY<=200+(i+1)*100) {
            selectitem=i+1;
          }
        }
      }
    }
    if (quit.press()) {
      plants = new ArrayList<Plant>();
      lions   = new ArrayList<Lion>();
      zebras = new ArrayList<Zebra>();
      dungs   = new ArrayList<Dung>();
      msecond=0;
      second=0;
      minute=0;
      gameover=false;
      scene=0;
    }
    break;
  }
}
public void judgegameover() {
  if (zebras.size ()==0||lions.size ()==0) {
    gameover=true;
  }
}
public void mouseDragged() {

  if (mouseX<=800) {
    if (menupage==1) {
      switch(selectitem) {
      case 1:

        if (wait>=30) {
          lions.add(new Lion(mouseX, mouseY, 14, color(255, 0, 0), lionHP));
          wait=0;
        }
        break;
      case 2:
        if (wait>=30) {
          zebras.add(new Zebra(mouseX, mouseY, 12, color(0, 0, 255), zebraHP));
          wait=0;
        }
        break;
      case 3:
        if (wait>=30) {
          plants.add(new Plant(mouseX, mouseY, 10, color(0, 255, 0)));
          wait=0;
        }
        break;
      }
      wait++;
    } else if (menupage==2) {
      fill(255);
      stroke(0);
      strokeWeight(1);
      ellipse(mouseX, mouseY, 10, 10);
      for (int i = plants.size ()-1; i>=0; i--) {
        Plant p = plants.get(i);
        if (dist(mouseX, mouseY, p.xpos, p.ypos)<=(p.diameter/2)) plants.remove(i);
      }
      for (int i = zebras.size ()-1; i>=0; i--) {
        Zebra z = zebras.get(i);
        if (dist(mouseX, mouseY, z.xpos, z.ypos)<=(z.diameter/2)) zebras.remove(i);
      }
      for (int i = lions.size ()-1; i>=0; i--) {
        Lion l = lions.get(i);
        if (dist(mouseX, mouseY, l.xpos, l.ypos)<=(l.diameter/2)) lions.remove(i);
      }
      for (int i = dungs.size ()-1; i>=0; i--) {
        Dung d = dungs.get(i);
        if (dist(mouseX, mouseY, d.xpos, d.ypos)<=(d.diameter/2)) dungs.remove(i);
      }
    }
  }
}


public void  mouseWheel (MouseEvent e) {
  if (e.getAmount()>0) {
    if (1.1f<=scale&&scale<=5) {       
      scale-=0.1f;
    }
  } else {
    if (1<=scale&&scale<=4.9f) {       
      scale+=0.1f;
    }
  }
}
public void  keyPressed () {
  if (key == CODED) {
    if (keyCode ==UP) {
      if (1.1f<=scale&&scale<=5) {       
        scale-=0.1f;
      }
    } else if (keyCode==DOWN) {
      if (1<=scale&&scale<=4.9f) {       
        scale+=0.1f;
      }
    }
  }
}
class Animal {

  float vx, vy, xpos, ypos; //x方向の速度、y方向の速度、位置
  float diameter;           //動物の大きさ
  int life=0, food=0;//寿命、捕食回数
  boolean paku = false;     //true＝捕食された
  int col;//動物の色

  //setup内で呼ばれるコンストラクタ
  Animal(float x, float y, float _diameter, int _col, int _life) {
    xpos = x;
    ypos = y;
    diameter = _diameter;
    col = _col;
    life = _life;
  }
  //draw内で呼ばれるコンストラクタ
  Animal(float x, float y, float _diameter, int _col, int _life, boolean _breed) {
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
  public void move() {
    if (frameCount%(60*(int)random(1, 3))==0) {
      life--;

      vx=random(-2, 2);
      vy=random(-2, 2);
    }

    //位置の更新
    xpos+=vx;
    ypos+=vy;
  }
  public void edge() {
    if (xpos < diameter/2 || xpos > xrange-diameter/2) {
      vx*=-2;
    }
    if (ypos < diameter/2 || ypos > yrange-diameter/2) {
      vy*=-2;
    }
  }
  public void display() {
    fill(col);
    noStroke();
    ellipse(xpos, ypos, diameter, diameter);
    stroke(0);
  }
  //検死関数 
  public boolean dead(float _xpos, float _ypos, float _diameter, boolean _paku, int _life) {
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
class button {
  int x;
  int y;
  int w;
  int h;
  String str;
  button( String _str,int _x, int _y, int _w, int _h) {
    x=_x;
    y=_y;
    w=_w;
    h=_h;
    str=_str;
  }
  public void display() {
    
    fill(255);
    if(bounds()){
      stroke(255,255,0);
      fill(20,20);
    }else{
      stroke(0);
    }
    rectMode(CENTER);
    rect(x, y, w, h);
    textAlign( CENTER );
    textSize(h/2);
    fill(0);
    strokeWeight(5);
    text(str, x, y+10);
  }
  public boolean bounds(){
    return (mouseX >= x - w / 2 && mouseX < x + w / 2 && mouseY >= y - h / 2 && mouseY < y + h / 2);
  }
  
  public boolean press(){
    return(mousePressed && this.bounds());
  }
}
class Camera {
  PVector pos; //Camera's position 
  //The Camera should sit in the top left of the window

  Camera() {
    pos = new PVector(0, 0);
    //You should play with the program and code to see how the staring position can be changed
  }

  public void draw() {
    //I used the mouse to move the camera
    //The mouse's position is always relative to the screen and not the camera's position
    //E.g. if the mouse is at 1000,1000 then the mouse's position does not add 1000,1000 to keep up with the camera
    if (mouseX < 100) pos.x-=5;
    else if (mouseX > xrange - 100) pos.x+=5;
    if (mouseY < 100) pos.y-=5;
    else if (mouseY > yrange - 100) pos.y+=5;
    if(pos.x>map(xrange,0,xrange,0,xrange*scale)-xrange) pos.x=map(xrange,0,xrange,0,xrange*scale)-yrange;
    else if(pos.x<0) pos.x=0;
    if(pos.y>map(yrange,0,yrange,0,yrange*scale)-yrange) pos.y=map(yrange,0,yrange,0,yrange*scale)-yrange;
    else if(pos.y<0) pos.y=0;
    //I noticed on the web the program struggles to find the mouse so I made it key pressed
    if (keyPressed) {
      if (key == 'w') pos.y -= 5;
      if (key == 's') pos.y += 5;
      if (key == 'a') pos.x -= 5;
      if (key == 'd') pos.x += 5;
    }
  }
}
class Dung extends Plant {

  int fTime;
  Dung(float x, float y, float _diameter, int _col, boolean _yummy) {
    super(x, y, _diameter, _col, _yummy);
    fTime=frameCount-1;
  }

  public void update() {
    time();
    display(yummy);
  }

  public void time() {
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




  public void display(boolean _yummy) {
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
  public boolean dead() {

    if (life<=0) return true;
    else       return false;
  }
}
class Lion extends Animal {
  //Animalクラスのコンストラクタを継承
  Lion(float x, float y, float _diameter, int _col, int _life) {
    super(x, y, _diameter, _col, _life);
  }
  Lion(float x, float y, float _diameter, int _col, int _life, boolean _breed) {
    super(x, y, _diameter, _col, _life, _breed);
  }

  public void update() {
    move();
    eat();
    edge();
    display();
  }

  public void move() {
    if (frameCount%(60*(int)random(1, 3))==0) {
      life--;

      vx=random(-lionMaxspeed, lionMaxspeed);
      vy=random(-lionMaxspeed, lionMaxspeed);
    }

    //位置の更新
    xpos+=vx;
    ypos+=vy;
  }

  public void eat() {
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
          strokeWeight(0.5f);
          line(xpos, ypos, z.xpos, z.ypos);
          vx=(z.xpos-xpos)*2/(1.5f*diameter);
          vy=(z.ypos-ypos)*2/(1.5f*diameter);
          break;
        }
      }
    }
  }

  public void edge() {
    super.edge();
  }

  public void display() {
    super.display();
  }

  public boolean dead() {
    return super.dead(xpos, ypos, diameter, paku, life);
  }
}
class Plant {

  float xpos, ypos, diameter;
  int life=1;
  int col;
  boolean yummy=false;

  Plant(float x, float y, float _diameter, int _col) {
    xpos = x;//xrange/2+random(-x+_diameter/2, x-_diameter/2);
    ypos = y;//yrange/2+random(-y+_diameter/2, y-_diameter/2);
    diameter = _diameter;
    col = _col;
  }

  Plant(float x, float y, float _diameter, int _col, boolean _yummy) {
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

  public void update() {
    display();
  }

  public void display() {
    stroke(col);
    strokeWeight(2);
    line(xpos, ypos, xpos-diameter, ypos-diameter);
    line(xpos, ypos, xpos+diameter, ypos-diameter);
    stroke(0);
    strokeWeight(1);
  }

  public boolean dead() {
     if (yummy == true) return true;
    if (life<=0) return true;
    else       return false;
  }
}
class Plusminus {
  button plus;
  button minus;
  String text;
  int x, y;
  int value1=-1;
  float value2=-1;
  Plusminus(String _text, int _value, int _x, int _y) {
    text=_text;
    value1=_value;
    x=_x;
    y=_y;
    plus=new button("＋", x+165, y, 50, 50);
    minus=new button("－", x-165, y, 50, 50);
  }
  Plusminus(String _text, float _value, int _x, int _y) {
    text=_text;
    value2=_value;
    x=_x;
    y=_y;
    plus=new button("＋", x+165, y, 50, 50);
    minus=new button("－", x-165, y, 50, 50);
  }

  public void display() {
    textAlign(CENTER);
    textSize(25);
    plus.display();
    if (value1!=-1) {
      text(""+text+": "+value1+"", x, y+5);
    } else {
      text(""+text+": "+value2+"", x, y+5);
    }
    minus.display();
  }

  public boolean plusbounds() {
    return plus.bounds();
  }
  public boolean minusbounds() {
    return minus.bounds();
  }
  public boolean pluspress() {
    return plus.press();
  }
  public boolean minuspress() {
    return minus.press();
  }
}
class Setting {
  Plusminus syokimenu;
  Plusminus zebraHPmenu, zebrabornmenu, zebraMaxspeedmenu, zebraDungmenu, zebrasearchHPmenu, zebravisionmenu;
  Plusminus lionHPmenu, lionbornmenu, lionMaxspeedmenu, lionDungmenu, lionsearchHPmenu, lionvisionmenu;
  button start,syokibutton;
  Setting() {
    fill(0);
    textSize(25);
    start=new button("Start!!!", 600, 700, 300, 100);
    syokibutton=new button("初期化",900,700,200,50);
  }
  public void draw() {
    background(255);    
    syokimenu =new Plusminus("初期生命数", syoki, 200, 100);

    zebraHPmenu =new Plusminus("草食動物の体力", zebraHP, 600, 100);
    zebrabornmenu =new Plusminus("草食動物の繁殖頻度", zebraborn, 600, 200);
    zebraMaxspeedmenu =new Plusminus("草食動物の最高速度", zebraMaxspeed, 600, 300);
    zebraDungmenu =new Plusminus("草食動物の排泄頻度", zebradung, 600, 400);
    zebrasearchHPmenu=new Plusminus("草食動物の瀕死体力", zebrasearchHP, 600, 500);
    zebravisionmenu=new Plusminus("草食動物の視野", zebravision, 600, 600);

    lionHPmenu =new Plusminus("肉食動物の体力", lionHP, 1000, 100);    
    lionbornmenu =new Plusminus("肉食動物の繁殖頻度", lionborn, 1000, 200);
    lionMaxspeedmenu =new Plusminus("肉食動物の最高速度", lionMaxspeed, 1000, 300);
    lionDungmenu =new Plusminus("肉食動物の排泄頻度", liondung, 1000, 400);
    lionsearchHPmenu=new Plusminus("肉食動物の瀕死体力", lionsearchHP, 1000, 500);
    lionvisionmenu=new Plusminus("肉食動物の視野", lionvision, 1000, 600);

    syokimenu.display();

    zebraHPmenu.display();
    zebrabornmenu.display();
    zebraMaxspeedmenu.display();
    zebraDungmenu.display();
    zebrasearchHPmenu.display();
    zebravisionmenu.display();

    lionHPmenu.display();
    lionbornmenu.display();
    lionMaxspeedmenu.display();   
    lionDungmenu.display();
    lionsearchHPmenu.display();
    lionvisionmenu.display();

    explain();
    syokibutton.display();
    start.display();
  }
  public void mousePressed() {
    if (syokimenu.pluspress()) {
      syoki+=1;
    }
    if (syokimenu.minuspress()&&syoki>1) {
      syoki-=1;
    }

    if (zebraHPmenu.pluspress()) {
      zebraHP+=1;
    }
    if (zebraHPmenu.minuspress()&&zebraHP>1) {
      zebraHP-=1;
    }
    if (zebrabornmenu.pluspress()) {
      zebraborn+=1;
    }
    if (zebrabornmenu.minuspress()&&zebraborn>1) {
      zebraborn-=1;
    }
    if (zebraMaxspeedmenu.pluspress()) {
      zebraMaxspeed+=0.5f;
    }
    if (zebraMaxspeedmenu.minuspress()&&zebraMaxspeed>0.1f) {
      zebraMaxspeed-=0.5f;
    }
    if (zebraDungmenu.pluspress()) {
      zebradung+=1;
    }
    if (zebraDungmenu.minuspress()&&zebradung>1) {
      zebradung-=1;
    }
    if (zebrasearchHPmenu.pluspress()&&zebrasearchHP<zebraHP) {
      zebrasearchHP+=1;
    }
    if (zebrasearchHPmenu.minuspress()&&zebrasearchHP>0) {
      zebrasearchHP-=1;
    }  
    if (zebravisionmenu.pluspress()) {
      zebravision+=1;
    }
    if (zebrasearchHPmenu.minuspress()&&zebravision>0) {
      zebravision-=1;
    }  

    if (lionHPmenu.pluspress()) {
      lionHP+=1;
    }
    if (lionHPmenu.minuspress()&&lionHP>1) {
      lionHP-=1;
    }
    if (lionbornmenu.pluspress()) {
      lionborn+=1;
    }
    if (lionbornmenu.minuspress()&&lionborn>1) {
      lionborn-=1;
    }
    if (lionMaxspeedmenu.pluspress()) {
      lionMaxspeed+=0.5f;
    }
    if (lionMaxspeedmenu.minuspress()&&lionMaxspeed>0.1f) {
      lionMaxspeed-=0.5f;
    }
    if (lionDungmenu.pluspress()) {
      liondung+=1;
    }
    if (lionDungmenu.minuspress()&&liondung>1) {
      liondung-=1;
    }
    if (lionsearchHPmenu.pluspress()&&lionsearchHP<lionHP) {
      lionsearchHP+=1;
    }
    if (lionsearchHPmenu.minuspress()&&lionsearchHP>0) {
      lionsearchHP-=1;
    }  
    if (lionvisionmenu.pluspress()) {
      lionvision+=1;
    }
    if ( lionvisionmenu.minuspress()&&lionvision>0) {
      lionvision-=1;
    }  
    
    if(syokibutton.press()){
    syoki=200;
    zebraHP=15;
    zebrasearchHP=5;
    zebraborn=5;
    zebravision=15;
    zebradung=5;
    lionHP=15;
    lionsearchHP=5;
    lionborn=10;
    lionvision=17;
    liondung=1;
    zebraMaxspeed=2.0f;
    lionMaxspeed=2.0f;
    }
    

    if (start.press()) {
      for (int i=0; i<syoki; i++) {   
        float r = random(1);
        if (r<0.85f) {
          plants.add(new Plant(xrange/2+random(-xrange/2+5, xrange/2-5), yrange/2+random(-yrange/2+5, yrange/2-5), 10, color(0, 255, 0)));
        } else if (r<0.97f) {
          zebras.add(new Zebra(xrange/2+random(-xrange/2+6, xrange/2-6), yrange/2+random(-yrange/2+6, yrange/2-6), 12, color(0, 0, 255), zebraHP));
        } else {
          lions.add(new Lion(xrange/2+random(-xrange/2+7, xrange/2-7), yrange/2+random(-yrange/2+7, yrange/2-7), 14, color(255, 0, 0), lionHP));
        }
      }
      scene=2;
    }
  }
  public void explain() {
    rectMode(CORNER);
    fill(255);
    stroke(0);
    rect(0, 0, 1200, 50);
    textSize(25);
    fill(0);
    if (syokimenu.plusbounds()) {
      text("最初に生成される生命数を増やします。", 600, 30);
    }
    if (syokimenu.minusbounds()) {      
      text("最初に生成される生命数を減らします。", 600, 30);
    }
    if (zebraHPmenu.plusbounds()) {
      text("草食動物の初期体力を上げます。", 600, 30);
    }
    if (zebraHPmenu.minusbounds()) {
      text("草食動物の初期体力を下げます。", 600, 30);
    }
    if (zebrabornmenu.plusbounds()) {
      text("草食動物が繁殖するための食事数を増やします。", 600, 30);
    }
    if (zebrabornmenu.minusbounds()) {
      text("草食動物が繁殖するための食事数を減らします。", 600, 30);
    }
    if (zebraMaxspeedmenu.plusbounds()) {
      text("草食動物の最高移動速度を上げます。", 600,30);
    }
    if (zebraMaxspeedmenu.minusbounds()) {
      text("草食動物の最高移動速度を下げます。", 600, 30);
    }
    if (zebraDungmenu.plusbounds()) {
      text("草食動物がフンをするための食事数を増やします。", 600, 30);
    }
    if (zebraDungmenu.minusbounds()) {
      text("草食動物がフンをするための食事数を減らします。", 600, 30);
    }
    if (zebrasearchHPmenu.plusbounds()) {
      text("草食動物がエサを探し始める最大体力を上げます。", 600, 30);
    }
    if (zebrasearchHPmenu.minusbounds()) {
      text("草食動物がエサを探し始める最大体力を下げます。", 600, 30);
    }  
    if (zebravisionmenu.plusbounds()) {
      text("草食動物がエサを探せる視界を広げます。", 600,30);
    }
    if (zebravisionmenu.minusbounds()) {
      text("草食動物がエサを探せる視界を狭めます。", 600, 30);
    }  

    if (lionHPmenu.plusbounds()) {
      text("肉食動物の初期体力を上げます。", 600, 30);
    }
    if (lionHPmenu.minusbounds()) {
      text("肉食動物の初期体力を下げます。", 600, 30);
    }
    if (lionbornmenu.plusbounds()) {
      text("肉食動物が繁殖するための食事数を増やします。", 600, 30);
    }
    if (lionbornmenu.minusbounds()) {
      text("肉食動物が繁殖するための食事数を減らします。", 600, 30);
    }
    if (lionMaxspeedmenu.plusbounds()) {
      text("肉食動物の最高移動速度を上げます。", 600, 30);
    }
    if (lionMaxspeedmenu.minusbounds()) {
      text("肉食動物の最高移動速度を下げます。", 600, 30);
    }
    if (lionDungmenu.plusbounds()) {
      text("肉食動物がフンをするための食事数を増やします。", 600, 30);
    }
    if (lionDungmenu.minusbounds()) {
      text("肉食動物がフンをするための食事数を減らします。", 600,30);
    }
    if (lionsearchHPmenu.plusbounds()) {
      text("肉食動物がエサを探し始める最大体力を上げます。", 600, 30);
    }
    if (lionsearchHPmenu.minusbounds()) {
      text("肉食動物がエサを探し始める最大体力を下げます。", 600, 30);
    }  
    if (lionvisionmenu.plusbounds()) {
      text("肉食動物がエサを探せる視界を広げます。", 600, 30);
    }
    if ( lionvisionmenu.minusbounds()) {
      text("肉食動物がエサを探せる視界を広げます。", 600, 30);
    }
  }
}
class Title{
  button sandbox;
  Title(){
  sandbox=new button("Start",600,600,300,100);
  
  }
  public void draw(){
  background(255);
  fill(0);
  textAlign( CENTER );
  textSize(100);
  text("Simulator",width/2,200);
  textSize(50);
  sandbox.display();
  }
  public void mousePressed(){
    if(sandbox.press()){
    scene=1;
    }
  }
  
}
class Zebra extends Animal {
  //Animalクラスのコンストラクタを継承
  Zebra(float x, float y, float _diameter, int _col, int _life) {
    super(x, y, _diameter, _col, _life);
  }
  Zebra(float x, float y, float _diameter, int _col, int _life, boolean _breed) {
    super(x, y, _diameter, _col, _life, _breed);
  }

  public void update() {
    move();
    eat();
    edge();
    display();
  }

  public void move() {
    if (frameCount%(60*(int)random(1, 3))==0) {
      life--;

      vx=random(-zebraMaxspeed, zebraMaxspeed);
      vy=random(-zebraMaxspeed, zebraMaxspeed);
    }

    //位置の更新
    xpos+=vx;
    ypos+=vy;
  }

  public void eat() {
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
          strokeWeight(0.5f);
          line(xpos, ypos, p.xpos, p.ypos);
          vx=(p.xpos-xpos)/(1.5f*diameter);
          vy=(p.ypos-ypos)/(1.5f*diameter);
          break;
        }
      }
    }
  }

  public void edge() {
    super.edge();
  }

  public void display() {
    super.display();
  }

  public boolean dead() {
    return super.dead(xpos, ypos, diameter, paku, life);
  }
}
  public void settings() {  size(1200, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "simulator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

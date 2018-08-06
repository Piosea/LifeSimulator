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
float zebraMaxspeed=2.0, lionMaxspeed=2.0;


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
void setup() {
  size(1200, 800);
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
void draw() {
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
void menudisplay() {
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
      if (r<0.85) {
        plants.add(new Plant(xrange/2+random(-xrange/2+5, xrange/2-5), yrange/2+random(-yrange/2+5, yrange/2-5), 10, color(0, 255, 0)));
      } else if (r<0.97) {
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

void mousePressed() {
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
void judgegameover() {
  if (zebras.size ()==0||lions.size ()==0) {
    gameover=true;
  }
}
void mouseDragged() {

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


void  mouseWheel (MouseEvent e) {
  if (e.getAmount()>0) {
    if (1.1<=scale&&scale<=5) {       
      scale-=0.1;
    }
  } else {
    if (1<=scale&&scale<=4.9) {       
      scale+=0.1;
    }
  }
}
void  keyPressed () {
  if (key == CODED) {
    if (keyCode ==UP) {
      if (1.1<=scale&&scale<=5) {       
        scale-=0.1;
      }
    } else if (keyCode==DOWN) {
      if (1<=scale&&scale<=4.9) {       
        scale+=0.1;
      }
    }
  }
}

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
  void draw() {
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
  void mousePressed() {
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
      zebraMaxspeed+=0.5;
    }
    if (zebraMaxspeedmenu.minuspress()&&zebraMaxspeed>0.1) {
      zebraMaxspeed-=0.5;
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
      lionMaxspeed+=0.5;
    }
    if (lionMaxspeedmenu.minuspress()&&lionMaxspeed>0.1) {
      lionMaxspeed-=0.5;
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
    zebraMaxspeed=2.0;
    lionMaxspeed=2.0;
    }
    

    if (start.press()) {
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
      scene=2;
    }
  }
  void explain() {
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
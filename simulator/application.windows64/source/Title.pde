class Title{
  button sandbox;
  Title(){
  sandbox=new button("Start",600,600,300,100);
  
  }
  void draw(){
  background(255);
  fill(0);
  textAlign( CENTER );
  textSize(100);
  text("Simulator",width/2,200);
  textSize(50);
  sandbox.display();
  }
  void mousePressed(){
    if(sandbox.press()){
    scene=1;
    }
  }
  
}

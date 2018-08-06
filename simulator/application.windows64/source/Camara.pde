class Camera {
  PVector pos; //Camera's position 
  //The Camera should sit in the top left of the window

  Camera() {
    pos = new PVector(0, 0);
    //You should play with the program and code to see how the staring position can be changed
  }

  void draw() {
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

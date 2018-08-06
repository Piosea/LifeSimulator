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

  void display() {
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

  boolean plusbounds() {
    return plus.bounds();
  }
  boolean minusbounds() {
    return minus.bounds();
  }
  boolean pluspress() {
    return plus.press();
  }
  boolean minuspress() {
    return minus.press();
  }
}
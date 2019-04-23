/********************************************************
* Domino object                                         *
*                                                       *
* Constrctor -                                          *
* Receives left and right Value                         *
*                                                       *
* left, right, state, sum                               *
* state = 0 if in pile, 1 = player 1, 2 = player 2, etc *
*                                                       *
* Getter Methods -                                      *
* getLeft                                               *
* getRight                                              *
* getSum                                                *
*                                                       *
* TODO -                                                *
* Deconstructor                                         *
********************************************************/


import java.util.*;

public class Domino {

  ///////////////////////
  // Domino Attributes //
  ///////////////////////

  private int left;
  private int right;
  private int sum;
  private int state = 0;
  private int[] openSide = new int[2];

  ////////////////
  // Constrctor //
  ////////////////

  /**
   * Domino Constructor
   * @param left  Value to be placed on left side
   * @param right Value to be placed on right side
   */
  public Domino(int left, int right){
    this.left = left;
    this.right = right;
    this.sum = left+right;
    this.state = 0;
    openSide[0] = 1;
    openSide[1] = 1;
  }

  //////////////////
  // Print domino //
  //////////////////

  //TODO - Make Better

  /**
   * Prints the Domino to Terminal
   * @return String of Domino Attributes
   */
  public String toString(){
    return " : "+this.left+" "+this.right+" :";
  }

  public String toStringL(){
    return  " : "+this.left+" "+this.right+" : State: "+this.state+" Left Available: "+this.openSide[0]+" Right Available: "+this.openSide[1];
  }

  ////////////////////////////////////////
  // Set state of Domino - Where is it? //
  ////////////////////////////////////////

  /**
   * Set state of Domino. State is where the domino is
   * @param state New State.
   * 0 = in pile | 1 = Player1 | 2 = Player2
   */
  public void setState(int state){
    this.state = state;
  }

  /**
   * Marks side of domino closed
   * @param side side of domino that is being connected. 0 = Left | 1 = Right
   */
  public void connectSide(int side){
    openSide[side] = 0;
  }

  public boolean connectable(Domino d){
    if (d.leftOpen()){
      if (this.left == d.getLeft() || this.right == d.getLeft()) return true;
    }
    if (d.rightOpen()){
      if (this.left == d.getRight() || this.right == d.getRight()) return true;
    }
    return false;
  }

  /**
   * Checks for valid positions, stores in a ArrayList, then shuffles order
   * @param  d {Domino} Domino we are trying to connect to
   * @return   ArrayList with Valid Connections
   *
   * 1 = Left Right Connection
   * 2 = Left Left Connection
   * 3 = Right Left Connection
   * 4 = Right Right Connection
   *
   */
  public ArrayList<Integer> validConnections(Domino d){
    ArrayList<Integer> r = new ArrayList<Integer>();
    if (d.leftOpen()){
      if (this.right == d.getLeft()) r.add(1);
      if (this.left == d.getLeft()) r.add(2);
    }
    if (d.rightOpen()){
      if (this.left == d.getRight()) r.add(3);
      if (this.right == d.getRight()) r.add(4);
    }
    Collections.shuffle(r);
    return r;
  }

  /**
   * Rotate domino. Swap left and right attribute
   */
  public void rotate(){
    int temp = this.left;
    this.left = this.right;
    this.right = temp;
    temp = openSide[0];
    openSide[0] = openSide[1];
    openSide[1] = temp;
  }

  ////////////////////
  // Getter methods //
  ////////////////////

  /**
   * Get Left value
   * @return Value on the left of the domino
   */
  public int getLeft(){
    return left;
  }

  public boolean leftOpen(){
    return openSide[0] == 1;
  }

  public boolean rightOpen(){
    return openSide[1] == 1;
  }

  public boolean isValid(){
    return openSide[0] == 1 || openSide[1] == 1;
  }

  /**
   * Get Right value
   * @return Value on the right of the domino
   */
  public int getRight(){
    return right;
  }

  /**
   * Get sum of the domino
   * @return Total sun of the domino. Left + Right
   */
  public int getSum(){
    return this.sum;
  }

}

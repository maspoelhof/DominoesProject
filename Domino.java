/******************************************************
Tile object                                           *
                                                      *
left, right, state                                    *
state = 0 if in pile, 1 = player 1, 2 = player 2, etc *
                                                      *
leftConnect                                           *
rightConnect                                          *
                                                      *
TODO -                                                *
Deconstructor                                         *
******************************************************/


import java.util.*;

public class Domino {

  ///////////////////////
  // Domino Attributes //
  ///////////////////////

  private int left;
  private int right;
  private int sum;
  private int state = 0;

  ////////////////
  // Constrctor //
  ////////////////

  public Domino(int left, int right){
    this.left = left;
    this.right = right;
    this.sum = left+right;
    this.state = 0;
  }

  //////////////////
  // Print domino //
  //////////////////

  //TODO - Make Better
  public String toString(){
    return " : "+this.left+" "+this.right+" : ";
  }

  ////////////////////////////////////////
  // Set state of Domino - Where is it? //
  ////////////////////////////////////////

  public void setState(int state){
    this.state = state;
  }

  public void rotate(){
    int temp = this.left;
    this.left = this.right;
    this.right = temp;
  }

  ////////////////////
  // Getter methods //
  ////////////////////

  public int getLeft(){
    return left;
  }

  public int getRight(){
    return right;
  }

  public int getSum(){
    return this.sum;
  }

}

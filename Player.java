/**********************************
Name:                             *
                                  *
Show hand                         *
Play Tile                         *
  Check if Winner (Hand is empty) *
                                  *
TODO -                            *
Deconstructor                     *
Make prettier, more attributes    *
**********************************/


import java.util.*;

public class Player {

  ///////////////////////
  // Player Attributes //
  ///////////////////////

  private String name;
  private int num;
  private ArrayList<Domino> hand;
  private int handSum;

  //////////////////
  // Constructors //
  //////////////////

  public Player(int n, String s){
    this.name = s;
    this.num = n;
  }

  public Player(int n){
    this.num = n;
  }

  /////////////
  // Setters //
  /////////////

  public void setHand(ArrayList<Domino> h){
    this.hand = h;

    for (int i = 0; i < hand.size(); i++){
      this.handSum+=hand.get(i).getSum();
    }
  }

  public void addHand(Domino d){
    this.hand.add(d);
    this.handSum+=d.getSum();
  }

  public void pop(Domino d){
    this.hand.remove(d);
    this.handSum-=d.getSum();
  }

  public boolean isWinner(){
    return this.hand.size()<=0;
  }

  /////////////
  // Getters //
  /////////////

  public int getSum(){
    return this.handSum;
  }

  public int getNum(){
    return this.num;
  }

  public ArrayList<Domino> getHand(){
    return hand;
  }

  /////////////////////////////////
  // toString used for debugging //
  /////////////////////////////////

  public void printHand()
    for (int i = 0; i < hand.size(); i++){
      System.out.print(hand.get(i).toString()+" ");
    }
    System.out.println();
  }
}

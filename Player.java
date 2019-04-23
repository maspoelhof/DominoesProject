/************************
 * Player Object        *
 *                      *
 * Constructors -       *
 * Player(number, name) *
 * Player(number)       *
 *                      *
 * Setters -            *
 * setHand(hand)        *
 * addHand(domino)      *
 * pop(domino)          *
 * isWinner()           *
 *                      *
 * Getters -            *
 * getSum()             *
 * getNum()             *
 * getHand()            *
 *                      *
 * TODO -               *
 * Deconstructor        *
 *                      *
 ************************/



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

  /**
   * Create Playe object with a name
   * @param n {int} number of player
   * @param s {String} Name of player
   */
  public Player(int n, String s){
    this.name = s;
    this.num = n;
  }

  /**
   * Create Playe object without a name
   * @param n {int} number of player
   */
  public Player(int r){
    this.num = n;
    this.name = "Player "+r;
  }

  /////////////
  // Setters //
  /////////////

  /**
   * Receives the hand in the form of an array List
   * @param h {ArrayList} hand for the player
   */
  public void setHand(ArrayList<Domino> h){
    this.hand = h;

    for (int i = 0; i < hand.size(); i++){
      this.handSum+=hand.get(i).getSum();
    }
  }

  /**
   * Add a domino to the players hand
   * @param d {Domino} Domino object to be added
   */
  public void addHand(Domino d){
    this.hand.add(d);
    this.handSum+=d.getSum();
  }

  /**
   * Removes domino object from players hand
   * @param d {Domino} Domino object to be removed
   */
  public void pop(Domino d){
    this.hand.remove(d);
    this.handSum-=d.getSum();
  }

  /**
   * Check if player is out of dominos
   * @return true if out of dominos, false if not
   */
  public boolean isWinner(){
    return this.hand.size()<=0;
  }

  /////////////
  // Getters //
  /////////////

  /**
   * Returns the sum of all of the dominos in the players hand
   * @return {int} Sum of Dominos
   */
  public int getSum(){
    return this.handSum;
  }

  /**
   * Returns player number
   * @return {int} Player Number
   */
  public int getNum(){
    return this.num;
  }

  public String getName(){
    return this.name;
  }

  /**
   * Returns hand of the player
   * @return {ArrayList} Hand of player
   */
  public ArrayList<Domino> getHand(){
    return hand;
  }

  /////////////////////////////////
  // toString used for debugging //
  /////////////////////////////////

  /**
   * Prints the hand that the player currently has
   *
   * Used for debugging
   */
  public void printHand(){
    for (int i = 0; i < hand.size(); i++){
      System.out.print(hand.get(i).toString()+" ");
    }
    System.out.println();
  }
}

/****************************************
* Create Pieces, store them in an array *
* Pass out the pieces to the players    *
*                                       *
* linked list of played pieces          *
*                                       *
* TODO -                                *
* Deconstructor                         *
* Clean up                              *
****************************************/


import java.util.*;

public class Table {

  //////////////////////
  // Table Attributes //
  //////////////////////

  private boolean debug = false;

  //////////////////////////////////////////////////
  // Stores Domino Objects and current game info  //
  //////////////////////////////////////////////////
  private HashMap<String, Domino> pile = new HashMap<String, Domino>();
  private boolean isEmpty = false;
  private Deque <Domino> played = new LinkedList<Domino>();
  private int head;
  private int tail;

  /////////////////////////////////////////////
  // Players and relevant info at the table  //
  /////////////////////////////////////////////
  private Player[] players;
  private int numPlayers;
  private int handSize;

  //////////////////////////////////////////////////////////////////
  // Method of keeping track of the order dominos are passed out  //
  //////////////////////////////////////////////////////////////////
  private ArrayList<Integer> randOrder;
  private int j;

  ////////////////
  // Round info //
  ////////////////
  public int turn;
  public int failedRounds = 0;
  public Player winner = null;

  /////////////////
  // Constructor //
  /////////////////

  public Table(Player[] players, int handSize, boolean debug){
    this.numPlayers = players.length;
    this.players = players;
    this.handSize = handSize;
    this.turn = (int)(Math.random()*this.numPlayers);

    this.debug = debug;

    createDominos();
    randomize();
    distributeDominos();
  }

  //////////////////////////////
  // Driver for the gameplay  //
  //////////////////////////////

  /**
   * Play round of Dominos game
   * @return {boolean} if a winner was found
   */
  public boolean play(){

    if (this.debug) System.out.println("Head: "+this.head+" Tail: "+this.tail);

    ArrayList<Domino> hand = players[turn].getHand();
    Domino highValDom = null;
    int highestVal = -1;

    boolean playable = false;

    //check eligible pieces
    for (int i = 0; i<hand.size(); i++){
      Domino d = hand.get(i);
      int left = d.getLeft();
      int right = d.getRight();

      if (left == this.head || left == this.tail || right == this.head || right == this.tail){
        if (left+right>highestVal){
          playable = true;
          highValDom = d;
          highestVal = left+right;
        }
      }
    }

    while (!playable && !this.isEmpty){
      if (this.debug) System.out.println("hey");
      draw(players[this.turn]);

      Domino d = hand.get(hand.size()-1);
      int left = d.getLeft();
      int right = d.getRight();

      if(left == this.head || left == this.tail || right == this.head || right == this.tail){
        playable = true;
        highValDom = d;
      }
    }

    //Can't play and can't draw
    if (!playable) {
      if (debug) System.out.println("hey");
      this.failedRounds++;
      this.turn = (this.turn+1)%this.numPlayers;
      return false;
    }

    // play highest value domino
    else {
      if (debug) System.out.println("Player "+this.turn+" can play");
      if (debug) System.out.println(highValDom.toString());
      this.failedRounds = 0;
      if (highValDom.getRight() == this.head){
        if (debug) System.out.println("Play piece on head without rotate");
        this.played.addFirst(highValDom);
        this.head = highValDom.getLeft();
        players[this.turn].pop(highValDom);
      }
      else if (highValDom.getLeft() == this.tail){
        if (debug) System.out.println("Play piece on tail without rotate");
        this.played.addLast(highValDom);
        this.tail = highValDom.getRight();
        players[this.turn].pop(highValDom);
      }
      else if (highValDom.getLeft() == this.head) {
        if (debug) System.out.println("Play piece on head after roate");
        highValDom.rotate();
        this.played.addFirst(highValDom);
        this.head = highValDom.getLeft();
        players[this.turn].pop(highValDom);
      }
      else if (highValDom.getRight() == this.tail){
        if (debug) System.out.println("Play piece on tail after rotate");
        highValDom.rotate();
        this.played.addLast(highValDom);
        this.tail = highValDom.getRight();
        players[this.turn].pop(highValDom);
      }
      if (players[this.turn].isWinner()){
        if(debug) System.out.println(this.turn+" is the winner!");
        this.winner = players[this.turn];
        return true;
      }
    }
    if (debug) System.out.println("Player "+this.turn+" played but isn't a winner");
    this.turn = (this.turn+1)%this.numPlayers;
    return false;
  }

  //////////////////
  // Draw method  //
  //////////////////

  private void draw(Player p){
    Domino d = this.pile.get("d"+this.randOrder.get(this.j++));
    d.setState(this.turn);
    players[this.turn].addHand(d);
    if (this.j >= 27) this.isEmpty = true;
  }

  /////////////////////////////
  // toString for debugging  //
  /////////////////////////////

  public void printTable(){
    for (int i = 0; i < this.played.size(); i++){
      System.out.print(this.played.poll().toString()+" | ");
    }
  }

  ////////////////////////////
  // Table builder methods  //
  ////////////////////////////

   /*******************************************************************
   * Basically what this is doing is storing domino Objects           *
   * inside of a HashMap with the keys d1, d2, d3, d4.                *
   * The reason I am doing this is so that I can access the Objects   *
   * easily and, in conjunction with the randomized arraylist         *
   * down below, randomly.                                            *
   *                                                                  *
   * It will:                                                         *
   * 1. create the 28 dominos inside of a HashMap with keys d1,d2,etc *
   * 2. create an ArrayList holding numbers 1-28                      *
   * 3. Shuffle the ArrayList                                         *
   *                                                                  *
   * I then use a pointer, j, to reference the randomized ArrayList   *
   * and use the integer in the ArrayList combined with "d" to        *
   * make a key. That key corresponds to a domino object inside       *
   * of the hash map. It then gives that domino object to the player  *
   ********************************************************************/


  //this feels like a fucking hack but i think its kinda good
  private void createDominos(){
    for (int i = 0, k = 1; i<7; i++){
      for (int j = i; j>=0; j--, k++){
        this.pile.put("d"+k, new Domino(i,j));
      }
    }
  }

  private void randomize(){
    this.randOrder = new ArrayList<Integer>(28);
    for (int i = 0; i < 28; i++){
      this.randOrder.add(i+1);
    }
    Collections.shuffle(this.randOrder);
    this.j = 0;
  }

  private void distributeDominos(){
    for (int i = 0; i<players.length; i++){
      ArrayList<Domino> hand = new ArrayList<Domino>();
      for (int k = 0; k<handSize; k++){
        Domino d = this.pile.get("d"+this.randOrder.get(this.j++));
        d.setState(i);
        hand.add(k,d);
      }
      players[i].setHand(hand);
    }
  }
}

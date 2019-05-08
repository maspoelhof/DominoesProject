/********************************************************
 * Table object that holds the player object,           *
 * domino pile, and simulates the gameplay              *
 *                                                      *
 * Constructors -                                       *
 * Table(Player[] players, int handSize, boolean debug) *
 *                                                      *
 * Builders -                                           *
 * createDominos()                                      *
 * randomize()                                          *
 * distributeDominos()                                  *
 *                                                      *
 * play()                                               *
 *                                                      *
 * TODO -                                               *
 * Deconstructor                                        *
 ********************************************************/



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
  private ArrayList<Domino> validDominos = new ArrayList<Domino>();

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

  /**
   * Builds table object
   * @param players  {int[]} Array of player objects
   * @param handSize {handSize} Size of hand for each player
   * @param this.debug    {boolean} this.debug
   *
   * creates domino objects
   * creates randomized counter
   * gives players hands
   */
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

  /**
   * Flip over a random domino from the pile
   *
   * Used to start the game
  */
  public void playFirst(){
    Domino drawnDomino = draw();
    played.addFirst(drawnDomino);
    validDominos.add(drawnDomino);
    System.out.println("The first domino that was played was "+drawnDomino.toString());
    System.out.println("Let's start playing!");
  }

  //////////////////////////////
  // Driver for the gameplay  //
  //////////////////////////////

  /**
   * Play round of Dominos game
   * @return {boolean} if a winner was found
   *
   * Tries to place a domino on the head or tail of current train
   * Draws from pile if the player has no eligible dominos
   * Counter failedRounds keeps track of rounds where no one was able to play
   */
  public boolean play(){

    if (this.debug) System.out.println("Head: "+played.peekFirst()+" Tail: "+played.peekLast());

    //Get hand of the player
    ArrayList<Domino> hand = players[turn].getHand();

    HashMap<Integer,Domino> playableDominos = new HashMap<Integer,Domino>();

    boolean playable = false;
    int validDominoIndex = 0;

    //Check for eligible pieces in players hand
    for (int i = 0; i<hand.size(); i++){

      Domino d = hand.get(i);

      for (int j = 0; j < validDominos.size(); j++){

        Domino c = validDominos.get(j);

        if (this.debug) System.out.println("Testing if "+d.toStringL()+" can connect to "+c.toStringL());

        if (d.connectable(c)){

          playableDominos.put(validDominoIndex++,d);

          if (this.debug) System.out.println("Valid Connections for the players Domino: "+d.toString()+" and "
            +c.toString()+" are "+d.validConnections(c).toString());

          playable = true;
          break;
        }
      }
    }

    //If there is not a valid piece in hand, draw
    while (!playable && !this.isEmpty){

      System.out.println(players[this.turn].getName()+" has to draw");

      Domino d = draw();
      players[this.turn].addHand(d);
      System.out.println(players[this.turn].getName()+" drew "+d.toString());

      // checks if d can be played
      for (int i = 0; i < validDominos.size(); i++){

        Domino c = validDominos.get(i);

        if (this.debug) System.out.println("Testing if "+d.toStringL()+" can connect to "+c.toStringL());

        if (d.connectable(c)){

          playableDominos.put(validDominoIndex++,d);

          if (this.debug) System.out.println("Valid Connections for the players Domino: "+d.toString()+" and "
            +c.toString()+" are "+d.validConnections(c).toString());

          playable = true;
          break;
        }
      }
    }

    //Can't play and can't draw
    if (!playable) {
      System.out.println(players[this.turn].getName()+" cannot play");

      this.failedRounds++;

      this.turn = (this.turn+1)%this.numPlayers;

      return false;
    }

    //Plays Domino on Board
    else {

      System.out.println("Valid Dominos in "+players[this.turn].getName()+"'s hand are: \n"+playableDominos.toString());

      this.failedRounds = 0;

      int dominoPicker = (int)(Math.random()*(playableDominos.size()));

      Domino toPlay = playableDominos.get(dominoPicker);
      System.out.println(players[this.turn].getName()+" is going to play: "+toPlay.toString());

      for (int i = 0; i < validDominos.size(); i++){

        Domino connectTo = validDominos.get(i);

        if (toPlay.connectable(connectTo)){

          int position = toPlay.validConnections(connectTo).get(0);

          if (this.debug) System.out.println("Playable Location: "+position);

          switch(position){

            //Play piece on head without rotate
            case 1:
              if (this.debug) System.out.println("Play piece on the left of "+connectTo.toString()+" without rotate");
              this.played.addFirst(toPlay); //TODO
              validDominos.add(toPlay);
              if (this.debug) System.out.println(toPlay.toString()+" was added to the list of Valid Dominos");
              players[this.turn].pop(toPlay);
              toPlay.connectSide(1);
              connectTo.connectSide(0);
              if (!connectTo.isValid()) validDominos.remove(connectTo);
              break;

            //Play piece on head with rotate
            case 2:
              if (this.debug) System.out.println("Play piece on the left of "+connectTo.toString()+" with rotate");
              toPlay.rotate();
              this.played.addFirst(toPlay); //TODO
              validDominos.add(toPlay);
              if (this.debug) System.out.println(toPlay.toString()+" was added to the list of Valid Dominos");
              players[this.turn].pop(toPlay);
              toPlay.connectSide(1);
              connectTo.connectSide(0);
              if (!connectTo.isValid()) validDominos.remove(connectTo);
              break;

            //Play piece on tail without rotate
            case 3:
              if (this.debug) System.out.println("Play piece on the right of "+connectTo.toString()+" without rotate");
              this.played.addLast(toPlay); //TODO
              validDominos.add(toPlay);
              if (this.debug) System.out.println(toPlay.toString()+" was added to the list of Valid Dominos");
              players[this.turn].pop(toPlay);
              toPlay.connectSide(0);
              connectTo.connectSide(1);
              if (!connectTo.isValid()) validDominos.remove(connectTo);
              break;

            //Play piece on tail with rotate
            case 4:
              if (this.debug) System.out.println("Play piece on the right of "+connectTo.toString()+" with rotate");
              toPlay.rotate();
              this.played.addLast(toPlay); //TODO
              validDominos.add(toPlay);
              if (this.debug) System.out.println(toPlay.toString()+" was added to the list of Valid Dominos");
              players[this.turn].pop(toPlay);
              toPlay.connectSide(0);
              connectTo.connectSide(1);
              if (!connectTo.isValid()) validDominos.remove(connectTo);
              break;

          }
          System.out.println("The list of valid Dominos is now: ");
          for (int j = 0;j<validDominos.size();j++) {
            System.out.print(" "+validDominos.get(j).toString());
          }
          System.out.println();
          break;
        }
      }

      //Check if player is a winner by checking if hand is empty
      if (players[this.turn].isWinner()){
        if(this.debug) System.out.println(players[this.turn].getName()+" is the winner!");
        this.winner = players[this.turn];
        return true;
      }
    }

    //return false because a winner was not found, increment turn
    if (this.debug) System.out.println(players[this.turn].getName()+" played but isn't a winner");
    this.turn = (this.turn+1)%this.numPlayers;
    return false;
  }

  //////////////////
  // Draw method  //
  //////////////////

  /**
   * Receives a player object and gives that player a domino from the pile
   * @return d {Domino} Domino that was drawn from pile
   */
  private Domino draw(){
    Domino d = this.pile.get("d"+this.randOrder.get(this.j++));
    d.setState(this.turn);
    if (this.j >= 27){
       this.isEmpty = true;
       System.out.println("The pile is now empty");
     }
    return d;
  }

  /////////////////////////////
  // toString for debugging  //
  /////////////////////////////

  /**
   * Print the final state of the board.
   * Only call at the end of the game
   */
  public void printTable(){
    System.out.print("| ");
    while (!this.played.isEmpty()){
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

   /**
    * Create 28 domino objects
    */
  private void createDominos(){
    for (int i = 0, k = 1; i<7; i++){
      for (int j = i; j>=0; j--, k++){
        this.pile.put("d"+k, new Domino(i,j));
      }
    }
  }

  /**
   * Creates a size 28 ArrayList then shuffles it to simulate randomness
   */
  private void randomize(){
    this.randOrder = new ArrayList<Integer>(28);
    for (int i = 0; i < 28; i++){
      this.randOrder.add(i+1);
    }
    Collections.shuffle(this.randOrder);
    this.j = 0;
  }

  /**
   * Gives each player object a hand of handSize size
   */
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

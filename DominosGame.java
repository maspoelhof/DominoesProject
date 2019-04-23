/**
 * Main Class
 *
 * Holds default variables
 * Allows for user to change settings
 *
 * While loop that runs until a winner is found
 * Or until no one can play
*/

import java.util.*;

public class DominosGame {

  public static void main(String[] args){

    boolean debug = false;

    //////////////
    // Defaults //
    //////////////

    int numPlayers = 2;
    int handSize = 10;
    int rounds = 1;
    boolean named = false;
    Player[] players = new Player[numPlayers];
    boolean winnerFound = false;
    Player winningPlayer = null;

    Scanner s = new Scanner(System.in);

    //////////////////////
    // Debugging toggle //
    //////////////////////

    System.out.print("Debug? (1 for yes 0 for no): ");
    int debugChoice = s.nextInt();

    if (debugChoice == 1){
      debug = true;
    }

    ////////////////////////////////////////////
    // Gives user ability to name the players //
    ////////////////////////////////////////////



    System.out.println("------Welcome to our Dominos Game!------");

    //TODO
    //(2-4 players functionality)
    //would need to ask for preferred hand size too (5 or 7)


    System.out.println("Would you like to assign names for your players or have an Anonymous Game?");
    System.out.print("Press 0 for Anonymous Game or press 1 to give names: ");
    int choice = s.nextInt();

    String[] names = new String[numPlayers];

    if (choice == 1){
      named = true;

      System.out.println("Excellent choice! Let's give our players some names!");
      s.nextLine();

      for (int i = 0; i < numPlayers; i++){
        System.out.print("What are the names of our "+numPlayers+" players?: ");
        names[i] = s.nextLine();
      }
      System.out.println(Arrays.toString(names));
    }

    else System.out.println("Secrecy is fun too!");

    s.close();

    ///////////////////////////
    // Create player objects //
    ///////////////////////////

    System.out.println("\n\nNow that we're done with that, let's get started!");

    for (int i = 0; i < numPlayers; i++){
      Player p = (named) ? new Player(i+1, names[i]) : new Player(i+1);
      players[i] = p;
    }

    /////////////////////////
    // Create Table Object //
    /////////////////////////

    System.out.println("Let's head over to the Table, right this way\n");
    Table table1 = new Table(players, handSize, debug);

    if (debug){
      for (int i = 0; i < numPlayers; i++){
        System.out.println(players[i]+" was given the hand: ");
        players[i].printHand();
      }
    }

    System.out.println("We will start the game by flipping a domino from the pile");
    table1.playFirst();
    System.out.println();

    //////////////////
    //Play the game //
    //////////////////

     /**********************************************************
     *  Will Loop until returned true or all of the players    *
     * Fail to move. failedRounds is tracked in table object   *
     * by counting how many times there was a turn but no one  *
     * could play                                              *
     * //TODO make this dynamic to # of players                *
     **********************************************************/

    while (!winnerFound){

      System.out.println("-----------------------------------------------------------");
      System.out.println("It is "+players[table1.turn].getName()+"'s turn and their hand is: ");
      System.out.println("-----------------------------------------------------------\n");
      players[table1.turn].printHand();
      System.out.println();

      //If both players failed to play on consecutive plays
      if (table1.failedRounds >= numPlayers){
        boolean tie = false;
        System.out.println(players[0]+" and "+players[1]+" are out of possible moves");

        if (debug){
          System.out.println("Player 1's ending hand is: ");
          players[0].printHand();
          System.out.println("\nPlayer 2's ending hand is: ");
          players[1].printHand();
        }
      }

        //Calculate winner by adding up the totals
        if (winnerFound){
          for (int i = 0; i < numPlayers; i++){
            if (winningPlayer == null){
              winningPlayer = players[i];
              winnerFound = true;
            }
            if (players[i].getSum() > winningPlayer.getSum()){
              winningPlayer = players[i];
              tie = false;
              winnerFound = true;
            }
            else if (winningPlayer.getSum() == players[i].getSum()) tie = true;
          }
          if (tie) System.out.println("It's a....tie");
          }
      else winnerFound = table1.play();
      if (winnerFound && winningPlayer == null) winningPlayer = table1.winner;
    }

    //Output winner if there is one (Not a tie)
    //TODO Output Winner is a better way
    if (winnerFound){
      System.out.println("And the winner is.....");
      System.out.println(winningPlayer.getName()+"!!!");
      System.out.println("The point totals are: ");
      System.out.println(players[0].getSum()+" and "+players[1].getSum());
      if (debug) table1.printTable();
    }

    //TODO Give option to make it loop
    System.out.println("\n\nBye...");
  }

}

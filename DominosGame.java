/*
Main

Output to user:
# of players (2-4)
Anonymous or Public? (Y/N)
If Yes: Name Players
Print Settings
Press 1 to play, 0 to change settings

Create Player Objects
Determine first play based on hand size
Play first tile from player (Double)
while Winner=False, play
Iterate through players playing one piece
If they can't play, skip
When winner is returned, rank based on hand size
Output results

Play again?
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
      System.out.println("hey");
      debug = true;
    }

    ////////////////////////////////////////////
    // Gives user ability to name the players //
    ////////////////////////////////////////////


/*
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
      System.out.print("What is the name of our first player?: ");
      String player1Name = s.nextLine();
      System.out.print("And who is "+player1Name+" playing against?: ");
      String player2Name = s.nextLine();


    }
    else{
      System.out.println("Secrecy is fun too!");
    }
*/
    s.close();

    ///////////////////////////
    // Create player objects //
    ///////////////////////////

    System.out.println("\n\nNow that we're done with that, let's get started!");

    for (int i = 0; i < numPlayers; i++){
      //Player p = (named) ? new Player(i, names[i]) : new Player(i);
      Player p = new Player(i);
      players[i] = p;
    }

    /////////////////////////
    // Create Table Object //
    /////////////////////////

    System.out.println("Let's head over to the Table, right this way");
    Table table1 = new Table(players, handSize, debug);

    if (debug){
      System.out.println("Player 1 was given the hand: ");
      players[0].printHand();

      System.out.println("Player 2 was given the hand: ");
      players[1].printHand();
    }

    System.out.println("Player "+table1.turn+" will go first!");


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

      if (debug){
        System.out.println("It is player "+(table1.turn+1)+"'s turn and their hand is: ");
        players[table1.turn].printHand();
        System.out.println();
      }

      //If both players failed to play on consecutive plays
      if (table1.failedRounds >= numPlayers){
        System.out.println("Player 1 and Player 2 are out of possible moves");

        if (debug){
          System.out.println("Player 1's ending hand is: ");
          players[0].printHand();
          System.out.println("\nPlayer 2's ending hand is: ");
          players[1].printHand();
        }

        //Calculate winner by adding up the totals

        if (players[0].getSum() < players[1].getSum()){
          winningPlayer = players[0];
          winnerFound = true;
        }
        else if (players[0].getSum() > players[1].getSum()){
          winningPlayer = players[1];
          winnerFound = true;
        }
        else System.out.println("We have...a tie");
        break;
      }
      else winnerFound = table1.play();
      if (winnerFound) winningPlayer = table1.winner;
    }

    //Output winner if there is one (Not a tie)
    //TODO Output Winner is a better way
    if (winnerFound){
      System.out.println("And the winner is.....");
      System.out.println("Player "+winningPlayer.getNum()+"!!!");
      System.out.println("The point totals are: ");
      System.out.println(players[0].getSum()+" and "+players[1].getSum());
      if (debug) table1.printTable();
    }

    //TODO Give option to make it loop
    System.out.println("\n\nBye...");
  }

}

package cs3500.pa03.controller;


import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShotHolder;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.model.player.Player;
import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Runs the BattleSalvo Game
 */
public class BattleSalvoController implements Controller {
  // fields
  // players are the model
  Player player1;
  Player player2;
  View view;
  ShotHolder shotHolder;

  /**
   * Instantiates a BattleSalvoController object
   *
   * @param player1    user player
   * @param player2    ai player
   * @param view       view
   * @param shotHolder holds the user's shots
   */
  BattleSalvoController(Player player1, Player player2, View view, ShotHolder shotHolder) {
    this.player1 = player1;
    this.player2 = player2;
    this.view = view;
    this.shotHolder = shotHolder;
  }

  /**
   * Runs through each step of the game, including taking user input and displaying content
   */
  public void run() {

    //get board dimensions
    List<Integer> dimensions = view.welcome();
    int height = dimensions.get(0);
    int width = dimensions.get(1);
    int maxNumShips = Math.min(height, width);
    //get the fleet
    Map<ShipType, Integer> fleet = view.getFleet(maxNumShips);
    // init the game
    List<Ship> player1Ships = player1.setup(height, width, fleet);
    List<Ship> player2Ships = player2.setup(height, width, fleet);

    //init the shot arraylist for the loop and give them one shot to begin with in order to kick
    //game off - those coordinates will be over written
    List<Coord> player1Shots = new ArrayList<>();
    player1Shots.add(new Coord(0, 0));
    List<Coord> player2Shots = new ArrayList<>();
    player2Shots.add(new Coord(0, 0));

    //game loop
    while (getShotNum(player1Ships) != 0 && getShotNum(player2Ships) != 0) {
      // get the shots and keep looping until they are valid
      List<Coord> shots = view.getShots(getShotNum(player1Ships), width, height);
      shotHolder.setShots(shots);
      //each player takes shots
      player1Shots = player1.takeShots();
      player2Shots = player2.takeShots();
      //shots are sent to other player
      List<Coord> player1ShipsHit = player1.reportDamage(player2Shots);
      List<Coord> player2ShipsHit = player2.reportDamage(player1Shots);
      //the players are informed of the hit ships
      player1.successfulHits(player2ShipsHit);
      player2.successfulHits(player1ShipsHit);
    }
    //end message
    endGameMessage(player1Shots, player2Shots);

  }

  /**
   * end of game message to display to user
   *
   * @param player1Shots player 1's coordinates
   * @param player2Shots player 2's coordinates
   */
  private void endGameMessage(List<Coord> player1Shots, List<Coord> player2Shots) {

    if (player1Shots.isEmpty() && player2Shots.isEmpty()) {
      view.displayAnything("GAME OVER. Draw.");
    } else if (player1Shots.isEmpty()) {
      view.displayAnything("GAME OVER. " + player2.name() + " won!");
    } else {
      view.displayAnything("GAME OVER. " + player1.name() + " won!");
    }

  }

  /**
   * Take a list of ships and returns the number of ships which have not been sunken yet
   *
   * @param ships list of ships
   * @return number of ships which haven't sunk yet, which is equal to the shot number of the player
   */
  private int getShotNum(List<Ship> ships) {
    int num = 0;
    for (Ship ship : ships) {
      if (!ship.isSunk()) {
        num++;
      }
    }
    return num;
  }
}

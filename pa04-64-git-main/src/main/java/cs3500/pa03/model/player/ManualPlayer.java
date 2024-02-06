package cs3500.pa03.model.player;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShotHolder;
import cs3500.pa03.model.enums.GameResult;
import cs3500.pa03.model.enums.HitStatus;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.view.View;
import java.util.List;
import java.util.Map;
import java.util.Random;




/**
 * Represents a ManualPlayer
 */
public class ManualPlayer extends PlayerAbstract {

  //fields
  private final ShotHolder shotHolder;
  private final View view;

  /**
   * Constructor
   *
   * @param name       name
   * @param shotHolder holds the shots
   * @param view       view
   * @param random     random number generator
   */
  public ManualPlayer(String name, ShotHolder shotHolder, View view, Random random) {
    super(name, random);
    this.shotHolder = shotHolder;
    this.view = view;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board. Also displays generated board to the user
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    setupHelper(height, width, specifications);
    //need to display starting conditons for the player

    view.displayBoard(board.getGameBoard());
    view.displayOpponentBoard(opponentTracker);

    return board.getShips();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = shotHolder.getShots();

    //for edge case for when shots left are less than the shots the user inputted
    if (shots.size() > shotsLeft) {
      shots = shots.subList(0, shotsLeft - 1);
    }


    //update the opponent tracker to all the shots to miss
    for (Coord coord : shots) {
      this.opponentTracker[coord.getY()][coord.getX()] = HitStatus.MISS;
      shotsLeft--;
    }
    return shots;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship. Also updates the opponent tracker and displays board
   * to the user
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    //update the 2d array that tracks the opponent
    for (Coord coord : shotsThatHitOpponentShips) {
      this.opponentTracker[coord.getY()][coord.getX()] = HitStatus.HIT;
    }
    // update the boards + display

    view.displayBoard(board.getGameBoard());
    view.displayOpponentBoard(opponentTracker);

  }


  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }


}

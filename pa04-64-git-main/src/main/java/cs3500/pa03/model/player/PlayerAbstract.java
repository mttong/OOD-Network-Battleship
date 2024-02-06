package cs3500.pa03.model.player;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Gameboard;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enums.GameResult;
import cs3500.pa03.model.enums.HitStatus;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.model.enums.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;



/**
 * Abstract Class for a player
 */
public abstract class PlayerAbstract implements Player {
  //fields
  protected Gameboard board;
  protected List<Ship> ships;
  protected String name;
  protected HitStatus[][] opponentTracker;
  Random random;

  protected int shotsLeft;

  /**
   * Constructor
   *
   * @param name name
   */
  PlayerAbstract(String name, Random random) {
    this.name = name;
    this.random = random;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }


  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    setupHelper(height, width, specifications);
    return board.getShips();
  }

  /**
   * Initializes the board + opponent tracker board
   *
   * @param height height of the board
   * @param width width of the board
   * @param specifications ship types and their amounts
   */
  protected void setupHelper(int height, int width, Map<ShipType, Integer> specifications) {
    this.board = new Gameboard(height, width, random);
    board.initBoard(specifications);
    opponentTracker = new HitStatus[height][width];
    initOpponentTracker();
    this.ships = board.getShips();

    this.shotsLeft = board.getSize();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hitShots = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      int x = coord.getX();
      int y = coord.getY();
      Cell current = board.getGameBoard()[y][x];
      //if the current cell contains a ship, then add to the list
      if (current.getShipStatus().equals(Status.SHIP)) {
        //System.out.println("Hit a ship!");
        //add the coordinate to the hit shots
        hitShots.add(coord);
        //tell the ship that it has been hit
        current.getShip().updateHitPositions(coord);

      }
      //change the cell's hit status to true regardless if there is a ship or not
      current.setHitStatus(true);
    }
    return hitShots;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public abstract void endGame(GameResult result, String reason);

  /**
   * Initializes the board which tracks the opponent's board data
   */
  private void initOpponentTracker() {
    for (int i = 0; i < opponentTracker.length; i++) {
      for (int j = 0; j < opponentTracker[0].length; j++) {
        opponentTracker[i][j] = HitStatus.NONE;
      }
    }
  }

}

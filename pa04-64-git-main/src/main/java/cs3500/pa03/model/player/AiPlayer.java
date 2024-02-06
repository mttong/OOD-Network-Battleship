package cs3500.pa03.model.player;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enums.GameResult;
import cs3500.pa03.model.enums.HitStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * Represents a Computer Player - AI
 */
public class AiPlayer extends PlayerAbstract {

  /**
   * Constructor - instantiates an AI player object
   *
   * @param name the name of the ai player
   */
  public AiPlayer(String name, Random random) {
    super(name, random);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return this AI player's shots to the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    int shotNum = getNumShots();

    if (shotNum > shotsLeft) {
      shotNum = shotsLeft;
    }
    //Random random = new Random();
    for (int i = 0; i < shotNum; i++) {
      boolean goodHit = false;

      //keep generating a shot until a non-hit spot is found
      while (!goodHit) {
        //generate a random spot on the board
        int x = random.nextInt(board.getGameBoard().length); //row
        int y = random.nextInt(board.getGameBoard()[i].length); //column
        //the ai has not hit the spot yet
        if (opponentTracker[x ][y].equals(HitStatus.NONE)) {
          //update all the metadata with the hit shot
          goodHit = true;
          shots.add(new Coord(y, x));

          shotsLeft--;

          opponentTracker[x][y] = HitStatus.MISS;
        }
      }
    }
    return shots;
  }


  /**
   * Reports to this AI player what shots in their previous volley returned from takeShots()
   * successfully hit the opponent's ship. Updates the opponent tracker board accordinly
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    //update the 2d array that tracks the opponent
    for (Coord coord : shotsThatHitOpponentShips) {
      this.opponentTracker[coord.getY()][coord.getX()] = HitStatus.HIT;
    }
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
    return;
  }

  /**
   * Gets the number of unsunken ships, which is equal to the number of shots this AI player gets
   *
   * @return number of unsunken ships
   */
  private int getNumShots() {
    int num = 0;
    for (Ship ship : ships) {
      if (!ship.isSunk()) {
        num++;
      }
    }
    return num;
  }

}

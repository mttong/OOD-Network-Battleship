package cs3500.pa03.model.player;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShotHolder;
import cs3500.pa03.model.enums.GameResult;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.View;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests the ManualPlayer Class
 */
class ManualPlayerTest {
  ManualPlayer player;
  View view;
  ShotHolder holder;

  /**
   * Sets up the board
   */
  @BeforeEach
  public void setUp() {
    view = new BattleSalvoView(new StringBuilder(), new StringReader(""));
    holder = new ShotHolder();
    this.player = new ManualPlayer("manual", holder, view, new Random(100));
  }

  /**
   * Tests the Player take shots method
   */
  @Test
  public void testTakeShots() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player.setup(6, 6, specifications);

    List<Coord> shots = List.of(new Coord(0, 0), new Coord(1, 1));
    holder.setShots(shots);
    assertEquals(shots, player.takeShots());
  }

  /**
   * Tests the endGame method
   */
  @Test
  public void testEndGame() {
    player.endGame(GameResult.WIN, "winner");
    player.endGame(GameResult.LOSE, "loser");
    player.endGame(GameResult.DRAW, "draw");
  }

  /**
   * Tests the succcessfulHits() method
   * Was not sure how to test the output since there are no getter methods in
   */
  @Test
  public void testSuccessfulHits() {
    List<Coord> shots = List.of(new Coord(0, 0), new Coord(1, 1));
    player.setup(6, 6, new HashMap<>());
    player.successfulHits(shots);

  }


}
package cs3500.pa03.model.player;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enums.GameResult;
import cs3500.pa03.model.enums.ShipType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the AI Player
 */
class AiPlayerTest {

  AiPlayer player;

  /**
   * Sets up the testing
   */
  @BeforeEach
  public void setUp() {
    player = new AiPlayer("AI", new Random(100));
  }

  /**
   * Tests the takeShots() method
   */
  @Test
  public void testTakeShots() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    player.setup(6, 6, specifications);
    player.takeShots();

    assertEquals(1, player.takeShots().size());
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
package cs3500.pa03.model.player;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShotHolder;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.View;
import cs3500.pa04.enums.Direction;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test for PlayerAbstract Class
 */
class PlayerAbstractTest {
  ManualPlayer player1;
  AiPlayer player2;
  View view;
  ShotHolder holder;

  /**
   * Sets up the test
   */
  @BeforeEach
  public void setUp() {
    view = new BattleSalvoView(new StringBuilder(), new StringReader(""));
    holder = new ShotHolder();
    this.player1 = new ManualPlayer("manual", holder, view, new Random(100));
    this.player2 = new AiPlayer("ai", new Random(100));
  }

  /**
   * Tests the name method
   */
  @Test
  public void testName() {
    assertEquals("manual", player1.name());
    assertEquals("ai", player2.name());
  }

  /**
   * Tests the setUp method
   */
  @Test
  public void testSetUpSuccess() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    specifications.put(ShipType.DESTROYER, 1);

    Ship ship1 = new Ship(ShipType.CARRIER, Direction.HORIZONTAL);
    Ship ship2 = new Ship(ShipType.BATTLESHIP, Direction.HORIZONTAL);
    Ship ship3 = new Ship(ShipType.DESTROYER, Direction.HORIZONTAL);

    ship1.addHitPositionsTracker(new Coord(5, 1));
    ship2.addHitPositionsTracker(new Coord(4, 5));
    ship3.addHitPositionsTracker(new Coord(0, 4));
    Ship ship4 = new Ship(ShipType.SUBMARINE, Direction.HORIZONTAL);
    ship4.addHitPositionsTracker(new Coord(1, 3));

    ship1.addHitPositionsTracker(new Coord(5, 0));
    ship1.addHitPositionsTracker(new Coord(5, 5));
    ship1.addHitPositionsTracker(new Coord(5, 4));
    ship1.addHitPositionsTracker(new Coord(5, 3));
    ship1.addHitPositionsTracker(new Coord(5, 2));

    ship2.addHitPositionsTracker(new Coord(4, 3));
    ship2.addHitPositionsTracker(new Coord(4, 4));
    ship2.addHitPositionsTracker(new Coord(4, 1));
    ship2.addHitPositionsTracker(new Coord(4, 2));

    ship3.addHitPositionsTracker(new Coord(0, 3));
    ship3.addHitPositionsTracker(new Coord(0, 2));
    ship3.addHitPositionsTracker(new Coord(0, 1));

    ship4.addHitPositionsTracker(new Coord(1, 2));
    ship4.addHitPositionsTracker(new Coord(1, 1));

    List<Ship> ships = new ArrayList<>(Arrays.asList(ship1, ship2, ship3, ship4));


    List<Ship> playerShips1 = player1.setup(6, 6, specifications);
    List<Ship> playerShips2 = player2.setup(6, 6, specifications);
    assertEquals(4, playerShips1.size());
    assertEquals(4, playerShips2.size());
    /*
    assertEquals(ship2, playerShips.get(0));
    assertEquals(ship3, playerShips.get(1));
    assertEquals(ship4, playerShips.get(2));
    assertEquals(ship1, playerShips.get(3));
    */
  }

  /**
   * Tests setUp with an empty ship hashmap
   */
  @Test
  public void testEmptyShips() {
    Map<ShipType, Integer> empty = new HashMap<>();
    List<Ship> emptyShips = player2.setup(6, 6, empty);
    assertEquals(0, emptyShips.size());
  }

  /**
   * Tests the Report Damage method - all hit
   */
  @Test
  public void testReportDamageAllHit() {
    player1 = new ManualPlayer("manual", holder, view, new Random(100));
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player1.setup(6, 6, specifications);

    List<Coord> hitCoord = new ArrayList<>();
    hitCoord.add(new Coord(4, 2));
    hitCoord.add(new Coord(4, 3));
    hitCoord.add(new Coord(4, 4));

    assertEquals(hitCoord, player1.reportDamage(hitCoord));
  }

  /**
   * Tests the Report Damage method - some hit
   */
  @Test
  public void testReportDamageSomeHit() {
    player1 = new ManualPlayer("manual", holder, view, new Random(100));
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player1.setup(6, 6, specifications);

    List<Coord> hitCoord = new ArrayList<>();
    hitCoord.add(new Coord(4, 2));
    hitCoord.add(new Coord(4, 3));
    hitCoord.add(new Coord(4, 4));
    hitCoord.add(new Coord(2, 2));
    hitCoord.add(new Coord(2, 3));

    List<Coord> hitCoord1 = new ArrayList<>();
    hitCoord1.add(new Coord(4, 2));
    hitCoord1.add(new Coord(4, 3));
    hitCoord1.add(new Coord(4, 4));

    assertEquals(hitCoord1, player1.reportDamage(hitCoord));
  }

  /**
   * Tests the Report Damage method - none hit
   */
  @Test
  public void testReportDamageNoneHit() {
    player1 = new ManualPlayer("manual", holder, view, new Random(100));
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player1.setup(6, 6, specifications);

    List<Coord> hitCoord = new ArrayList<>();
    hitCoord.add(new Coord(2, 2));
    hitCoord.add(new Coord(2, 3));
    hitCoord.add(new Coord(2, 4));
    hitCoord.add(new Coord(2, 5));

    List<Coord> hitCoord1 = new ArrayList<>();

    assertEquals(hitCoord1, player1.reportDamage(hitCoord));
  }


}
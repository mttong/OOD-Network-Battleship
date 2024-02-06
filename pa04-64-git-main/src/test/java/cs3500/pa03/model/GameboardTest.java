package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.model.enums.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Gameboard Class
 */
class GameboardTest {
  Gameboard board1;
  Gameboard board2;
  Map<ShipType, Integer> specification1;
  Map<ShipType, Integer> specification2;
  int height;
  int width;

  /**
   * Set uo before each test
   */
  @BeforeEach
  public void setUp() {
    //testing with the smallest input possible - small data
    this.height = 6;
    this.width = 6;
    this.board1 = new Gameboard(height, width, new Random(100));
    //make the hashmap of ship types
    specification1 = new HashMap<>();
    specification1.put(ShipType.CARRIER, 1);
    specification1.put(ShipType.BATTLESHIP, 1);
    specification1.put(ShipType.DESTROYER, 1);
    specification1.put(ShipType.SUBMARINE, 1);

    //board with a different seed
    this.board2 = new Gameboard(height, width, new Random(2));
    //make the hashmap of ship types - 6 ships this time
    specification2 = new HashMap<>();
    specification2.put(ShipType.CARRIER, 2);
    specification2.put(ShipType.BATTLESHIP, 2);
    specification2.put(ShipType.DESTROYER, 1);
    specification2.put(ShipType.SUBMARINE, 1);


  }

  /**
   * Tests the initBoard method (Success)
   */
  @Test
  public void testInitBoard() {
    //ensure that they are all null to begin with
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        assertNull(board1.getGameBoard()[i][j]);
      }
    }
    board1.initBoard(specification1);
    //check to see if board was created correctly and ships were placed
    // in order to do so, loop through the board and see how many cells
    // are ships and how many cells are empty
    int shipCells = 0;
    int allCells = 0;
    int emptyCells = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (board1.getGameBoard()[i][j].getShipStatus().equals(Status.SHIP)) {
          shipCells++;
        } else if (board1.getGameBoard()[i][j].getShipStatus().equals(Status.EMPTY)) {
          emptyCells++;
        }
        allCells++;
      }
    }
    assertEquals(18, emptyCells);
    assertEquals(18, shipCells);
    assertEquals(36, allCells);
  }

  /**
   * Tests a larger board creation - 15 x 9 with max number of ships
   */
  @Test
  public void testInitBoardRectangleDimensionsAndMaxShips() {
    Gameboard board3 = new Gameboard(15, 9, new Random(2));
    Map<ShipType, Integer> specification3;
    //make the hashmap of ship types
    specification3 = new HashMap<>();
    specification3.put(ShipType.CARRIER, 3);
    specification3.put(ShipType.BATTLESHIP, 3);
    specification3.put(ShipType.DESTROYER, 2);
    specification3.put(ShipType.SUBMARINE, 1);

    //ensure that they are all null to begin with
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        assertNull(board3.getGameBoard()[i][j]);
      }
    }
    board3.initBoard(specification3);
    //check to see if board was created correctly and ships were placed
    // in order to do so, loop through the board and see how many cells
    // are ships and how many cells are empty
    int shipCells = 0;
    int allCells = 0;
    int emptyCells = 0;
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 9; j++) {
        if (board3.getGameBoard()[i][j].getShipStatus().equals(Status.SHIP)) {
          shipCells++;
        } else if (board3.getGameBoard()[i][j].getShipStatus().equals(Status.EMPTY)) {
          emptyCells++;
        }
        allCells++;
      }
    }
    assertEquals(91, emptyCells);
    assertEquals(44, shipCells);
    assertEquals(135, allCells);
  }
}
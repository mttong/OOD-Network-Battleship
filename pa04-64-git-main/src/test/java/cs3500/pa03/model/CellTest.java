package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.model.enums.Status;
import cs3500.pa04.enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests Cell Class
 */
class CellTest {

  Cell cellEmpty;
  Cell cellShip;

  /**
   * Initial set up
   */
  @BeforeEach
  public void setUp() {
    cellEmpty = new Cell();
    cellShip = new Cell(new Ship(ShipType.BATTLESHIP, Direction.HORIZONTAL));
  }

  /**
   * Tests the getHitStatus methods
   */
  @Test
  public void getHitStatus() {
    assertFalse(cellEmpty.getHitStatus());
    assertFalse(cellShip.getHitStatus());
  }

  /**
   * Tests the getShipMethods
   */
  @Test
  public void getShip() {
    assertNull(cellEmpty.getShip());
    assertEquals(new Ship(ShipType.BATTLESHIP, Direction.HORIZONTAL), cellShip.getShip());
  }

  /**
   * Tests the getShipStatus method
   */
  @Test
  public void getShipStatus() {
    assertEquals(Status.EMPTY, cellEmpty.getShipStatus());
    assertEquals(Status.SHIP, cellShip.getShipStatus());
  }

  /**
   * Tests the setHitStatus method
   */
  @Test
  public void setHitStatus() {
    assertFalse(cellEmpty.getHitStatus());
    assertFalse(cellShip.getHitStatus());

    cellEmpty.setHitStatus(true);
    cellShip.setHitStatus(true);

    assertTrue(cellEmpty.getHitStatus());
    assertTrue(cellShip.getHitStatus());

  }

}
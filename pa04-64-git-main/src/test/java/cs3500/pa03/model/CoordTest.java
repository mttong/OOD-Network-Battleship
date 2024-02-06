package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.enums.ShipType;
import cs3500.pa04.enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the coordinate class
 */
class CoordTest {

  Coord coord;

  /**
   * Initial set up
   */
  @BeforeEach
  public void setUp() {
    coord = new Coord(1, 1);
  }

  /**
   * Tests the getX() method
   */
  @Test
  public void testGetX() {
    assertEquals(1, coord.getX());
  }

  /**
   * Tests the getY() method
   */
  @Test
  public void testGetY() {
    assertEquals(1, coord.getY());
  }

  /**
   * Tests the equals() method - true
   */
  @Test
  public void testEqualsTrue() {
    Coord otherCoord = new Coord(1, 1);
    assertEquals(coord, otherCoord);
  }

  /**
   * Tests the equals() method - false
   */
  @Test
  public void testEqualsFalse() {
    Coord otherCoord = new Coord(1, 3);
    Coord otherCoord2 = new Coord(3, 1);
    assertNotEquals(coord, otherCoord2);
    assertNotEquals(coord, otherCoord);
  }

  /**
   * Tests the equals method - exception
   */
  @Test
  public void testEqualsException() {
    assertThrows(IllegalArgumentException.class,
        () -> coord.equals(new Ship(ShipType.CARRIER, Direction.HORIZONTAL)));
  }

}
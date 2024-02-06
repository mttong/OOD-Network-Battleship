package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests the ShotHolder Class
 */
class ShotHolderTest {

  ShotHolder holder;

  /**
   * Setting up the holder
   */
  @BeforeEach
  public void setUp() {
    holder = new ShotHolder();
  }

  /**
   * Tests the getShots() method
   */
  @Test
  public void testGetShots() {

    assertEquals(new ArrayList<>(), holder.getShots());
  }

  /**
   * Tests the setShots() method
   */
  @Test
  public void testSetShots() {
    assertEquals(new ArrayList<>(), holder.getShots());
    List<Coord> list = new ArrayList<>();
    list.add(new Coord(0, 0));
    list.add(new Coord(1, 1));
    list.add(new Coord(2, 2));
    list.add(new Coord(3, 3));
    holder.setShots(list);
    assertEquals(list, holder.getShots());
  }




}
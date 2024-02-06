package cs3500.pa03.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShotHolder;
import cs3500.pa03.model.player.MockPlayer;
import cs3500.pa03.view.MockView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for BattleSalvo Controller Class
 */
class BattleSalvoControllerTest {

  BattleSalvoController controller;
  MockView view;
  MockPlayer player1;
  MockPlayer player2;

  /**
   * Set up
   */
  @BeforeEach
  public void setUp() {
    this.view = new MockView();
    player1 = new MockPlayer();
    player2 = new MockPlayer();
    ShotHolder holder = new ShotHolder();
    controller = new BattleSalvoController(player1, player2, view, holder);
  }

  /**
   * Tests the run method in the controller
   */
  @Test
  public void testRun() {
    controller.run();
    assertEquals("welcome getFleet getShots getShots display ", view.build.toString());
    assertEquals("setup takeshots" + " reportDamage hits takeshots reportDamage hits ",
        player1.build.toString());
    assertEquals("setup takeshots reportDamage hits " + "takeshots reportDamage hits ",
        player2.build.toString());
  }

}
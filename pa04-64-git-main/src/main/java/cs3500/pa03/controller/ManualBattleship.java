package cs3500.pa03.controller;

import cs3500.pa03.model.ShotHolder;
import cs3500.pa03.model.player.AiPlayer;
import cs3500.pa03.model.player.ManualPlayer;
import cs3500.pa03.model.player.Player;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.View;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

/**
 * Begins the game of ManualBattleship - player vs. AI!
 */
public class ManualBattleship {

  /**
   * Runs the game
   */
  public void run() {
    //create view
    View view = new BattleSalvoView(new PrintStream(System.out), new InputStreamReader(System.in));
    ShotHolder shotHolder = new ShotHolder();
    //create players
    Player player1 = new ManualPlayer("manual", shotHolder, view, new Random());
    Player player2 = new AiPlayer("AI", new Random());
    //controller + run
    Controller controller = new BattleSalvoController(player1, player2, view, shotHolder);
    controller.run();
  }
}

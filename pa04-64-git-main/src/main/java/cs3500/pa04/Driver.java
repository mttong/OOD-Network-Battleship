package cs3500.pa04;

import cs3500.pa03.controller.ManualBattleship;
import cs3500.pa03.model.player.AiPlayer;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    //play the game
    if (args.length == 2) {
      //play game by connecting to the server
      try {
        Driver.runClient(args[0], Integer.parseInt(args[1])); //host, port -- validate inputs
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    } else if (args.length == 0) {
      //play the game manually
      ManualBattleship battle = new ManualBattleship();
      battle.run();
    } else {
      //unable to run
      throw new RuntimeException("Cannot run game");
    }
  }

  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port) throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    ProxyController controller = new ProxyController(server, new AiPlayer("AI", new Random()));
    controller.run();
  }
}
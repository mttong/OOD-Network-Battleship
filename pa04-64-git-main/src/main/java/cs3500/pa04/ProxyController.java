package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa03.model.player.Player;
import cs3500.pa04.enums.GameType;
import cs3500.pa04.enums.Message;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a Proxy Controller that receives + processes messages from the server
 */
public class ProxyController implements Controller {

  //fields
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Constructor
   *
   * @param server connection to the server socket
   * @param ai     the ai player
   * @throws IOException if unable to open server
   */
  public ProxyController(Socket server, Player ai) throws IOException {
    this.server = server;
    this.player = ai;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      //keep listening to the server
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if (name.equals(Message.JOIN.message())) {
      handleJoin();
    } else if (name.equals(Message.SETUP.message())) {
      handleSetUp(arguments);
    } else if (name.equals(Message.TAKESHOTS.message())) {
      handleTakeShots();
    } else if (name.equals(Message.REPORTDAMAGE.message())) {
      handleReportDamage(arguments);
    } else if (name.equals(Message.SUCCESSFULHITS.message())) {
      handleSuccessfulHits(arguments);
    } else if (name.equals(Message.ENDGAME.message())) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Processes a server's join message and sends an appropriate message back
   */
  private void handleJoin() {
    //information needed for a client response
    String gitUserName = "soniasheth";
    GameType gameType = GameType.SINGLE;

    //create a joinJSON message to send back to the server
    JoinJson joinMessage = new JoinJson(gitUserName, gameType.name());
    //serialize the response
    JsonNode serializeResponse = JsonUtils.serializeRecord(joinMessage);
    //send the message back to the server
    sendMessageToServer(Message.JOIN.message(), serializeResponse);
  }

  /**
   * Processes a server's setup message and sends an appropriate message back
   *
   * @param arguments server's message args
   */
  private void handleSetUp(JsonNode arguments) {
    //convert to the data representation of set-up (SetUpJson)
    SetUpJson setUpArgs = this.mapper.convertValue(arguments, SetUpJson.class);

    //get the height and width from ther server's message
    int height = setUpArgs.height();
    int width = setUpArgs.width();

    //parse the fleet from the server's message
    FleetSpecJson fleetSpec = setUpArgs.fleetSpec();
    Map<ShipType, Integer> specification = new HashMap<>();
    specification.put(ShipType.CARRIER, fleetSpec.numCarrier());
    specification.put(ShipType.BATTLESHIP, fleetSpec.numBattleShip());
    specification.put(ShipType.DESTROYER, fleetSpec.numDestroyer());
    specification.put(ShipType.SUBMARINE, fleetSpec.numSubmarine());

    //have the player generate the fleet (and board)
    List<Ship> aiShips = player.setup(height, width, specification);

    //convert to ShipAdapter for JSON
    List<ShipAdapter> aiShipsFinal = new ArrayList<>();
    for (Ship ship : aiShips) {
      aiShipsFinal.add(new ShipAdapter(ship.getStart(), ship.getLength(), ship.getDirection()));
    }

    //create the json response
    FleetJson setUpFleet = new FleetJson(aiShipsFinal);
    JsonNode serializeResponse = JsonUtils.serializeRecord(setUpFleet);

    //send message back to the server
    sendMessageToServer(Message.SETUP.message(), serializeResponse);
  }


  /**
   * Processes a server's take-shots message and sends an appropriate message back
   */
  private void handleTakeShots() {
    //player takes the shots
    List<Coord> shots = player.takeShots();

    //create the json response
    VolleyJson aiShots = new VolleyJson(shots);
    JsonNode serializeResponse = JsonUtils.serializeRecord(aiShots);

    //send message back to the server
    sendMessageToServer(Message.TAKESHOTS.message(), serializeResponse);
  }

  /**
   * Processes a server's report-damage message and sends an appropriate message back
   *
   * @param arguments server's message args
   */

  private void handleReportDamage(JsonNode arguments) {
    //shots received from the server
    VolleyJson shotsReceived = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> shots = shotsReceived.getVolley();

    //called report damage on player + get the damage done
    List<Coord> damage = player.reportDamage(shots);

    //create the json response
    VolleyJson reportDamage = new VolleyJson(damage);
    JsonNode jsonResponse = JsonUtils.serializeRecord(reportDamage);

    //send message back to the server
    sendMessageToServer(Message.REPORTDAMAGE.message(), jsonResponse);
  }

  /**
   * Processes a server's successful hits message and sends an appropriate message back
   *
   * @param arguments server's message args
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    //shots received frm server
    VolleyJson shotsReceived = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> shots = shotsReceived.getVolley();

    //call player method
    player.successfulHits(shots);

    //send message back to the server
    JsonNode emptyContent = JsonNodeFactory.instance.objectNode();
    sendMessageToServer(Message.SUCCESSFULHITS.message(), emptyContent);
  }

  /**
   * Processes a server's end-game message and sends an appropriate message back
   *
   * @param arguments server's message contnent
   */
  private void handleEndGame(JsonNode arguments) {
    //get the server's end game message content
    EndGameJson endGameMessage = this.mapper.convertValue(arguments, EndGameJson.class);

    //call endgame on player
    player.endGame(endGameMessage.getResult(), endGameMessage.getReason());

    //send message back to the server
    JsonNode emptyContent = JsonNodeFactory.instance.objectNode();
    sendMessageToServer(Message.ENDGAME.message(), emptyContent);
    System.out.println(endGameMessage.getReason());

    //attempt to close the socket
    try {
      server.close();
    } catch (IOException e) {
      //do nothing
    }
  }

  /**
   * Packages a MessageJson message and send it back to the server
   *
   * @param methodName method name
   * @param content    content being sent back
   */
  private void sendMessageToServer(String methodName, JsonNode content) {
    //create the message JSON node
    MessageJson message = new MessageJson(methodName, content);
    //serialize the message
    JsonNode clientResponse = JsonUtils.serializeRecord(message);
    //send message back to the server
    this.out.println(clientResponse);
    //System.out.println(clientResponse);
  }
}

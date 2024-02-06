package cs3500.pa04;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import cs3500.pa03.model.enums.GameResult;
import cs3500.pa03.model.player.AiPlayer;
import cs3500.pa03.model.player.MockPlayer;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ProxyController test
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private Random random;

  private String separation;

  /**
   * set up
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    this.separation = System.lineSeparator();
    this.random = new Random(100);

  }

  /**
   * Tests the handleJoin method
   */
  @Test
  public void testHandleJoin() {
    //prepare a sample message
    MessageJson sampleMessage = new MessageJson("join", JsonNodeFactory.instance.objectNode());
    JsonNode serverMessage = JsonUtils.serializeRecord(sampleMessage);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(serverMessage.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, new AiPlayer("ai", new Random()));
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();
    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":"
        + "\"soniasheth\",\"game-type\":\"SINGLE\"}}" + separation;

    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests the handleSetUp method
   */
  @Test
  public void testHandleSetUp() {
    //prepare a sample message
    FleetSpecJson fleet = new FleetSpecJson(1, 0, 0, 0);
    SetUpJson setUp = new SetUpJson(10, 10, fleet);

    //fleet with one ship for testing purposes

    JsonNode serverMessage = createSampleMessage("setup", setUp);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(serverMessage.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, new AiPlayer("ai", random));
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();
    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\""
        + ":{\"x\":4,\"y\":4},\"length\":5,\"direction\":\"VERTICAL\"}]}}" + separation;
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests the handle take shots method
   */
  @Test
  public void testHandleTakeShots() {
    //prepare a sample message
    MessageJson sampleMessage =
        new MessageJson("take-shots", JsonNodeFactory.instance.objectNode());
    JsonNode serverMessage = JsonUtils.serializeRecord(sampleMessage);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(serverMessage.toString()));

    // Create a ProxyController with a mock player for take shots
    try {
      this.controller = new ProxyController(socket, new MockPlayer()); //used a mock player
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();
    String expected =
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y"
            + "\":0},{\"x\":1,\"y\":1},{\"x\":2,\"y\":2},{\"x\":3,\"y\":3}]}}" + separation;
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests handleReportDamage
   */
  @Test
  public void testHandleReportDamage() {
    //prepare a sample message
    VolleyJson volley = new VolleyJson(new ArrayList<>());
    JsonNode serverMessage = createSampleMessage("report-damage", volley);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(serverMessage.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, new MockPlayer()); //used a mock player
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();
    String expected = "{\"method-name\":\"report-damage\",\"arguments\""
        + ":{\"coordinates\":[{\"x\":0,\"y\":1},{\"x\":0,\"y\":0}]}}" + separation;
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests the handleSuccuessfulHits method
   */
  @Test
  public void testHandleSuccHits() {
    //prepare a sample message
    MessageJson sampleMessage =
        new MessageJson("successful-hits", JsonNodeFactory.instance.objectNode());
    JsonNode serverMessage = JsonUtils.serializeRecord(sampleMessage);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(serverMessage.toString()));

    // Create a ProxyController with a mock player
    try {
      this.controller = new ProxyController(socket, new MockPlayer()); //used a mock player
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();
    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":{}}" + separation;
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests the handleEndGame method
   */
  @Test
  public void testHandleEndGame() {
    //prepare a sample message
    EndGameJson end = new EndGameJson(GameResult.WIN, "You win!");
    JsonNode serverMessage = createSampleMessage("end-game", end);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(serverMessage.toString()));

    // Create a ProxyController with a MockPlayer
    try {
      this.controller = new ProxyController(socket, new MockPlayer()); //used a mock player
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();
    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}" + separation;
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

}
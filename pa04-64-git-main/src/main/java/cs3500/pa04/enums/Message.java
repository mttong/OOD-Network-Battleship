package cs3500.pa04.enums;

/**
 * Messages
 */
public enum Message {
  JOIN("join"), SETUP("setup"),
  TAKESHOTS("take-shots"),
  REPORTDAMAGE("report-damage"),
  SUCCESSFULHITS("successful-hits"),
  ENDGAME("end-game");

  private final String messageName;

  /**
   * constructor
   *
   * @param message method name
   */
  Message(String message) {
    this.messageName = message;
  }

  /**
   * getter
   *
   * @return strings
   */
  public String message() {
    return messageName;
  }
}

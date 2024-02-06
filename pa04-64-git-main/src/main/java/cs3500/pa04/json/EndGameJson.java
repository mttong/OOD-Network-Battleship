package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.enums.GameResult;

/**
 * Record that represents an end-game message
 *
 * @param result GameResult end: Win, Loss, Draw
 * @param reason the reason why the game ended
 */
public record EndGameJson(@JsonProperty("result") GameResult result,
                          @JsonProperty("reason") String reason) {

  /**
   * get result
   *
   * @return GameResult
   */
  public GameResult getResult() {
    return this.result;
  }

  /**
   * get reason
   *
   * @return String
   */
  public String getReason() {
    return this.reason;
  }
}

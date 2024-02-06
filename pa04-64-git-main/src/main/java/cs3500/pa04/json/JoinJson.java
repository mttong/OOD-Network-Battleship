package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Record JSON that represents a Join message
 *
 * @param gitName  github user name
 * @param gameType MULTI or SINGULAR
 */
public record JoinJson(@JsonProperty("name") String gitName,
                       @JsonProperty("game-type") String gameType) {

}

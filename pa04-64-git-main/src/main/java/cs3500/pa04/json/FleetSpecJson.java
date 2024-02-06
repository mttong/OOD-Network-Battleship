package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record that represents the ships and number of each
 *
 * @param numBattleShip number of battleship ships
 * @param numCarrier    number of carrier ships
 * @param numDestroyer  number of destroyer ships
 * @param numSubmarine  number of submarine ships
 */
public record FleetSpecJson(@JsonProperty("BATTLESHIP") int numBattleShip,
                            @JsonProperty("CARRIER") int numCarrier,
                            @JsonProperty("DESTROYER") int numDestroyer,
                            @JsonProperty("SUBMARINE") int numSubmarine) {
}

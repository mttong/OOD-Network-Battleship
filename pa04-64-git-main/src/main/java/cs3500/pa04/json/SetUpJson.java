package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record that represents a set-up message
 *
 * @param width     width of the board
 * @param height    height of the board
 * @param fleetSpec record of ships placed on the board
 */
public record SetUpJson(@JsonProperty("width") int width, @JsonProperty("height") int height,
                        @JsonProperty("fleet-spec") FleetSpecJson fleetSpec) {

}

package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;

/**
 * Record that represents a ship in terms of starting coord, length, and direction
 *
 * @param coord  starting position
 * @param length how long
 * @param dir    horizontal or vertical
 */
public record ShipAdapter(@JsonProperty("coord") Coord coord, @JsonProperty("length") int length,
                          @JsonProperty("direction") String dir) {

}

package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Record that represents a list of ships
 *
 * @param fleet the list of ships in for of ship adapter records
 */
public record FleetJson(@JsonProperty("fleet") List<ShipAdapter> fleet) {
}

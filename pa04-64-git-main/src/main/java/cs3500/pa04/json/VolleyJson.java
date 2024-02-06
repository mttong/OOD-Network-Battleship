package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * Record that represents a volley, a list of coordinates
 *
 * @param volley list of coordinates
 */
public record VolleyJson(@JsonProperty("coordinates") List<Coord> volley) {

  /**
   * get volley
   *
   * @return list of coordinates
   */
  public List<Coord> getVolley() {
    return this.volley;
  }
}

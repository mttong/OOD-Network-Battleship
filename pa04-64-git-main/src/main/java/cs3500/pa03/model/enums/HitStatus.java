package cs3500.pa03.model.enums;

/**
 * Represents the 3 types of hit statuses
 */
public enum HitStatus {
  HIT, //there was a ship there and shot at
  MISS, //there was not a ship there and not shot at
  NONE //there is no ship there AND not shot at yet
}

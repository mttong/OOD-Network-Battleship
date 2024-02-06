package cs3500.pa03.model;

import cs3500.pa03.model.enums.ShipType;
import cs3500.pa04.enums.Direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a singular Ship
 */
public class Ship {
  //fields
  private final ShipType type;
  private final HashMap<Coord, Boolean> hitPositionsTracker;
  private final List<Coord> posns;
  private final Direction direction;


  /**
   * constructor
   *
   * @param type ShipType
   * @param dir  Direction
   */
  public Ship(ShipType type, Direction dir) {
    this.type = type;
    this.direction = dir;
    this.hitPositionsTracker = new HashMap<>();
    this.posns = new ArrayList<>();
  }


  /**
   * Returns whether the ship has sunken yet
   *
   * @return false if the ship is not sunk or true is the ship is sunk
   */
  public boolean isSunk() {
    // returns true if the ship is sunk
    boolean sunk = true;
    for (Map.Entry<Coord, Boolean> element : hitPositionsTracker.entrySet()) {
      // return false if there is a position not hit yet
      if (!element.getValue()) {
        return false;
      }
    }
    return sunk;
  }

  /**
   * Updates the hitPositionsTracker HashMap and change the boolean corresponding to the given coord
   * to true
   */
  public void updateHitPositions(Coord position) {
    //hitPositionsTracker.get(position);
    for (Map.Entry<Coord, Boolean> coordBooleanEntry : this.hitPositionsTracker.entrySet()) {
      if (coordBooleanEntry.getKey().equals(position)) {
        this.hitPositionsTracker.put(position, true);
      }
    }


  }


  /**
   * Adds an entry with the give Coord as the key and false as the value
   *
   * @param position Key Value (A Coord)
   */
  public void addHitPositionsTracker(Coord position) {
    // add the value and intitial value is false
    this.hitPositionsTracker.put(position, false);

    this.posns.add(position);
  }

  /**
   * Checks if the given object is equal to this ship - needed for testing
   *
   * @param other given object
   * @return boolean, whether the given object is the same as this ship
   */
  public boolean equals(Object other) {
    if (!(other instanceof Ship that)) {
      throw new IllegalArgumentException("Not a Ship");
    }
    return this.hitPositionsTracker.equals(that.hitPositionsTracker) && this.type.equals(that.type);
  }

  /**
   * gets hit positions hashmap of ship
   *
   * @return HashMap
   */
  public HashMap<Coord, Boolean> getHitPositionsTracker() {
    return this.hitPositionsTracker;
  }

  /**
   * gets length of ship
   *
   * @return int
   */
  public int getLength() {
    return hitPositionsTracker.size();
  }

  /**
   * gets starting coordinate of ship
   *
   * @return Coord
   */
  public Coord getStart() {
    return posns.get(0);
  }

  /**
   * gets direction of ship
   *
   * @return Direction
   */
  public String getDirection() {
    return this.direction.toString().toUpperCase();
  }
}

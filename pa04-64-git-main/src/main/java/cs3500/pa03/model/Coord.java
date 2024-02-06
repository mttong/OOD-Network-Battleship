package cs3500.pa03.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an (x,y) Coordinate
 */
public class Coord {
  //fields
  private final int coordX;
  private final int coordY;

  /**
   * Constructor - Constructs a Coord Object
   *
   * @param x x location
   * @param y y location
   */

  @JsonCreator
  public Coord(
      @JsonProperty("x") int x, @JsonProperty("y") int y) {
    this.coordX = x;
    this.coordY = y;
  }

  /**
   * Gets the X coordinate of this coordinate
   *
   * @return the X location of the coordinate
   */
  public int getX() {
    return this.coordX;
  }

  /**
   * Gets the y coordinate of this coordinate
   *
   * @return the y location of the coordinate
   */
  public int getY() {
    return this.coordY;
  }

  /**
   * Determines if a given object is equal to this coord object
   *
   * @param other given object
   * @return boolean, whether this object is equal to the given object
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Coord that)) {
      throw new IllegalArgumentException("Not a Coordinate");
    }
    return this.coordX == that.coordX
        && this.coordY == that.coordY;
  }

  /**
   * Changes the hashCode of this COord object
   *
   * @return the new hashcode
   */
  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  /**
   * Returns a string representation of the object
   *
   * @return String
   */
  @Override
  public String toString() {
    return "x: " + this.coordX + " y: " + this.coordY;
  }

}

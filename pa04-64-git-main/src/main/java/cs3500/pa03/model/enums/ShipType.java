package cs3500.pa03.model.enums;

/**
 * Represents the different types of ships
 */
public enum ShipType {
  CARRIER(6), BATTLESHIP(5), DESTROYER(4), SUBMARINE(3);

  final int size;

  /**
   * constructor
   *
   * @param size size of ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * get size of ship
   *
   * @return size
   */
  public int getSize() {
    return size;
  }

}

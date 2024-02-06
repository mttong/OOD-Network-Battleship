package cs3500.pa03.model;


import cs3500.pa03.model.enums.Status;

/**
 * Represents a singular cell on the Gameboard
 */
public class Cell {
  // a cell is either hit or it is not (true / false)
  private boolean hitStatus;
  private final Ship ship;
  private final Status shipStatus; //if it has a ship or not

  /**
   * Constructs a Cell Object - with a shi[
   *
   * @param ship the ship present in this cell
   */
  public Cell(Ship ship) {
    this.hitStatus = false;
    this.ship = ship;
    this.shipStatus = Status.SHIP;
  }

  /**
   * Constructs a Cell Object - with a null ship - meaning no ship in that cell
   *
   */
  public Cell() {
    this.hitStatus = false;
    this.ship = null;
    this.shipStatus = Status.EMPTY;
  }


  /**
   * Returns whether this cell has been hit yet
   *
   * @return false if the cell has NOT been shot at yet and true if it has
   */
  public boolean getHitStatus() {
    return this.hitStatus;
  }

  /**
   * Gets this Cell's ship
   *
   * @return this cell's ship
   */
  public Ship getShip() {
    return this.ship;
  }

  /**
   * Gets this cell's status - whether is has a ship or not
   *
   * @return cell's status - whether is has a ship or not
   */
  public Status getShipStatus() {
    return this.shipStatus;
  }

  /**
   * Sets the hit status of this cell to the given boolean value
   *
   * @param hitStatus boolean, true or false
   */
  public void setHitStatus(boolean hitStatus) {
    this.hitStatus = hitStatus;
  }

}

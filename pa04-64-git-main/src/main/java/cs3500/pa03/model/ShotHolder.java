package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the shots (as a list of coordinates) inputted by the user
 */
public class ShotHolder {
  private List<Coord> shots;

  /**
   * Constructor - instantiates the Shot Holder object
   */
  public ShotHolder() {
    this.shots = new ArrayList<>();
  }

  /**
   * Sets the list of shots to given list of shots
   *
   * @param shots list of user's shots (as coordinates)
   */
  public void setShots(List<Coord> shots) {
    this.shots = shots;
  }

  /**
   * Gets the user's shots
   *
   * @return list of Coordinates
   */
  public List<Coord> getShots() {
    return this.shots;
  }

}

package cs3500.pa03.view;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enums.HitStatus;
import cs3500.pa03.model.enums.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MockView
 */
public class MockView implements View {

  public StringBuilder build;

  /**
   * Constructor
   */
  public MockView() {
    this.build = new StringBuilder();

  }

  /**
   * display anything
   *
   * @param prompt String, message to display / append
   */
  @Override
  public void displayAnything(String prompt) {
    build.append("display ");

  }

  /**
   * welcome user
   *
   * @return List of Integers
   */
  @Override
  public List<Integer> welcome() {
    build.append("welcome ");
    return new ArrayList<>(Arrays.asList(6, 6));
  }

  /**
   * get fleet
   *
   * @param size max fleet size
   * @return Hashmap of fleet
   */
  @Override
  public Map<ShipType, Integer> getFleet(int size) {
    build.append("getFleet ");

    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);

    return fleet;
  }

  /**
   * display board
   *
   * @param board the gameboard
   */
  @Override
  public void displayBoard(Cell[][] board) {
    build.append("displayBoard ");
  }

  /**
   * display opponent board
   *
   * @param board opponent board data
   */
  @Override
  public void displayOpponentBoard(HitStatus[][] board) {
    build.append("displayOB ");
  }

  /**
   * get shots
   *
   * @param numShots  number of shots they can take
   * @param widthMax  max width of shots
   * @param heightMax max height of shots
   * @return list of coordinates
   */
  @Override
  public List<Coord> getShots(int numShots, int widthMax, int heightMax) {
    build.append("getShots ");
    return new ArrayList<>(Arrays.asList(new Coord(1, 1), new Coord(0, 1)));
  }


}
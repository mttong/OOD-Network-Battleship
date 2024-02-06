package cs3500.pa03.model;

import cs3500.pa03.model.enums.ShipType;
import cs3500.pa04.enums.Direction;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Represents a Gameboard part of the BattleSalvo
 */
public class Gameboard {
  //fields
  private final int height;
  private final int width;
  private final ArrayList<Ship> ships;
  private final Cell[][] board;
  private final Random random;

  /**
   * Constructor: initializes a Gameboard
   *
   * @param height gameboard height
   * @param width gameboard width
   */
  public Gameboard(int height, int width, Random random) {
    this.height = height;
    this.width = width;
    this.ships = new ArrayList<>();
    this.board = new Cell[height][width];
    this.random = random;
  }

  /**
   * Creates the board + initializes the board with ships randomly placed
   *
   * @param specification the types of ships mapped to the number of each needed for the board
   */

  public void initBoard(Map<ShipType, Integer> specification) {
    //place all the ships + init these spaces
    for (Map.Entry<ShipType, Integer> entry : specification.entrySet()) {
      placeShip(entry.getKey(), entry.getValue());
    }
    //initialize all the indices that are null
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (board[i][j] == null) {
          //initlize a cell without a ship
          board[i][j] = new Cell();
        }
      }
    }
  }

  /**
   * Places a give type of ship a given number of times randomly on the board
   *
   * @param type the type of ship being placed
   * @param number quanity of the specific type of ship
   */
  private void placeShip(ShipType type, int number) {
    int shipLength = type.getSize();
    Direction dir;
    //place 'number' amount of ships
    for (int i = 0; i < number; i++) {
      boolean placed = false;
      while (!placed) {
        // vertical or horizontal
        boolean isVertical = random.nextBoolean();
        int beginRow;
        int beginCol;
        int endRow;
        int endCol;
        if (isVertical) {
          //vertical placement
          beginRow = random.nextInt(height - shipLength + 1);
          beginCol = random.nextInt(width);
          endRow = beginRow + shipLength - 1;
          endCol = beginCol;
          dir = Direction.VERTICAL;
        } else {
          //horizontal placement
          beginRow = random.nextInt(height);
          beginCol = random.nextInt(width - shipLength + 1);
          endRow = beginRow;
          endCol = beginCol + shipLength - 1;
          dir = Direction.HORIZONTAL;
        }
        //check is valid and if so, place the ship
        if (checkValidLocation(beginRow, endRow, endCol, beginCol)) {
          //creates a new ship
          Ship ship = new Ship(type, dir);

          for (int j = beginRow; j <= endRow; j++) {
            for (int k = beginCol; k <= endCol; k++) {
              Coord position = new Coord(k, j);
              //gives the cell a ship and its position
              board[j][k] = new Cell(ship);
              //add the coordinate to the ship's coord hashmap
              ship.addHitPositionsTracker(position);
            }
          }
          placed = true;
          // add to the list of ships
          this.ships.add(ship);
        }
      }
    }
  }

  /**
   * Checks whether a ship placement is valid
   *
   * @param startRow where the ship would start horizontally
   * @param endRow where the ship would end horizontally
   * @param endCol where the ship would end vertically
   * @param startCol where the ship would start vertically
   *
   * @return boolean : true is valid and false is not valid
   */
  private boolean checkValidLocation(int startRow, int endRow, int endCol, int startCol) {
    boolean valid = true;
    for (int i = startRow; i <= endRow; i++) {
      for (int j = startCol; j <= endCol; j++) {
        //if the spot has already been initialized, it is an invlaid location
        if (board[i][j] != null) {
          valid = false;
          break;
        }
      }
    }
    return valid;
  }

  /**
   * Gets this gameboard's board
   *
   * @return this gameboard's board
   */
  public Cell[][] getGameBoard() {
    return this.board;
  }

  /**
   * Gets this gameboard's ships
   *
   * @return this gameboard's ships
   */
  public ArrayList<Ship> getShips() {
    return this.ships;
  }

  public int getSize() {
    return height * width;
  }
}

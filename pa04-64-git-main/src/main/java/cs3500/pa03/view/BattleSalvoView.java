package cs3500.pa03.view;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enums.HitStatus;
import cs3500.pa03.model.enums.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a BattleSalvoView Object
 */
public class BattleSalvoView implements View {
  //fields
  Scanner userInput;
  Appendable append;
  Readable readable;

  /**
   * Instantiates a BattleSalvoView object
   *
   * @param appendable appendable
   * @param readable   readable
   */
  public BattleSalvoView(Appendable appendable, Readable readable) {
    userInput = new Scanner(readable);
    this.append = appendable;
    this.readable = readable;
  }

  /**
   * Appends the given string to the appendable
   *
   * @param prompt String, message to display / append
   */
  public void displayAnything(String prompt) {
    try {
      append.append(prompt).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("not appendable.");
    }
  }

  /**
   * welcomes user and gets dimensions for game
   *
   * @return list of dimensions
   */
  public List<Integer> welcome() {
    try {
      this.append.append("Hello! Welcome to the OOD BattleSalvo Game!\n")
          .append("Please enter a valid height and width below: \n")
          .append("----------------------------------------------------\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("not appendable");
    }

    String errormessage = """
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
        """;

    List<Integer> dimensions = getNums(errormessage, 2);
    while (!inRange(dimensions.get(0), dimensions.get(1))) {
      try {
        this.append.append(errormessage);
      } catch (IOException e1) {
        throw new IllegalArgumentException("not appendable");
      }
      dimensions = getNums(errormessage, 2);
    }


    return dimensions;
  }

  /**
   * have user select fleet size
   *
   * @param size maximum size of fleet
   * @return Hashmap of the Ship types with the fleet values
   */
  public Map<ShipType, Integer> getFleet(int size) {
    Map<ShipType, Integer> fleetSelection = new HashMap<>();
    try {
      this.append.append(
              "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine] \n")
          .append("Remember, your fleet may not exceed size ").append(String.valueOf(size))
          .append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("not appendable");
    }

    String errormessage = "Uh oh! Invalid fleet numbers \n";
    List<Integer> fleetSelectionValues = getNums(errormessage, 4);

    ShipType[] shipTypes = ShipType.class.getEnumConstants();
    for (int i = 0; i < ShipType.values().length; i++) {
      int shipCount = fleetSelectionValues.get(i);
      fleetSelection.put(shipTypes[i], shipCount);
    }

    if (!validFleetSelection(fleetSelection, size)) {
      printMessage(errormessage);
      return getFleet(size);
    }

    return fleetSelection;
  }

  /**
   * Dispays the player's board to the console
   *
   * @param board the gameboard
   */
  @Override
  public void displayBoard(Cell[][] board) {
    try {
      append.append("--------------------------------------\n");
      append.append("Your Board:\n");
      append.append("--------------------------------------\n");
      for (Cell[] cells : board) {
        for (int i = 0; i < board[0].length; i++) {
          //there is a ship there and it is not hit
          if (cells[i].getShip() != null && cells[i].getHitStatus()) {
            append.append("H ");
          } else if (cells[i].getShip() != null) {
            //there is a ship there and it has been hit
            append.append("X ");
          } else if (cells[i].getHitStatus()) {
            // not ship but it has been hit
            append.append("M ");
          } else {
            //empty spot
            append.append("- ");
          }
        }
        append.append("\n");
      }
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  /**
   * Displays the opponenet board data to the console
   *
   * @param board opponent board data
   */
  @Override
  public void displayOpponentBoard(HitStatus[][] board) {
    try {
      append.append("--------------------------------------\n");
      append.append("Opponent's Board Data:\n");
      append.append("--------------------------------------\n");
      for (HitStatus[] hitStatuses : board) {
        for (int j = 0; j < board[0].length; j++) {
          if (hitStatuses[j].equals(HitStatus.HIT)) {
            append.append("H ");
          } else if (hitStatuses[j].equals(HitStatus.MISS)) {
            append.append("M ");
          } else {
            append.append("- ");
          }
        }
        append.append("\n");
      }
    } catch (IOException e) {
      throw new RuntimeException();
    }

  }

  /**
   * allow user to enter in shots
   *
   * @param numShots  maximum amount of shots
   * @param widthMax  board width
   * @param heightMax board height
   * @return list of shots as Coordinates
   */
  public List<Coord> getShots(int numShots, int widthMax, int heightMax) {
    try {
      this.append.append("Please enter ").append(String.valueOf(numShots)).append(" shots \n");
    } catch (IOException e) {
      throw new IllegalArgumentException("not appendable");
    }

    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < numShots; i++) {
      String errormessage =
          "Your shot " + i + " was invalid. Please enter another one. \n" + "Your width max is "
              + (widthMax - 1) + " and your height max is " + (heightMax - 1) + "\n";
      shots.add(getCoord(widthMax, heightMax, errormessage));
    }

    return shots;
  }

  /**
   * helper to get a coordinate
   *
   * @param widthMax     board width
   * @param heightMax    board height
   * @param errormessage error message if unable to create coordinate
   * @return Coordinate
   */
  private Coord getCoord(int widthMax, int heightMax, String errormessage) {
    List<Integer> nums = getNums(errormessage, 2);

    while (nums.get(0) > widthMax - 1 || nums.get(1) > heightMax - 1) {
      printMessage(errormessage);
      nums = getNums(errormessage, 2);
    }

    return new Coord(nums.get(0), nums.get(1));
  }


  /**
   * ensure all fleet selection values are valid
   *
   * @param fleet   entered fleet
   * @param maxSize maximum size of fleet
   * @return boolean, true if value
   */
  private boolean validFleetSelection(Map<ShipType, Integer> fleet, int maxSize) {
    int total = 0;
    for (int value : fleet.values()) {
      if (value <= 0) {
        return false;
      }
      total += value;
    }

    return total <= maxSize;
  }

  /**
   * get a specified amount of numbers
   *
   * @param errormessage error message if numbers aren't creatable
   * @param amount       amount of numbers to create
   * @return List of the number
   */
  private List<Integer> getNums(String errormessage, int amount) {
    List<Integer> numbers = new ArrayList<>();

    String[] nums = userInput.nextLine().split(" ");

    //checking to ensure at least two inputs were added
    if (nums.length < amount) {
      printMessage(errormessage);
      return getNums(errormessage, amount);
    }

    //checking to ensure inputs were integers
    try {
      for (int i = 0; i < amount; i++) {
        numbers.add(Integer.parseInt(nums[i]));
      }
    } catch (NumberFormatException e) {
      printMessage(errormessage);
      return getNums(errormessage, amount);
    }

    //checking to ensure inputs aren't negative
    for (int num : numbers) {
      if (num < 0) {
        printMessage(errormessage);
        return getNums(errormessage, amount);
      }
    }

    return numbers;
  }

  /**
   * checking to make sure dimensions are in range
   *
   * @param num1 width input
   * @param num2 height input
   * @return false if not in range
   */
  private boolean inRange(int num1, int num2) {
    //checking to ensure values were within range
    return (num1 >= 6 && num1 <= 15 && num2 >= 6 && num2 <= 15);
  }

  /**
   * helper to print error message
   *
   * @param errormessage String
   */
  private void printMessage(String errormessage) {
    try {
      this.append.append(errormessage);
    } catch (IOException e) {
      throw new IllegalArgumentException("not appendable");
    }
  }
}

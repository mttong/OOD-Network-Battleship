package cs3500.pa03.view;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.Cell;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enums.HitStatus;
import cs3500.pa03.model.enums.ShipType;
import cs3500.pa04.enums.Direction;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BattleSalvoViewTest {
  BattleSalvoView view;
  private Appendable appendable;

  @BeforeEach
  public void setUp() {
    appendable = new StringBuilder();
  }


  /**
   * Tests the displayAnything() method
   */
  @Test
  public void testDisplayAnything()  {
    String test = "testing";
    view = new BattleSalvoView(new StringBuilder(), new StringReader(test));

    //empty to begin with
    assertEquals("", view.append.toString());

    view.displayAnything("hello");
    assertEquals("hello\n", view.append.toString());


  }


  /**
   * Tests the displayBoard method
   */
  @Test
  public void testDisplayBoard() {
    //smaller input
    Cell[][] board = new Cell[2][2];
    board[0][0] = new Cell(new Ship(ShipType.SUBMARINE, Direction.HORIZONTAL));
    board[0][1] = new Cell(new Ship(ShipType.SUBMARINE, Direction.HORIZONTAL));
    board[0][1].setHitStatus(true);
    board[1][0] = new Cell();
    board[1][0].setHitStatus(true);
    board[1][1] = new Cell();

    String boardDisp =
            """
            --------------------------------------
            Your Board:
            --------------------------------------
            """;
    String board2 = boardDisp + "X " + "H \n" + "M " + "- \n";
    view = new BattleSalvoView(new StringBuilder(), new StringReader("hello"));
    view.displayBoard(board);
    assertEquals(board2, view.append.toString());
  }

  /**
   * Tests displayOpBoard() method
   */
  @Test
  public void testDisplayOpBoard() {
    HitStatus[][] board = new HitStatus[2][2];
    board[0][0] = HitStatus.HIT;
    board[0][1] = HitStatus.HIT;
    board[1][0] = HitStatus.MISS;
    board[1][1] = HitStatus.NONE;

    String boardDisp =
        """
        --------------------------------------
        Opponent's Board Data:
        --------------------------------------
        """;
    String board2 = boardDisp + "H " + "H \n" + "M " + "- \n";
    view = new BattleSalvoView(new StringBuilder(), new StringReader("hello"));
    view.displayOpponentBoard(board);
    assertEquals(board2, view.append.toString());
  }

  /**
   * Tests a failing case for the displayBoard
   */
  @Test
  public void testFailingAppendable1() {
    BattleSalvoView view1 = new BattleSalvoView(new FailingAppendable(), new StringReader(""));
    assertThrows(
        RuntimeException.class,
        () -> view1.displayBoard(new Cell[2][2]));
  }

  /**
   * Tests a failing case for the displayOppBoard
   */
  @Test
  public void testFailingAppendable2() {
    BattleSalvoView view1 = new BattleSalvoView(new FailingAppendable(), new StringReader(""));
    assertThrows(
        RuntimeException.class,
        () -> view1.displayOpponentBoard(new HitStatus[2][2]));
  }


  /**
   * test welcome
   */
  @Test
  public void testWelcome() {
    Readable read = new StringReader("6 6");
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        """, appendable.toString());
  }

  /**
   * welcome should fail
   */
  @Test
  public void testWelcomeFail() {
    String input = """
        hello bye
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail1() {
    String input = """
        4 4
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail4() {
    String input = """
        16 16
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * tests should fail
   */
  @Test
  public void testWelcomeFail5() {
    String input = """
        6 4
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail6() {
    String input = """
        6 17
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail7() {
    String input = """
        4 6
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail8() {
    String input = """
        24 6
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail2() {
    String input = """
        4
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * test should fail
   */
  @Test
  public void testWelcomeFail3() {
    String input = """
        -1 -1
        6 6
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.welcome();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ----------------------------------------------------
        Uh oh! You've entered invalid numbers.\s
         Please remember to enter in two numbers within the range of (6, 15),\s
         inclusive to set the dimensions of your game.
         """, appendable.toString());
  }

  /**
   * fleet selection should fail
   */
  @Test
  public void testFleetSelectionError() {
    String input = """
        2 2 2 2
        1 1 1 1
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getFleet(6);

    assertEquals("""
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]\s
        Remember, your fleet may not exceed size 6
        Uh oh! Invalid fleet numbers\s
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]\s
        Remember, your fleet may not exceed size 6
        """, appendable.toString());
  }

  /**
   * fleet selection error
   */
  @Test
  public void testFleetSelectionError1() {
    String input = """
        2 2 2 0
        1 1 1 1
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getFleet(6);

    assertEquals("""
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]\s
        Remember, your fleet may not exceed size 6
        Uh oh! Invalid fleet numbers\s
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]\s
        Remember, your fleet may not exceed size 6
        """, appendable.toString());
  }

  /**
   * fleet selection error
   */
  @Test
  public void testFleetSelectionError2() {
    String input = """
        2 2 2 -1
        1 1 1 1
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getFleet(6);

    assertEquals("""
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]\s
        Remember, your fleet may not exceed size 6
        Uh oh! Invalid fleet numbers\s
        """, appendable.toString());
  }

  /**
   * enter shots test
   */
  @Test
  public void testEnterShots() {
    String input = """
        1 1
        0 0
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getShots(2, 2, 2);

    assertEquals("Please enter 2 shots \n", appendable.toString());
  }

  /**
   * enter shots error
   */
  @Test
  public void testEnterShotsError() {
    String input = """
        1 1
        3 3
        0 0
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getShots(2, 2, 2);

    assertEquals("""
        Please enter 2 shots\s
        Your shot 1 was invalid. Please enter another one.\s
        Your width max is 1 and your height max is 1
        """, appendable.toString());
  }

  /**
   * enter shots error
   */
  @Test
  public void testEnterShotsError1() {
    String input = """
        1 1
        3 1
        0 0
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getShots(2, 2, 2);

    assertEquals("""
        Please enter 2 shots\s
        Your shot 1 was invalid. Please enter another one.\s
        Your width max is 1 and your height max is 1
        """, appendable.toString());
  }

  /**
   * enter shots error
   */
  @Test
  public void testEnterShotsError2() {
    String input = """
        1 1
        1 3
        0 0
        """;
    Readable read = new StringReader(input);
    View view = new BattleSalvoView(appendable, read);

    view.getShots(2, 2, 2);

    assertEquals("""
        Please enter 2 shots\s
        Your shot 1 was invalid. Please enter another one.\s
        Your width max is 1 and your height max is 1
        """, appendable.toString());
  }


}
package cs3500.pa03.view;


/**
 * Appendable that fails for testing purposes
 */
public class FailingAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) {
    throw new RuntimeException("Append operation failed.");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) {
    throw new RuntimeException("Append operation failed.");
  }

  @Override
  public Appendable append(char c) {
    throw new RuntimeException("Append operation failed.");
  }
}

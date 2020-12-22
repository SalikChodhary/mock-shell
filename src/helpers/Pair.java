package helpers;

/**
 * Class allows the creation of immutable pairs, in the form of (object, object)
 * 
 * @param <S> Type of first object in pair
 * @param <T> Type of second object in pair
 */
public class Pair<S, T> {
  /**
   * First item in pair
   */
  public final S first;
  /**
   * Second item in pair
   */
  public final T second;

  /**
   * Constructor for creating pair, values cannot be changed after creation
   * 
   * @param first - first object in pair, i.e pair.first
   * @param second - second object in pair, i.e. pair.second
   */
  public Pair(S first, T second) {
    this.first = first;
    this.second = second;
  }

}

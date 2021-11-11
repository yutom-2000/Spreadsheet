package data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.ConcatVisitor;
import edu.cs3500.spreadsheets.model.cell.datavisitor.EvaluateVisitor;
import edu.cs3500.spreadsheets.model.cell.datavisitor.FirstLessThanVisitor;
import edu.cs3500.spreadsheets.model.cell.datavisitor.ProductVisitor;
import edu.cs3500.spreadsheets.model.cell.datavisitor.SumVisitor;
import edu.cs3500.spreadsheets.model.cell.function.Concat;
import edu.cs3500.spreadsheets.model.cell.function.FirstLessThan;
import edu.cs3500.spreadsheets.model.cell.function.Product;
import edu.cs3500.spreadsheets.model.cell.function.Sum;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testing class for Data classes.
 */
public class DataTest {
  private Data num;
  private Data word;
  private Data tf;
  private Data ref12;
  private HashMap<Coord, Data> cells;

  /**
   * Private void method to instantiate default values for testing.
   */
  private void initializeValues() {
    this.num = new NumberValue(1);
    this.word = new StringValue("abc");
    this.tf = new BooleanValue(true);
    this.ref12 = new Reference(new ArrayList<>(Arrays.asList(new Coord(1, 2))));
    this.cells = new HashMap<>();
    cells.put(new Coord(1, 1), num);
    cells.put(new Coord(1, 2), word);
    cells.put(new Coord(1, 3), tf);
    cells.put(new Coord(1, 4), ref12);
  }


  @Test
  public void testEquality() {
    assertEquals(new BooleanValue(true), new BooleanValue(true));
    assertNotEquals(new BooleanValue(true), new BooleanValue(false));
    assertEquals(new NumberValue(1.00001), new NumberValue(1.00002));
    assertNotEquals(new NumberValue(1.001), new NumberValue(1.0001));
    assertEquals(new StringValue("a"), new StringValue("a"));
    assertNotEquals(new StringValue("a"), new StringValue("b"));
  }

  @Test
  public void testSum() {
    Data one = new NumberValue(1);
    Data two = new NumberValue(2);
    Data three = new NumberValue(3);
    Data word = new StringValue("abc");
    Data tf = new BooleanValue(true);
    ArrayList<Data> args1 = new ArrayList<>(Arrays.asList(one, two));
    Data sum2Args = new Sum(args1);
    assertEquals(new NumberValue(3), sum2Args.accept(new SumVisitor(), null));
    args1.add(three);
    assertEquals(new NumberValue(6), sum2Args.accept(new SumVisitor(), null));
    args1.add(word);
    assertEquals(new NumberValue(6), sum2Args.accept(new SumVisitor(), null));
    args1.add(tf);
    assertEquals(new NumberValue(6), sum2Args.accept(new SumVisitor(), null));
  }

  @Test
  public void testEvaluate() {
    Data one = new NumberValue(1);
    Data two = new NumberValue(2);
    Data three = new NumberValue(3);
    Data word = new StringValue("abc");
    Data tf = new BooleanValue(true);
    Data ref41 = new Reference(new ArrayList<>(Arrays.asList(new Coord(4, 1))));
    Data ref11 = new Reference(new ArrayList<>(Arrays.asList(new Coord(1, 1))));
    HashMap<Coord, Data> cells = new HashMap<>();
    cells.put(new Coord(1, 1), one);
    cells.put(new Coord(1, 2), two);
    cells.put(new Coord(2, 1), three);
    cells.put(new Coord(2, 2), word);
    cells.put(new Coord(3, 1), tf);
    cells.put(new Coord(3, 2), ref41);
    cells.put(new Coord(4, 1), ref11);

    assertEquals(new NumberValue(1), one.accept(new EvaluateVisitor(), cells));
    assertEquals(new NumberValue(2), two.accept(new EvaluateVisitor(), cells));
    assertEquals(new NumberValue(3), three.accept(new EvaluateVisitor(), cells));
    assertEquals(new BooleanValue(true), tf.accept(new EvaluateVisitor(), cells));
    assertEquals(new StringValue("abc"), word.accept(new EvaluateVisitor(), cells));
    assertEquals(new NumberValue(1), ref11.accept(new EvaluateVisitor(), cells));
    assertEquals(new NumberValue(1), ref41.accept(new EvaluateVisitor(), cells));
    assertEquals(new BooleanValue(true), new FirstLessThan(one, two)
            .accept(new EvaluateVisitor(), cells));
  }

  @Test(expected = IllegalArgumentException.class) //TODO add test case for cyclical reference
  public void testEvaluateExceptions() {
    Data num = new NumberValue(1);
    Data word = new StringValue("abc");
    Data tf = new BooleanValue(true);
    Data ref12 = new Reference(new ArrayList<>(Arrays.asList(new Coord(1, 2))));
    HashMap<Coord, Data> cells = new HashMap<>();
    cells.put(new Coord(1, 1), num);
    cells.put(new Coord(1, 2), word);
    cells.put(new Coord(1, 3), tf);
    cells.put(new Coord(1, 4), ref12);
    new FirstLessThan(num, word).accept(new EvaluateVisitor(), cells);
    new FirstLessThan(num, tf).accept(new EvaluateVisitor(), cells);
    new FirstLessThan(word, tf).accept(new EvaluateVisitor(), cells);
  }

  @Test
  public void testConcat() {
    this.initializeValues();
    assertEquals(new StringValue(""), this.num.accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue(""), this.tf.accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue("abc"), this.word.accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue("abcabc"), new Concat(
            new ArrayList(Arrays.asList(word, word))
    ).accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue("abcabc"), new Concat(
            new ArrayList(Arrays.asList(tf, num, word, ref12))
    ).accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue("abc"), new Concat(
            new ArrayList(Arrays.asList(tf, num, word,
                    new Reference(new ArrayList<>(Arrays.asList(new Coord(1, 1))))))
    ).accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue(""), new Sum(new ArrayList<>())
            .accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue(""), new Product(new ArrayList())
            .accept(new ConcatVisitor(), cells));
    assertEquals(new StringValue(""), new FirstLessThan(null, null)
            .accept(new ConcatVisitor(), cells));
  }

  @Test
  public void testFirstLessThanVisitor() {
    this.initializeValues();
    assertEquals(new NumberValue(1), num.accept(new FirstLessThanVisitor(), cells));
  }

  @Test
  public void testProductVisitor() {
    this.initializeValues();
    assertEquals(new NumberValue(0), tf.accept(new ProductVisitor(), cells));
    assertEquals(new NumberValue(0), word.accept(new ProductVisitor(), cells));
    assertEquals(new NumberValue(1), num.accept(new ProductVisitor(), cells));
    assertEquals(new NumberValue(2),
            new Sum(new ArrayList<>(Arrays.asList(new NumberValue(1), new NumberValue(1))))
                    .accept(new ProductVisitor(), cells));
    assertEquals(new NumberValue(2),
            new Product(new ArrayList<>(Arrays.asList(new NumberValue(1),
                    new NumberValue(2))))
                    .accept(new ProductVisitor(), cells));
    assertEquals(new NumberValue(1), new Product(Arrays.asList(tf, word))
            .accept(new EvaluateVisitor(), cells));
    assertEquals(new NumberValue(2.0),
            new Product(Arrays.asList(tf, word, new NumberValue(2)))
                    .accept(new EvaluateVisitor(), cells));
  }

  @Test
  public void testToString() {
    this.initializeValues();
    assertEquals("\"abc\"", this.word.toString());
    assertEquals("\"ab\\\"c\"", new StringValue("ab\"c").toString());
    assertEquals("\"ab\\\\c\"", new StringValue("ab\\c").toString());
    ArrayList<Data> args = new ArrayList(Arrays.asList(new NumberValue(1)
            , new NumberValue(2)));
    assertEquals("=(SUM 1.0 2.0)",
            new Sum(args).toString());
    assertEquals("=(PRODUCT 1.0 2.0)",
            new Product(args).toString());
    assertEquals("=(< 1.0 2.0)",
            new FirstLessThan(new NumberValue(1.0), new NumberValue(2.0)).toString());
    assertEquals("=(CONCAT \"abc\" \"def\")",
            new Concat(new ArrayList(Arrays.asList(new StringValue("abc")
                    , new StringValue("def")))).toString());
    assertEquals("A1", new Coord(1, 1).toString());
  }

  @Test
  public void testFirstLessThanEquals() {
    this.initializeValues();
    assertEquals(true, new FirstLessThan(new NumberValue(1), new NumberValue(2)).equals(
            new FirstLessThan(new NumberValue(1), new NumberValue(2))));
  }



}

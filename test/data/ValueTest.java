//package Data;
//
//import org.junit.Test;
//
//import edu.cs3500.spreadsheets.model.cell.dataVisitor.ConcatVisitor;
//import edu.cs3500.spreadsheets.model.cell.dataVisitor.EvaluateVisitor;
//import edu.cs3500.spreadsheets.model.cell.dataVisitor.FirstLessThanVisitor;
//import edu.cs3500.spreadsheets.model.cell.dataVisitor.ProductVisitor;
//import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
//import edu.cs3500.spreadsheets.model.cell.value.NumberValue;
//import edu.cs3500.spreadsheets.model.cell.value.StringValue;
//import edu.cs3500.spreadsheets.model.cell.value.Value;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
///**
// * Tests for operations on Values
// */
//public class ValueTest {
//
//
//  //Test Block for NumberValue
//  @Test
//  public void testAdd() {
//    assertEquals(new NumberValue(4.0), new NumberValue(1.0).add(new NumberValue(3.0)));
//  }
//
//  @Test
//  public void testConcat() {
//    Value n = new NumberValue(1.0);
//    Value b = new BooleanValue(true);
//    Value s = new StringValue("a");
//    assertEquals(new StringValue(""), n.accept(new ConcatVisitor()));
//    assertEquals(new StringValue(""), b.accept(new ConcatVisitor()));
//    assertEquals(new StringValue("a"), s.accept(new ConcatVisitor()));
//  }
//
//  @Test
//  public void testEvaluate() {
//    Value n = new NumberValue(1.0);
//    Value b = new BooleanValue(true);
//    Value s = new StringValue("a");
//    assertEquals(new NumberValue(1.0), n.accept(new EvaluateVisitor()));
//    assertEquals(new BooleanValue(true), b.accept(new EvaluateVisitor()));
//    assertEquals(new StringValue("a"), s.accept(new EvaluateVisitor()));
//  }
//
//  @Test (expected = IllegalArgumentException.class)
//  public void testFirstLessThanExceptions() {
//    Value n = new NumberValue(1.0);
//    Value b = new BooleanValue(true);
//    Value s = new StringValue("a");
//    n.accept(new FirstLessThanVisitor());
//    b.accept(new FirstLessThanVisitor());
//    s.accept(new FirstLessThanVisitor());
//  }
//
//  @Test
//  public void testProductVisitor() {
//    Value n = new NumberValue(1.0);
//    Value b = new BooleanValue(true);
//    Value s = new StringValue("a");
//    assertEquals(new NumberValue(1.0), n.accept(new ProductVisitor()));
//    assertEquals(new NumberValue(0.0), b.accept(new ProductVisitor()));
//    assertEquals(new NumberValue(0.0), s.accept(new ProductVisitor()));
//  }
//
//}

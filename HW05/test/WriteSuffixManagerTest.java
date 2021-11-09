import org.junit.Before;
import org.junit.Test;

import controller.utils.IManager;
import controller.utils.IWriter;
import controller.utils.WriteSuffixManager;

import static org.junit.Assert.fail;

/**
 * To test WriteSuffixManager class.
 */
public class WriteSuffixManagerTest {

  IManager<IWriter> manager;

  @Before
  public void setUp() {
    manager = new WriteSuffixManager();
  }

  @Test
  public void testNormalLoad() {
    try {
      manager.provide("ppm");
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testEx1() {
    manager.provide("png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEx2() {
    manager.provide(null);
  }
}
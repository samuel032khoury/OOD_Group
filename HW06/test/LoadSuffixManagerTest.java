import org.junit.Before;
import org.junit.Test;

import controller.utils.ILoader;
import controller.utils.IManager;
import controller.utils.LoadSuffixManager;

import static org.junit.Assert.fail;

/**
 * To test LoadSuffixManager class.
 */
public class LoadSuffixManagerTest {

  IManager<ILoader> manager;

  @Before
  public void setUp() {
    manager = new LoadSuffixManager();
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
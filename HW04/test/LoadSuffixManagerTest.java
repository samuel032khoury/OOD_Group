import org.junit.Before;
import org.junit.Test;

import controller.utils.ILoader;
import controller.utils.IManager;
import controller.utils.LoadSuffixManager;

import static org.junit.Assert.*;

public class LoadSuffixManagerTest {

  IManager<ILoader> manager;

  @Before
  public void setUp() throws Exception {
    manager = new LoadSuffixManager();
  }

  @Test
  public void testNormalLoad() {
    try {
      ILoader loader = manager.provide("ppm");
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testEx1() {
    ILoader loader = manager.provide("png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEx2() {
    ILoader loader = manager.provide(null);
  }
}
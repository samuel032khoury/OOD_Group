import org.junit.Before;
import org.junit.Test;

import controller.utils.ILoader;
import controller.utils.IManager;
import controller.utils.IWriter;
import controller.utils.LoadSuffixManager;
import controller.utils.WriteSuffixManager;

import static org.junit.Assert.fail;

public class WriteSuffixManagerTest {

  IManager<IWriter> manager;

  @Before
  public void setUp() throws Exception {
    manager = new WriteSuffixManager();
  }

  @Test
  public void testNormalLoad() {
    try {
      IWriter writer = manager.provide("ppm");
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testEx1() {
    IWriter writer = manager.provide("png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEx2() {
    IWriter writer = manager.provide(null);
  }
}
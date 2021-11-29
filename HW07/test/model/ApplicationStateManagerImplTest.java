package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests all the public method in {@link ApplicationStateManagerImpl}.
 */
public class ApplicationStateManagerImplTest extends AbstractModelTest {

  private ApplicationStateManager manager;

  @Before
  public void setUp() {
    super.setUp();
    this.manager = new ApplicationStateManagerImpl();
    this.manager.store("hello", img);
    this.manager.store("key", img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fetchImageExceptionInvalidImageName() {
    manager.fetchImage("invalidName");
  }

  @Test(expected = IllegalArgumentException.class)
  public void storeImageExceptionEmptyFileName() {
    manager.store("", img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void storeImageExceptionNullImage() {
    manager.store("hello1", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void storeImageExceptionEmptyFileNameAndNullImage() {
    manager.store("", null);
  }

  @Test
  public void testFetchImage() {
    assertEquals(manager.fetchImage("key").getImageData(), img.getImageData());
    assertEquals(manager.fetchImage("hello").getImageData(), img.getImageData());
  }
}
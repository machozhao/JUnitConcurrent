package com.ibm.lbs.framework.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testMult() {
    long a = 56756;
    long b = 125435;
    Assert.assertTrue(( a + b ) > 0);
  }

  @Test
  public void testConcurrent() throws Exception {
    // "testMult" is name of test method, this parameter means which test method will be call each thread.
    // 800 means total of threads
    // 1000000 means total of call in each thread.
    TestCaseRunnable.fireUp(this, "testMult", 800, 1000000);
  }
}

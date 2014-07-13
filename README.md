JUnitConcurrent
===============

Test tool for Junit. It can run test case in conccurent mode, and you can get some load performance, such as TPS, avg response time, etc.

For performance tunning and optimizing, we need to run test case in conccurent mode, and hope to get performance data. This tiny code will help you do these.

How to use:
============================================================================
  1. Write your testcase. 
     For example:
     
     @Test
     public void testMult() {
       long a = 56756;
       long b = 125435;
       Assert.assertTrue(( a + b ) > 0);
     }


  2. Write and extends a testcase, and add the following lines:
    @Test
    public void testConcurrent() throws Exception {
      // "testMult" is name of test method, this parameter means which test method will be call each thread.
      // 800 means total of threads
      // 1000000 means total of call in each thread.
      // TestCaseRunnable is our tool class for concurrent testing
      TestCaseRunnable.fireUp(this, "testMult", 800, 1000000);
    }

  3. Run this testConcurrent() method in Junit runner, and you will get these output:
     success: [1817741tps, 12747835], fail: [0tps, 0], total: [1817743tps, 12747838], min: [0ms], max: [54ms], avg: [0ms], last: [0ms]
===============================================================================

Zhao Dong Lu
July, 2014

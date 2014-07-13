package com.ibm.lbs.framework.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TestCaseRunnable implements Runnable {

  private int totalOfCall = 100;

  private Object testObject;

  private TestCasePerfMonitor monitor;

  private String testMethodName;

  public TestCaseRunnable() {
    super();
  }

  public TestCaseRunnable(Object testObject, String testMethodName, int totalOfCall, TestCasePerfMonitor monitor) {
    super();
    this.testObject = testObject;
    this.testMethodName = testMethodName;
    this.totalOfCall = totalOfCall;
    this.monitor = monitor;
  }

  public int getTotalOfCall() {
    return totalOfCall;
  }

  public void setTotalOfCall(int totalOfCall) {
    this.totalOfCall = totalOfCall;
  }

  public Object getTestObject() {
    return testObject;
  }

  public void setTestObject(Object testObject) {
    this.testObject = testObject;
  }

  public TestCasePerfMonitor getMonitor() {
    return monitor;
  }

  public void setMonitor(TestCasePerfMonitor monitor) {
    this.monitor = monitor;
  }

  public String getTestMethodName() {
    return testMethodName;
  }

  public void setTestMethodName(String testMethodName) {
    this.testMethodName = testMethodName;
  }

  public void run() {
    Class clazz = this.testObject.getClass();
    Method testMethod = null;
    try {
      testMethod = clazz.getMethod(this.testMethodName);
    } catch (SecurityException e1) {
      e1.printStackTrace();
    } catch (NoSuchMethodException e1) {
      e1.printStackTrace();
    }
    for (int i = 0; i < totalOfCall; i++) {
      long b = System.currentTimeMillis();
      try {
        testMethod.invoke(testObject);
        monitor.setElapse(System.currentTimeMillis() - b);
        monitor.countSuccess();
      } catch (Exception e) {
        monitor.setElapse(System.currentTimeMillis() - b);
        monitor.countFail();
        e.printStackTrace();
      }
    }
  }

  public static void fireUp(Object testObject, String testMethodName, int totalThread, int totalOfCallInEachThread) throws InterruptedException {
    TestCasePerfMonitor monitor = new TestCasePerfMonitor();
    Thread monitorThread = new Thread(monitor, monitor.getClass().getSimpleName());
    monitorThread.start();
  
    List<Thread> threads = new ArrayList<Thread>();
  
    for (int i = 0; i < totalThread; i++) {
      Thread thread = new Thread(new TestCaseRunnable(testObject, testMethodName, totalOfCallInEachThread, monitor), testMethodName + "#" + (i + 1));
      threads.add(thread);
    }
  
    monitor.begin();
    // Start all of Thread
    for (Thread thread : threads) {
      thread.start();
    }
  
    // Wait all of finish
    for (Thread thread : threads) {
      thread.join();
    }
  }
}
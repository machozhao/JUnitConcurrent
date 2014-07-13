package com.ibm.lbs.framework.junit;

public final class TestCasePerfMonitor implements Runnable {

  private long beginTime = 0;
  private long endTime = 0;

  private long totalSuccess = 0;
  private long totalFail = 0;
  private long minElapse = Long.MAX_VALUE;
  private long maxElapse = Long.MIN_VALUE;
  private long lastElapse = 0;
  private long totalElapse;

  public long getLastElapse() {
    return lastElapse;
  }

  public long getMinElapse() {
    return minElapse;
  }

  public long getMaxElapse() {
    return maxElapse;
  }

  public long getAvgElapse() {
    if (this.getTotal() == 0) {
       return 0;
    }
    return totalElapse / this.getTotal();
  }

  public long getTotal() {
    return this.totalSuccess + this.totalFail;
  }

  public long getTotalSuccess() {
    return totalSuccess;
  }

  public long getTotalFail() {
    return totalFail;
  }

  public synchronized void countSuccess() {
    this.totalSuccess++;
  }

  public synchronized void countFail() {
    this.totalFail++;
  }

  public void begin() {
    this.beginTime = System.currentTimeMillis();
  }

  public void end() {
    this.endTime = System.currentTimeMillis();
  }

  private long getElipseTime() {
    return System.currentTimeMillis() - this.beginTime;
  }

  public String toString() {
    return String.format("success: [%stps, %s], fail: [%stps, %s], total: [%stps, %s], min: [%sms], max: [%sms], avg: [%sms], last: [%sms]", this.getTotalSuccess() * 1000 / this.getElipseTime(),
        this.getTotalSuccess(), this.getTotalFail() * 1000 / this.getElipseTime(), this.getTotalFail(), this.getTotal() * 1000 / this.getElipseTime(), this.getTotal(), this.getMinElapse(), this.getMaxElapse(), this.getAvgElapse(), this.getLastElapse());
  }

  public void run() {
    while (true) {
      if (this.beginTime == 0) {
        System.out.println("Not start");
      } else {
        System.out.println(this.toString());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
    }
  }

  public synchronized void setElapse(long elapse) {
    if (elapse < this.minElapse) {
       this.minElapse = elapse;
    }
    if (elapse > this.maxElapse) {
      this.maxElapse = elapse;
    }
    this.totalElapse += elapse;
    this.lastElapse = elapse;
  }

}
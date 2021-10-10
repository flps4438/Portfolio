class Work {

  private int id;
  private int cpuBurst;
  private int arrivalTime;
  private int priority;
  private int leftTime;
  private int finishTime;
  private float reponseRatio;

  public Work ( int id, int cpuBurst, int arrivalTime, int priority ) {
    this.id = id;
    this.cpuBurst = cpuBurst;
    this.leftTime = cpuBurst;
    this.arrivalTime = arrivalTime;
    this.priority = priority;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) { this.id = id; }

  public int getCpuBurst() {
    return cpuBurst;
  }

  public void setCpuBurst(int cpuBurst) { this.cpuBurst = cpuBurst; }

  public int getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getLeftTime() {
    return leftTime;
  }

  public void setLeftTime(int leftTime) {
    this.leftTime = leftTime;
  }

  public void afterOneSecond() { this.leftTime --; }

  public int getEndTime() {
    return finishTime;
  }

  public void setEndTime(int finishTime) {
    this.finishTime = finishTime;
  }

  public boolean isDo() {
    return leftTime != cpuBurst;
  }

  public boolean isFinish() {
    return leftTime == 0;
  }

  public int getTurnaroundTime() {
    return finishTime - arrivalTime;
  }

  public int getWaitingTime() {
    return getTurnaroundTime() - cpuBurst;
  }
  
  public float getReponseRatio( int nowTime ) {

    if ( ! isFinish() )

      return ( (float) ( nowTime - arrivalTime ) + cpuBurst ) / cpuBurst;

    else return 0;
  }
}
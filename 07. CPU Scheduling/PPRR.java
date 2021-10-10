import java.util.Vector;

public class PPRR extends Schedule {

    final int timeSlice;

    public PPRR(String fileName, Vector<Work> workList, Vector<Work> arrivalWorkList, int timeSlice) {

        super(fileName, "PPRR", workList, arrivalWorkList);
        this.timeSlice = timeSlice;
    }

    void run() {

        time = 0;

        int leftTime = timeSlice;

        Work nowWork = null;

        Vector<Work> waitingQuene = new Vector<>();

        while ( ! arrivalWorkList.isEmpty() || nowWork != null ) {

            while (!arrivalWorkList.isEmpty() && arrivalWorkList.elementAt(0).getArrivalTime() <= time) {

                waitingQuene.add(arrivalWorkList.elementAt(0));
                arrivalWorkList.remove(0);
            }

            waitingQuene.sort((o1, o2) -> {

                int compare = Integer.compare(o1.getPriority(), o2.getPriority());

                if ( compare == 0 ) {

                    if ( o1.isDo() || o2.isDo() ) return compare;

                    compare = Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());

                    if (compare == 0)

                        return Integer.compare(o1.getId(), o2.getId());
                    else return compare;
                } else return compare;
            });

            if ( nowWork != null ) {

                nowWork.afterOneSecond();
                leftTime --;

                if ( nowWork.isFinish() ) {

                    nowWork.setEndTime(time);

                    finishWorkList.add(nowWork);

                    nowWork = null;
                }
                else if ( ! waitingQuene.isEmpty() && nowWork.getPriority() == waitingQuene.elementAt(0).getPriority() ) {

                    if ( leftTime == 0 ) {

                        waitingQuene.add( nowWork );

                        nowWork = null;
                    }
                }
                else if ( ! waitingQuene.isEmpty() && nowWork.getPriority() > waitingQuene.elementAt(0).getPriority() ) {

                    waitingQuene.add(nowWork);

                    nowWork = null;

                }
            }


            if ( nowWork == null && ! waitingQuene.isEmpty() ) {

                nowWork = waitingQuene.elementAt(0);

                waitingQuene.remove(0);

                leftTime = timeSlice;

                ganttChart.add(nowWork.getId());
            }
            else if ( nowWork != null )

                ganttChart.add( nowWork.getId() );

            else if ( ! arrivalWorkList.isEmpty() )

                ganttChart.add( -1 );

            time ++;
        }

        for ( int i : ganttChart )

            System.out.print( decode(i) );

        System.out.println();

    }
}

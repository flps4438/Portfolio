import java.util.*;

public class RR extends Schedule{

    final int timeSlice;

    public RR (String fileName , Vector<Work> workList, Vector<Work> arrivalWorkList, int timeSlice) {

        super(fileName, "RR", workList, arrivalWorkList);

        this.timeSlice = timeSlice;
    }

    void run() {

        time = 0;

        int leftTime = timeSlice;

        Work nowWork = null;
        Vector<Work> waitingQuene = new Vector<>();

        while ( ! arrivalWorkList.isEmpty() || nowWork != null ) {

            while ( ! arrivalWorkList.isEmpty() && arrivalWorkList.elementAt(0).getArrivalTime() <= time ) {

                waitingQuene.add(arrivalWorkList.elementAt(0));
                arrivalWorkList.remove(0);
            }

            if ( nowWork != null ) {

                leftTime --;
                nowWork.afterOneSecond();

                if ( nowWork.isFinish() ) {

                    nowWork.setEndTime( time );

                    finishWorkList.add( nowWork );

                    nowWork = null;
                }
                else if ( leftTime == 0 ) {

                    waitingQuene.add( nowWork );

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

                ganttChart.add(nowWork.getId());

            else if ( ! arrivalWorkList.isEmpty() )

                ganttChart.add( -1 );

            time ++;
        }

        for ( int i : ganttChart )

            System.out.print( decode(i) );

        System.out.println();

    }
}

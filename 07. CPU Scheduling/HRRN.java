import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class HRRN extends Schedule {

    public HRRN(String fileName, Vector<Work> workList, Vector<Work> arrivalWorkList) {

        super(fileName, "HRRN", workList, arrivalWorkList);
    }

    void run() {

        time = 0;

        Work nowWork = null;

        Vector<Work> waitingQuene = new Vector<>();

        while ( ! arrivalWorkList.isEmpty() || nowWork != null ) {

            while ( ! arrivalWorkList.isEmpty() && arrivalWorkList.elementAt(0).getArrivalTime() <= time ) {

                waitingQuene.add(arrivalWorkList.elementAt(0));
                arrivalWorkList.remove(0);
            }

            waitingQuene.sort((o1, o2) -> {

                int compare = Float.compare( o1.getReponseRatio(time), o2.getReponseRatio(time) );

                if ( compare == 0 ) {

                    compare = Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());

                    if (compare == 0)

                        return Integer.compare(o1.getId(), o2.getId());
                    else return compare;
                } else return - compare;
            });

            if ( nowWork != null ) {

                nowWork.afterOneSecond();

                if ( nowWork.isFinish() ) {

                    nowWork.setEndTime(time);

                    finishWorkList.add(nowWork);

                    nowWork = null;
                }
            }


            if ( nowWork == null && ! waitingQuene.isEmpty() ) {

                nowWork = waitingQuene.elementAt(0);

                waitingQuene.remove(0);

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

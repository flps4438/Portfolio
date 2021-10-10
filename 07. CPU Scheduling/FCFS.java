import java.util.Vector;

public class FCFS extends Schedule {

    public FCFS( String inputFileName, Vector<Work> workList, Vector<Work> arrivalWorkList) {

        super( inputFileName, "FCFS", workList, arrivalWorkList);
    }

    public void run() {

        time = 0;

        Work nowWork = null;


        while ( ! arrivalWorkList.isEmpty() || nowWork != null ) {

            if ( nowWork != null ) {

                nowWork.afterOneSecond();

                if ( nowWork.isFinish() ) {

                    nowWork.setEndTime( time );

                    finishWorkList.add( nowWork );

                    nowWork = null;
                }
                else
                    ganttChart.add(nowWork.getId());
            }

            if ( ! arrivalWorkList.isEmpty() && nowWork == null ) {

                if ( arrivalWorkList.elementAt(0).getArrivalTime() <= time ) {

                    nowWork = arrivalWorkList.elementAt(0);
                    arrivalWorkList.remove(0);

                    ganttChart.add(nowWork.getId());
                }
                else {

                    ganttChart.add(-1); // -1 mean "-"

                }
            }

            time ++;
        }

        for ( int i : ganttChart )

          System.out.print( decode(i) );

        System.out.println();

    }
}

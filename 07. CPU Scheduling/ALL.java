import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Vector;

public class ALL extends Schedule {

    int timeSlice;

    private FCFS fcfs;
    private RR rr;
    private SRTF srtf;
    private PPRR pprr;
    private HRRN hrrn;

    public ALL( String fileName, Vector<Work> workList, Vector<Work> arrivalWorkList, int timeSlice ) {

        super(fileName, "All", workList, arrivalWorkList);

        this.timeSlice = timeSlice;
    }

    @Override
    void run() {

        Vector<Work> fcfsArrivalWorkList = new Vector<>();
        Vector<Work> rrArrivalWorkList = new Vector<>();
        Vector<Work> srtfArrivalWorkList = new Vector<>();
        Vector<Work> pprrArrivalWorkList = new Vector<>();
        Vector<Work> hrrnArrivalWorkList = new Vector<>();

        for ( Work w : arrivalWorkList ) {

            Work work = new Work( w.getId(), w.getCpuBurst(), w.getArrivalTime(), w.getPriority() );

            fcfsArrivalWorkList.add(work);
        }

        for ( Work w : arrivalWorkList ) {

            Work work = new Work( w.getId(), w.getCpuBurst(), w.getArrivalTime(), w.getPriority() );

            rrArrivalWorkList.add(work);
        }

        for ( Work w : arrivalWorkList ) {

            Work work = new Work( w.getId(), w.getCpuBurst(), w.getArrivalTime(), w.getPriority() );

            srtfArrivalWorkList.add(work);
        }

        for ( Work w : arrivalWorkList ) {

            Work work = new Work( w.getId(), w.getCpuBurst(), w.getArrivalTime(), w.getPriority() );

            pprrArrivalWorkList.add(work);
        }

        for ( Work w : arrivalWorkList ) {

            Work work = new Work( w.getId(), w.getCpuBurst(), w.getArrivalTime(), w.getPriority() );

            hrrnArrivalWorkList.add(work);
        }

        fcfs = new FCFS( null, workList, fcfsArrivalWorkList );
        rr = new RR( null, workList, rrArrivalWorkList, timeSlice );
        srtf = new SRTF( null, workList, srtfArrivalWorkList );
        pprr = new PPRR( null, workList, pprrArrivalWorkList, timeSlice);
        hrrn = new HRRN( null, workList, hrrnArrivalWorkList );


        fcfs.run();
        rr.run();
        srtf.run();
        pprr.run();
        hrrn.run();
    }

    @Override
    void writeFile() {

        try {

            FileWriter writer = new FileWriter(Objects.requireNonNull(Main.class.getResource("")).getPath()
                                                + outputFileName + ".txt");

            writer.write(mode + "\n");

            writer.write("==        FCFS==\n");
            writer.write(fcfs.getGanttChart() + "\n" );
            writer.write("==          RR==\n");
            writer.write(rr.getGanttChart() + "\n" );
            writer.write("==        SRTF==\n");
            writer.write(srtf.getGanttChart() + "\n" );
            writer.write("==        PPRR==\n");
            writer.write(pprr.getGanttChart() + "\n" );
            writer.write("==        HRRN==\n");
            writer.write(hrrn.getGanttChart() + "\n" );

            fcfs.startWriteAll();
            rr.startWriteAll();
            srtf.startWriteAll();
            pprr.startWriteAll();
            hrrn.startWriteAll();

            writer.write("===========================================================\n" +
                    "\n" +
                    "waiting\n" +
                    "ID\tFCFS\tRR\tSRTF\tPPRR\tHRRN\n" +
                    "===========================================================\n");

            workList.sort( Comparator.comparingInt( Work::getId ) );

            for ( int i = 0 ; i < workList.size() ; i ++ ) {

                writer.write( workList.elementAt(i).getId() + "\t" );

                writer.write( fcfs.getWorkAt(i).getWaitingTime() + "\t" );
                writer.write( rr.getWorkAt(i).getWaitingTime() + "\t"  );
                writer.write( srtf.getWorkAt(i).getWaitingTime() + "\t"  );
                writer.write( pprr.getWorkAt(i).getWaitingTime() + "\t"  );
                writer.write( hrrn.getWorkAt(i).getWaitingTime() + "\n" );
            }

            writer.write("===========================================================\n" +
                    "\n" +
                    "Turnaround Time\n" +
                    "ID\tFCFS\tRR\tSRTF\tPPRR\tHRRN\n" +
                    "===========================================================\n");

            for ( int i = 0 ; i < workList.size() ; i ++ ) {

                writer.write( workList.elementAt(i).getId() + "\t" );
                writer.write( fcfs.getWorkAt(i).getTurnaroundTime() + "\t"  );
                writer.write( rr.getWorkAt(i).getTurnaroundTime() + "\t"  );
                writer.write( srtf.getWorkAt(i).getTurnaroundTime() + "\t"  );
                writer.write( pprr.getWorkAt(i).getTurnaroundTime() + "\t"  );
                writer.write( hrrn.getWorkAt(i).getTurnaroundTime() + "\n"  );
            }

            writer.write("===========================================================\n\n");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

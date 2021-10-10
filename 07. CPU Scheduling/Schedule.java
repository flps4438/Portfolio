import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Vector;

public abstract class Schedule {

    String mode;
    String inputFileName;
    String outputFileName;

    Vector<Work> workList;
    Vector<Work> arrivalWorkList;
    Vector<Work> finishWorkList;

    Vector<Integer> ganttChart;

    protected int time;

    public Schedule(String fileName, String mode, Vector<Work> workList, Vector<Work> arrivalWorkList) {

        this.inputFileName = fileName;
        outputFileName = "out_" + inputFileName;

        this.mode = mode;

        this.workList = workList;
        this.arrivalWorkList = arrivalWorkList;

        finishWorkList = new Vector<>();
        ganttChart = new Vector<>();
    }

    abstract void run();

    void writeFile() {

        finishWorkList.sort( Comparator.comparingInt( Work::getId ) );

        try {

            FileWriter writer = new FileWriter(Objects.requireNonNull(Main.class.getResource("")).getPath()
                                                + outputFileName + ".txt" );

            writer.write(mode + "\n");

            for ( int i : ganttChart )

                writer.write( decode(i) );

            writer.write("\n");

            writer.write("===========================================================\n" +
                    "\n" +
                    "waiting\n" +
                    "ID\t" + mode + "\n" +
                    "===========================================================\n");

            for ( Work w : finishWorkList )

                writer.write(w.getId() + "\t" + w.getWaitingTime() + "\n" );

            writer.write("===========================================================\n" +
                    "\n" +
                    "Turnaround Time\n" +
                    "ID\t" + mode + "\n" +
                    "===========================================================\n");

            for ( Work w : finishWorkList )

                writer.write(w.getId() + "\t" + w.getTurnaroundTime() + "\n" );

            writer.close();
        }
        catch ( IOException e ) {

            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getGanttChart() {

        StringBuilder returnString = new StringBuilder();

        for ( int i : ganttChart )

            returnString.append( decode(i) );

        return returnString.toString();
    }

    public void startWriteAll() {

        finishWorkList.sort( Comparator.comparingInt( Work::getId ) );
    }

    public Work getWorkAt( int at ) {

        return finishWorkList.elementAt( at );
    }

    String decode(int in ) {

        if ( in == -1 ) return "-";
        else if ( 0 <= in && in <= 9 ) return String.valueOf( in );
        else return String.valueOf( (char) ('A' + (in - 10)) );
    }
}

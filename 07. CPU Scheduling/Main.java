import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        int mode;
        int time_slice;

        Vector<Work> workList = new Vector<>();
        Vector<Work> arrivalWorkList = new Vector<>();

        Scanner scanner = new Scanner( System.in );

        System.out.println("-------------------------------------------");
        System.out.println("* 1. FCFS (First Come First Serve)        *");
        System.out.println("* 2. RR (Round Robin)                     *");
        System.out.println("* 3. SRTF (Shortest Remaining Time First) *");
        System.out.println("* 4. PPRR (Preemptive Priority + RR)      *");
        System.out.println("* 5. HRRN (Highest Response Ratio Next)   *");
        System.out.println("* 6. ALL                                  *");
        System.out.println("-------------------------------------------");

        String fileName = "";

        FileReader fr = null;

        while ( fileName.equals("") ) {

            System.out.println();
            System.out.print("Enter Inputfile name (EX : input1) : ");

            fileName = scanner.next();

            try {

                fr = new FileReader( Objects.requireNonNull(Main.class.getResource(fileName + ".txt")).getPath());

                if ( ! fr.ready() ) fileName = "";

            } catch ( IOException e ) {

                fileName = "";
            }

            if ( fileName.equals("") ) System.out.println("File not found");
        }

        System.out.println();

        try {

            BufferedReader br = new BufferedReader(fr);

            String inputString = "";

            if ( br.ready() )

                inputString = br.readLine();

            br.readLine();

            Vector<String> inputNumber = new Vector<>();
            String [] inputSplit = inputString.split("( +)|(\t+)");

            for ( String s : inputSplit )

                if ( ! s.equals("") ) inputNumber.add(s);

            mode = Integer.parseInt(inputNumber.elementAt(0));
            time_slice = Integer.parseInt(inputNumber.elementAt(1));

            while ( br.ready() ) {

                inputString = br.readLine();

                if ( ! inputString.equals("") ) {

                    inputNumber.clear();

                    inputSplit = inputString.split("( +)|(\t+)");

                    for (String s : inputSplit)

                        if (!s.equals("")) inputNumber.add(s);

                    int id = Integer.parseInt(inputNumber.elementAt(0));
                    int burst = Integer.parseInt(inputNumber.elementAt(1));
                    int arrival = Integer.parseInt(inputNumber.elementAt(2));
                    int priority = Integer.parseInt(inputNumber.elementAt(3));

                    Work work = new Work(id, burst, arrival, priority);

                    workList.add(work);
                }
            }

            arrivalWorkList.setSize( workList.size() );
            Collections.copy( arrivalWorkList, workList );

            arrivalWorkList.sort((o1, o2) -> {

                int compare = Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());

                if ( compare == 0 )

                    return Integer.compare(o1.getId(), o2.getId());

                else return compare;
            });

            fr.close();

            FCFS fcfs;
            RR rr;
            SRTF srtf;
            PPRR pprr;
            HRRN hrrn;
            ALL all;

            if ( mode == 1 ) {

                fcfs = new FCFS( fileName, workList, arrivalWorkList );

                fcfs.run();

                fcfs.writeFile();
            }
            else if ( mode == 2 ) {

                rr = new RR( fileName, workList, arrivalWorkList, time_slice );

                rr.run();

                rr.writeFile();
            }
            else if ( mode == 3 ) {

                srtf = new SRTF( fileName, workList, arrivalWorkList );

                srtf.run();

                srtf.writeFile();
            }
            else if ( mode == 4 ) {

                pprr = new PPRR( fileName, workList, arrivalWorkList, time_slice);

                pprr.run();

                pprr.writeFile();
            }
            else if ( mode == 5 ) {

                hrrn = new HRRN( fileName, workList, arrivalWorkList );

                hrrn.run();

                hrrn.writeFile();
            }
            else if ( mode == 6 ) {

                all = new ALL( fileName, workList, arrivalWorkList, time_slice );

                all.run();

                all.writeFile();
            }

            fr.close();

        } catch ( IOException e ) {

            e.printStackTrace();

        }
    }
}

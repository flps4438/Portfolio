#include <iostream>
#include <thread>
#include <fstream>
#include <ctime>
#include <vector>
#include <cmath>
#include <sys/wait.h>
#include <sys/mman.h>
#include <unistd.h>
#include <windows.h>

using namespace std;

void * create_shared_memory(size_t size) {
    // Our memory buffer will be readable and writable:
    int protection = PROT_READ | PROT_WRITE;

    // The buffer will be shared (meaning other processes can access it), but
    // anonymous (meaning third-party processes cannot obtain an address for it),
    // so only this process and its children will be able to use it:
    int visibility = MAP_SHARED | MAP_ANONYMOUS;

    // The remaining parameters to `mmap()` are not important for this use case,
    // but the manpage for `mmap` explains their purpose.
    return mmap(NULL, size, protection, visibility, -1, 0);
}

class Calculate {

public:
    explicit Calculate( int array[], int size, string fileName, int mode );
    double mission1();
    double mission2();
    double mission3();
    double mission4();

    void writeFile();

private:

    int *array;
    int size;
    string fileName;
    int mode;
    double cpu_time;

    static void bubble_sort( int array[], int start, int end );
    static void Merge( int temp[], int p, int q, int m);

};

Calculate::Calculate( int array[], int size, string fileName, int mode ) {

    this->array = array;
    this->size = size;
    this->fileName = fileName;
    this->mode = mode;

}

void Calculate::Merge( int *temp, int p, int q, int m ) {

    int n1 = m - p;
    int n2 = q - m + 1;
    /*Let L[0...n1] and R[0...n2] be new arrays
    and copy A[p...m] to L[0...n1]
    and copy A[m+1...q] to R[0...n2] */
    int * L = new int[n1 + 1];
    int * R = new int[n2 + 1];

    for ( int i = 0 ; i < n1 ; i++ )

        L[i] = temp[p+i];

    for ( int i = 0 ; i < n2 ; i++ )

        R[i] = temp[i+m];

    L[n1] = 2147483647;
    R[n2] = 2147483647;

    int i = 0, j = 0;

    for ( int k = p ; k <= q ; k++ ) {

        if ( L[i] <= R[j] ) {

            temp[k] = L[i];
            i++;
        }
        else {

            temp[k] = R[j];
            j++;
        }
    }

}

void Calculate::bubble_sort(int *array, int start, int end) {

    cout << "Start bubble" << endl;

    int tmp = 0;
    int size = end - start;

    int l = size / 50;

    cout << "Status (each . mean 2%) : ";

    for ( int i = end - 1 ; i > start ; i -- ) {

        if ( l != 0 && i % l == 0 )

            cout << ".";

        else if ( l == 0 ) cout << ".";

        for ( int j = start ; j <= i - 1 ; j ++ ) {

            if ( array[j] > array[j+1] ) {

                tmp = array[j];
                array[j] = array[j+1];
                array[j+1] = tmp;
            }
        }
    }

    cout << endl;

}

double Calculate::mission1() {

    double cpu_time_used;
    clock_t start, end; // 儲存時間用的變數

    start = clock(); // 計算開始時間

    bubble_sort( array, 0, size );

    end = clock();

    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;

    cout << "Finish !"<< endl;

    bool check = true;

    for ( int i = 0 ; i < size - 1 ; i ++ )

        if ( array[i] > array[i + 1] )

            check = false;

    cout << endl << endl << "Check : " << check << endl;

    cpu_time = cpu_time_used;

    return cpu_time_used;
}

double Calculate::mission2() {

    double cpu_time_used;
    clock_t start, end; // 儲存時間用的變數

    int k = 0;

    while ( k < 1 || k > size ) {

        cout << "Input k ( k > 0 ) : ";
        cin >> k;
    }

    start = clock();

    int step = size / k;

    vector<int> stepPosition;


    for ( int i = 0 ; stepPosition.size() < k ; i += step ) {

        int end = i + step;

        if ( end >= size || end + step > size ) end = size;

        stepPosition.push_back(i);

        if ( end == size ) stepPosition.push_back(end);
    }

    if ( stepPosition[stepPosition.size() - 1] != size ) stepPosition.push_back(size);

    vector<thread> bubbleThreads;

    for ( int i = 1 ; i <= k ; i ++ ) {

        // cout << "Start : " << stepPosition[i-1] << " | End : " << stepPosition[i] - 1 << endl;

        thread bubbleThread( bubble_sort, array, stepPosition[i-1], stepPosition[i] );

        bubbleThreads.push_back( move( bubbleThread ) );
    }

    for ( thread & bubbleThread : bubbleThreads )

        bubbleThread.join();

    // for ( int i = 0 ; i < size ; i ++ ) cout << array[i] << endl;

    int mergeStep = 2;
    int limit = stepPosition.size() - 1;
    int temp_start_1 = 0, temp_end_1 = 0;
    bool odd_1 = false;

    for (  ; mergeStep <= stepPosition.size() - 1 ; mergeStep *= 2 ) {

        // cout << endl << endl << "New Merge, step = " << mergeStep << endl;

        vector<thread> mergeThreads;

        for ( int i = 0 ; i < limit ; i += mergeStep ) {

            int startIndex = i;
            int endIndex = i + mergeStep;
            int midIndex = (startIndex + endIndex) / 2;

            if ( endIndex <= limit || ( ! odd_1 && endIndex > limit && midIndex < limit ) ) {

                if ( endIndex > limit ) {

                    endIndex = stepPosition.size() - 1;

                    temp_start_1 = startIndex;
                    temp_end_1 = endIndex;

                    odd_1 = true;
                }

                int start = stepPosition[startIndex];
                int mid = stepPosition[midIndex];
                int end = stepPosition[endIndex] - 1;

                // cout << endl << "Start : " << start << " End : " << end << " Mid : " << mid << endl;

                thread mergeThread( &Merge, array, start, end, mid );

                mergeThreads.push_back( move( mergeThread ) );
            }
            else {

                if ( ! odd_1 ) {

                    temp_start_1 = startIndex;
                    temp_end_1 = stepPosition.size() - 1;

                    odd_1 = true;

                    limit = startIndex;
                }
                else {

                    int start = stepPosition[startIndex];
                    int mid = stepPosition[temp_start_1];
                    int end = stepPosition[temp_end_1] - 1;

                    // cout << endl << "ExtraMerge ! Start : " << start << " End : " << end << " Mid : " << mid << endl;

                    thread mergeExtraThread( &Merge, array, start, end, mid );

                    mergeThreads.push_back( move( mergeExtraThread ) );

                    limit = stepPosition.size() - 1;

                    if ( mergeStep * 2 > limit ) {

                        temp_start_1 = startIndex;
                        temp_end_1 = stepPosition.size() - 1;
                    }
                    else
                        odd_1 = false;
                }
            }
        }

        for ( thread & mergeThread : mergeThreads )

            mergeThread.join();

    }

    if ( odd_1 ) {

        int start = stepPosition[0];
        int mid = stepPosition[temp_start_1];
        int end = stepPosition[temp_end_1] - 1;

        // cout << endl << "LeftMerge ! Start : " << start << " End : " << end << " Mid : " << mid << endl;

        thread mergeLeftThread( &Merge, array, start, end, mid );

        mergeLeftThread.join();
    }

    end = clock();

    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;

    cout << "Finish !" << endl;

    bool check = true;

    for ( int i = 0 ; i < size - 1 ; i ++ )

        if ( array[i] > array[i + 1] )

            check = false;

    cout << endl << endl << "Check : " << check << endl;

    cpu_time = cpu_time_used;

    return cpu_time_used;
}

double Calculate::mission3() {

    double cpu_time_used;

    clock_t start, end; // 儲存時間用的變數

    int k = 0;

    while ( k < 1 || k > size ) {

        cout << "Input k ( k > 0 ) : ";
        cin >> k;
    }

    void * shareMem = create_shared_memory(size * 4 );

    memcpy( shareMem, array, size * 4 );

    start = clock();

    int step = size / k;
    vector<int> stepPosition;

    for ( int i = 0 ; stepPosition.size() < k ; i += step ) {

        int end = i + step;

        if ( end >= size || end + step > size ) end = size;

        //  << "Start : " << i << " | End : " << (end - 1) << endl;

        stepPosition.push_back(i);

        if ( end == size ) stepPosition.push_back(end);
    }

    if ( stepPosition[stepPosition.size() - 1] != size ) stepPosition.push_back(size);


    int time = k / 1000;
    int left = k - (time * 1000);

    int count = 0;

    for ( int i = 0 ; i <= time ; i ++ ) {

        int forkTime = 1000;

        if ( i == time ) forkTime = left;

        int pids[forkTime];

        for ( int j = 0 ; j < forkTime ; j++ ) {

            count ++;

            if ( ( pids[j] = fork() ) < 0 ) {

                perror( "Error in forking.\n" );
                exit( EXIT_FAILURE );
            }
            else if ( pids[j] == 0 ) {

                bubble_sort( static_cast<int *>(shareMem), stepPosition[count - 1], stepPosition[count] );

                exit(0);
            }
        }

        int status;
        int n = forkTime;

        while ( n > 0 ) {

            wait( & status);
            n --;
        }

    }

    array = static_cast<int *>(shareMem);

    int mergeStep = 2;
    int limit = stepPosition.size() - 1;
    int temp_start_1 = 0, temp_end_1 = 0;
    bool odd_1 = false;

    void * parameterMem = create_shared_memory( 16);
    int * parameter = new int[4];

    parameter[0] = limit;
    parameter[1] = 0;
    parameter[2] = 0;
    parameter[3] = 0;

    memcpy( parameterMem, parameter, 16 );


    for (  ; mergeStep <= stepPosition.size() - 1 ; mergeStep *= 2 ) {

        // cout << endl << endl << "New Merge, step = " << mergeStep << endl;

        int mergeTime = 0;

        for ( int i = 0 ; i < static_cast<int *>(parameterMem)[0] ; i += mergeStep ) {

            int startIndex = i;
            int endIndex = i + mergeStep;
            int midIndex = (startIndex + endIndex) / 2;

            if ( endIndex <= static_cast<int *>(parameterMem)[0] || ( static_cast<int *>(parameterMem)[3] == 0
                        && endIndex > static_cast<int *>(parameterMem)[0]
                        && midIndex < static_cast<int *>(parameterMem)[0] ) ) {

                mergeTime++;
            }
            else {

                // cout << "OOOOOOOOOOOOdd add, start =" << startIndex << " end = " << endIndex << " mid = " << midIndex << endl;
                mergeTime++;
            }
        }

        int pids[mergeTime];
        int i = 0;

        for ( int j = 0 ; j < mergeTime ; j ++ ) {

            int startIndex = i;
            int endIndex = i + mergeStep;
            int midIndex = (startIndex + endIndex) / 2;

            if ( ( pids[j] = fork() ) < 0 ) {

                perror( "Error in forking.\n" );
                exit( EXIT_FAILURE );
            }
            else if ( pids[j] == 0 ) {

                /*cout << endl << "parameterMem : " << static_cast<int *>(parameterMem)[0] << "\t"
                     << static_cast<int *>(parameterMem)[1] << "\t" << static_cast<int *>(parameterMem)[2] << "\t"
                     << static_cast<int *>(parameterMem)[3] << endl << endl;*/

                if ( endIndex <= static_cast<int *>(parameterMem)[0] || ( static_cast<int *>(parameterMem)[3] == 0
                                                                 && endIndex > static_cast<int *>(parameterMem)[0]
                                                                 && midIndex < static_cast<int *>(parameterMem)[0] ) ) {

                    if ( endIndex > static_cast<int *>(parameterMem)[0] ) {

                        endIndex = stepPosition.size() - 1;

                        parameter[1] = startIndex;
                        parameter[2] = endIndex;
                        parameter[3] = 1;

                        memcpy( parameterMem, parameter, 16 );
                    }

                    int start = stepPosition[startIndex];
                    int mid = stepPosition[midIndex];
                    int end = stepPosition[endIndex] - 1;

                    // cout << endl << "Start : " << start << " End : " << end << " Mid : " << mid << endl;

                    Merge(static_cast<int *>(shareMem), start, end, mid);

                } else {

                    /*cout << endl << endl << "odd" << endl << endl;

                    cout << endl << "parameterMem : " << static_cast<int *>(parameterMem)[0] << "\t"
                        << static_cast<int *>(parameterMem)[1] << "\t" << static_cast<int *>(parameterMem)[2] << "\t"
                        << static_cast<int *>(parameterMem)[3] << endl << endl;*/

                    if ( static_cast<int *>(parameterMem)[3] == 0 ) {

                        parameter[0] = startIndex;
                        parameter[1] = startIndex;
                        parameter[2] = stepPosition.size() - 1;
                        parameter[3] = 1;

                        memcpy( parameterMem, parameter, 16 );

                    } else {

                        int start = stepPosition[startIndex];
                        int mid = stepPosition[static_cast<int *>(parameterMem)[1]];
                        int end = stepPosition[static_cast<int *>(parameterMem)[2]] - 1;

                        // cout << endl << "ExtraMerge ! Start : " << start << " End : " << end << " Mid : " << mid << endl;

                        Merge(static_cast<int *>(shareMem), start, end, mid);

                        parameter[0] = stepPosition.size() - 1;
                        parameter[1] = startIndex;
                        parameter[2] = stepPosition.size() - 1;
                        parameter[3] = 1;

                        memcpy( parameterMem, parameter, 16 );

                        if ( mergeStep * 2 > static_cast<int *>(parameterMem)[0]) {

                            parameter[1] = startIndex;
                            parameter[2] = stepPosition.size() - 1;
                        } else
                            parameter[3] = 0;

                        memcpy( parameterMem, parameter, 16 );
                    }

                    /*cout << endl << "parameterMem : " << static_cast<int *>(parameterMem)[0] << "\t"
                         << static_cast<int *>(parameterMem)[1] << "\t" << static_cast<int *>(parameterMem)[2] << "\t"
                         << static_cast<int *>(parameterMem)[3] << endl << endl;*/
                }

                exit(0);
            }

            i += mergeStep;
        }

        int status;
        int n = mergeTime;

        while ( n > 0 ) {

            wait( & status);
            n --;
        }
    }

    if ( static_cast<int *>(parameterMem)[3] == 1 ) {

        int start = stepPosition[0];
        int mid = stepPosition[static_cast<int *>(parameterMem)[1]];
        int end = stepPosition[static_cast<int *>(parameterMem)[2]] - 1;

        // cout << endl << "LeftMerge ! Start : " << start<< " End : " << end << " Mid : " << mid << endl;

        Merge( static_cast<int *>(shareMem), start, end, mid );
    }

    array = static_cast<int *>(shareMem);

    end = clock();

    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;

    cout << "Finish !" << endl;

    bool check = true;

    for ( int i = 0 ; i < size - 1 ; i ++ )

        if ( array[i] > array[i + 1] )

            check = false;

    cout << endl << endl << "Check : " << check << endl;

    cpu_time = cpu_time_used;

    return cpu_time_used;
}

double Calculate::mission4() {

    double cpu_time_used;

    clock_t start, end; // 儲存時間用的變數

    static int processCount = 0;

    int k = 0;

    while ( k < 1 || k > size ) {

        cout << "Input k ( k > 0 ) : ";
        cin >> k;
    }

    start = clock();

    void * shmem = create_shared_memory(4 * size);

    pid_t pid = fork();

    if ( pid == 0 ) {

        int step = size / k;

        vector<int> stepPosition;

        for ( int i = 0 ; stepPosition.size() < k ; i += step ) {

            int end = i + step;

            if ( end >= size || end + step > size ) end = size;

            stepPosition.push_back(i);

            if ( end == size ) stepPosition.push_back(end);
        }

        if ( stepPosition[stepPosition.size() - 1] != size ) stepPosition.push_back(size);

        for ( int i = 1 ; i <= k ; i ++ ) {

            // cout << "Start : " << stepPosition[i-1] << " | End : " << stepPosition[i] - 1  << endl;

            bubble_sort( array, stepPosition[i-1] , stepPosition[i] );
        }

        // for ( int i = 0 ; i < size ; i ++ ) cout << array[i] << endl;

        int mergeStep = 2;
        int limit = stepPosition.size() - 1;
        int temp_start_1 = 0, temp_end_1 = 0;
        bool odd_1 = false;

        for (  ; mergeStep <= stepPosition.size() - 1 ; mergeStep *= 2 ) {

            // cout << endl << endl << "New Merge, step = " << mergeStep << endl;

            for ( int i = 0 ; i < limit ; i += mergeStep ) {

                int startIndex = i;
                int endIndex = i + mergeStep;
                int midIndex = (startIndex + endIndex) / 2;

                if ( endIndex <= limit || ( ! odd_1 && endIndex > limit && midIndex < limit ) ) {

                    if ( endIndex > limit ) {

                        endIndex = stepPosition.size() - 1;

                        temp_start_1 = startIndex;
                        temp_end_1 = endIndex;

                        odd_1 = true;
                    }

                    int start = stepPosition[startIndex];
                    int mid = stepPosition[midIndex];
                    int end = stepPosition[endIndex] - 1;

                    // cout << endl << "Start : " << start << " End : " << end << " Mid : " << mid << endl;

                    Merge( array, start, end, mid );
                }
                else {

                    if ( ! odd_1 ) {

                        temp_start_1 = startIndex;
                        temp_end_1 = stepPosition.size() - 1;

                        odd_1 = true;

                        limit = startIndex;
                    }
                    else {

                        int start = stepPosition[startIndex];
                        int mid = stepPosition[temp_start_1];
                        int end = stepPosition[temp_end_1] - 1;

                        // cout << endl << "ExtraMerge ! Start : " << start << " End : " << end << " Mid : " << mid << endl;

                        Merge( array, start, end, mid );

                        limit = stepPosition.size() - 1;

                        if ( mergeStep * 2 > limit ) {

                            temp_start_1 = startIndex;
                            temp_end_1 = stepPosition.size() - 1;
                        }
                        else
                            odd_1 = false;
                    }
                }
            }
        }

        if ( odd_1 ) {

            int start = stepPosition[0];
            int mid = stepPosition[temp_start_1];
            int end = stepPosition[temp_end_1] - 1;

            // cout << endl << "LeftMerge ! Start : " << start<< " End : " << end << " Mid : " << mid << endl;

            Merge( array, start, end, mid );
        }

        // for ( int i = 0 ; i < size ; i ++ ) cout << array[i] << endl;

        memcpy( shmem, array, size * 4 );

        // cout << "Child finish" << endl;

        exit(NULL);
    }
    else if ( pid > 0 ) {

        wait(nullptr);

        // cout << "Parent start" << endl;

        array = static_cast<int *>(shmem);

        // for ( int i = 0 ; i < size ; i ++ ) cout << array[i] << endl;
    }
    else {

        cout << "Error" << endl;

    }

    end = clock();

    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;

    cout << "Finish !" << endl;

    bool check = true;

    for ( int i = 0 ; i < size - 1 ; i ++ )

        if ( array[i] > array[i + 1] )

            check = false;

    cout << endl << endl << "Check : " << check << endl;

    cpu_time = cpu_time_used;

    return cpu_time_used;
}

void Calculate::writeFile() {

    cout << endl << endl << "Start write output file" << endl;

    time_t now = time(0);
    tm *ltm = localtime( &now );

    string outputFileName = fileName + "_output";
    outputFileName += to_string(mode);
    outputFileName += ".txt";

    fstream outputFile;

    outputFile.open( outputFileName, ios::out );

    outputFile << "Sort : " << endl;

    for ( int i = 0 ; i < size ; i ++ )

        outputFile << array[i] << endl;


    outputFile << "CPU Time : ";
    outputFile << cpu_time << " s" << endl ;

    outputFile << "Output Time : ";

    outputFile << 1900 + ltm->tm_year << "-" << 1 + ltm->tm_mon << "-" << ltm->tm_mday;
    outputFile << " " << ltm->tm_hour << ":" << ltm->tm_min << ":" << ltm->tm_sec ;

    outputFile.close();

    cout << endl << endl << "End write output file" << endl;
}

/*
void Merge_Sort( vector<int> before, int p, int q ) {

    if (p < q) {

        int m = (p + q) / 2;
        Merge_Sort( before, p, m );  // 遞迴排序子陣列 A[p...m]
        Merge_Sort( before, m + 1, q ); // 遞迴排序子陣列 A[m+1...q]
        // Merge( before, p, q, m );  // 將左右兩個以排序好的子陣列合併
    }

}*/

int main() {

    string fileName;
    fstream file;
    vector<int> *beforeArray = new vector<int>();
    int mode = 0;

    while ( mode != 5 ) {

        mode = 0; // init

        cout << endl << endl;
        cout << "------------------------MENU-----------------------" << endl;
        cout << "* 1. BubbleSort                                   *" << endl;
        cout << "* 2. K thread Bubble then merge sort              *" << endl;
        cout << "* 3. K processes Bubble then merge sort           *" << endl;
        cout << "* 4. one process Bubble and merge sort            *" << endl;
        cout << "* 5. quit                                         *" << endl;
        cout << "---------------------------------------------------" << endl << endl;

        while ( mode < 1 || mode > 5 ) {

            string modeInput;

            cout << "Choose mode : ";
            cin >> modeInput;

            try {

                mode = stoi( modeInput );

            }
            catch( exception &err ) {

                mode = 0;
            }
        }

        if ( mode == 5 ) break;

        cout << endl << "Input sort file name : ";
        cin >> fileName;

        file.open(fileName, ios::in);

        while ( ! file ) {

            cout << fileName << " not found, please try again!" << endl << endl;

            cout << "Input sort file name : ";
            cin >> fileName;

            file.open(fileName, ios::in);
        }

        cout << endl << "Start read data" << endl;

        while ( ! file.eof() ) {

            string temp = "";

            file >> temp;

            if ( ! temp.empty() )

                beforeArray->push_back(stoi(temp));

        }

        cout << endl << "Read data finish, data size = " << beforeArray->size() << endl;

        int * array = beforeArray->data();
        int size = beforeArray->size();


        Calculate *calculate = new Calculate( array, size, fileName.substr(0, fileName.size() - 4), mode );

        double cpu_time_used = 0;

        if ( mode == 1 ) {

            cpu_time_used = calculate->mission1();

            cout << endl << endl;

        }
        else if ( mode == 2 ) {

            cpu_time_used = calculate->mission2();

        }
        else if ( mode == 3 ) {


            cpu_time_used = calculate->mission3();

        }
        else if ( mode == 4 ) {

            cpu_time_used = calculate->mission4();

        }

        cout << "CPU Time = " << cpu_time_used << " s"<< endl;

        calculate->writeFile();

        file.close();
        beforeArray->clear();
    }

    return 0;
}
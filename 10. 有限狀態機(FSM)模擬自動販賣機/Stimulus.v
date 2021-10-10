module stimulus ;

reg clk, reset, cancel;
reg [2:0] select;
reg [6:0] coin;

parameter t = 20;

initial clk = 1'b1;
always #5 clk = ~clk;

FSM fsm( clk, reset, coin, select, cancel );

initial 
begin
    reset = 1;
    coin = 0;
    select = 0;
    cancel = 0;

/* ppt test | test 1 */
    #t reset = 0;
    #t coin = 10 ;		// coin 10,	total 10 dollars	tea
    #t coin = 5 ;		// coin 5,	total 15 dollars	tea | coke
    #t coin = 1 ;		// coin 1,	total 16 dollars	tea | coke
    #t coin = 10 ;		// coin 10,	total 26 dollars	tea | coke | coffee | milk

    #t coin = 0 ;
    #t select = 3 ; // 3 = coffee	coffee out
    #t select = 0 ;
    #t $finish; 

/* test 2 
    #t reset = 0;
    #t coin = 5;
    #t coin = 10;  
    #t coin = 0;
    #t cancel = 1;
    #t cancel = 0;
    #t coin = 10;  
    #t coin = 10;
    #t coin = 1;
    #t coin = 1;
    #t coin = 1;
    #t coin = 1;
    #t coin = 1;

    #t coin = 0;
    #t select = 4 ;
    #t select = 0 ;
    #t $finish; */

/* test 3 

    #t reset = 0;
    #t coin = 5;
    #t coin = 10;
    #t coin = 0;
    #t cancel = 1;
    #t cancel = 0;
    #t coin = 10;
    #t coin = 10;
    #t coin = 0;
    #t select = 2;
    #t select = 0;
    #t coin = 10;
    #t coin = 10;
    #t coin = 0;
    #t select = 4;
    #t select = 0;
    #t coin = 10;
    #t coin = 0;
    #t select = 1;
    #t select = 0;
    #t coin = 50;
    #t coin = 0;
    #t select = 3;
    #t select = 0;
    #t $finish; */

end 
endmodule

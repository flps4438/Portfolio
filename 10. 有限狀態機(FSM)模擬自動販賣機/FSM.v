module FSM( clk, reset, coin, select, cancel );

input clk, reset, cancel;
input [2:0] select;
input [6:0] coin;

reg [6:0] money;
reg [1:0] status;
reg [1:0] next_status;
reg can;
reg error;

parameter S0 = 2'd0;
parameter S1 = 2'd1;
parameter S2 = 2'd2;
parameter S3 = 2'd3;
parameter not_select = 3'd0;
parameter tea = 3'd1;
parameter coke = 3'd2;
parameter coffee = 3'd3;
parameter milk = 3'd4;

initial money = 7'd0;
initial next_status = S0;
initial can = 1'b1;
initial error = 1'b0;

always @( posedge clk )
begin    

    can = ~can;

    if ( reset ) begin
        
        money <= 7'd0; 
    end
    else status <= next_status;
end        

always @( status or posedge clk )
begin

    case ( status )

        S0:
            begin
                if ( can == 1'd0 && cancel == 1'd0 && select == not_select ) begin

                    money = money + coin;

                    if ( money >= 7'd10 );                   
                 
                    else if ( coin != 7'd0 )
                  
                        $display( "coin %d, total %d dollar\t", coin, money );
                end 
            end
        S1:
            begin
                

                if ( money >= 7'd25 && coin != 7'd0 && can == 1'd1 )

                    $display( "coin %d, total %d dollar\t\ttea | coke | coffee | milk", coin, money );

                else if ( money >= 7'd20 && coin != 7'd0 && can == 1'd1 )

                    $display( "coin %d, total %d dollar\t\ttea | coke | coffee", coin, money );

                else if ( money >= 7'd15 && coin != 7'd0 && can == 1'd1 )

                    $display( "coin %d, total %d dollar\t\ttea | coke", coin, money );

                else if ( money >= 7'd10 && coin != 7'd0 && can == 1'd1 )

                    $display( "coin %d, total %d dollar\t\ttea", coin, money );
            end
        S2:
            begin

                if ( can == 1'd1 ) begin 

                    if ( select == tea ) 
                    
                        begin

                            money = money - 7'd10;

                            if ( money[6] == 1'b0 )
                                $display( "tea out" );
                        end

                    else if ( select == coke ) 

                        begin

                            money = money - 7'd15;
                            
                            if ( money[6] == 1'b0 )
                                $display( "coke out" );
                        end

                    else if ( select == coffee ) 
    
                        begin

                            money = money - 7'd20;

                            if ( money[6] == 1'b0 )
                                $display( "coffee out" );
                        end

                    else if ( select == milk ) 

                        begin

                            money = money - 7'd25;

                            if ( money[6] == 1'b0 )  
                                $display( "milk out" );
                        end

                    else $display( "error out" );

                    if ( money[6] == 1'b1 ) begin

                        $display( "money not enough!" ); 
                          
                        money = money + ( select * 5 ) + 5;  
                    end  
                end
            end

        S3:
            if ( can == 1'b1 ) begin
                begin 
                    $display( "exchange %d dollars", money ); 
                    money = 7'd0;
                end
            end
    endcase                   
end

always @( status or posedge clk )
begin
 
    case ( status )

        S0: 
            if ( cancel == 1'b1 )
                next_status = S3;
            else if ( error == 1'b1 ) error = 1'b0;   
            else if ( money >= 7'd10 )       
                next_status = S1;
        S1:
            if ( select != not_select ) begin

                if ( ( select == 3'd1 && money >= 7'd10 ) || ( select == 3'd2 && money >= 7'd15 ) || ( select == 3'd3 && money >= 7'd20 ) || ( select == 3'd4 && money >= 7'd25 ) )
                    next_status = S2;  
                else begin
 
                    if ( can == 1'd1 ) begin   
                        $display( "money not enough!" );
                        error = 1'b1;                        
                        next_status = S0; 
                    end
                end    
            end
            else if ( coin == 7'd0 && cancel == 1'b0 ); 
            else next_status = S0;  

        S2:  next_status = S3;
        S3:  next_status = S0;
    endcase
end

endmodule


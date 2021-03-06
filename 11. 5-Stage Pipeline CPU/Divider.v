`timescale 1ns/1ns
module Divider( clk, reset, dataA, dataB, dout );

input clk, reset;
input[31:0] dataA, dataB;

output [63:0] dout;

reg[63:0] divr, rem;
reg[31:0] quot;
reg[5:0] t;
reg[63:0] dout;


always @( posedge clk ) begin

    if ( reset == 1'b1 ) begin
      
        rem = 64'b0;
        t = 0;  
        dout = 0;
    end  
    else if ( t < 6'd33 ) begin

        if ( t == 6'b0 ) begin

            divr = { dataB, 32'b0 };
            rem = { 32'b0, dataA };
            quot = 32'b0;
        end

        rem = rem - divr;

        if ( rem[63] == 0 ) begin
            quot = (quot << 1);
            quot = (quot + 1'b1);
        end     
        else begin
            
            rem = (rem + divr);
            quot = (quot << 1 );   
        end
         
        divr = ( divr >> 1 );
        t = t + 1;
  
    end
    if ( t == 6'd33 )

        dout = { rem[31:0], quot };    
end

endmodule

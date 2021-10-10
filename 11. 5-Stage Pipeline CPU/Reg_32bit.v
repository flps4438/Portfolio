`timescale 1ns/1ns
module Reg_32bit( clk, rst, en_reg, in, out );

    input clk, rst, en_reg;
    input[31:0]	in;
    output[31:0] out;

    reg [31:0] out;
   
    always @( posedge clk ) begin

        if ( rst )

	    out <= 32'b0;

        else if ( en_reg )

	    out <= in;
    end

endmodule

`timescale 1ns/1ns
module IF_ID( clk, rst, pcIn, pcOut, rdIn, rdOut );


input clk, rst;
input[31:0] pcIn, rdIn;

output[31:0] pcOut, rdOut;

reg[31:0] pcOut, rdOut;

always @( posedge clk ) begin

    if ( rst == 1'b1 ) begin
	
		rdOut = 32'b0;
		pcOut = 32'b0;
    end
    else begin 
	
		pcOut = pcIn;
		rdOut = rdIn;
    end
end

endmodule

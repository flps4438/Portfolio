`timescale 1ns/1ns
module MEM_WB( clk, rst, regWriteIn, regWriteOut, memToRegIn, memToRegOut, 
			   rdIn, rdOut, aluIn, aluOut, wnIn, wnOut );


input clk, rst;
input[31:0]  rdIn, aluIn;
input[4:0] wnIn;
input regWriteIn, memToRegIn;

output[31:0] rdOut, aluOut;
output[4:0] wnOut;
output regWriteOut, memToRegOut;

reg[31:0] rdOut, aluOut;
reg[4:0] wnOut;
reg regWriteOut, memToRegOut;

always @(posedge clk) begin

    if ( rst == 1'b1 ) begin
	
		rdOut  = 32'b0;
		aluOut = 32'b0;
		wnOut  = 5'b0;
		regWriteOut = 1'b0;
		memToRegOut = 1'b0;
    end
    else begin 
	
		rdOut  = rdIn;
		aluOut = aluIn;
		wnOut  = wnIn;
		regWriteOut = regWriteIn;
		memToRegOut = memToRegIn;
    end
end

endmodule

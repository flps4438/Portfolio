`timescale 1ns/1ns
module EX_MEM( clk, rst, memWriteIn, memWriteOut, memReadIn, memReadOut, 
               memToRegIn, memToRegOut, regWriteIn, regWriteOut,
	       aluIn, aluOut, rd2In, rd2Out, wnIn, wnOut );


input clk, rst, memWriteIn, memReadIn, memToRegIn, regWriteIn ;
input[31:0] aluIn, rd2In;
input[4:0] wnIn;

output memWriteOut, memReadOut, memToRegOut, regWriteOut ;
output[31:0] aluOut, rd2Out;
output[4:0] wnOut;

reg memWriteOut, memReadOut, memToRegOut, regWriteOut ;
reg[31:0] aluOut, rd2Out;
reg[4:0] wnOut;


always @(posedge clk) begin

    if ( rst == 1'b1 ) begin
	
	memWriteOut = 1'b0;
	memReadOut = 1'b0;
	memToRegOut = 1'b0;
	regWriteOut = 1'b0;
	aluOut = 32'b0;
	rd2Out = 32'b0;
	wnOut = 5'b0;
		
    end
    else begin 	
	
	memWriteOut = memWriteIn;
	memReadOut = memReadIn;
	memToRegOut = memToRegIn;
	regWriteOut = regWriteIn;
	aluOut = aluIn;
	rd2Out = rd2In;
	wnOut = wnIn;
		
    end
end

endmodule

`timescale 1ns/1ns
module ID_EX( clk, rst, aluSrcIn, aluSrcOut, regDstIn, regDstOut, aluOPIn, aluOPOut, oriIn, oriOut,
			  memWriteIn, memWriteOut, memReadIn, memReadOut, memToRegIn, memToRegOut, regWriteIn, regWriteOut,
			  extendIn, extendOut, rd1In, rd1Out, rd2In, rd2Out, rn2In, rn2Out, wnIn, wnOut);


input clk, rst, aluSrcIn, regDstIn, oriIn, memWriteIn, memReadIn, memToRegIn, regWriteIn ;
input[1:0] aluOPIn;
input[4:0] rn2In, wnIn;
input[31:0] extendIn, rd1In, rd2In;

output aluSrcOut, regDstOut, oriOut, memWriteOut, memReadOut, memToRegOut, regWriteOut ;
output[1:0] aluOPOut;
output[4:0] rn2Out, wnOut;
output[31:0] extendOut, rd1Out,rd2Out;

reg aluSrcOut, regDstOut, oriOut, memWriteOut, memReadOut, memToRegOut, regWriteOut ;
reg[1:0] aluOPOut;
reg[4:0] rn2Out, wnOut;
reg[31:0] extendOut, rd1Out,rd2Out;


always @(posedge clk) begin

    if ( rst == 1'b1 ) begin
	
		aluSrcOut = 1'b0;
		regDstOut = 1'b0;
		oriOut = 1'b0;
		memWriteOut = 1'b0;
		memReadOut = 1'b0;
		memToRegOut = 1'b0;
		regWriteOut =1'b0;
		aluOPOut = 2'b0;
		rn2Out = 5'b0;
		wnOut = 5'b0;
		extendOut = 32'b0;
		rd1Out = 32'b0;
		rd2Out = 32'b0;
	
    end
    else begin 
	
		aluSrcOut = aluSrcIn;
		regDstOut = regDstIn;
		oriOut = oriIn;
		memWriteOut = memWriteIn;
		memReadOut = memReadIn;
		memToRegOut = memToRegIn;
		regWriteOut = regWriteIn;
		aluOPOut = aluOPIn;
		rn2Out = rn2In ;
		wnOut = wnIn;
		extendOut = extendIn;
		rd1Out = rd1In;
		rd2Out = rd2In;
    end
end

endmodule
`timescale 1ns/1ns
module CONCAT( jmpaddr, pcaddr, sout );
	
	input[27:0] jmpaddr;
	input[31:0] pcaddr;
	
	output[31:0] sout;
	wire[31:0] sout;
	

	assign sout = {pcaddr[31:28], jmpaddr};
	
endmodule
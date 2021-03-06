`timescale 1ns/1ns
module ALU( sel, a, b, sout, zero );

input[5:0] sel;
input[31:0] a, b;

output zero;
output[31:0] sout;

wire[31:0] negB;
wire z = 1'b0;

assign negB = b ^ 32'b11111111111111111111111111111111;

wire[31:0] ansOfAND, ansOfOR, ansOfADD, ansOfSUB, ansOfSLT, sout;
wire temp, zero;

assign ansOfAND = a & b;
assign ansOfOR = a | b;

Adder x(1'b0, a, b, ansOfADD,);
Adder y(1'b1, a, negB, ansOfSUB, temp);

assign ansOfSLT = {31'd0 ,temp};

assign zeroTemp = |ansOfSUB;
assign zero = ~zeroTemp;

assign close = |sel;

assign sout = (close==1'b0) ? 0 : (sel[3]?ansOfSLT:(sel[2]?(sel[0]?ansOfOR:ansOfAND):(sel[1]?ansOfSUB:ansOfADD)));

endmodule

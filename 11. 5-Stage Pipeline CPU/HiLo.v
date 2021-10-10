`timescale 1ns/1ns
module HiLo( clk, rst, ctrl, divAns, Hi, Lo );

input clk, rst, ctrl;
input[63:0] divAns;
output[31:0] Hi;
output[31:0] Lo;

reg[31:0] Hi, Lo;

always@( posedge clk or rst ) begin

    if ( rst == 1'b1 ) begin

        Hi = 32'b0;
        Lo = 32'b0;

    end
    else begin

        if ( ctrl == 1'b1 ) begin

            Hi = divAns[63:32];
            Lo = divAns[31:0];
        end 
    end
end

endmodule

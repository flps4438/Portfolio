`timescale 1ns/1ns
module ALU_Control( clk ,ALU_op, funct, Ori, ALU_sel, ALUorSH, ALUorDE, HiorLo, div_rst, HiLo_ctrl );

input [1:0] ALU_op;
input [5:0] funct;
input clk, Ori;
output [5:0] ALU_sel;
output ALUorSH, ALUorDE, HiorLo, div_rst, HiLo_ctrl;

reg [5:0] t;
reg [5:0] ALU_sel;
reg ALUorSH, ALUorDE, HiorLo, div_rst, HiLo_ctrl;

always @(ALU_op or funct or posedge clk ) begin

    if ( t == 6'd34 ) begin

       $display("Divu count reset"); 

       t = 0;

    end

    if ( ALU_op == 2'b11 ) begin // not use

        if ( Ori )

            ALU_sel = 6'd37;
        else 
            ALU_sel = 6'd0;

        ALUorSH = 0;
        ALUorDE = 0;
        HiorLo = 0;
        div_rst = 1;
        HiLo_ctrl = 0;
        t = 0;
    end
    else if ( ALU_op == 2'b10 ) begin

        if ( funct == 6'd32 || funct == 6'd34 || funct == 6'd36 || funct == 6'd37 || funct == 6'd42 ) begin 
            
            // add, sub, and, or

            ALU_sel = funct;
            ALUorSH = 0;
            ALUorDE = 0;
            HiorLo = 0; 
            div_rst = 1;
            HiLo_ctrl = 0;
        end
        else if ( funct == 6'd2 ) begin // srl

            ALU_sel = 6'd0;
            ALUorSH = 1;
            ALUorDE = 0;
            HiorLo = 0; 
            div_rst = 1;
            HiLo_ctrl = 0;
        end
	else if ( funct == 6'd27 ) begin // divu

            ALU_sel = 6'd0;
            ALUorSH = 0;
            ALUorDE = 0;
            HiorLo = 0;
            div_rst = 0;
            HiLo_ctrl = 0;
            
            t = 1;

            $display("Divu start, now t = %d", t );
        end
	else if ( funct == 6'd16 ) begin // mfhi

            ALU_sel = 6'd0;
            ALUorSH = 0;
            ALUorDE = 1;
            HiorLo = 1; 
            div_rst = 1;
            HiLo_ctrl = 0;    
        end  
	else if ( funct == 6'd18 ) begin // mflo

            ALU_sel = 6'd0;
            ALUorSH = 0;
            ALUorDE = 1;
            HiorLo = 0;
            div_rst = 1;
            HiLo_ctrl = 0;
        end
	else if ( funct ==6'd0 ) begin // nop

            ALU_sel = 6'd0;
            ALUorSH = 0; 
            ALUorDE = 0;
            HiorLo = 0; 
//
            if ( t != 6'd0 && t < 6'd33 ) begin

                $display("Divu calculate, now t = %d", t );

                HiLo_ctrl = 0;
                div_rst = 0;
                t = t + 1;
            end
            else if ( t == 6'd33 ) begin

                $display("Divu calculate end");  
                
                HiLo_ctrl = 1;
                div_rst = 0;
                t = t + 1;
            end 
            else begin

                div_rst = 1;
                HiLo_ctrl = 0;
            end           
        end
	else begin

	    $display("control_single unimplemented funct %d", funct);
	    ALU_sel = 6'd0;
            ALUorSH = 0;
            ALUorDE = 0;
            HiorLo = 0; 
            div_rst = 1;
            HiLo_ctrl = 0;
	end
    end
    else if ( ALU_op == 2'b01 ) begin // SUB

        ALU_sel = 6'd34;
        ALUorSH = 0;
        ALUorDE = 0;
        HiorLo = 0;
        div_rst = 1;
        HiLo_ctrl = 0; 
    end
    else if ( ALU_op == 2'b00 ) begin // ADD

        ALU_sel = 6'd32;
        ALUorSH = 0;
        ALUorDE = 0;
        HiorLo = 0;
        div_rst = 1;
        HiLo_ctrl = 0; 
    end
end

endmodule

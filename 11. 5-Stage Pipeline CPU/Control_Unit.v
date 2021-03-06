module Control_unit( opcode, funct, aluSrc, regDst, aluOP, ori, branch,
		     memWrite, memRead, memToReg, regWrite, jump, jrDst );
					 
    input[5:0] opcode;
    input[5:0] funct;
	
    output[1:0] aluOP;
    output aluSrc, regDst, ori, branch, memWrite, memRead, memToReg, regWrite, jump, jrDst;
	
    reg[1:0] aluOP;
    reg aluSrc, regDst, ori, branch, memWrite, memRead, memToReg, regWrite, jump, jrDst;

    always @( opcode or funct ) begin
	
	if ( opcode == 6'd0 ) begin // r-type
		
	    if ( funct == 6'd8 ) begin // jr
	            
		aluOP = 2'b11;
		aluSrc = 1'b0;
		regDst = 1'b0;
		ori = 1'b0;
		branch = 1'b0;
		memWrite = 1'b0;
		memRead = 1'b0;
		memToReg = 1'b0;
		regWrite = 1'b0;
	        jump = 1'b1;
		jrDst = 1'b1;
				
		$display("In control_unit, now instruction : JR");
	    end
            else if ( funct ==  6'd27 ) begin // divu

                aluOP = 2'b10;
	        aluSrc = 1'b0;
	        regDst = 1'b1;
	        ori = 1'b0;
	        branch = 1'b0;
		memWrite = 1'b0;
		memRead = 1'b0;
		memToReg = 1'b0;
		regWrite = 1'b0;
		jump = 1'b0;
		jrDst = 1'b0;
            end
	    else begin
			
	        aluOP = 2'b10;
	        aluSrc = 1'b0;
	        regDst = 1'b1;
	        ori = 1'b0;
	        branch = 1'b0;
		memWrite = 1'b0;
		memRead = 1'b0;
		memToReg = 1'b0;
		regWrite = 1'b1;
		jump = 1'b0;
		jrDst = 1'b0;
				
		$display("In control_unit, now instruction : R-Type, Funct: %d", funct);
            end		
	end
		
	else if( opcode == 6'd35 ) begin //lw
		
            aluOP = 2'b00;
            aluSrc = 1'b1;
            regDst = 1'b0;
            ori = 1'b0;
	    branch = 1'b0;
            memWrite = 1'b0;
            memRead = 1'b1;
	    memToReg = 1'b1;
	    regWrite = 1'b1;
            jump = 1'b0;
	    jrDst = 1'b0;
			
	    $display("In control_unit, now instruction : LW");
        end
		
	else if( opcode == 6'd43 ) begin // sw
		
            aluOP = 2'b00;
            aluSrc = 1'b1;
	    regDst = 1'b0;
	    ori = 1'b0;
	    branch = 1'b0;
	    memWrite = 1'b1;
	    memRead = 1'b0;
	    memToReg = 1'b0;
	    regWrite = 1'b0;
            jump = 1'b0;
	    jrDst = 1'b0;
			
	    $display("In control_unit, now instruction : SW");
	end
	
	else if ( opcode == 6'd4 ) begin // beq
		
            aluOP = 2'b11;
	    aluSrc = 1'b0;
	    regDst = 1'b0;
	    ori = 1'b0;
	    branch = 1'b1;
	    memWrite = 1'b0;
	    memRead = 1'b0;
	    memToReg = 1'b0;
	    regWrite = 1'b0;
	    jump = 1'b0;
	    jrDst = 1'b0;
			
	    $display("In control_unit, now instruction : BEQ");
	end
		
	else if ( opcode == 6'd13 ) begin // ori
		
	    aluOP = 2'b11;
	    aluSrc = 1'b1;
	    regDst = 1'b0;
	    ori = 1'b1;
	    branch = 1'b0;
	    memWrite = 1'b0;
	    memRead = 1'b0;
	    memToReg = 1'b0;
            regWrite = 1'b1;
	    jump = 1'b0;
	    jrDst = 1'b0;
		
	    $display("In control_unit, now instruction : ORI");
	end
		
	else if ( opcode == 6'd2 ) begin //j
		
	    aluOP = 2'b11;
            aluSrc = 1'b0;
	    regDst = 1'b0;
	    ori = 1'b0;
	    branch = 1'b0;
	    memWrite = 1'b0;
	    memRead = 1'b0;
	    memToReg = 1'b0;
	    regWrite = 1'b0;
            jump = 1'b1;
	    jrDst = 1'b0;
			
	    $display("In control_unit, now instruction : J");
	end
	else begin
		
	    $display("control_single unimplemented opcode %d, funct %d", opcode, funct);
		
    	    aluOP = 2'bxx;
	    aluSrc = 1'bx;
	    regDst = 1'bx;
	    ori = 1'bx;
	    branch = 1'bx;
	    memWrite = 1'bx;
	    memRead = 1'bx;
	    memToReg = 1'bx;
	    regWrite = 1'bx;
	    jump = 1'bx;
	    jrDst = 1'bx;
        end
    end

endmodule

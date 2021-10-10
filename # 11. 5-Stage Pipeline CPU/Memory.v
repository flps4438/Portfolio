/*
	Title: Memory
	Author: Selene (Computer System and Architecture Lab, ICE, CYCU)
	
	Input Port
		1. clk
		2. MemRead: ??????????
		3. MemWrite: ??????????
		4. wd: ????????
		5. addr: ?????????
	Output Port
		1. rd: ????????
*/
module Memory( clk, memRead, memWrite, addr, wd, rd );

input clk, memRead, memWrite;
input [31:0] addr, wd;
output [31:0] rd;

reg [31:0] rd;

// Memory size: 1KB.
reg [7:0] mem_array [0:1023];


always @( memRead or mem_array[addr] or mem_array[addr+1] or mem_array[addr+2] or mem_array[addr+3] ) begin
	
    if ( memRead == 1'b1 ) begin

        rd[7:0] = mem_array[addr];
	rd[15:8] = mem_array[addr+1];
	rd[23:16] = mem_array[addr+2];
	rd[31:24] = mem_array[addr+3];
	$display( "%d, reading data: Mem[%d] => %d", $time/10, addr, rd );
    end
    else rd = 32'hxxxxxxxx;
end
	

always @( posedge clk ) begin
   
    if ( memWrite == 1'b1 ) begin
        
        $display( "%d, writing data: Mem[%d] <= %d", $time/10, addr, wd );
	mem_array[addr] <= wd[7:0];
	mem_array[addr+1] <= wd[15:8];
	mem_array[addr+2] <= wd[23:16];
	mem_array[addr+3] <= wd[31:24];
    end
end

endmodule

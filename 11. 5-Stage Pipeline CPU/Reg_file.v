module Reg_file( clk, regWrite, rn1, rn2, wn, wd, rd1, rd2 );

input clk, regWrite;
input [4:0] rn1, rn2, wn;
input [31:0] wd;

output [31:0] rd1, rd2;

reg [31:0] rd1, rd2;
reg [31:0] file_array [31:1];

always @( rn1 or file_array[rn1] ) begin

    if ( rn1 == 0 )
        
        rd1 = 32'd0;

    else 
    
        rd1 = file_array[rn1];

    $display( "%d, reg_file[%d] => %d (Port 1)", $time/10, rn1, rd1);

end

always @( rn2 or file_array[rn2] ) begin

    if ( rn2 == 0 )
        
        rd2 = 32'd0;

    else 
    
        rd2 = file_array[rn2];

    $display( "%d, reg_file[%d] => %d (Port 2)", $time/10, rn2, rd2);

end

always @( posedge clk ) begin


    if ( regWrite && ( wn != 0 ) ) begin

        file_array[wn] <= wd;

        $display( "%d, reg_file[%d] <= %d (Write)", $time/10, wn, wd);
    end

end

endmodule

`timescale 1ns/1ns
module Adder(sel, a, b, dout, cout);

  input sel;
  input[31:0] a, b;
  output[31:0] dout;
  output cout;
 
  wire    [31:0]   c;
  
  FA      tfa0(.a(a[0]), .b(b[0]), .cin(sel), .cout(c[0]), .sum(dout[0]));
  FA      tfa1(.a(a[1]), .b(b[1]), .cin(c[0]), .cout(c[1]), .sum(dout[1]));
  FA      tfa2(.a(a[2]), .b(b[2]), .cin(c[1]), .cout(c[2]), .sum(dout[2]));
  FA      tfa3(.a(a[3]), .b(b[3]), .cin(c[2]), .cout(c[3]), .sum(dout[3]));
  FA      tfa4(.a(a[4]), .b(b[4]), .cin(c[3]), .cout(c[4]), .sum(dout[4]));
  FA      tfa5(.a(a[5]), .b(b[5]), .cin(c[4]), .cout(c[5]), .sum(dout[5]));
  FA      tfa6(.a(a[6]), .b(b[6]), .cin(c[5]), .cout(c[6]), .sum(dout[6]));
  FA      tfa7(.a(a[7]), .b(b[7]), .cin(c[6]), .cout(c[7]), .sum(dout[7]));
  FA      tfa8(.a(a[8]), .b(b[8]), .cin(c[7]), .cout(c[8]), .sum(dout[8]));
  FA      tfa9(.a(a[9]), .b(b[9]), .cin(c[8]), .cout(c[9]), .sum(dout[9]));

  FA      tfa10(.a(a[10]), .b(b[10]), .cin(c[9]), .cout(c[10]), .sum(dout[10]));
  FA      tfa11(.a(a[11]), .b(b[11]), .cin(c[10]), .cout(c[11]), .sum(dout[11]));
  FA      tfa12(.a(a[12]), .b(b[12]), .cin(c[11]), .cout(c[12]), .sum(dout[12]));
  FA      tfa13(.a(a[13]), .b(b[13]), .cin(c[12]), .cout(c[13]), .sum(dout[13]));
  FA      tfa14(.a(a[14]), .b(b[14]), .cin(c[13]), .cout(c[14]), .sum(dout[14]));
  FA      tfa15(.a(a[15]), .b(b[15]), .cin(c[14]), .cout(c[15]), .sum(dout[15]));
  FA      tfa16(.a(a[16]), .b(b[16]), .cin(c[15]), .cout(c[16]), .sum(dout[16]));
  FA      tfa17(.a(a[17]), .b(b[17]), .cin(c[16]), .cout(c[17]), .sum(dout[17]));
  FA      tfa18(.a(a[18]), .b(b[18]), .cin(c[17]), .cout(c[18]), .sum(dout[18]));
  FA      tfa19(.a(a[19]), .b(b[19]), .cin(c[18]), .cout(c[19]), .sum(dout[19]));

  FA      tfa20(.a(a[20]), .b(b[20]), .cin(c[19]), .cout(c[20]), .sum(dout[20]));
  FA      tfa21(.a(a[21]), .b(b[21]), .cin(c[20]), .cout(c[21]), .sum(dout[21]));
  FA      tfa22(.a(a[22]), .b(b[22]), .cin(c[21]), .cout(c[22]), .sum(dout[22]));
  FA      tfa23(.a(a[23]), .b(b[23]), .cin(c[22]), .cout(c[23]), .sum(dout[23]));
  FA      tfa24(.a(a[24]), .b(b[24]), .cin(c[23]), .cout(c[24]), .sum(dout[24]));
  FA      tfa25(.a(a[25]), .b(b[25]), .cin(c[24]), .cout(c[25]), .sum(dout[25]));
  FA      tfa26(.a(a[26]), .b(b[26]), .cin(c[25]), .cout(c[26]), .sum(dout[26]));
  FA      tfa27(.a(a[27]), .b(b[27]), .cin(c[26]), .cout(c[27]), .sum(dout[27]));
  FA      tfa28(.a(a[28]), .b(b[28]), .cin(c[27]), .cout(c[28]), .sum(dout[28]));
  FA      tfa29(.a(a[29]), .b(b[29]), .cin(c[28]), .cout(c[29]), .sum(dout[29]));

  FA      tfa30(.a(a[30]), .b(b[30]), .cin(c[29]), .cout(c[30]), .sum(dout[30]));
  FA      tfa31(.a(a[31]), .b(b[31]), .cin(c[30]), .cout(c[31]), .sum(dout[31]));

  assign cout = dout[31];

endmodule
 

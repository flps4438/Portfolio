`timescale 1ns/1ns
module FA(a, b, cin, cout, sum);

    input a, b, cin;
    output cout, sum;
   
    wire  e1, e2, e3, e4, e5, e6;

    and(e1, a, b);
    and(e2, a, cin);
    and(e3, b, cin);
    or(e4, e1, e2);
    or(cout, e3, e4);

    xor(e5, a, b);
    xor(sum, cin, e5);

endmodule

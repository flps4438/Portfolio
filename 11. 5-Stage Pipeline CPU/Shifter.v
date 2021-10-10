`timescale 1ns/1ns
module Shifter( a, shift, out );

input wire[31:0] a;
input wire[4:0] shift;
output wire[31:0] out;

wire[31:0] tempA, tempB, tempC, tempD;

//==================================
Mux_2to1 a1(a[0], a[1], shift[0], tempA[0]);
Mux_2to1 a2(a[1], a[2], shift[0], tempA[1]);
Mux_2to1 a3(a[2], a[3], shift[0], tempA[2]);
Mux_2to1 a4(a[3], a[4], shift[0], tempA[3]);
Mux_2to1 a5(a[4], a[5], shift[0], tempA[4]);
Mux_2to1 a6(a[5], a[6], shift[0], tempA[5]);
Mux_2to1 a7(a[6], a[7], shift[0], tempA[6]);
Mux_2to1 a8(a[7], a[8], shift[0], tempA[7]);
Mux_2to1 a9(a[8], a[9], shift[0], tempA[8]);
Mux_2to1 a10(a[9], a[10], shift[0], tempA[9]);

Mux_2to1 a11(a[10], a[11], shift[0], tempA[10]);
Mux_2to1 a12(a[11], a[12], shift[0], tempA[11]);
Mux_2to1 a13(a[12], a[13], shift[0], tempA[12]);
Mux_2to1 a14(a[13], a[14], shift[0], tempA[13]);
Mux_2to1 a15(a[14], a[15], shift[0], tempA[14]);
Mux_2to1 a16(a[15], a[16], shift[0], tempA[15]);
Mux_2to1 a17(a[16], a[17], shift[0], tempA[16]);
Mux_2to1 a18(a[17], a[18], shift[0], tempA[17]);
Mux_2to1 a19(a[18], a[19], shift[0], tempA[18]);
Mux_2to1 a20(a[19], a[20], shift[0], tempA[19]);

Mux_2to1 a21(a[20], a[21], shift[0], tempA[20]);
Mux_2to1 a22(a[21], a[22], shift[0], tempA[21]);
Mux_2to1 a23(a[22], a[23], shift[0], tempA[22]);
Mux_2to1 a24(a[23], a[24], shift[0], tempA[23]);
Mux_2to1 a25(a[24], a[25], shift[0], tempA[24]);
Mux_2to1 a26(a[25], a[26], shift[0], tempA[25]);
Mux_2to1 a27(a[26], a[27], shift[0], tempA[26]);
Mux_2to1 a28(a[27], a[28], shift[0], tempA[27]);
Mux_2to1 a29(a[28], a[29], shift[0], tempA[28]);
Mux_2to1 a30(a[29], a[30], shift[0], tempA[29]);

Mux_2to1 a31(a[30], a[31], shift[0], tempA[30]);
Mux_2to1 a32(a[31], 1'b0, shift[0], tempA[31]);
//====================================

Mux_2to1 b1(tempA[0], tempA[2], shift[1], tempB[0]);
Mux_2to1 b2(tempA[1], tempA[3], shift[1], tempB[1]);
Mux_2to1 b3(tempA[2], tempA[4], shift[1], tempB[2]);
Mux_2to1 b4(tempA[3], tempA[5], shift[1], tempB[3]);
Mux_2to1 b5(tempA[4], tempA[6], shift[1], tempB[4]);
Mux_2to1 b6(tempA[5], tempA[7], shift[1], tempB[5]);
Mux_2to1 b7(tempA[6], tempA[8], shift[1], tempB[6]);
Mux_2to1 b8(tempA[7], tempA[9], shift[1], tempB[7]);
Mux_2to1 b9(tempA[8], tempA[10], shift[1], tempB[8]);
Mux_2to1 b10(tempA[9], tempA[11], shift[1], tempB[9]);

Mux_2to1 b11(tempA[10], tempA[12], shift[1], tempB[10]);
Mux_2to1 b12(tempA[11], tempA[13], shift[1], tempB[11]);
Mux_2to1 b13(tempA[12], tempA[14], shift[1], tempB[12]);
Mux_2to1 b14(tempA[13], tempA[15], shift[1], tempB[13]);
Mux_2to1 b15(tempA[14], tempA[16], shift[1], tempB[14]);
Mux_2to1 b16(tempA[15], tempA[17], shift[1], tempB[15]);
Mux_2to1 b17(tempA[16], tempA[18], shift[1], tempB[16]);
Mux_2to1 b18(tempA[17], tempA[19], shift[1], tempB[17]);
Mux_2to1 b19(tempA[18], tempA[20], shift[1], tempB[18]);
Mux_2to1 b20(tempA[19], tempA[21], shift[1], tempB[19]);

Mux_2to1 b21(tempA[20], tempA[22], shift[1], tempB[20]);
Mux_2to1 b22(tempA[21], tempA[23], shift[1], tempB[21]);
Mux_2to1 b23(tempA[22], tempA[24], shift[1], tempB[22]);
Mux_2to1 b24(tempA[23], tempA[25], shift[1], tempB[23]);
Mux_2to1 b25(tempA[24], tempA[26], shift[1], tempB[24]);
Mux_2to1 b26(tempA[25], tempA[27], shift[1], tempB[25]);
Mux_2to1 b27(tempA[26], tempA[28], shift[1], tempB[26]);
Mux_2to1 b28(tempA[27], tempA[29], shift[1], tempB[27]);
Mux_2to1 b29(tempA[28], tempA[30], shift[1], tempB[28]);
Mux_2to1 b30(tempA[29], tempA[31], shift[1], tempB[29]);

Mux_2to1 b31(tempA[30], 1'b0, shift[1], tempB[30]);
Mux_2to1 b32(tempA[31], 1'b0, shift[1], tempB[31]);
//====================================

Mux_2to1 c1(tempB[0], tempB[4], shift[2], tempC[0]);
Mux_2to1 c2(tempB[1], tempB[5], shift[2], tempC[1]);
Mux_2to1 c3(tempB[2], tempB[6], shift[2], tempC[2]);
Mux_2to1 c4(tempB[3], tempB[7], shift[2], tempC[3]);
Mux_2to1 c5(tempB[4], tempB[8], shift[2], tempC[4]);
Mux_2to1 c6(tempB[5], tempB[9], shift[2], tempC[5]);
Mux_2to1 c7(tempB[6], tempB[10], shift[2], tempC[6]);
Mux_2to1 c8(tempB[7], tempB[11], shift[2], tempC[7]);
Mux_2to1 c9(tempB[8], tempB[12], shift[2], tempC[8]);
Mux_2to1 c10(tempB[9], tempB[13], shift[2], tempC[9]);

Mux_2to1 c11(tempB[10], tempB[14], shift[2], tempC[10]);
Mux_2to1 c12(tempB[11], tempB[15], shift[2], tempC[11]);
Mux_2to1 c13(tempB[12], tempB[16], shift[2], tempC[12]);
Mux_2to1 c14(tempB[13], tempB[17], shift[2], tempC[13]);
Mux_2to1 c15(tempB[14], tempB[18], shift[2], tempC[14]);
Mux_2to1 c16(tempB[15], tempB[19], shift[2], tempC[15]);
Mux_2to1 c17(tempB[16], tempB[20], shift[2], tempC[16]);
Mux_2to1 c18(tempB[17], tempB[21], shift[2], tempC[17]);
Mux_2to1 c19(tempB[18], tempB[22], shift[2], tempC[18]);
Mux_2to1 c20(tempB[19], tempB[23], shift[2], tempC[19]);
Mux_2to1 c21(tempB[20], tempB[24], shift[2], tempC[20]);

Mux_2to1 c22(tempB[21], tempB[25], shift[2], tempC[21]);
Mux_2to1 c23(tempB[22], tempB[26], shift[2], tempC[22]);
Mux_2to1 c24(tempB[23], tempB[27], shift[2], tempC[23]);
Mux_2to1 c25(tempB[24], tempB[28], shift[2], tempC[24]);
Mux_2to1 c26(tempB[25], tempB[29], shift[2], tempC[25]);
Mux_2to1 c27(tempB[26], tempB[30], shift[2], tempC[26]);
Mux_2to1 c28(tempB[27], tempB[31], shift[2], tempC[27]);
Mux_2to1 c29(tempB[28], 1'b0, shift[2], tempC[28]);
Mux_2to1 c30(tempB[29], 1'b0, shift[2], tempC[29]);
Mux_2to1 c31(tempB[30], 1'b0, shift[2], tempC[30]);
Mux_2to1 c32(tempB[31], 1'b0, shift[2], tempC[31]);
//====================================

Mux_2to1 d1(tempC[0], tempC[8], shift[3], tempD[0]);
Mux_2to1 d2(tempC[1], tempC[9], shift[3], tempD[1]);
Mux_2to1 d3(tempC[2], tempC[10], shift[3], tempD[2]);
Mux_2to1 d4(tempC[3], tempC[11], shift[3], tempD[3]);
Mux_2to1 d5(tempC[4], tempC[12], shift[3], tempD[4]);
Mux_2to1 d6(tempC[5], tempC[13], shift[3], tempD[5]);
Mux_2to1 d7(tempC[6], tempC[14], shift[3], tempD[6]);
Mux_2to1 d8(tempC[7], tempC[15], shift[3], tempD[7]);
Mux_2to1 d9(tempC[8], tempC[16], shift[3], tempD[8]);
Mux_2to1 d10(tempC[9], tempC[17], shift[3], tempD[9]);

Mux_2to1 d11(tempC[10], tempC[18], shift[3], tempD[10]);
Mux_2to1 d12(tempC[11], tempC[19], shift[3], tempD[11]);
Mux_2to1 d13(tempC[12], tempC[20], shift[3], tempD[12]);
Mux_2to1 d14(tempC[13], tempC[21], shift[3], tempD[13]);
Mux_2to1 d15(tempC[14], tempC[22], shift[3], tempD[14]);
Mux_2to1 d16(tempC[15], tempC[23], shift[3], tempD[15]);
Mux_2to1 d17(tempC[16], tempC[24], shift[3], tempD[16]);
Mux_2to1 d18(tempC[17], tempC[25], shift[3], tempD[17]);
Mux_2to1 d19(tempC[18], tempC[26], shift[3], tempD[18]);
Mux_2to1 d20(tempC[19], tempC[27], shift[3], tempD[19]);
Mux_2to1 d21(tempC[20], tempC[28], shift[3], tempD[20]);

Mux_2to1 d22(tempC[21], tempC[29], shift[3], tempD[21]);
Mux_2to1 d23(tempC[22], tempC[30], shift[3], tempD[22]);
Mux_2to1 d24(tempC[23], tempC[31], shift[3], tempD[23]);
Mux_2to1 d25(tempC[24], 1'b0, shift[3], tempD[24]);
Mux_2to1 d26(tempC[25], 1'b0, shift[3], tempD[25]);
Mux_2to1 d27(tempC[26], 1'b0, shift[3], tempD[26]);
Mux_2to1 d28(tempC[27], 1'b0, shift[3], tempD[27]);
Mux_2to1 d29(tempC[28], 1'b0, shift[3], tempD[28]);
Mux_2to1 d30(tempC[29], 1'b0, shift[3], tempD[29]);
Mux_2to1 d31(tempC[30], 1'b0, shift[3], tempD[30]);
Mux_2to1 d32(tempC[31], 1'b0, shift[3], tempD[31]);
//====================================

Mux_2to1 e1(tempD[0], tempD[16], shift[4], out[0]);
Mux_2to1 e2(tempD[1], tempD[17], shift[4], out[1]);
Mux_2to1 e3(tempD[2], tempD[18], shift[4], out[2]);
Mux_2to1 e4(tempD[3], tempD[19], shift[4], out[3]);
Mux_2to1 e5(tempD[4], tempD[20], shift[4], out[4]);
Mux_2to1 e6(tempD[5], tempD[21], shift[4], out[5]);
Mux_2to1 e7(tempD[6], tempD[22], shift[4], out[6]);
Mux_2to1 e8(tempD[7], tempD[23], shift[4], out[7]);
Mux_2to1 e9(tempD[8], tempD[24], shift[4], out[8]);
Mux_2to1 e10(tempD[9], tempD[25], shift[4], out[9]);

Mux_2to1 e11(tempD[10], tempD[26], shift[4], out[10]);
Mux_2to1 e12(tempD[11], tempD[27], shift[4], out[11]);
Mux_2to1 e13(tempD[12], tempD[28], shift[4], out[12]);
Mux_2to1 e14(tempD[13], tempD[29], shift[4], out[13]);
Mux_2to1 e15(tempD[14], tempD[30], shift[4], out[14]);
Mux_2to1 e16(tempD[15], tempD[31], shift[4], out[15]);
Mux_2to1 e17(tempD[16], 1'b0, shift[4], out[16]);
Mux_2to1 e18(tempD[17], 1'b0, shift[4], out[17]);
Mux_2to1 e19(tempD[18], 1'b0, shift[4], out[18]);
Mux_2to1 e20(tempD[19], 1'b0, shift[4], out[19]);
Mux_2to1 e21(tempD[20], 1'b0, shift[4], out[20]);

Mux_2to1 e22(tempD[21], 1'b0, shift[4], out[21]);
Mux_2to1 e23(tempD[22], 1'b0, shift[4], out[22]);
Mux_2to1 e24(tempD[23], 1'b0, shift[4], out[23]);
Mux_2to1 e25(tempD[24], 1'b0, shift[4], out[24]);
Mux_2to1 e26(tempD[25], 1'b0, shift[4], out[25]);
Mux_2to1 e27(tempD[26], 1'b0, shift[4], out[26]);
Mux_2to1 e28(tempD[27], 1'b0, shift[4], out[27]);
Mux_2to1 e29(tempD[28], 1'b0, shift[4], out[28]);
Mux_2to1 e30(tempD[29], 1'b0, shift[4], out[29]);
Mux_2to1 e31(tempD[30], 1'b0, shift[4], out[30]);
Mux_2to1 e32(tempD[31], 1'b0, shift[4], out[31]);
 
//====================================

endmodule








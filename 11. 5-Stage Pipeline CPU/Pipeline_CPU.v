`timescale 1ns/1ns
module Pipeline_CPU( clk, rst );

input clk, rst;

wire [31:0] pc, pc_next, pc_add, id_pc, pc_branch, pc_branch_src, pc_jump, pc_jump_src;
wire [31:0] instr, id_instr;
wire pc_src, jump;

Reg_32bit pc_reg( .clk(clk), .rst(rst), .en_reg(1'b1), .in(pc_next), .out(pc) );

Memory instrMem( .clk(clk), .memRead(1'b1), .memWrite(1'b0), .addr(pc), .wd(), .rd(instr) );

ALU pc_alu( .sel(6'd32), .a(pc), .b(32'd4), .sout(pc_add), .zero() );

Mux_2to1 branch_mux( .in0(pc_add), .in1(pc_branch), .sel(pc_src), .out(pc_branch_src) );

Mux_2to1 jump_mux( .in0(pc_branch_src), .in1(pc_jump_src), .sel(jump), .out(pc_next) );

IF_ID if_id( .clk(clk), .rst(rst), .pcIn(pc_add), .pcOut(id_pc), .rdIn(instr), .rdOut(id_instr) );

wire [5:0] op_code, id_funct;
wire [4:0] rs, rt, rd;
wire [15:0] immediate;
wire [25:0] jump_address;
wire [27:0] jump_address_sh;

wire [31:0] id_rd1, id_rd2, extnd_immediate, pc_immediate;

wire id_aluSrc, id_regDst, id_ori, branch, id_memWrite, id_memRead, id_memToReg, id_regWrite, zero, jr_src;
wire[1:0] id_aluOP;

wire [31:0] ex_rd1, ex_rd2, ex_immediate;
wire ex_aluSrc, ex_regDst, ex_ori, ex_memWrite, ex_memRead, ex_memToReg, ex_regWrite;
wire [1:0] ex_aluOP;
wire [4:0] ex_rt, ex_rd;

wire [31:0] wb_wd;
wire [4:0] wb_wn;
wire wb_regWrite;

assign op_code = id_instr[31:26];
assign rs = id_instr[25:21];
assign rt = id_instr[20:16];
assign rd = id_instr[15:11];
assign immediate = id_instr[15:0];
assign jump_address = id_instr[25:0];
assign id_funct = id_instr[5:0];


Control_unit conU( .opcode(op_code), .funct(id_funct), .aluSrc(id_aluSrc), .regDst(id_regDst), .aluOP(id_aluOP),
                   .ori(id_ori), .branch(branch), .memWrite(id_memWrite), .memRead(id_memRead),
                   .memToReg(id_memToReg), .regWrite(id_regWrite), .jump(jump), .jrDst(jr_src) );

Reg_file regFile( .clk(clk), .regWrite(wb_regWrite), .rn1(rs), .rn2(rt), .wn(wb_wn), .wd(wb_wd), .rd1(id_rd1), .rd2(id_rd2) );

assign jump_address_sh = jump_address << 2;

CONCAT concat( .jmpaddr(jump_address_sh), .pcaddr(id_pc), .sout(pc_jump) );

Mux_2to1 jump_Addr_Mux( .in0(pc_jump), .in1(id_rd1), .sel(jr_src), .out(pc_jump_src) );


EXTND extnd( .in(immediate), .out(extnd_immediate) );

assign pc_immediate = extnd_immediate << 2;
assign pc_branch = id_pc + pc_immediate;

ALU sub( .sel(6'd34), .a(id_rd1), .b(id_rd2), .sout(), .zero(zero) );

assign pc_src = zero & branch;

ID_EX id_ex( .clk(clk), .rst(rst), .aluSrcIn(id_aluSrc), .aluSrcOut(ex_aluSrc), .regDstIn(id_regDst), .regDstOut(ex_regDst),
             .aluOPIn(id_aluOP), .aluOPOut(ex_aluOP), .oriIn(id_ori), .oriOut(ex_ori), .memWriteIn(id_memWrite),
             .memWriteOut(ex_memWrite), .memReadIn(id_memRead), .memReadOut(ex_memRead), .memToRegIn(id_memToReg),
             .memToRegOut(ex_memToReg), .regWriteIn(id_regWrite), .regWriteOut(ex_regWrite),
	     .extendIn(extnd_immediate), .extendOut(ex_immediate), .rd1In(id_rd1), .rd1Out(ex_rd1), .rd2In(id_rd2),
             .rd2Out(ex_rd2), .rn2In(rt), .rn2Out(ex_rt), .wnIn(rd), .wnOut(ex_rd) );

wire [63:0] div_ans;
wire [31:0] alu_input_b, ex_aluAns, ex_shAns, ex_aluorSh_ans, ex_ans, hi, lo, div_out_ans;
wire [5:0] funct, ALU_sel;
wire [4:0] shamt, ex_wn;
wire ALUorSH, ALUorDE, HiorLo, div_rst, div_control_rst, HiLo_ctrl;

wire mem_memRead, mem_memWrite, mem_memToReg, mem_regWrite;
wire [31:0] mem_ans, mem_rd2;
wire [4:0] mem_wn;


assign funct = ex_immediate[5:0];
assign shamt = ex_immediate[10:6];

ALU_Control aluControl( .clk(clk), .ALU_op(ex_aluOP), .funct(funct), .Ori(ex_ori), .ALU_sel(ALU_sel), .ALUorSH(ALUorSH),
                        .ALUorDE(ALUorDE), .HiorLo(HiorLo), .div_rst(div_control_rst), .HiLo_ctrl(HiLo_ctrl) );

Mux_2to1 aluInputMux( .in0(ex_rd2), .in1(ex_immediate), .sel(ex_aluSrc), .out(alu_input_b) );

ALU alu( .sel(ALU_sel), .a(ex_rd1), .b(alu_input_b), .sout(ex_aluAns), .zero() );

Shifter shifter( .a(ex_rd2), .shift(shamt), .out(ex_shAns) );

or ( div_rst, rst, div_control_rst );

Divider divider( .clk(clk), .reset(div_rst), .dataA(ex_rd1), .dataB(ex_rd2), .dout(div_ans) );

HiLo hilo( .clk(clk), .rst(rst), .ctrl(HiLo_ctrl), .divAns(div_ans), .Hi(hi), .Lo(lo) );


Mux_2to1 hiOrLoMux( .in0(lo), .in1(hi), .sel(HiorLo), .out(div_out_ans) );

Mux_2to1 aluOrShMux( .in0(ex_aluAns), .in1(ex_shAns), .sel(ALUorSH), .out(ex_aluorSh_ans) );

Mux_2to1 aluOrDeMux( .in0(ex_aluorSh_ans), .in1(div_out_ans), .sel(ALUorDE), .out(ex_ans) );

Mux_2to1 wnMux( .in0(ex_rt), .in1(ex_rd), .sel(ex_regDst), .out(ex_wn) );

EX_MEM ex_mem( .clk(clk), .rst(rst), .memWriteIn(ex_memWrite), .memWriteOut(mem_memWrite), .memReadIn(ex_memRead),
               .memReadOut(mem_memRead), .memToRegIn(ex_memToReg), .memToRegOut(mem_memToReg),
               .regWriteIn(ex_regWrite), .regWriteOut(mem_regWrite), .aluIn(ex_ans), .aluOut(mem_ans),
               .rd2In(ex_rd2), .rd2Out(mem_rd2), .wnIn(ex_wn), .wnOut(mem_wn) );

wire [31:0] mem_readData, wb_memRead, wb_ans;
wire wb_memToReg;

Memory dataMemory( .clk(clk), .memRead(mem_memRead), .memWrite(mem_memWrite), .addr(mem_ans), .wd(mem_rd2), .rd(mem_readData) );

MEM_WB mem_wb( .clk(clk), .rst(rst), .regWriteIn(mem_regWrite), .regWriteOut(wb_regWrite), 
               .memToRegIn(mem_memToReg), .memToRegOut(wb_memToReg), 
               .rdIn(mem_readData), .rdOut(wb_memRead), .aluIn(mem_ans), .aluOut(wb_ans), .wnIn(mem_wn), .wnOut(wb_wn) );

Mux_2to1 wdMux( .in0(wb_ans), .in1(wb_memRead), .sel(wb_memToReg), .out(wb_wd) );

// assign pc_src = rst ? 0 : pc_src;

endmodule

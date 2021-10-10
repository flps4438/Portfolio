			.data
title:		.asciz "*****Print Name*****\n"
teamprint:  .asciz "Team 34\n"
member01:  	.asciz "Chih Chung, Hsu\n"
member02:  	.asciz "Wayne, Lin\n"
member03:  	.asciz "Chih Chung, Hsu\n"
endprint:	.asciz "*****End Print*****\n"
			.text
			.global name
name:
	stmfd	sp!, {lr}
	ldr		r0, =title
	bl		printf
	ldr		r0, =teamprint
	bl		printf
	mov		r3,	#3
	mov		r4, #0
	
loop:
	mrs		r0, cpsr
	bic		r0, r0, #0xF0000000
	msr		cpsr_f, r0
	sbcs	r0, r3, r4
	cmp		r0, #1
	mov		r5,	r0
	ldrgt	r0, =member01
	ldreq	r0, =member02
	ldrlt	r0, =member03
	bl		printf
	mov		r3, r5
	cmp		r3, #0
	bne 	loop
	
	ldr		r0, =endprint
	bl		printf
	
	ldmfd	sp!, {lr}
	ldr     r0, =teamprint
	ldr     r1, =member01
	ldr     r2, =member02
	ldr     r3, =member03
	
	mov		pc, lr

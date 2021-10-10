				.data
idFmtInput: 	.string "%d"
coFmtInput: 	.string "%c"
textLine1: 		.asciz "*****Input ID*****\n"
textInput: 		.asciz "** Please Enter Member %d ID: **\n"
textCommand: 	.asciz "** Please Enter Command **\n"
textresult1: 	.asciz "***** Print Team ID and ID Summation *****\n"
textresult2: 	.asciz "***** End Print *****\n"
textID:			.asciz "%d\n%d\n%d\n\n"
resultTextOut: 	.asciz "ID Summation = %d\n"
id1: 			.word  0
id2: 			.word  0
id3: 			.word  0
sum:			.word  0
cin:			.word  '0'
				.text
				.globl id
id:
		stmfd 	sp!, {lr}
		ldr 	r0, =textLine1
		bl 		printf
		mov 	r8, #0
	
scid:   cmp     r8, #3
        bge     inC
        ldr     r0, =textInput
        mov     r1, r8
        add     r1, r1, #1
        bl      printf
		ldr     r0, =idFmtInput
        cmp		r8, #1
		ldrlt	r1, =id1
		ldrge	r1, =id2
		ldrgt	r1, =id3
		bl      scanf
        add     r8, r8, #1
        b       scid
inC:
		@ read \n
		ldr		r0, =coFmtInput
		ldr		r1, =cin
		bl 		scanf
		ldr		r1, =cin
		ldr		r1, [r1]
		cmp     r1, #10
		bne     inC
		@ input command
		ldr		r0, =textCommand
		bl 		printf
		@ read command
		ldr		r0, =coFmtInput
		ldr		r1, =cin
		bl 		scanf
		ldr		r1, =cin
		ldr		r1, [r1]
		@ cmp input command
		ldr		r1, =cin
		ldr		r1, [r1]
		cmp		r1, #112
		beq	    after
		bne		inC
after:
		ldr		r0, =textresult1
		bl		printf
		ldr		r8, =id1
		ldr		r9, =id2
		ldr		r10, =id3
		sub		r0, r9, r8
        ldr		r9, [r8, r0]
		sub		r0,	r10, r8
		mov		r11, r8
		ldr		r8, [r11], r0
        ldr		r10, [r11]
		ldr		r0, =textID
		mov		r1, r8
		mov		r2, r9
		mov		r3, r10
		bl		printf
		@ add sum
		add		r1, r8, r9
		add		r1, r1, r10
		ldr		r0, =sum
		str		r1, [r0]
		@ print ans
		ldr		r0, =resultTextOut
		ldr 	r1, = sum
		ldr 	r1, [r1]
		bl		printf
		ldr		r0, =textresult2
		bl		printf
		
		ldmfd sp!, {lr}
		
		ldr     r0, =sum
        ldr     r1, =id1
        ldr     r2, =id2
        ldr     r3, =id3
		mov pc, lr
		
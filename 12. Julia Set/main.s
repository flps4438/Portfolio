    .data
num:    .asciz"%d"
idname:	.asciz"%d   %s"
str:	.asciz"%s\n"
title:  .asciz"\nMain Function:\n*****Print All*****\n"
end:   	.asciz"\n*****End Print***** \n"
sumid:	.asciz"ID Summation = %d\n"
nametitle:	.asciz "Function1: Name\n"
idtitle:	.asciz "Function2: ID\n"
    .text
	.global main

main:
    stmfd	sp!, {lr}

	ldr 	r0,=nametitle
    bl  	printf

	bl 		name
	stmfd 	sp!, {r0-r3}
	ldmfd 	sp!, {r4-r7}
	
	ldr 	r0, =idtitle
    bl  	printf
	
	bl  	id
	stmfd 	sp!, {r0-r3}
	ldmfd 	sp!, {r8-r11}

	ldr 	r0, =title
    bl  	printf

	ldr 	r0, =str
	mov 	r1, r4
    bl  	printf

	ldr 	r0,	=idname
    mov 	r1,	r9
    ldr 	r1, [r1]
	mov		r2,	r5
    bl  	printf

    ldr 	r0,	=idname
    mov 	r1,	r10
    ldr 	r1,	[r1]
	mov		r2,	r6
    bl  	printf

    ldr 	r0,	=idname
    mov 	r1,	r11
    ldr 	r1, [r1]
	mov		r2,	r7
    bl  	printf

	ldr 	r0,	=sumid
	mov		r1,	r8
	ldr 	r1, [r1]
    bl  	printf

    ldr 	r0, =end
    bl 		printf

	ldmfd   sp!,{lr}
    mov     pc,lr


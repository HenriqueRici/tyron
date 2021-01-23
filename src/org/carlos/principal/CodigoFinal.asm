section .data
	input: db "%d",0
	output: db "%d",10,0
	msg0: db "Hello World!!!",10,0

section .text
	global _main
	extern _printf
	extern _scanf
_main:
	push msg0
	call _printf
	add esp, 4
ret
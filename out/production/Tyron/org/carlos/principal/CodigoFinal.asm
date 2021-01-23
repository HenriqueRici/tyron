section .data
	input: db "%d",0
	output: db "%d",10,0
	numero: dd 0
	fatorial: dd 0
	resultado: dd 0
	msg0: db "Entre com um numero",10,0

section .text
	global _main
	extern _printf
	extern _scanf
_main:
	mov eax, 1
	mov dword[resultado], eax

	push msg0
	call _printf
	add esp, 4

	push numero
	push input
	call _scanf
	add esp, 8

.L1:
	mov eax, dword[fatorial]
	cmp eax, dword[numero]
	jge .L2

	mov eax, dword[fatorial]
	mov ecx, 1
	add eax, ecx
	mov dword[fatorial], eax

	mov eax, dword[resultado]
	mov ecx, dword[fatorial]
	mul ecx
	mov dword[resultado], eax

	jmp .L1

.L2:
	mov ebx, dword[resultado]
	push ebx
	push output
	call _printf
	add esp, 8

ret
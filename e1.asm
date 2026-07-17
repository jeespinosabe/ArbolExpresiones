; Espinosa Benitez Jesus
.model small
.stack
.data
a dw 1
b dw 2
c dw 3
T1 dw ?
T2 dw ?
.code 
mov ax,@data 
mov ds,ax 
;======== MULTIPLICACION ==========
xor ax,ax
xor bx,bx
xor dx,dx
mov ax,b
mov bx,c
mul bx
mov T1,ax

;======== SUMA ==========
xor ax,ax
xor bx,bx
mov ax,a
mov bx,T1
add ax,bx
mov T2,ax


mov ax,4c00h 
int 21h 
end
mov ax,4c00h 
int 21h 
end
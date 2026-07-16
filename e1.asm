; Espinosa Benitez Jesus
.model small
.stack
.data
a dw 1
b dw 2
.code 
mov ax,@data 
mov ds,ax 
mov ax, a
mov bx, b
add ax,bx


mov ax,4c00h 
int 21h 
end
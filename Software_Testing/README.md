# Software_Testing
This is the project for COEN 6761
Imagine a robot that walks around a room under the control of a Java program. The robot holds a pen in one of two positions, up or down. While the pen is down, the robot traces out shapes as it moves; while the pen is up, the robot moves about freely without writing anything. The room will be represented by an N by N array called floor that is initialized to zeros. Initially the robot is at position [0, 0] of the floor, its pen is up, and is facing north. The robot moves around the floor (i.e. the array) as it receives commands from the user. The set of robot commands your program must process are as follows:
Command Meaning [U|u] Pen up 
[D|d] Pen down 
[R|r] Turn right 
[L|l] Turn left 
[M s|m s] Move forward s spaces (s is a non-negative integer) 
[P|p] Print the N by N array and display the indices 
[C|c] Print current position of the pen and whether it is up or down and its facing direction 
[Q|q] Stop the program 
[I n|i n] Initialize the system: The values of the array floor are zeros and the robot is back to [0, 0], pen up and facing north. n size of the array, an integer greater.
By default, input of command [M s|m s] and [I n|i n] should follow the format of command 
character following by one space and then an integer.

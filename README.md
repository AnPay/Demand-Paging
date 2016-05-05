#################################
#                               #
# OS Lab 4 Running Instructions # 
#                               #
#################################

Name: Tianshu Lyu
NetID: tl1443

This project implements demand paging in memory management. The program mainly puts pages in processes' virtual memories to frames in the physical memory when they are used by the computer. It calculates the page faults (page not in the frame when referencing) happening during running and the average residency time in the machine of different processes. The most important part is the replacement algorithm when a page is using but there is no place for it in the machine. It implememts three replacement algorithms, LRU (least recent used), random (simply using a random frame) and LIFO (last in first out).

The following is the instruction for compiling and running the program:

1. Download the OSLab4 directory from access.cims.nyu.edu or from NYU Classes.
2. Open the terminal and use cd command to enter the directory which contains all java files. (e.g.: cd /Desktop/OSLab4)
3. Compile the java project with command: javac Paging.java
4. Run the program with command:
if you want the simple output: 
java Paging 10 10 20 1 10 lru 0

if you want the detailed result with process status:
java Paging 10 10 20 1 10 lru 1

if you want the detailed result with also all the random numbers used:
java Paging 10 10 20 1 10 lru 11

(After Paging first is the general arguments necessary for the running of the program the same as in the requirement.
Only the last digit is different. 0 for the simplest one, 1 for the detailed result and 11 for the most detailed one with random numbers included)

5. Then the program will print out the proper result:
if it's a simple output (with 0 in the last digit):
Just the number of page faults and average residency time for each process and total.

if it's a detailed result (with 1 in the last digit):
The program will first display the result of the reference in each cycle.
Following that is the statistical results the same as the simplest output.

if it's the most detailed result (with 11 in the last digit):
The program will first display the resut of the reference in each cycle also with the random number used by the process every time a new random number is used.
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Project {

    public static List<String> validInputs = new ArrayList<>(Arrays.asList("U","D", "R", "L", "P", "C", "Q"));
   
    /* Changing the direction of the robot according to the user inputs.
     * Here 4 means East direction,
     * 3 means West direction,
     * 2 means South direction,
     * 1 means North direction.
     */
    public static int getDirection(int currentDirection, String currentMove) {
        if (currentMove.equalsIgnoreCase("R")) {
            if (currentDirection == 1) {
                return 4;                     
            } else if (currentDirection == 2) {
                return 3;                     
            } else if (currentDirection == 3) {
                return 1;                     
            } else if (currentDirection == 4) {
                return 2;                     
            }
        } else {
            if (currentDirection == 1) {
                return 3;
            } else if (currentDirection == 2) {
                return 4;
            } else if (currentDirection == 3) {
                return 2;
            } else if (currentDirection == 4) {
                return 1;
            }
        }
        return 1;
    }
    
    /* This method performs the remaining commands given the user.
     * [U|u] Pen up 
     * [D|d] Pen down 
     * [R|r] Turn right 
     * [L|l] Turn left 
     * [M s|m s] Move forward s spaces (s is a non-negative integer)
     */

    public static int[] getCoordinates(int[][] outputArray, int x, int y, String currentMove, int currentDirection, int penState) {
    	int len = outputArray.length;
        if (currentMove.equalsIgnoreCase("U")) {
            return new int[]{x, y, currentDirection, 0};
        } else if (currentMove.equalsIgnoreCase("D")) {
            outputArray[x][y] = 1;
            return new int[]{x, y, currentDirection, 1};
        } else if (currentMove.equalsIgnoreCase("R")) {
            return new int[]{x, y, Project.getDirection(currentDirection, currentMove), penState};
        } else if (currentMove.equalsIgnoreCase("L")) {
            return new int[]{x, y, Project.getDirection(currentDirection, currentMove), penState};
        } else if (currentMove.length() > 1) {
            if (currentMove.charAt(0) == 'M' || currentMove.charAt(0) == 'm') {
                int numberOfSpaces = Integer.valueOf(currentMove.substring(1).trim());
                if (currentDirection == 1 && x+numberOfSpaces < len) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x + numberOfSpaces, y, currentDirection, penState};
                } else if (currentDirection == 2 && x-numberOfSpaces >= 0) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x - numberOfSpaces, y, currentDirection, penState};
                } else if (currentDirection == 3 && y-numberOfSpaces >= 0) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x, y - numberOfSpaces, currentDirection, penState};
                } else if (currentDirection == 4 && y+numberOfSpaces < len) {
                    if (penState == 1) {
                        Project.draw(outputArray, currentDirection, numberOfSpaces, x, y);
                    }
                    return new int[]{x, y + numberOfSpaces, currentDirection, penState};
                }else {
                	System.out.println("Out of bound. Please give the input inside the floor bounds.");
                	return new int[]{x, y , currentDirection, penState};
                }

            }
        }
        return new int[0];
    }
    /*
     * Tracking the robot movement along the floor.
     */
     public static void draw(int[][] outputArray, int currentDirection, int numberOfSpaces, int x, int y) {
         if (currentDirection == 1) {
             for (int index = x + 1; index <= (x + numberOfSpaces); index++) {
                 outputArray[index][y] = 1;
             }
         } else if (currentDirection == 2) {
             for (int index = x - 1; index >= (x - numberOfSpaces); index--) {
                 outputArray[index][y] = 1;
             }
         } else if (currentDirection == 3) {
             for (int index = y - 1; index >= (y - numberOfSpaces); index--) {
                 outputArray[x][index] = 1;
             }
         } else if (currentDirection == 4) {
             for (int index = y + 1; index <= (y + numberOfSpaces); index++) {
                 outputArray[x][index] = 1;
             }
         }
     }

    // Printing the array.
    public static void printMyArray(int[][] arr) {
        int arrLen = arr.length;
        int[][] finalArray = new int[arrLen][arrLen];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == 1) {
                    finalArray[arrLen - 1 - i][j] = 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                sb.append((finalArray[i][j] == 1 ? "*":" ") + " ");
            	//sb.append(finalArray[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
    // Checking the inputs from the user.
    public static boolean isInputValid(String input) {
        if (Project.validInputs.contains(input.toUpperCase()) || input.matches("^(M|m)\\s[0-9]+$") 
        		|| input.matches("^(I|i)\\s[0-9]+$")) {
            return true;
        }
        return false;
    }
    /* Taking Inputs from the user.
     * If the user input is not Q||q, then checking If user input is valid or not,
     * Initialize the robot array floor, Print the current status of the board,
     * Call the getCoordinate() method for other inputs. 
     * [I n|i n] Initialize the system: The values of the array floor are zeros and the robot 
     *   is back to [0, 0], pen up and facing north. n size of the array, an integer greater than zero.
     * [C|c] Print current position of the pen and whether it is up or down and its 
     *   facing direction.
     * [P|p] Print the N by N array and display the indices
     * */ 

     
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter command: \r\n");
        String s = sc.nextLine();
        int[][] outputArray = new int[0][0];
        int[] trackingArray = new int[]{0, 0, 1, 0};

        while (!s.equalsIgnoreCase("Q")) {
            if (!isInputValid(s)) {
                System.out.println("Invalid Input");
                return;
            }
            if (s.length() > 1 && (s.charAt(0) == 'I' || s.charAt(0) == 'i')) {
                int sizeOf2DArray = Integer.valueOf(s.substring(1).trim());
                outputArray = new int[sizeOf2DArray][sizeOf2DArray];
                trackingArray[0] = 0;     // x which is rows
                trackingArray[1] = 0;     // y which is columns
                trackingArray[2] = 1;     // Current Direction
                trackingArray[3] = 0;     // PenUprDown
            } else if (s.equalsIgnoreCase("C")) {
                String dir;
                if (trackingArray[2] == 1) {
                    dir = "north";
                } else if (trackingArray[2] == 2) {
                    dir = "south";
                } else if (trackingArray[2] == 3) {
                    dir = "west";
                } else dir = "east";
                String penState = trackingArray[3] == 0 ? "up" : "down";
                System.out.println("Position: " + trackingArray[1] + ", " + trackingArray[0] + " - Pen: " + penState + " - Facing: " + dir);
            } else if (s.equalsIgnoreCase("P")) {
                printMyArray(outputArray);
            } else {
                int result[] = getCoordinates(outputArray, trackingArray[0], trackingArray[1], s, trackingArray[2], trackingArray[3]);
                trackingArray[0] = result[0];
                trackingArray[1] = result[1];
                trackingArray[2] = result[2];
                trackingArray[3] = result[3];
            }

            System.out.print("Enter command: \r\n");
            s = sc.nextLine();
        }
    }
    }


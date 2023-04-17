import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class QATest {

    private static ByteArrayOutputStream outContent;
    private static PrintStream originalOut;

    private static InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void getDirectionTest() {
        int i = 2;
        String k = "R";
        int r = Project.getDirection(i,k);
        int expected = 3;
        assertEquals(expected,r);
    }

    @Test
    void getCoordinates() {
        int[][] outputArray = new int[5][5];

        assertArrayEquals(new int[]{0, 0, 1, 0}, Project.getCoordinates(outputArray, 0, 0, "U", 1, 1));
        assertArrayEquals(new int[]{0, 0, 1, 1}, Project.getCoordinates(outputArray, 0, 0, "D", 1, 0));
        assertArrayEquals(new int[]{0, 0, 4, 0}, Project.getCoordinates(outputArray, 0, 0, "R", 1, 0));
        assertArrayEquals(new int[]{0, 0, 3, 0}, Project.getCoordinates(outputArray, 0, 0, "L", 1, 0));

        // Test moving forward when pen is up
        assertArrayEquals(new int[]{3, 0, 1, 0}, Project.getCoordinates(outputArray, 0, 0, "M 3", 1, 0));
        // Test moving forward when pen is down
        assertArrayEquals(new int[]{3, 0, 1, 1}, Project.getCoordinates(outputArray, 0, 0, "M 3", 1, 1));
        // Test moving forward with invalid move
        assertArrayEquals(new int[]{0, 0, 1, 0}, Project.getCoordinates(outputArray, 0, 0, "M 6", 1, 0));
    }

    void getCoordinatesTest() {
        int x = 1;
        int y = 2;
        int currentDirection = 1;
        int penState = 1;
        int[][] outputArray = new int[0][];
        String currentMove = "U";
        int[] expected = {1,2,1,0};
        int[] actual = Project.getCoordinates(outputArray, x, y, currentMove, currentDirection, penState);
        assertArrayEquals(expected, actual);
    }


    @Test
    void stringInputTest() throws Exception {
        String input = "I 5\nC\nM 2\nC\nP\nQ";
        InputStream stream = new ByteArrayInputStream(input.getBytes
                (Charset.forName("UTF-8")));
        System.setIn(stream);
        String expected = "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 0 - Pen: up - Facing: north\r\n" +
                "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 2 - Pen: up - Facing: north\r\n" +
                "Enter command: \r\n"+
                "          \n" +
                "          \n" +
                "          \n" +
                "          \n" +
                "          \n" +
                "\r\n"+
                "Enter command: \r\n";
        String args[] = new String[0];
        Project.main(args);
        assertEquals(expected,outContent.toString());
    }

    //tests initialization state if it's at 0,0
    @Test
    void stringInputTest2() throws Exception{
        String input = "I 10\nC\nQ";
        InputStream stream = new ByteArrayInputStream(input.getBytes
                (Charset.forName("UTF-8")));
        System.setIn(stream);
        String expected = "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 0 - Pen: up - Facing: north\r\n"+
                "Enter command: \r\n";
        String args[] = new String[0];
        Project.main(args);
        assertEquals(expected, outContent.toString());
    }
    @Test
    void draw() {
    }

    @Test
    void printMyArray() {
    }

    @Test
    void isInputValid() {
        assertTrue(Project.isInputValid("U"));
        assertTrue(Project.isInputValid("D"));
        assertTrue(Project.isInputValid("R"));
        assertTrue(Project.isInputValid("L"));
        assertTrue(Project.isInputValid("P"));
        assertTrue(Project.isInputValid("C"));

    }

    @Test
    void testDraw() {
        int[][] outputArray = new int[5][5];

        // Draw vertical line
        Project.draw(outputArray, 1, 2, 0, 0);

        // Expected output
        int[][] expected1 = new int[][]{{0, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        assertArrayEquals(expected1, outputArray);
    }

    @Test
    public void printMyArray() {
        int[][] outputArray = new int[][]{{0, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {1, 0, 0, 0, 0}, {1, 0, 0, 0, 0}};
        String expected = "*         \n" + "*         \n" + "          \n" + "*         \n" + "          \n" + "\r\n";
        Project.printMyArray(outputArray);
        assertEquals(expected, outContent.toString());
    }
    @Test
    void  testDirection(){
        int test1 = Project.getDirection(1, "R");
        assertEquals(4, test1);
        int test2 = Project.getDirection(2, "R");
        assertEquals(3, test2);
        int test3 = Project.getDirection(3, "R");
        assertEquals(1, test3);
        int test4 = Project.getDirection(4, "R");
        assertEquals(2, test4);
        int test5 = Project.getDirection(1, "L");
        assertEquals(3, test5);
        int test6 = Project.getDirection(2, "ABC");
        assertEquals(4, test6);
        int test7 = Project.getDirection(3, "ABCDE");
        assertEquals(2, test7);
        int test8 = Project.getDirection(4, "U");
        assertEquals(1, test8);
        int test9 = Project.getDirection(5, "R");
        assertEquals(1, test9);
        int test10 = Project.getDirection(5, "L");
        assertEquals(1, test10);

    }

    @Test
    void stringInputTest1() throws Exception {
        String input = "I 5\nC\nM 2\nC\nP\nQ";
        InputStream stream = new ByteArrayInputStream(input.getBytes
                (Charset.forName("UTF-8")));
        System.setIn(stream);
        String expected = "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 0 - Pen: up - Facing: north\r\n" +
                "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 2 - Pen: up - Facing: north\r\n" +
                "Enter command: \r\n"+
                "          \n" +
                "          \n" +
                "          \n" +
                "          \n" +
                "          \n" +
                "\r\n"+
                "Enter command: \r\n";
        String args[] = new String[0];
        Project.main(args);
        assertEquals(expected,outContent.toString());
    }

    @Test
    public void stringInputTest2() throws Exception{
        String input = "I 10\nL\nC\nM 4\nC\nR\nC\nD\nM 3\nC\nP\nQ";
        InputStream stream = new ByteArrayInputStream(input.getBytes
                (Charset.forName("UTF-8")));
        System.setIn(stream);
        String expected = "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 0 - Pen: up - Facing: west\r\n" +
                "Enter command: \r\n"+
                "Out of bound. Please give the input inside the floor bounds.\r\n" +
                "Enter command: \r\n"+
                "Position: 0, 0 - Pen: up - Facing: west\r\n" +
                "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 0 - Pen: up - Facing: north\r\n" +
                "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Enter command: \r\n"+
                "Position: 0, 3 - Pen: down - Facing: north\r\n" +
                "Enter command: \r\n"+
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "*                   \n" +
                "*                   \n" +
                "*                   \n" +
                "*                   \n" +
                "\r\n"+
                "Enter command: \r\n";
        String args[] = new String[0];
        Project.main(args);
        assertEquals(expected, outContent.toString());
    }
//    @Test
//    void testInitialization(){
//        String[] args = null;
//        Project.main()
//
//    }
}
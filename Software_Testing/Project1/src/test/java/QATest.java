

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.Project;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
    }

    @Test
    void draw() {
    }

    @Test
    void printMyArray() {
    }

    @Test
    void isInputValid() {
    }

    @Test
    void main() {
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
}
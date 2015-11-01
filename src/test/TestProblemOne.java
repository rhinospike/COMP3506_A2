package test;


import adsa2p1.ProblemOne;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;


public class TestProblemOne {

    // Greedy
    @Test
    public void testTimetable1Greedy() throws IOException {
        timetableTest("Timetables/timetable1", "greedy", 1);
    }

    @Test
    public void testTimetable2Greedy() throws IOException {
        timetableTest("Timetables/timetable2", "greedy", 4);
    }

    @Test
    public void testTimetable8Greedy() throws IOException {
        timetableTest("Timetables/timetable8", "greedy", 4);
    }

    @Test
    public void testTimetable10Greedy() throws IOException {
        timetableTest("Timetables/timetable10", "greedy", 7);
    }

    @Test
    public void testTimetable101Greedy() throws IOException {
        timetableTest("Timetables/timetable10_1", "greedy", 8);
    }

    @Test
    public void testTimetable102Greedy() throws IOException {
        timetableTest("Timetables/timetable10_2", "greedy", 6);
    }

    @Test
    public void testTimetable103Greedy() throws IOException {
        timetableTest("Timetables/timetable10_3", "greedy", 6);
    }

    @Test
    public void testTimetable104Greedy() throws IOException {
        timetableTest("Timetables/timetable10_4", "greedy", 7);
    }

    @Test
    public void testTimetable105Greedy() throws IOException {
        timetableTest("Timetables/timetable10_5", "greedy", 5);
    }

    // Optimal
    @Test
    public void testTimetable1Optimal() throws IOException {
        timetableTest("Timetables/timetable1", "optimal", 1);
    }

    @Test
    public void testTimetable2Optimal() throws IOException {
        timetableTest("Timetables/timetable2", "optimal", 4);
    }

    @Test
    public void testTimetable8Optimal() throws IOException {
        timetableTest("Timetables/timetable8", "optimal", 2);
    }

    @Test
    public void testTimetable10Optimal() throws IOException {
        timetableTest("Timetables/timetable10", "optimal", 6);
    }

    @Test
    public void testTimetable101Optimal() throws IOException {
        timetableTest("Timetables/timetable10_1", "optimal", 7);
    }

    @Test
    public void testTimetable102Optimal() throws IOException {
        timetableTest("Timetables/timetable10_2", "optimal", 5);
    }

    @Test
    public void testTimetable103Optimal() throws IOException {
        timetableTest("Timetables/timetable10_3", "optimal", 6);
    }

    @Test
    public void testTimetable104Optimal() throws IOException {
        timetableTest("Timetables/timetable10_4", "optimal", 6);
    }

    @Test
    public void testTimetable105Optimal() throws IOException {
        timetableTest("Timetables/timetable10_5", "optimal", 4);
    }
    
    // Balanced
    @Test
    public void testTimetable1Balanced() throws IOException {
        timetableTest("Timetables/timetable1", "balanced", 1);
    }

    @Test
    public void testTimetable2Balanced() throws IOException {
        timetableTest("Timetables/timetable2", "balanced", 4);
    }

    @Test
    public void testTimetable8Balanced() throws IOException {
        timetableTest("Timetables/timetable8", "balanced", 2);
    }

    @Test
    public void testTimetable10Balanced() throws IOException {
        timetableTest("Timetables/timetable10", "balanced", 6);
    }

    @Test
    public void testTimetable101Balanced() throws IOException {
        timetableTest("Timetables/timetable10_1", "balanced", 7);
    }

    @Test
    public void testTimetable102Balanced() throws IOException {
        timetableTest("Timetables/timetable10_2", "balanced", 5);
    }

    @Test
    public void testTimetable103Balanced() throws IOException {
        timetableTest("Timetables/timetable10_3", "balanced", 6);
    }

    @Test
    public void testTimetable104Balanced() throws IOException {
        timetableTest("Timetables/timetable10_4", "balanced", 6);
    }

    @Test
    public void testTimetable105Balanced() throws IOException {
        timetableTest("Timetables/timetable10_5", "balanced", 4);
    }


    public void timetableTest(String filename, String mode, int expectedRooms) throws IOException {
        ProblemOne.main(new String [] {filename, mode});

        BufferedReader br = new BufferedReader( new FileReader(filename + ".out") );
        String line = br.readLine();
        assertEquals("Resulting number of rooms wansn't as expected",
                expectedRooms,
                Integer.parseInt(line));
        br.close();
    }
}
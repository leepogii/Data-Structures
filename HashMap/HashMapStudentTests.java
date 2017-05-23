import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * These tests are NOT exhaustive. You should definitely write your own.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class HashMapStudentTests {

    private HashMap<MyString, String> directory;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        directory = new HashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        directory.add(new MyString("Jonathan"), "TA: 1332");
        directory.add(new MyString("Monica"), "Professor: 2050");
        directory.add(new MyString("Mary"), "Professor: 1371");
        String prof = directory.add(new MyString("Monica"), "Professor: 1332");
        assertEquals("Professor: 2050", prof);
        directory.add(new MyString("Saikrishna*"), "TA: 1332");
        directory.add(new MyString("BestLang"), "C++");
        String badLanguage = directory.add(new MyString("BestLang"), "Swift");
        assertEquals("Swift is better than C++", "C++", badLanguage);

        assertEquals(5, directory.size());

        MapEntry<MyString, String>[] expected = expectedAddStuffBacking();
        checkBackingArray(expected, directory.toArray());
    }

    @Test (timeout = TIMEOUT)
    public void testAddWithConflicts() {
        directory.add(new MyString("Emily"), "TA: 1332");
        directory.add(new MyString("Carey"), "TA: 1332 (again)");
        directory.add(new MyString("Siddu"), "TA: 1332 (another one)");

        assertEquals(3, directory.size());

        @SuppressWarnings("unchecked")
        MapEntry<MyString, String>[] expected = new MapEntry[11];

        expected[5] = new MapEntry<>(new MyString("Emily"), "TA: 1332");
        expected[5].setNext(new MapEntry<>(new MyString("Carey"),
                    "TA: 1332 (again)"));
        expected[5].getNext().setNext(new MapEntry<>(new MyString("Siddu"),
                    "TA: 1332 (another one)"));

        checkBackingArray(expected, directory.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        addStuff();

        directory.add(new MyString("*Carey MacDonald*"),
                "TA: 1332"); // The *s are for testing purposes
        directory.add(new MyString("Conor Fitzpatrick"), "TA: 2050");
        directory.add(new MyString("ABC"), "DEF!");

        @SuppressWarnings("unchecked")
        MapEntry<MyString, String>[] expected = new MapEntry[23];

        expected[3] = new MapEntry<>(new MyString("ABC"), "DEF!");
        expected[4] = new MapEntry<>(new MyString("Mary"), "Professor: 1371");
        expected[6] = new MapEntry<>(new MyString("Monica"), "Professor: 1332");
        expected[8] = new MapEntry<>(new MyString("Jonathan"), "TA: 1332");
        expected[8].setNext(new MapEntry<>(new MyString("BestLang"), "Swift"));
        expected[10] = new MapEntry<>(new MyString("Saikrishna"), "TA: 1332");
        expected[17] = new MapEntry<>(new MyString("*Carey MacDonald*"),
                "TA: 1332");
        expected[17].setNext(new MapEntry<>(new MyString("Conor Fitzpatrick"),
                "TA: 2050"));

        checkBackingArray(expected, directory.toArray());
        assertEquals(8, directory.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        addStuff();

        assertEquals("TA: 1332", directory.remove(new MyString("Jonathan")));

        MapEntry<MyString, String>[] actuals = directory.toArray();
        assertNotNull(actuals[8]);
        assertEquals(new MyString("BestLang"), actuals[8].getKey());
        assertEquals(4, directory.size());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        addStuff();
        assertEquals("TA: 1332", directory.get(new MyString("Saikrishna")));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNull() {
        directory.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotFound() {
        directory.get(new MyString(""));
    }

    /**
     * Add a baseline of items to the hash map.
     */
    private void addStuff() {
        directory.add(new MyString("Jonathan"), "TA: 1332");
        directory.add(new MyString("Monica"), "Professor: 1332");
        directory.add(new MyString("Mary"), "Professor: 1371");
        directory.add(new MyString("Saikrishna"), "TA: 1332");
        directory.add(new MyString("BestLang"), "Swift");
    }

    /**
     * Get the expected positions and ordering of the entries in the hash map.
     *
     * @return array with expected entries with the baseline
     */
    private static MapEntry<MyString, String>[] expectedAddStuffBacking() {
        @SuppressWarnings("unchecked")
        MapEntry<MyString, String>[] expected = new MapEntry[11];

        expected[0] = new MapEntry<>(new MyString("Saikrishna*"), "TA: 1332");
        expected[4] = new MapEntry<>(new MyString("Mary"), "Professor: 1371");
        expected[6] = new MapEntry<>(new MyString("Monica"), "Professor: 1332");
        expected[8] = new MapEntry<>(new MyString("Jonathan"), "TA: 1332");
        expected[8].setNext(new MapEntry<>(new MyString("BestLang"), "Swift"));
        return expected;
    }

    /**
     * Check to see that the expected {@code MapEntry} array matches the actual
     * {@code MapEntry} array.
     *
     * @param expected expected array
     * @param actual actual array
     */
    private static void checkBackingArray(MapEntry<MyString, String>[] expected,
         MapEntry<MyString, String>[] actual) {
        assertEquals(expected.length, actual.length);
        for (int x = 0; x < expected.length; x++) {
            MapEntry<MyString, String> currentExpected = expected[x];
            MapEntry<MyString, String> currentActual = actual[x];

            while (currentExpected != null) {
                assertEquals(currentExpected, currentActual);
                currentExpected = currentExpected.getNext();
                currentActual = currentActual.getNext();
            }
            assertEquals(currentExpected, currentActual);
        }
    }

    private static class MyString {
        private String s;

        /**
         * Create a wrapper object around a String object, for the purposes
         * of controlling the hash code.
         *
         * @param s string to store in this object
         */
        public MyString(String s) {
            this.s = s;
        }

        @Override
        public int hashCode() {
            return s.length();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MyString) {
                return s.equals(((MyString) o).s);
            }
            if (o instanceof String) {
                return s.equals(o);
            }
            return false;
        }

        @Override
        public String toString() {
            return s;
        }
    }
}

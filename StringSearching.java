import java.util.ArrayList;
import java.util.List;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class StringSearching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The patter passed is null");
        }
        if (text == null) {
            throw new IllegalArgumentException("The text passed is null");
        }
        if (text.length() == 0) {
            return new ArrayList<>();
        }
        List<Integer> foundIndex = new ArrayList<>();
        int[] table = buildFailureTable(pattern);
        int i = 0;
        int j = 0;
        while (i + pattern.length() - j <= text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (pattern.length() == j) {
                    foundIndex.add(i - j);
                    j = table[j - 1];
                }
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = table[j - 1];
                }
            }
        }
        return foundIndex;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building failure table for
     * @return integer array of size text.length that you are building a failure
     * table for
     */
    public static int[] buildFailureTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern passed is null");
        }
        int[] table = new int[pattern.length()];
        table[0] = 0;
        int i = 0;
        int j = 1;
        int counter = 0;
        while (j < table.length) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                counter++;
                table[j] = counter;
                i++;
                j++;
            } else {
                counter = 0;
                if (i != 0) {
                    i = 0;
                } else {
                    table[j] = counter;
                    j++;
                }
            }
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last table. Works better with large
     * alphabets.
     *
     * Make sure to implement the table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
            CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The patter passed is null");
        }
        if (text == null) {
            throw new IllegalArgumentException("The text passed is null");
        }
        if (text.length() == 0) {
            return new ArrayList<>();
        }
        List<Integer> foundIndex = new ArrayList<>();
        int[] table = buildLastTable(pattern);
        int i = pattern.length() - 1;
        int j = i;
        boolean done = false;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (pattern.charAt(j) == c) {
                while (j > 0 && !done) {
                    i--;
                    j--;
                    c = text.charAt(i);
                    if (pattern.charAt(j) != c) {
                        done = true;
                    }
                }
                if (!done) {
                    foundIndex.add(i);
                }
            }
            i = i + pattern.length() - Math.min(j, 1 + table[c]);
            j = pattern.length() - 1;
            done = false;

        }
        return foundIndex;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table[x].
     * Each entry should be -1 if x is not in the pattern or the last index of x
     * where x is a particular character in your pattern.
     *
     * Ex. octocat
     *
     * table[o] = 3
     * table[c] = 4
     * table[t] = 6
     * table[a] = 5
     * table[everything else] = -1
     *
     * HINT: Characters auto cast to their corresponding int in Unicode (UTF-16)
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return integer array of size {@code (Character.MAX_VALUE + 1)}
     * containing the mapping for all characters in Unicode
     */
    public static int[] buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern passed is null");
        }
        int[] table = new int[Character.MAX_VALUE + 1];
        int i = pattern.length() - 1;
        for (int j = 0; j < table.length; j++) {
            table[j] = -1;
        }
        while (i >= 0) {
            char c = pattern.charAt(i);
            if (table[c] == -1) {
                table[c] = i;
            }
            i--;
        }
        return table;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 433;

    /**
     * Runs Rabin-Karp algorithm. Generate initial hash, and compare it with
     * hash from substring of text same length as pattern. If the two
     * hashes match compare their individual characters, else update hash
     * and continue.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
            CharSequence text) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The patter passed is null");
        }
        if (text == null) {
            throw new IllegalArgumentException("The text passed is null");
        }
        if (text.length() == 0) {
            return new ArrayList<>();
        }
        List<Integer> foundIndex = new ArrayList<>();
        int patternHash = generateHash(pattern, pattern.length());
        int textHash = generateHash(text, pattern.length());
        int index = 0;
        while (index < text.length() - pattern.length() + 1) {
            //if (index > )
            if (patternHash == textHash) {
                int count = 0;
                for (int i = index; i < index + pattern.length() - 1; i++) {
                    if (text.charAt(i) == pattern.charAt(count)) {
                        count++;
                    }
                }
                if (count == pattern.length() - 1) {
                    foundIndex.add(index);
                }
            }
            if (index + pattern.length() > text.length() - 1) {
                return foundIndex;
            }
            textHash = updateHash(textHash, pattern.length(),
                    text.charAt(index), text.charAt(index + pattern.length()));
            index++;
        }
        return foundIndex;
    }

    /**
     * Hash function used for Rabin-Karp. The formula for hashing a string is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 433 hash
     * = b * 433 ^ 3 + u * 433 ^ 2 + n * 433 ^ 1 + n * 433 ^ 0 = 98 * 433 ^ 3 +
     * 117 * 433 ^ 2 + 110 * 433 ^ 1 + 110 * 433 ^ 0 = 7977892179
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if current is null
     * @throws IllegalArgumentException if length is negative or greater
     *     than the length of current
     * @param current substring you are generating hash function for
     * @param length the length of the string you want to generate the hash for,
     * starting from index 0. For example, if length is 4 but current's length
     * is 6, then you include indices 0-3 in your hash (and pretend the actual
     * length is 4)
     * @return hash of the substring
     */
    public static int generateHash(CharSequence current, int length) {
        if (current == null) {
            throw new IllegalArgumentException("The current passed is null");
        }
        if (length < 0 || length > current.length()) {
            throw new IllegalArgumentException("Invalid length is passed");
        }
        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash += current.charAt(i) * pow(BASE, length - 1 - i);
        }
        return hash;
    }

    /**
     * Updates a hash in constant time to avoid constantly recalculating
     * entire hash. To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 433
     * hash("unny") = (hash("bunn") - b * 433 ^ 3) * 433 + y * 433 ^ 0 =
     * (7977892179 - 98 * 433 ^ 3) * 433 + 121 * 433 ^ 0 = 9519051770
     *
     * The computation of BASE raised to length - 1 may require O(n) time,
     * but the method should otherwise run in O(1).
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if length is negative
     * @param oldHash hash generated by generateHash
     * @param length length of pattern/substring of text
     * @param oldChar character we want to remove from hashed substring
     * @param newChar character we want to add to hashed substring
     * @return updated hash of this substring
     */
    public static int updateHash(int oldHash, int length, char oldChar,
            char newChar) {
        if (length < 0) {
            throw new IllegalArgumentException("The length should be bigger"
                    + " than zero");
        }
        return ((oldHash - oldChar
                * pow(BASE, length - 1)) * BASE) + newChar;
    }

    /**
     * Private method for calculating powers.
     * @param base base value
     * @param exp exponent value
     * @return calculated result
     */
    private static int pow(int base, int exp) {
        if (exp <= 0) {
            return 1;
        } else {
            return pow(base, exp - 1) * base;
        }
    }
}

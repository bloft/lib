package dk.lbloft.util;

import java.io.*;

/**
 */
public class CloserTest {

    public static void main(String... args) {
        try(Closer c = new Closer()) {
            FileOutputStream fis = c.add(new FileOutputStream("/home/bklo/test.out"));
            PrintStream ps = c.add(new PrintStream(fis));

            ps.println("test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

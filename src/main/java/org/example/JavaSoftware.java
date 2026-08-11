package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaSoftware {
/**
 * Creates a CSV file from a 2D list of strings.
 *
 * @param filePath The full path where the CSV file will be saved
 * @param data A list of rows, where each row is a list of string values
 *
 *<br>
 *<br>
 * Example:<br>
 *
 * DATA = Arrays.asList(
 *<pre>
 *     Arrays.asList("Name", "Age", "Town"),<br>
 *     Arrays.asList("Name", "Age", "Town"),<br>
 *     Arrays.asList("Name", "Age", "Town")</pre>
 *
 * );
 *<br>
 *<br>
 * String FILE_PATH = "C:\\Users\\name\\Desktop\\output.csv"
 * <br>
 * <br>
 * createCSVFile(FILE_PATH, DATA);
 * // Creates a CSV file at the specified path
 */
public static void createCSVFile(
          String filePath,
          List<List<String>> data) {
     File file = new File(filePath);
     
     try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
          for (List<String> row : data) {
               out.write(String.join(",", row));
               out.newLine();
          }
          System.out.println("CSV file created successfully at " + filePath);
     } catch (IOException e) {
          e.printStackTrace();
     }
     
}


public static void println(String s) {
     System.out.println(s);
}

public static void println(boolean b) {
     System.out.println(b);
}

public static void println(char c) {
     System.out.println(c);
}

public static void println(byte b) {
     System.out.println(b);
}

public static void println(short s) {
     System.out.println(s);
}

public static void println(int i) {
     System.out.println(i);
}

public static void println(long l) {
     System.out.println(l);
}

public static void println(float f) {
     System.out.println(f);
}

public static void println(double d) {
     System.out.println(d);
}

public static void println(char[] a) {
     System.out.println(a);
}

public static void println(byte[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(short[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(int[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(long[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(float[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(double[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(boolean[] a) {
     System.out.println(Arrays.toString(a));
}

public static void println(Object[] a) {
     System.out.println(Arrays.deepToString(a));
}

public static void println(List<?> list) {
     System.out.println(list);
}

public static void println(Set<?> set) {
     System.out.println(set);
}

public static void println(Map<?, ?> map) {
     System.out.println(map);
}

public static void println(Object b) { System.out.println(b); }


}




import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author riazs
 */
public class Test {
    private static final Pattern p = Pattern.compile("(\\[download\\]\\s+)(?<percent>\\d+\\.\\d)(%.*\\s+of\\s+)(?<size>.*)(\\s+at\\s+)(?<speed>.*)\\s+ETA\\s+(?<minutes>\\d*):(?<seconds>\\d*)\\s*");
    public static void main(String[] args) {
        String line = "[download]   0.2% of 15.81MiB at  8.73MiB/s ETA 00:01          ";
        Matcher m = p.matcher(line);
        System.out.println(line + " + " +  m.matches());
    }
}

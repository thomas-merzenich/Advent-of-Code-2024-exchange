import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class tj3 {

    public static void main(String[] args) {
        var file = "exchange/day03/thomas/input.txt";
//        var file = "exchange/day03/thomas/input-example.txt";
        System.out.println("java/tag1.java");
        System.out.println("Read " + file);
        var input = readFile(file);
//        System.out.println("Input from File: " + input);
        var ergebnis = 0;
        for (ArrayList<String> zeile: input) {
            System.out.println("Zeile: " + zeile);
         for (String mul: zeile) {
             System.out.println("Mul: " + mul);
             if (mul.contains(",")) {
                 var zahlen = mul.split(",");
                 if (zahlen.length != 2) {
                     System.out.println("Fehler: " + mul);
                     continue;
                 }
                 var a = zahlen[0];
                 var b = zahlen[1];
                 int x = 0;
                 int y = 0;
                 try {
                     x = Integer.parseInt(a);
                     y = Integer.parseInt(b);
                     if (x > -1000 && x < 1000 && y > -1000 && y < 1000) {
                         ergebnis += (x * y);
                     }
                     // 186629454
                 } catch (NumberFormatException e) {
//                     System.out.println("War wohl nichts!");
                 }
             } else {
                 System.out.println("Kein Komma!");
             }
         }
        }
        System.out.println("Ergebnis: " + ergebnis);
    }


    private static ArrayList<ArrayList<String>> readFile(String file) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        var enabled = true;
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            while (scanner.hasNextLine()) {
                String rawline = scanner.nextLine();
                System.out.println("rawline = " + rawline);
                ArrayList<String> inputs = new ArrayList<>();
                ArrayList<String> zeile = new ArrayList<>();
                var line = "";
                var doCheck = true;
                while (doCheck) {
                    // start with do, so search for "don't()"
                    if (enabled) {
                        if (rawline.contains("don't()")) {
                            var index = rawline.indexOf("don't()") + 7;
                            line = line + rawline.substring(0, index);
                            rawline = rawline.substring(index);
                            System.out.println("don't() = " + rawline);
                            enabled = false;
                        } else {
                            line = line + rawline;
                            doCheck = false;
                        }
                    }
                    if (!enabled) {
                        if (rawline.contains("do()")) {
                            var index = rawline.indexOf("do()") + 4;
                            rawline = rawline.substring(index);
                            System.out.println("do() = " + rawline);
                            enabled = true;
                        } else {
                            doCheck = false;
                        }
                    }
                }
                System.out.println("line = " + line);
                while (true) {
                    var index = line.lastIndexOf("mul(");
                    if (index == -1) {
                        break;
                    }
                    inputs.add(line.substring(index).substring(4));
                    line = line.substring(0, index+1);
                }
                for (String input: inputs) {
                    var index =input.indexOf(")");
                    if (index != -1) {
                        input = input.substring(0, index);
                        zeile.add(input);
                    }
//                    System.out.println("Input: " + input);
//                    zeile.add(input);
                }
                System.out.println("zeile = " + zeile);
                list.add(zeile);
            }
            scanner.close();
            System.out.println("list = " + list);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
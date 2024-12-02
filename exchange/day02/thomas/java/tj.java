import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class tj {

    public static void main(String[] args) {
        var file = "exchange/day02/thomas/input.txt";
//        var file = "exchange/day02/thomas/input-example.txt";
        System.out.println("java/tag1.java");
        System.out.println("Read " + file);
        var input = readFile(file);
//        System.out.println("Liste: " + input);

        var countSafe = 0;
        for (ArrayList<Integer> report: input) {
            System.out.println("Report: " + report);
            if (check(report)) {
                System.out.println("Report is SAFE!");
                countSafe++;
            };
        }
        System.out.println("Safe reports: " + countSafe);
    }

    private static boolean check(ArrayList<Integer> report) {
        var direction="?";
        if (report.get(0) > report.get(1)) {
            direction = "down";
        } else if (report.get(0) < report.get(1)) {
            direction = "up";
        }
//        System.out.println("Direction: " + direction);
        for (int i = 0; i < report.size()-1; i++) {
            if (!checkLevels(report.get(i), report.get(i+1), direction)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkLevels(Integer level1, Integer level2, String direction) {
        if (direction.equals("up")) {
            if (level1 >= level2) {
                return false;
            }
        } else if (level1 <= level2) {
            return false;
        }
//        System.out.println("Check levels: " + level1 + " - " + level2);
        return Math.abs(level1 - level2) <= 3;
    }

    private static ArrayList<ArrayList<Integer>> readFile(String file) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            while (scanner.hasNextLine()) {
                String inputs = scanner.nextLine();
                ArrayList<Integer> report = new ArrayList<>();
                for (int i:Arrays.stream(inputs.split(" "))
                        .mapToInt(Integer::parseInt).toArray()) {
                    report.add(i);
                }
                list.add(report);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
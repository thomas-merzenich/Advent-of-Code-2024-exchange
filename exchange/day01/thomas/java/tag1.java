import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class tag1 {
    public static void main(String[] args) {
        var file = "exchange/day01/thomas/input.txt";
//        var file = "exchange/day01/thomas/input-example.txt";
        System.out.println("java/tag1.java");
        System.out.println("Read " + file);
        var input = readFile(file);
//        System.out.println("Liste: " + input);
        System.out.println("Sortiere beide Spalten");
        input.get(0).sort(Integer::compareTo);
        input.get(1).sort(Integer::compareTo);
        var distance = 0;
        var similarity = 0;
        for (int i = 0; i < input.get(0).size(); i++) {
            distance += Math.abs(input.get(0).get(i) - input.get(1).get(i));
            int finalI = i;
            int count = (int) input.get(1).stream().filter(j -> j.equals(input.get(0).get(finalI))).count();
            System.out.println("i = " + i + "input.get(0).get(i) = "+ input.get(0).get(i) + " count = " + count);
            similarity += (input.get(0).get(i) * count);

        }
        System.out.println("Distanz: " + distance);
        System.out.println("similarity = " + similarity);

    }

    private static ArrayList<ArrayList<Integer>> readFile(String file) {
        ArrayList<Integer> spalte1 = new ArrayList<>();
        ArrayList<Integer> spalte2 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            while (scanner.hasNext()) {
                spalte1.add(scanner.nextInt());
                spalte2.add(scanner.nextInt());
            }
            list.add(spalte1);
            list.add(spalte2);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
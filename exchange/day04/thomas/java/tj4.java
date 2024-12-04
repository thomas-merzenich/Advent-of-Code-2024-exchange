import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class tj4 {

    public static void main(String[] args) {
        var file = "exchange/day04/thomas/input.txt";
//        var file = "exchange/day04/thomas/input-example.txt";
        System.out.println("Read " + file);
        var input = readFile(file);

        Character[][] matrix = new Character[input.size()][input.getFirst().size()];
        for (int zeile = 0; zeile < input.size(); zeile++) {
            for (int spalte = 0; spalte < input.get(zeile).size(); spalte++) {
                matrix[zeile][spalte] = input.get(zeile).get(spalte);
            }
        }

        var counter = 0;
        int[] direction;
        Character[][] output = new Character[input.size()][input.getFirst().size()];
        for (int zeile = 0; zeile < input.size(); zeile++) {
            for (int spalte = 0; spalte < input.get(zeile).size(); spalte++) {
                output[zeile][spalte] = '.';
            }
        }
        printMatrix(matrix);
        for (int zeile = 0; zeile < matrix.length; zeile++) {
            for (int spalte = 0; spalte < matrix[zeile].length; spalte++) {
                char c = matrix[zeile][spalte];
                if (c == 'X') {
                    System.out.println("Found X at " + zeile + " " + spalte);
                    output[zeile][spalte] = 'X';
                    direction = new int[]{2, 2};
                    for (int horizontal = -1; horizontal < 2; horizontal++) {
                        for (int vertical = -1; vertical < 2; vertical++) {
                            direction[0] = horizontal;
                            direction[1] = vertical;

                            var z = zeile;
                            var s = spalte;
                            if (findNeighbours(matrix, z, s, 'M', direction)) {
                                z = z + direction[0];
                                s = s + direction[1];
//                        direction = new int[]{2, 2};
                                System.out.println("Found M at " + z + " " + s);
                                output[z][s] = 'M';
                                if (findNeighbours(matrix, z, s, 'A', direction)) {
                                    z = z + direction[0];
                                    s = s + direction[1];
//                            direction = new int[]{2, 2};
                                    System.out.println("Found A at " + z + " " + s);
                                    output[z][s] = 'A';
                                    if (findNeighbours(matrix, z, s, 'S', direction)) {
                                        z = z + direction[0];
                                        s = s + direction[1];
//                                direction = new int[]{2, 2};
                                        System.out.println("Found A at " + z + " " + s);
                                        output[z][s] = 'S';

                                        counter++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        printMatrix(output);
        System.out.println("Counter Part 1: " + counter);
        counter = 0;
        for (int zeile = 0; zeile < matrix.length; zeile++) {
            for (int spalte = 0; spalte < matrix[zeile].length; spalte++) {
                char c = matrix[zeile][spalte];
                if (c == 'A') {
                    if ((
                            (findNeighbours(matrix, zeile, spalte, 'M', new int[]{-1, -1})) && (findNeighbours(matrix, zeile, spalte, 'S', new int[]{1, 1}))
                                    ||
                                    (findNeighbours(matrix, zeile, spalte, 'S', new int[]{-1, -1})) && (findNeighbours(matrix, zeile, spalte, 'M', new int[]{1, 1}))
                    ) && (
                            (findNeighbours(matrix, zeile, spalte, 'M', new int[]{-1, 1})) && (findNeighbours(matrix, zeile, spalte, 'S', new int[]{1, -1}))
                                    ||
                                    (findNeighbours(matrix, zeile, spalte, 'S', new int[]{-1, 1})) && (findNeighbours(matrix, zeile, spalte, 'M', new int[]{1, -1}))
                    )) {
                        counter++;
                    }
                }
            }
        }
        System.out.println("Counter Part 2: " + counter);


    }

    private static boolean findNeighbours(Character[][] input, int zeile, int spalte, Character nextChar, int[] direction) {
        if (direction[0] == 2 && direction[1] == 2) {
            for (int horizontal = -1; horizontal < 2; horizontal++) {
                for (int vertical = -1; vertical < 2; vertical++) {
                    direction[0] = horizontal;
                    direction[1] = vertical;
                    System.out.println("Checking Direction " + direction[0] + " " + direction[1]);
                    if (findNeighbours(input, zeile, spalte, nextChar, direction)) return true;
                }
            }
            return false;
        }

        try {
            if (input[(zeile + direction[0])][(spalte + direction[1])] == nextChar) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    private static void printMatrix(Character[][] input) {
        System.out.println();
        for (Character[] characters : input) {
            for (Character character : characters) {
                System.out.print(character);
            }
            System.out.print("\n");
        }
        System.out.println();
    }


    private static ArrayList<ArrayList<Character>> readFile(String file) {
        ArrayList<ArrayList<Character>> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            var zeile = -1;
            while (scanner.hasNextLine()) {
                String rawline = scanner.nextLine();
                zeile++;
                list.add(new ArrayList<>());
                for (int spalte = 0; spalte < rawline.length(); spalte++) {
                    char c = rawline.charAt(spalte);
                    list.get(zeile).add(c);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
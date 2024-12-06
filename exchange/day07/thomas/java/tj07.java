import java.io.FileNotFoundException;
import java.util.*;

public class tj07 {

    public class Guard {
        public Integer x;
        public Integer y;
        public char direction;

        public Guard(Integer x, Integer y, char direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public Guard() {
        }

        @Override
        public String toString() {
            return "Guard{" +
                    "x=" + x +
                    ", y=" + y +
                    ", direction=" + direction +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guard guard = (Guard) o;
            return ((direction == guard.direction) && (x.equals(guard.x)) && (y.equals(guard.y)));
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }
    }
    public static void main(String[] args) {
        tj6 tj = new tj6();
        tj.trackGuard();
    }

    public void trackGuard() {
        var basename = "exchange/day07/thomas/";
//        var modus = "-example";
        var modus = "";
        var inputfile = basename + "input" + modus + ".txt";
        var rulesfile = basename + "rules" + modus + ".txt";
        System.out.println("Read " + basename);
        var lab = readFile(inputfile);

        var guard = findGuard(lab);
        var inBounds = 0;
        var steps = 0;
        printMap(lab);
        while (inBounds == 0) {
            inBounds = moveGuard(lab, guard, null);
            steps++;
//            System.out.println("step " + steps);
//            printMap(lab);
//            System.out.println();
        }
        printMap(lab);
        System.out.println("Guard moved " + steps + " steps");
        System.out.println("Locations visited: "+countLocations(lab));
        System.out.println("\n\n");

        var countPositions = 0;
        for (int x=0; x<lab.length; x++) {
            for (int y=0; y<lab[x].length; y++) {
                lab = readFile(inputfile);
                guard = findGuard(lab);
                if (lab[x][y] == '.' ) {
//                    System.out.println("---------------------------------------------------------");
                    lab[x][y] = 'O';
//                    System.out.println("Obstacle position: "+x+" "+y);
                    if (x == 6 && y == 3) {
                        System.out.println("Hier wird es spannend!");
                    }
//                    printMap(lab);
                    HashSet<Guard> pastPositions = new HashSet<>();
                    inBounds=0;
                    while (inBounds == 0) {
                        inBounds = moveGuard(lab, guard, pastPositions);
                    }
//                    printMap(lab);
                    lab[x][y] = '.';
                    if (inBounds == -1) {
//                        System.out.println("Position für Loop gefunden!");
                        countPositions++;
                    }
//                    System.out.println("---------------------------------------------------------");
                }
            }
        }
        System.out.println("Positions with loops: "+countPositions);
    }

    private Integer countLocations(Character[][] lab) {
        var count = 0;
        for (int x=0; x<lab.length; x++) {
            for (int y=0; y<lab[x].length; y++) {
                if ((lab[x][y] == '-') || (lab[x][y] == '|')) {
                    count++;
                }
            }
        }
        return count;
    }

    private void printMap(Character[][] lab) {
        System.out.println();
        for (int x=0; x<lab.length; x++) {
            for (int y=0; y<lab[x].length; y++) {
                System.out.print(lab[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private int moveGuard(Character[][] area, Guard guard, Set<Guard> pastPositions) {
        var newPosition = '.';
        if (pastPositions != null) {
            if (pastPositions.contains(guard)) {
                return -1;
            }
            pastPositions.add(new Guard(guard.x, guard.y, guard.direction));
        }
        switch (guard.direction) {
            case 'L': // links
                area[guard.x][guard.y] = '-';
                if (guard.y == 0) {
                    return 1;
                }
                newPosition = area[guard.x][guard.y-1];
                if ((newPosition == '#') || (newPosition == 'O')) {
                    return turnRight(guard, area, pastPositions);
                } else {
                    guard.y--;
                }
                area[guard.x][guard.y] = '<';
                break;
            case 'R':
                area[guard.x][guard.y] = '-';
                if (guard.y == area[0].length-1) {
                    return 1;
                }
                newPosition = area[guard.x][guard.y+1];
                if ((newPosition == '#') || (newPosition == 'O')) {
                    return turnRight(guard, area, pastPositions);
                } else {
                    guard.y++;
                }
                area[guard.x][guard.y] = '>';
                break;
            case 'U':
                area[guard.x][guard.y] = '|';
                if (guard.x == 0) {
                    return 1;
                }
                newPosition = area[guard.x-1][guard.y];
                if ((newPosition == '#') || (newPosition == 'O')) {
                    return turnRight(guard, area, pastPositions);
                } else {
                    guard.x--;
                }
                area[guard.x][guard.y] = '^';
                break;
            case 'D': // unten
                area[guard.x][guard.y] = '|';
                if (guard.x == area.length-1) {
                    return 1;
                }
                newPosition = area[guard.x+1][guard.y];
                if ((newPosition == '#') || (newPosition == 'O')) {
                    return turnRight(guard, area, pastPositions);
                } else {
                    guard.x++;
                }
                area[guard.x][guard.y] = 'v';
                break;
        }
        return 0;
    }

    private int turnRight(Guard guard, Character[][] area, Set<Guard> pastPositions) {
        var newDirection = 'X';
        switch (guard.direction) {
            case 'U':
                newDirection = 'R';
                break;
            case 'D':
                newDirection = 'L';
                break;
            case 'L':
                newDirection = 'U';
                break;
            case 'R':
                newDirection = 'D';
        }
        guard.direction = newDirection;
        return moveGuard(area, guard, pastPositions);
    }

    private Guard findGuard(Character[][] area) {
        Guard guard = new Guard();
        for (int x=0; x<area.length; x++) {
            for (int y=0; y<area[x].length; y++) {
                switch (area[x][y]) {
                    case '<':
                        guard.x = x;
                        guard.y = y;
                        guard.direction = 'L';
                        return guard;
                    case '>':
                        guard.x = x;
                        guard.y = y;
                        guard.direction = 'R';
                        return guard;
                    case 'v':
                        guard.x = x;
                        guard.y = y;
                        guard.direction = 'D';
                        return guard;
                    case '^':
                        guard.x = x;
                        guard.y = y;
                        guard.direction = 'U';
                        return guard;
                }
            }
        }
        return null;
    }

    private static Character[][] readFile(String file) {
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
        Character[][] matrix = new Character[list.size()][list.getFirst().size()];
        for (int zeile = 0; zeile < list.size(); zeile++) {
            for (int spalte = 0; spalte < list.get(zeile).size(); spalte++) {
                matrix[zeile][spalte] = list.get(zeile).get(spalte);
            }
        }
        return matrix;
    }
}
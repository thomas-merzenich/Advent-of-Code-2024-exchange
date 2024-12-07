import java.io.FileNotFoundException;
import java.util.*;

public class tj07 {

    public static void main(String[] args) {
        tj07 tj = new tj07();
        tj.doThePuzzle();
    }

    public void doThePuzzle() {
        var basename = "exchange/day07/thomas/";
//        var modus = "-example";
        var modus = "";
        var inputfile = basename + "input" + modus + ".txt";
        var rulesfile = basename + "rules" + modus + ".txt";
        System.out.println("Read " + basename);
        var puzzle = readFile(inputfile);
        var summe = 0L;
        for (String aufgabe : puzzle) {
            System.out.println("aufgabe = " + aufgabe);
            var soll = Long.valueOf(aufgabe.split(":")[0].trim());
            System.out.println("soll = " + soll);
            ArrayList<Long> zahlen = new ArrayList<>();
            for (String zahl : aufgabe.split(":")[1].split(" ")) {
                if (zahl.trim().isEmpty()) {
                    continue;
                }
                zahlen.add(Long.valueOf(zahl.trim()));
            }
            System.out.println("zahlen = " + zahlen);
            var ergebnis = evaluate(zahlen);
            System.out.println("ergebnis = " + ergebnis);
            for (Long zahl : ergebnis) {
                if (zahl.equals(soll)) {
                    summe += zahl;
                    break;
                }
            }
        }
        System.out.println("Summe = " + summe);
    }

    private ArrayList<Long> evaluate(ArrayList<Long> zahlen) {
        ArrayList<Long> unterergebnisse = new ArrayList<>();
        unterergebnisse.addAll(plus(zahlen.getFirst(), 1, zahlen));
        unterergebnisse.addAll(multi(zahlen.getFirst(), 1, zahlen));
        unterergebnisse.addAll(concatenate(zahlen.getFirst(), 1, zahlen));
        return unterergebnisse;
    }

    private ArrayList<Long> plus(Long bereitsBerechnet, int i, ArrayList<Long> zahlen) {
        var index = i;
        ArrayList<Long> unterergebnisse = new ArrayList<>();
        if (bereitsBerechnet == null) {
            bereitsBerechnet = 0L;
        }
        var neuBerechnet = bereitsBerechnet + zahlen.get(index);
        if (index < zahlen.size()-1) {
            unterergebnisse.addAll(plus(neuBerechnet, index+1, zahlen));
            unterergebnisse.addAll(multi(neuBerechnet, index+1, zahlen));
            unterergebnisse.addAll(concatenate(neuBerechnet, index+1, zahlen));
        } else {
            unterergebnisse.add(neuBerechnet);
        }
        return unterergebnisse;
    }

    private ArrayList<Long> multi(Long bereitsBerechnet, int i, ArrayList<Long> zahlen) {
        var index = i;
        ArrayList<Long> unterergebnisse = new ArrayList<>();
        if (bereitsBerechnet == null) {
            bereitsBerechnet = 1L;
        }
        var neuBerechnet = bereitsBerechnet * zahlen.get(index);
        if (index < zahlen.size()-1) {
            unterergebnisse.addAll(plus(neuBerechnet, index+1, zahlen));
            unterergebnisse.addAll(multi(neuBerechnet, index+1, zahlen));
            unterergebnisse.addAll(concatenate(neuBerechnet, index+1, zahlen));
        } else {
            unterergebnisse.add(neuBerechnet);
        }
        return unterergebnisse;
    }

    private ArrayList<Long> concatenate(Long bereitsBerechnet, int i, ArrayList<Long> zahlen) {
        var index = i;
        ArrayList<Long> unterergebnisse = new ArrayList<>();
        String zahl1 = bereitsBerechnet.toString();
        String zahl2 = zahlen.get(index).toString();
        String ergebnis = zahl1 + zahl2;
        var neuBerechnet = Long.valueOf(ergebnis);
        if (index < zahlen.size()-1) {
            unterergebnisse.addAll(plus(neuBerechnet, index+1, zahlen));
            unterergebnisse.addAll(multi(neuBerechnet, index+1, zahlen));
            unterergebnisse.addAll(concatenate(neuBerechnet, index+1, zahlen));
        } else {
            unterergebnisse.add(neuBerechnet);
        }
        return unterergebnisse;
    }

    private static ArrayList<String> readFile(String file) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            while (scanner.hasNextLine()) {
                String rawline = scanner.nextLine();
                list.add(rawline);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
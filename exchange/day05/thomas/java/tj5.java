import java.io.FileNotFoundException;
import java.util.*;

public class tj5 {

    public static void main(String[] args) {
        var basename = "exchange/day05/thomas/";
//        var modus = "-example";
        var modus = "";
        var inputfile = basename + "input" + modus + ".txt";
        var rulesfile = basename + "rules" + modus + ".txt";
        System.out.println("Read " + basename);
        var input = readFile(inputfile);
        var rules = readRules(rulesfile);

        System.out.println("Rules: " + rules);
        System.out.println();
        System.out.println("Input = " + input);

        var checkSum = 0;
        var correctSum = 0;
        for (ArrayList<Integer> update : input) {
            var check = checkUpdate(update, rules);
            if (check != 0) {
                checkSum += check;
            } else {
                correctSum += correctUpdate(update, rules);
            }
        }
        System.out.println("CheckSum = " + checkSum);
        System.out.println("correctSum = " + correctSum);
    }

    private static int correctUpdate(ArrayList<Integer> update, Map<Integer, ArrayList<Integer>> rules) {
        var check = true;
        for (int i = 0; i < update.size(); i++) {
            // Die erste Zahl braucht vielleicht eine Sonderbehandlung
            var rule =rules.get(update.get(i));
            if (rule != null) {
                for (int j = i + 1; j < update.size(); j++) {
                    if (rule.contains(update.get(j))) {
                        var h = update.get(i);
                        update.set(i, update.get(j));
                        update.set(j, h);
                        check = false;
                        break;
                    }
                }
                if (!check) {
                    break;
                }
            }
        }
        if (check) {
            System.out.println("Update = " + update + " is corrected");
            var index = (update.size()+1)/2;
            return update.get(index-1);
        } else {
            // iterativer Aufruf bis korrigiert
            return correctUpdate(update, rules);
        }
    }

    private static int checkUpdate(ArrayList<Integer> update, Map<Integer, ArrayList<Integer>> rules) {
        var check = true;
        for (int i = 0; i < update.size(); i++) {
            // Die erste Zahl braucht vielleicht eine Sonderbehandlung
            var rule =rules.get(update.get(i));
            if (rule != null) {
                for (int j = i + 1; j < update.size(); j++) {
                    if (rule.contains(update.get(j))) {
                        check = false;
                        break;
                    }
                }
                if (!check) {
                    break;
                }
            }
        }
        if (check) {
            System.out.println("Update = " + update + " is OK");
            var index = (update.size()+1)/2;
            return update.get(index-1);
        } else {
            System.out.println("Update = " + update + " is NOT OK");
        }
        return 0;
    }

    private static Map<Integer, ArrayList<Integer>> readRules(String file) {
        Map <Integer, ArrayList<Integer>> rules = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            while (scanner.hasNextLine()) {
                String rawline = scanner.nextLine();
                var values = rawline.split("\\|");
//                System.out.println("Values = " + Arrays.toString(values));
                var rule = rules.get(Integer.parseInt(values[1]));
                if (rule == null) {
                    rule = new ArrayList<>();
                }
                rule.add(Integer.parseInt(values[0])); // Zahl muss vor dem Key sein
                rules.put(Integer.parseInt(values[1]), rule);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rules;
    }

    private static ArrayList<ArrayList<Integer>> readFile(String file) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(file));
            while (scanner.hasNextLine()) {
                list.add(new ArrayList<>());
                String rawline = scanner.nextLine();
                var values = rawline.split(",");
                for (int i = 0; i < values.length; i++) {
                    list.getLast().add(Integer.parseInt(values[i]));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
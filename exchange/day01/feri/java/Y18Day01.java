import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * see: https://adventofcode.com/2018/day/1
 *
 */
public class Y18Day01 {

	
	
	public static void mainPart1(String inputfile) throws FileNotFoundException {
		
		long frequency = 0;
		try (Scanner scanner = new Scanner(new File(inputfile))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}
				frequency += Long.parseLong(line);
			}
		}
		System.out.println("resulting frequency: "+frequency);
	}

	
	
	public static void mainPart2(String inputfile) throws FileNotFoundException {
		
		List<Long> changes = readChanges(inputfile);
		long duplicateFrequency = findFirstDuplicateFrequency(changes);
		System.out.println("first duplicate frequency: "+duplicateFrequency);
		
	}

	private static List<Long> readChanges(String inputfile) throws FileNotFoundException {
		List<Long> changes = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(inputfile))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}
				changes.add(Long.parseLong(line));
			}
		}
		return changes;
	}
	
	private static long findFirstDuplicateFrequency(List<Long> changes) {
		Set<Long> existingFrequencies = new HashSet<>();
		long frequency = 0;
		long cnt = 0;
		existingFrequencies.add(frequency);
		while (true) {
			for (long change:changes) {
				frequency += change;
				cnt++;
				if (!existingFrequencies.add(frequency)) {
					System.out.println("CNT: "+cnt);
					return frequency;
				}
			}
		}
	}


	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("--- PART I  ---");
		mainPart1("exchange/day01/feri/input.txt");
		System.out.println("---------------");
		System.out.println();
		System.out.println("--- PART II ---");
		mainPart2("exchange/day01/feri/input.txt");
		System.out.println("---------------");
	}

	
}

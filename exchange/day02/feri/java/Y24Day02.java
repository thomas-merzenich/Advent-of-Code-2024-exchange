import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * see: https://adventofcode.com/2024/day/2
 *
 */
public class Y24Day02 {

	
	
	public static void mainPart1(String inputfile) throws FileNotFoundException {
		
		int cntSafe = 0;
		try (Scanner scanner = new Scanner(new File(inputfile))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}
				String[] report = line.split(" ");
				if (checkSafe(report)) {
					cntSafe++;
				}
			}
		}
		System.out.println("Number of safe lines: "+cntSafe);
	}

	
	
	private static boolean checkSafe(String[] report) {
		int direction=0;
		for (int i=1; i<report.length; i++) {
			int diff = Integer.parseInt(report[i]) - Integer.parseInt(report[i-1]);
			if (diff == 0) {
				return false;
			}
			if (direction==0) {
				direction = (int)Math.signum(diff);
			}
			diff = diff * direction;
			if ((diff < 1) || (diff > 3)) {
				return false;
			}
		}
		return true;
	}



	public static void mainPart2(String inputfile) throws FileNotFoundException {
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("--- PART I  ---");
//		mainPart1("exchange/day02/feri/input-example.txt");
		mainPart1("exchange/day02/feri/input.txt");     
		System.out.println("---------------");
		System.out.println();
		System.out.println("--- PART II ---");
		mainPart2("exchange/day02/feri/input-example.txt");
//		mainPart2("exchange/day02/feri/input.txt");
		System.out.println("---------------");
	}

	
}

package counter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class LetterCounter {
	public static void main(String[] args) {
		System.out.print("Please enter some text: ");
		Scanner sc = new Scanner(System.in);
		String text = sc.nextLine();
		text = text.toUpperCase();
		Map<Character, Integer> lettersCount = new HashMap<Character, Integer>();

		for (char ch : text.toCharArray()) {
			if (Character.isLetter(ch)) {
				if (!lettersCount.containsKey(ch)) {
					lettersCount.put(ch, 1);
				} else {
					lettersCount.put(ch, lettersCount.get(ch) + 1);
				}
			}
		}

		Map<Integer, Set<Character>> lettersSorted = 
				new TreeMap<Integer, Set<Character>>((e1, e2) -> e2 - e1);
		int maxCount = 0;

		for (Character ch : lettersCount.keySet()) {
			Integer count = lettersCount.get(ch);

			if (!lettersSorted.containsKey(count)) {
				HashSet<Character> letters = new HashSet<Character>();
				lettersSorted.put(count, letters);

				if (maxCount < count) {
					maxCount = count;
				}
			}

			lettersSorted.get(count).add(ch);
		}

		for (Integer count : lettersSorted.keySet()) {
			for (Character ch : lettersSorted.get(count)) {
				int percent = (int) (((double) count / maxCount) * 100);
				int timesToRepeat = percent / 5;
				String repeatedHashTags = new String(new char[timesToRepeat]).replace("\0", "#"); // LOL the name
				System.out.println(ch + ": " + count + " " + repeatedHashTags);
			}
		}

		sc.close();
	}
}

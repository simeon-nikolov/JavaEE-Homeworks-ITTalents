package postoffice;

import java.util.LinkedList;
import java.util.List;

public class PostBox implements LetterSendable {
	private int postBoxNumber;
	private List<Letter> letters;
	
	public PostBox(int postBoxNumber) {
		this.letters = new LinkedList<Letter>();
		this.postBoxNumber = postBoxNumber;
	}
	
	public int getPostBoxNumber() {
		return postBoxNumber;
	}
	
	private void addLetter(Letter letter) {
		if (letter != null) {
			synchronized (this.letters) {
				this.letters.add(letter);
			}
		}
	}

	public List<Letter> getAllLetters() {
		List<Letter> result = new LinkedList<Letter>();
		
		synchronized (this.letters) {
			result.addAll(this.letters);
			this.letters.clear();
		}
		
		return result;
	}

	@Override
	public void sendLetter(Letter letter) {
		this.addLetter(letter);
	}
}

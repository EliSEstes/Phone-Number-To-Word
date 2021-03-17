package phoneNumberToWord;

class Key {
	double number;
	String word;
	
	Key(double number, String word) {
		this.number = number;
		this.word = word;
	}

	public double getNumber() {
		return number;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setNumber(double newNumber) {
		this.number = newNumber;
	}
	
	public void setWord(String newWord) {
		this.word = newWord;
	}
}

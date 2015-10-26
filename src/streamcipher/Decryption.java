package streamcipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Decryption {
	private Cryptogram cryptedMessage;
	private ArrayList<Cryptogram> cryptograms;
	private ArrayList<Character> possibleChars;
	private ArrayList<Character> possibleKey;

	public Decryption() {
		cryptedMessage = new Cryptogram();
		cryptograms = new ArrayList<>();
		possibleChars = new ArrayList<>();
		generateAsciiChars();
	}

	public void addCryptograms(String pathToFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				cryptograms.add(new Cryptogram(currentLine));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCryptedMessage(String message) {
		this.cryptedMessage.setCryptogram(message);
	}

	public void generateAsciiChars() {
		for (int i = 65; i < 91; i++) {
			possibleChars.add(new Character(i));
		}

		for (int i = 48; i <= 57; i++) {
			possibleChars.add(new Character(i));
		}

		/*
		 * Frekwencja wystepowania liter w j. polskim
		 * https://pl.wikipedia.org/wiki/Alfabet_polski#Cz.C4.99sto.C5.9B.C4.
		 * 87_wyst.C4.99powania_liter
		 */

		possibleChars.add(new Character((int) 'a', 8.91));
		possibleChars.add(new Character((int) 'i', 8.21));
		possibleChars.add(new Character((int) 'o', 7.75));
		possibleChars.add(new Character((int) 'e', 7.66));
		possibleChars.add(new Character((int) 'z', 5.64));
		possibleChars.add(new Character((int) 'n', 5.52));
		possibleChars.add(new Character((int) 'r', 4.69));
		possibleChars.add(new Character((int) 'w', 4.65));
		possibleChars.add(new Character((int) 's', 4.32));
		possibleChars.add(new Character((int) 't', 3.98));
		possibleChars.add(new Character((int) 'c', 3.96));
		possibleChars.add(new Character((int) 'y', 3.76));
		possibleChars.add(new Character((int) 'k', 3.51));
		possibleChars.add(new Character((int) 'd', 3.25));
		possibleChars.add(new Character((int) 'p', 3.13));
		possibleChars.add(new Character((int) 'm', 2.8));
		possibleChars.add(new Character((int) 'u', 2.5));
		possibleChars.add(new Character((int) 'j', 2.28));
		possibleChars.add(new Character((int) 'l', 2.1));
		possibleChars.add(new Character((int) 'b', 1.47));
		possibleChars.add(new Character((int) 'g', 1.42));
		possibleChars.add(new Character((int) 'h', 1.08));
		possibleChars.add(new Character((int) 'f', 0.3));
		possibleChars.add(new Character((int) 'q', 0.14));
		possibleChars.add(new Character((int) 'v', 0.04));
		possibleChars.add(new Character((int) 'x', 0.02));
		possibleChars.add(new Character((int) ' ', 10.0));
		possibleChars.add(new Character((int) '!'));
		possibleChars.add(new Character((int) '"'));
		possibleChars.add(new Character((int) '.'));
		possibleChars.add(new Character((int) ','));
		possibleChars.add(new Character((int) ':'));
		possibleChars.add(new Character((int) '-'));
		possibleChars.add(new Character((int) ';'));
		possibleChars.add(new Character((int) ')'));
		possibleChars.add(new Character((int) '('));
		possibleChars.add(new Character((int) '?'));
	}

	public void printMessage() {
		ArrayList<Integer> key = decrypt();
		for (int i = 0; i < cryptedMessage.size(); i++) {
			System.out.print((char) (cryptedMessage.getCharacter(i) ^ key.get(i)));
		}
	}

	public ArrayList<Integer> decrypt() {
		ArrayList<Integer> key = new ArrayList<>();

		for (int i = 0; i < cryptedMessage.size(); i++) {
			possibleKey = new ArrayList<>();

			for (Cryptogram cryptogram : cryptograms) {
				for (Character possibleChar : possibleChars) {
					Character tempKey;
					tempKey = new Character(cryptogram.getCharacter(i) ^ possibleChar.getCharacter(),
							possibleChar.getWeight());
					if (!keyAlreadyExist(tempKey.getCharacter())) {
						possibleKey.add(tempKey);
					} else
						findKey(tempKey.getCharacter()).addWeight(possibleChar.getWeight());
				}
			}
			possibleKey = sort(possibleKey);
			key.add(chooseBestKey(i));
		}
		return key;
	}

	private Character findKey(int key) {
		for (Character possibleKey : possibleKey) {
			if (possibleKey.getCharacter() == key)
				return possibleKey;
		}
		return null;
	}

	private ArrayList<Character> sort(ArrayList<Character> tempKeys) {
		Character key;
		int j;

		for (int i = 1; i < tempKeys.size(); i++) {
			j = i;
			key = tempKeys.get(i);
			while (j > 0 && tempKeys.get(j - 1).getWeight() < key.getWeight()) {
				tempKeys.set(j, tempKeys.get(j - 1));
				j--;
			}
			tempKeys.set(j, key);
		}
		return tempKeys;
	}

	private int chooseBestKey(int i) {
		for (Character possibleKey : possibleKey) {
			int counter = 0;
			for (Cryptogram cryptogram : cryptograms) {
				if (isChar(cryptogram.getCharacter(i) ^ possibleKey.getCharacter())) counter++;
				else break;
			}
			if (counter >= cryptograms.size()) return possibleKey.getCharacter();
		}
		System.out.println("Load more ciphertexts to end decryption.");
		return -1;
	}

	private boolean isChar(int checkedNumber) {
		for (Character character : possibleChars) {
			if (character.getCharacter() == checkedNumber)
				return true;
		}
		return false;
	}

	private boolean keyAlreadyExist(int key) {
		for (Character possibleKey : possibleKey) {
			if (possibleKey.getCharacter() == key)
				return true;
		}
		return false;
	}
}

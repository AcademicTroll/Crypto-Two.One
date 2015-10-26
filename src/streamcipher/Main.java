package streamcipher;
public class Main {

	public static void main(String[] args) {
		Decryption decryptor;	
		decryptor = new Decryption();
		assert (decryptor != null);	
		decryptor.addCryptograms("data.txt");
        decryptor.setCryptedMessage("10111000 11101100 00101100 11100011 10011111 01110100 01110110 10000111 10001111 10100010 00100110 00100111 10111010 00001001 10100110 10110010 11101100 00000110 11110000 10101111 10110111 00010110 10011111 10101101 11010111 10011100 11101110 11001110 01011000 00011110 11101100 10101100 11110110 01010010 10010110 11011000 10000011 11010000 10011010 11100011 00001000 00010100 00010110 11000011 00100110 01101100 00011111 11110110 01001001 10010110 01000000 10101000 11111111 01100000 10111010 11011000 11001010 00000101 00011010 10110001 01001010 01001111 01101111 10011010 00110010 01100000 00001001 11110010 10010001 01101000 11100111 11001100 11011100 10110111 01100010 01010101 10000001 00110001");
        decryptor.decrypt();
        decryptor.printMessage();
	}
}
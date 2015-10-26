package streamcipher;
import java.util.ArrayList;

public class Cryptogram {
    private ArrayList<Integer> characters;

    public Cryptogram(){};
    public Cryptogram(String cryptogram){
        setCryptogram(cryptogram);
    }
    public void setCryptogram(String cryptogram){
        this.characters = new ArrayList<>();
        String delimiter = " ";
        String[] temporaryCharacterStorage;
        temporaryCharacterStorage = cryptogram.split(delimiter);
        for(String character : temporaryCharacterStorage){
            characters.add(Integer.parseInt(character, 2));
        }
    }
    public int size(){ return characters.size(); }
    public int getCharacter(int index){ return characters.get(index); }
}
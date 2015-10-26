package streamcipher;
public class Character {
    private int character;
    private double weight;
    
    public Character(int character, double weight){
        this.character = character;
        this.weight = weight; }
    public Character(int character){
        this.character = character;
        this.weight = 1.0; }
    
    public void addWeight(double weight){ this.weight += weight; }
    public int getCharacter(){ return this.character; }
    public double getWeight(){ return this.weight; }
}

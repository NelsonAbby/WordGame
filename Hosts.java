
public class Hosts extends Person{

    private Phrases phrases;

    public Hosts(String firstName, String phrase){
        super(firstName);
        setNewPhrase(phrase);
    }

    public void setNewPhrase(String phrase) {
        Phrases.setGamePhrase(phrase);
        phrases = new Phrases();

    }

    public Phrases getPhrases() {
        return phrases;
    }

}

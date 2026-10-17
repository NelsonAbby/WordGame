import java.util.Scanner;

public class Hosts extends Person{

    private Scanner scanner;
    private Phrases phrases;

    public Hosts(String firstName, Scanner scanner){
        super(firstName);
        this.scanner = scanner;
        enterNewPhrase();
    }

    public void enterNewPhrase() {

        System.out.println();
        System.out.println(getFirstName()
        + ", enter the secret phrase for the players:");

        String phrase = scanner.nextLine();

        Phrases.setGamePhrase(phrase);

        phrases = new Phrases();

    }

    public Phrases getPhrases() {
        return phrases;
    }

}

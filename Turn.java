import java.util.Scanner;

public class Turn {

    private Scanner scanner;

    public Turn(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean takeTurn(Players player, Hosts host){

        System.out.println(host.getFirstName() + " says, " + player.getFirstName() + ", guess one letter:");
        String guess = scanner.nextLine().trim();


        Phrases phrases = host.getPhrases();

        try {

            if (guess.length() > 1) {
                phrases.findLetters(guess);
            }

            if (!guess.matches("[a-zA-z]")) {
                System.out.println("Enter 1 letter from A to Z");
                return false;
            }

            boolean winner = phrases.findLetters(guess);

            if (winner) {

                if (Math.random() < 0.5) {

                    Money money = new Money();

                    int moneyChange = money.displayWinnings(player, true);

                    player.setMoney(player.getMoney() + moneyChange);
                
                }

                 else {
            
            Physical physical = new Physical();

            int moneyChange = physical.displayWinnings(player, true);

            player.setMoney(player.getMoney() + moneyChange);

            }

            System.out.println(player.toString());

        }

        return winner;

    } catch (MultipleLettersException e) {

        System.out.println(e.getMessage());
        System.out.println("Try again on your next turn.");

        return false;
        }
    }
}

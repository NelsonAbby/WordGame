import java.util.Scanner;

public class GamePlay {
    private Players player;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //New host
        Hosts host = new Hosts("Alex");
        host.randomizeNum();

        //Get playrs first name
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        //optional last name
        System.out.print("Would you like to enter a last name? (y/n): ");
        String answer = scanner.nextLine();

        GamePlay game = new GamePlay();

        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            game.player = new Players(firstName, lastName);
        }
        else {
            game.player = new Players(firstName);
        }

        Turn turn = new Turn();

        boolean playAgain = true;

        while(playAgain) {
            boolean correct = false;

            while(!correct) {
                correct = turn.takeTurn(game.player, host);
        }

        System.out.print("Want to play again? (y/n)");
        String again = scanner.next();

        if (again.equalsIgnoreCase("y")){
            host.randomizeNum();
        }
        else {
            playAgain = false;
        }
    }

    System.out.println("Play again soon!");
}
}
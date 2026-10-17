import java.util.Scanner;

public class GamePlay {
    private Players[]currentPlayers = new Players[3];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GamePlay game = new GamePlay();
        
        System.out.println("Welcome to WordGame!");
        System.out.println();

        for (int i = 0; i < game.currentPlayers.length; i++) {

            //Get players first name
            System.out.print("Player " + (i + 1) + ": Enter your first name: ");
            String firstName = scanner.nextLine();

            //optional last name
            System.out.print("Would you like to enter a last name? (y/n): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                System.out.print("Enter your last name: ");
                String lastName = scanner.nextLine();

                game.currentPlayers[i] = new Players(firstName, lastName);
            }
            
            else {
                game.currentPlayers[i] = new Players(firstName);
            }

            System.out.println();
        }

        //New host
        Hosts host = new Hosts("Alex", scanner);

            Turn turn = new Turn(scanner);

            boolean playAgain = true;

            while(playAgain) {

                boolean winner = false;

                while(!winner) {
                    
                    for (int i = 0; i < game.currentPlayers.length; i++) {

                        winner = turn.takeTurn(
                            game.currentPlayers[i],
                            host
                        );
                        if (winner) {
                            break;
                        }
                    }
            }

        System.out.print("Want to play again? (y/n)");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")){
            host.enterNewPhrase();
        }

        else {
            playAgain = false;
        }

    }

    System.out.println("Play again soon!");

    scanner.close();
}
}
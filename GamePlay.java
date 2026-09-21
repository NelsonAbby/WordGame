import java.util.Scanner;

public class GamePlay {
    private Person person;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Would you like to enter a last name? (y/n): ");
        String answer = scanner.nextLine();

        GamePlay game = new GamePlay();

        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            game.person = new Person(firstName, lastName);
        }
        else {
            game.person = new Person(firstName);
        }

        Numbers numbers = new Numbers();
        numbers.generateNumber();

        boolean correct = false;

        while(!correct) {
            System.out.print(game.person.getFirstName() + ", enter your guess: ");
            int guess = scanner.nextInt();

            correct = numbers.compareNumber(guess);
        }

        scanner.close();
    }
}
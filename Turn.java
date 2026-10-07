import java.util.Scanner;

public class Turn {

    private Scanner scanner;

    public Turn(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean takeTurn(Players player, Hosts host){

        System.out.println(host.getFirstName() + " says, " + player.getFirstName() + ", take a guess between 0 and 100:");
        int guess = scanner.nextInt();

        Numbers numbers = new Numbers();

        boolean correct = numbers.compareNumber(guess);

        int moneyChange;

        if(Math.random() < 0.5) {

            Money money = new Money();

            moneyChange = money.displayWinnings(player, correct);

        }
        else {
            
            Physical physical = new Physical();

            moneyChange = physical.displayWinnings(player, correct);
        }

        player.setMoney(player.getMoney() + moneyChange);

        System.out.println(player);

        return correct;
    }
}

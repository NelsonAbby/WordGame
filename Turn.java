import java.util.Scanner;

public class Turn {

    private int winningAmount = 100;
    private int losingAmount = 100;

    public boolean takeTurn(Players player, Hosts host){
        Scanner scanner = new Scanner(System.in);

        System.out.println(host.getFirstName() + " says, " + player.getFirstName() + ", take a guess between 0 and 100:");
        int guess = scanner.nextInt();

        Numbers numbers = new Numbers();

        boolean correct = numbers.compareNumber(guess);

        if(correct){
            player.setMoney(player.getMoney() + winningAmount);

            System.out.println(player);
            return true;
        }
        else {
            player.setMoney(player.getMoney() - losingAmount);

            System.out.println(player);
            return false;
        }
    }
}

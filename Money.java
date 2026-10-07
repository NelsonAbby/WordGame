public class Money implements Award {
    private int winningAmount = 100;
    private int losingAmount = -100;

    @Override 
    public int displayWinnings(Players player, boolean correct) {

        if (correct) {

            System.out.println(player.getFirstName() + " won $" + winningAmount + "!");

            return winningAmount;
        }
        
        else {
            System.out.println(player.getFirstName() + " lost $" + Math.abs(losingAmount) + ".");

            return losingAmount;
        }
    }
}


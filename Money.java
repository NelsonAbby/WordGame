public class Money implements Award {
    
    private int winningAmount = 100;
    private int losingAmount = -100;

    @Override 
    public int displayWinnings(Players player, boolean correct) {

        if (correct) {
            return winningAmount;
        }
        
        else {
            return losingAmount;
        }
    }
}


public class Physical implements Award{

    private String[] prizes = {
        "75 inch TV",
        "Beach Vacation",
        "New Car",
        "Boat",
        "Cruise Trip"
    };

    private String lastPrize;

    @Override 
    public int displayWinnings(Players player, boolean correct) {
        if (correct) {
            int prizeIndex = (int) (Math.random() * prizes.length);
            lastPrize = prizes[prizeIndex];
        }

        return 0;
    }

    public String getLastPrize() {
        return lastPrize;
    }
}

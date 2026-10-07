public class Physical implements Award{

    private String[] prizes = {
        "75 inch TV",
        "Beach Vacation",
        "New Car",
        "Boat",
        "Cruise Trip"
    };

    public int getRandomPrize() {

        int randomIndex = (int) (Math.random() * prizes.length);

        return randomIndex;
    }

    @Override 
    public int displayWinnings(Players player, boolean correct) {

        int prizeIndex = getRandomPrize();

        if (correct) {
            System.out.println(player.getFirstName() + " won a " + prizes[prizeIndex] + "!");
        }

        else {
            System.out.println(player.getFirstName() + " lost.");

            System.out.println("You ALMOST won a " + prizes[prizeIndex] + "!");
        }

        return 0;
    }
}

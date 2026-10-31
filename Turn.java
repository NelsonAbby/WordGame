public class Turn {

    private String lastMessage = "";

    public boolean takeTurn(Players player, Hosts host, String guess){

        Phrases phrases = host.getPhrases();

        if (guess.isEmpty()) {
            lastMessage = "Please enter one letter";
            return false;
        }

        String phraseBefore = phrases.getPlayingPhrase();

        try {

            boolean winner = phrases.findLetters(guess);

            String phraseAfter = phrases.getPlayingPhrase();

            if (phraseBefore.equals(phraseAfter)) {
                lastMessage = "Letter not revealed.\n";

            } else {
                lastMessage = "Good guess!\n";
            }

            lastMessage += "Phrase: " + phraseAfter + "\n";

            if (winner) {

                if (Math.random() < 0.5) {

                    Money money = new Money();

                    int moneyChange = money.displayWinnings(player, true);

                    player.setMoney(player.getMoney() + moneyChange);

                    lastMessage += "You won $" + moneyChange + "\n";
                
                }

                 else {
            
                    Physical physical = new Physical();
                    physical.displayWinnings(player, true);

                    lastMessage += "You won the prize: " + physical.getLastPrize() + "!\n";
                }

                lastMessage += player.toString();
                lastMessage +="\nThe phrase has been guessed!";

            } else {
                lastMessage += player.toString();
            }

            return winner;

        } catch (MultipleLettersException e) {
            lastMessage = e.getMessage();
            return false;

        } catch (IllegalArgumentException e) {
            lastMessage = e.getMessage();
            return false;
        }
    }

    public String getLastMessage() {
        return lastMessage;
    }
}

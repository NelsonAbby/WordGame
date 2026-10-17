public class Phrases {

    private static String gamePhrase;
    private String playingPhrase;

    public static void setGamePhrase(String phrase) {
        gamePhrase = phrase;
    }

    public Phrases() {
        StringBuilder phraseBuilder = new StringBuilder();

        for (int i = 0; i < gamePhrase.length(); i++) {
            char letter = gamePhrase.charAt(i);

            if (Character.isLetter(letter)) {
                phraseBuilder.append("_");
            }

            else {
                phraseBuilder.append(letter);
            }
        }

        playingPhrase = phraseBuilder.toString();
    }

    public boolean findLetters(String letter)
           throws MultipleLettersException {

        if (letter.length() > 1) {
            throw new MultipleLettersException();
        }

        StringBuilder updatedPhrase = 
            new StringBuilder(playingPhrase);

        boolean found = false;

        for (int i = 0; i < gamePhrase.length(); i++) {

            String currentLetter = 
                    String.valueOf(gamePhrase.charAt(i));

            if (currentLetter.equalsIgnoreCase(letter)) {
                updatedPhrase.setCharAt(i, gamePhrase.charAt(i));
                found = true;
            }
        }

        playingPhrase = updatedPhrase.toString();

        if (!found) {
            System.out.println("Incorrect letter");
        }

        System.out.println("Phrase: " + playingPhrase);

        if (!playingPhrase.contains("_")) {
            System.out.println("The phrase has been guessed!");
            return true;
        }

        return false;

    }

    
}

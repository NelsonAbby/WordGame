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

        if (!letter.matches("[a-zA-Z]")) {
            throw new IllegalArgumentException("Please enter one letter");
        }

        StringBuilder updatedPhrase = 
            new StringBuilder(playingPhrase);

        for (int i = 0; i < gamePhrase.length(); i++) {
            if (Character.toLowerCase(gamePhrase.charAt(i)) == Character.toLowerCase(letter.charAt(0))) {
                updatedPhrase.setCharAt(i, gamePhrase.charAt(i));
            }
        }

        playingPhrase = updatedPhrase.toString();

        return !playingPhrase.contains("_");
    }
    
    public String getPlayingPhrase() {
        return playingPhrase;
    }

    public String getGamePhrase() {
        return gamePhrase;
    }
}

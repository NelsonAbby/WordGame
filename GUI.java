import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    
    private ArrayList<Players> players = new ArrayList<>();
private ArrayList<String> guessedLetters = new ArrayList<>();

    private Hosts host;
    private int currentPlayerIndex = 0;

    private JLabel playersLabel;
    private JLabel hostLabel;
    private JLabel phraseLabel;

    private JButton addPlayerButton;
    private JButton setHostButton;
    private JButton startTurnButton;

    public GUI() {
        setTitle("Word Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLayout(new FlowLayout());

        playersLabel = new JLabel("Players: None");
        hostLabel= new JLabel("Host: None");
        phraseLabel = new JLabel("Phrase: Not set");

        addPlayerButton = new JButton("Add Player");
        setHostButton = new JButton("Set Host / Phrase");
        startTurnButton = new JButton("Start Turn");

        add(playersLabel);
        add(addPlayerButton);
        add(hostLabel);
        add(setHostButton);
        add(phraseLabel);
        add(startTurnButton);

        addPlayerButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent event) {
                addPlayer();
            }
        });

        setHostButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent event) {
                setHost();
            }
        });

        startTurnButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent event) {
                startTurn();
            }
        });

        startTurnButton.setEnabled(false);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void addPlayer() {
        String name = JOptionPane.showInputDialog("Enter player's first name:");

        if (name != null && !name.trim().isEmpty()) {
            players.add(new Players(name.trim()));
            updatePlayersLabel();
        }
    }

    private void updatePlayersLabel() {
        String playerList = "Players: ";

        for (Players player : players) {
            playerList += player.toString() + " | ";
        }
        playersLabel.setText(playerList);

        updateTurnButton();
    }

    private void setHost() {
        String name = JOptionPane.showInputDialog("Enter host name: ");

        if (name == null || name.trim().isEmpty()) {
            return;
        }

        String phrase = JOptionPane.showInputDialog("Enter the phrase that the players will be guessing:");

        if (phrase == null || phrase.trim().isEmpty()) {
            return;
        }

        host = new Hosts(name.trim(), phrase.trim());

        hostLabel.setText("Host: " + host.getFirstName());

        updatePhraseLabel();
        updateTurnButton();
    }

    private void updatePhraseLabel() {
        if (host != null) {
            phraseLabel.setText("Phrase: " + host.getPhrases().getPlayingPhrase());
        }
    }

    private void updateTurnButton() {

        startTurnButton.setEnabled(!players.isEmpty() && host != null);
    }

    private void startTurn() {

        Players player = players.get(currentPlayerIndex);

        String guess = JOptionPane.showInputDialog(this, player.getFirstName() + ", guess one letter:");

        if (guess == null) {
            return;
        }

        guess = guess.trim();
        guess = guess.trim().toLowerCase();

        if (guessedLetters.contains(guess)) {
            JOptionPane.showMessageDialog(this, "That letter has already been guessed!");
            return;
        }

        guessedLetters.add(guess);

        try {
            
            if (!guess.matches("[a-zA-Z]")) {
                throw new IllegalArgumentException(
                    "Enter one letter from A to Z");
            }
                
            boolean correct = host.getPhrases()
                .getGamePhrase()
                .toLowerCase()
                .contains(guess.toLowerCase());

            boolean winner = host.getPhrases().findLetters(guess);

            updatePhraseLabel();

            String message;

            if (correct) {
                message = "That guess is correct!";
            } else {
                message = "Sorry, try again on your next turn.";
            }

            if (winner) {
                message = "You solved the phrase!\n";

                if (Math.random() < 0.5) {
                    Money money = new Money();

                    int prizeMoney = money.displayWinnings(player, true);

                    player.setMoney(player.getMoney() + prizeMoney);

                    message += "You won $" + prizeMoney + "!\n";

                } else {
                    Physical physical = new Physical();

                    physical.displayWinnings(player, true);

                    message += "WOW! You won a " + physical.getLastPrize() + "!\n";
                }

                message += "\n" + player.toString();

                updatePlayersLabel();

                JOptionPane.showMessageDialog(this, message);

                int answer = JOptionPane.showConfirmDialog(this, "Want to try again?", "Play Again", JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.YES_OPTION) {

                    String newPhrase = JOptionPane.showInputDialog(this, "Enter a new phrase:");

                    if (newPhrase != null && !newPhrase.trim().isEmpty()) {
                        host.setNewPhrase(newPhrase.trim());

                        guessedLetters.clear();

                        currentPlayerIndex = 0;

                        updatePhraseLabel();

                    } else {
                        startTurnButton.setEnabled(false);
                    }

                    return;

                } else {
                    String messageThanks = "Thanks for playing!";

                    JOptionPane.showMessageDialog(this, messageThanks);
                }
            }

            message += "\n\n" + player.toString();

            JOptionPane.showMessageDialog(this, message);

            currentPlayerIndex++;

            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }

        } catch (MultipleLettersException exception) {

                JOptionPane.showMessageDialog(this, exception.getMessage());

        } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }
    public static void main(String[] args) {
        GUI game = new GUI();
        
    }
}

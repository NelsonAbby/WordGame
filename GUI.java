import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GUI extends JFrame{
    
    private ArrayList<Players> players = new ArrayList<>();
private ArrayList<String> guessedLetters = new ArrayList<>();

    private Hosts host;
    private int currentPlayerIndex = 0;

    private JLabel playersLabel;
    private JLabel hostLabel;
    private JLabel phraseLabel;
    
    private JButton startTurnButton;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu aboutMenu;

    private JMenuItem addPlayerItem;
    private JMenuItem setHostItem;
    private JMenuItem layoutItem;

    private JPanel playerPanel;
    private JPanel gamePanel;
    private JPanel messagePanel;

    private JTextArea messageArea;
    private JScrollPane scrollPane;

    private JCheckBox saveMessagesCheckBox;

    public GUI() {
        setTitle("Word Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLayout(new BorderLayout(10, 10));
        
        //New menu bar added
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);

        addPlayerItem = new JMenuItem("Add Player");
        setHostItem = new JMenuItem("Add Host");
        layoutItem = new JMenuItem("Layout");

        gameMenu.add(addPlayerItem); 
        gameMenu.add(setHostItem);

        aboutMenu.add(layoutItem);

        menuBar.add(gameMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);

        playersLabel = new JLabel("Players: None");
        hostLabel= new JLabel("Host: None");
        phraseLabel = new JLabel("Phrase: Not set");

        //Lesson 7 code replaced
        //addPlayerButton = new JButton("Add Player");
        //setHostButton = new JButton("Set Host / Phrase");
        startTurnButton = new JButton("Start Turn");

        playerPanel = new JPanel(new FlowLayout());
        gamePanel = new JPanel(new FlowLayout());
        messagePanel = new JPanel(new BorderLayout());

        playerPanel.add(playersLabel);
        playerPanel.add(hostLabel);

        gamePanel.add(phraseLabel);
        gamePanel.add(startTurnButton);

        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(messageArea);

        saveMessagesCheckBox = new JCheckBox("Save Messages");
        saveMessagesCheckBox.setToolTipText("Check the box here to keep previous game messages visible");

        messagePanel.add(scrollPane, BorderLayout.CENTER);
        messagePanel.add(saveMessagesCheckBox, BorderLayout.SOUTH);

        add(playerPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        addPlayerItem.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent event) {
                addPlayer();
            }
        });

        setHostItem.addActionListener(new ActionListener() {
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

        layoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(
                    GUI.this,
                    "I chose this BorderLayout for the main window because " +
                    "it breaks the game into different sections. " +
                    "I also used FlowLayout for the smaller panels because " +
                    "it arranges the sections nicely without having to use complicated positioning."
                );
            }
        });

        startTurnButton.setEnabled(false);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void addMessage(String message) {
        if (saveMessagesCheckBox.isSelected()) {
            messageArea.append(message + "\n\n");
        } else {
            messageArea.setText(message + "\n\n");
        }
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

        guess = guess.trim().toLowerCase();

        if (guessedLetters.contains(guess)) {
            addMessage("That letter has already been guessed!");
            return;
        }

        try {
            
            if (!guess.matches("[a-zA-Z]")) {
                throw new IllegalArgumentException(
                    "Enter one letter from A to Z");
            }

            guessedLetters.add(guess);
                
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

                addMessage(message);

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
                    addMessage("Thanks for playing!");
                    startTurnButton.setEnabled(false);
                    return;
                }
            } else {
                message += "\n\n" + player.toString();
                addMessage(message);
            }

            currentPlayerIndex++;

            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }

        } catch (MultipleLettersException exception) {

                addMessage(exception.getMessage());

        } catch (IllegalArgumentException exception) {
                addMessage(exception.getMessage());
        }
    }
    public static void main(String[] args) {
        GUI game = new GUI();
        
    }
}

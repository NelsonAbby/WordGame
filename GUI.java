import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GUI extends JFrame{
    
    private ArrayList<Players> players = new ArrayList<>();
private ArrayList<String> guessedLetters = new ArrayList<>();

    private Hosts host;
    private int currentPlayerIndex = 0;

    private JLabel playersLabel;
    private JLabel hostLabel;
    private JLabel phraseLabel;
    private JLabel animationLabel;
    
    private JButton startTurnButton;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu aboutMenu;

    private JMenuItem addPlayerItem;
    private JMenuItem setHostItem;
    private JMenuItem layoutItem;
    private JMenuItem attributionItem;

    private JPanel playerPanel;
    private JPanel gamePanel;
    private JPanel messagePanel;

    private JTextArea messageArea;
    private JScrollPane scrollPane;

    private JCheckBox saveMessagesCheckBox;

    private Clip backgroundMusic;

    public GUI() {
        setTitle("Word Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override 
            public void windowClosing(java.awt.event.WindowEvent event) {
                if (backgroundMusic != null) {
                    backgroundMusic.stop();
                    backgroundMusic.close();
                }

                dispose();
                System.exit(0);
            }
        });


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
        attributionItem = new JMenuItem("Attribution");

        gameMenu.add(addPlayerItem); 
        gameMenu.add(setHostItem);

        aboutMenu.add(layoutItem);
        aboutMenu.add(attributionItem);

        menuBar.add(gameMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);

        playersLabel = new JLabel("Players: None");
        hostLabel= new JLabel("Host: None");
        phraseLabel = new JLabel("Phrase: Not set");

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

        attributionItem.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(

                    GUI.this,
                    "Image Attributions:\n" +
                    "Beach Image by romaneau from Pixabay\n" +
                    "TV Image by Tommaso Tabacchi Pixabay\n" +
                    "New Car image by Pixabay from Pixabay\n" +
                    "Cruise Image by Ridoe from Pixabay\n" +
                    "Boat Image by Freddy from Pixabay\n" +
                    "Sound Attributions:\n" +
                    "Sunshine and Beaches by MVM Productions from Shutterstock\n",
                    "Attributions",
                    JOptionPane.INFORMATION_MESSAGE 
                );
            }
        });

        startTurnButton.setEnabled(false);

        setLocationRelativeTo(null);
        setVisible(true);

        playBackgroundMusic();

    }

    private void showIncorrectAnimation() {

        animationLabel = new JLabel("INCORRECT");

        animationLabel.setFont(new Font("Arial", Font.BOLD, 36));
        animationLabel.setForeground(Color.RED);
        animationLabel.setHorizontalAlignment(JLabel.CENTER);

        gamePanel.add(animationLabel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();

        Timer animationTimer = new Timer(150, new ActionListener() {

            int count= 0;
            
            @Override 
            public void actionPerformed(ActionEvent event) {

                count++;

                if (count == 1) {
                    animationLabel.setForeground(Color.ORANGE);
                } else if (count == 2) {
                    animationLabel.setForeground(Color.RED);
                } else if (count == 3) {
                    animationLabel.setForeground(Color.ORANGE);
                } else {
                    gamePanel.remove(animationLabel);
                    gamePanel.revalidate();
                    gamePanel.repaint();

                    Timer timer = (Timer) event.getSource();
                    timer.stop();
                }
            }
        });

        animationTimer.start();
            
        }

    private void playBackgroundMusic() {

        try {
            File musicFile = new File("sounds/background.wav");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            backgroundMusic = AudioSystem.getClip();

            backgroundMusic.open(audioStream);

            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            
        } catch (Exception exception) {
            addMessage("Background msuic could not be played.");
        }
    }

    private void showPrizeImage(String prize) {

        String imageFile = "";

        if (prize.equals("75 inch TV")) {
            imageFile = "images/tv.jpg";
        } else if (prize.equals("Beach Vacation")) {
            imageFile = "images/beach.jpg";
        } else if (prize.equals("New Car")) {
            imageFile = "images/car.jpg";
        } else if (prize.equals("Boat")) {
            imageFile = "images/boat.jpg";
        } else if (prize.equals("Cruise Trip")) {
            imageFile = "image/cruise.jpg";
        }

        ImageIcon prizeIcon = new ImageIcon(imageFile);

        JLabel imageLabel = new JLabel(prizeIcon);

        JOptionPane.showMessageDialog(
            this, imageLabel, "You Won A Prize!", JOptionPane.INFORMATION_MESSAGE
        );
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
                showIncorrectAnimation();
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

                    String prize = physical.getLastPrize();

                    message += "WOW! You won a " + prize + "!\n";

                    showPrizeImage(prize);
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

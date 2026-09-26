public class Numbers {
    private static int randomNum;

    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }

    public void generateNumber() {
        randomNum = (int)(Math.random() * 101);
    }

    public boolean compareNumber(int guess) {
        if (guess == randomNum) {
            System.out.println("You Guessed the Number!");
            return true;
        }
        else if (guess > randomNum) {
            System.out.println("Too high!");
            return false;
        }
        else {
            System.out.println("Too low!");
            return false;
        }
    }
}
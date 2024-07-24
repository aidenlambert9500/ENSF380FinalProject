package ca.ucalgary.edu.ensf380;

public class Advertisements implements Displayable {
    private String content;
    private int duration;

    public Advertisements(String content, int duration) {
        this.content = content;
        this.duration = duration;
    }

    @Override
    public void display() {
        System.out.println("Advertisement: " + content + " Duration: " + duration + " seconds");
    }
}

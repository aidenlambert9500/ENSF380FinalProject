package ca.ucalgary.edu.ensf380;

public class NewsFeed implements Displayable {
    private String headline;
    private String story;

    public NewsFeed(String headline, String story) {
        this.headline = headline;
        this.story = story;
    }

    @Override
    public void display() {
        System.out.println("News: " + headline + "\n" + story);
    }
}

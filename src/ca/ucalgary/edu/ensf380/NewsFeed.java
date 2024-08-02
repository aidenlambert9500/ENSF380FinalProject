
package ca.ucalgary.edu.ensf380;

import java.io.IOException;

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
    
    public static void main(String[] args) throws IOException {
    	NewsAPI news = new NewsAPI();
    	
    	news.fetchNewsByKeyword("LeBron");
    	System.out.println(news.getNews());
    }
}

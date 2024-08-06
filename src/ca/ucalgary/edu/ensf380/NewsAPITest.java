package ca.ucalgary.edu.ensf380;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class NewsAPITest {

    private NewsAPI newsAPI;

    @Before
    public void setUp() {
        newsAPI = new NewsAPI();
    }

    @Test
    public void testFetchNewsByKeyword() {
        try {
            newsAPI.fetchNewsByKeyword("lebron");
            ArrayList<String> news = newsAPI.getNews();
            
            // Since this involves actual network requests, the results may vary. 
            // Here, we are just checking if we got some results.
            assertNotNull(news);
            assertTrue(news.size() > 0);
            System.out.println("News titles by keyword 'lebron': " + news);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during fetchNewsByKeyword");
        }
    }

    @Test
    public void testFetchNews() {
        try {
            newsAPI.fetchNews();
            ArrayList<String> news = newsAPI.getNews();
            
            // Similar to the keyword test, we are verifying the method works without errors.
            assertNotNull(news);
            assertTrue(news.size() > 0);
            System.out.println("Breaking news titles: " + news);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during fetchNews");
        }
    }

    @Test
    public void testFetchNewsByUrl() {
        try {
            // Using a direct URL for testing. Replace with an actual API URL for real tests.
            ArrayList<String> news = newsAPI.fetchNewsByUrl("https://newsapi.org/v2/top-headlines?country=us&apiKey=a4ee91a541d94cb9950c4f2f22cd2a8a");
            
            // Check if we received news items
            assertNotNull(news);
            assertTrue(news.size() > 0);
            System.out.println("News titles from direct URL: " + news);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during fetchNewsByUrl");
        }
    }
}

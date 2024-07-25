/*
 * @author Aiden Lambert, Wesley Lui, Jacelynn Doan
 */

package ca.ucalgary.edu.ensf380;

import java.util.ArrayList;

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
    
    public static void main(String[] args) {
    	Database db = new Database();
    	db.connect();
    	ArrayList<String> ads = new ArrayList<String>();
    	ads = db.getAds();
    	System.out.println(ads);
    }
}

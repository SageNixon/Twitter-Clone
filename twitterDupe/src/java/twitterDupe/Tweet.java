package twitterDupe;

import java.sql.Timestamp;

public class Tweet {

    private int tweet_id;
    private int user_id;
    private String content;
    private Timestamp created_at;
    private int heart_count;
    
    
    private byte[] image;
    private String filename;

    
    
    
    private String username;

    public Tweet() {
        //empty constructor
    }

        public Tweet(int tweet_id, int user_id, String content, Timestamp created_at, int heart_count, byte[] image, String filename) {
        this.tweet_id = tweet_id;
        this.user_id = user_id;
        this.content = content;
        this.created_at = created_at;
        this.heart_count = heart_count;
        this.image = image;
        this.filename = filename;
    }

    public int getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(int tweet_id) {
        this.tweet_id = tweet_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
    
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getHeart_count() {
        return heart_count;
    }

    public void setHeart_count(int heart_count) {
        this.heart_count = heart_count;
    }
    
    
        public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

package twitterDupe;

/**
 *
 * @author sagenixon
 */
public class Heart {
    private int heart_id;
    private int user_id;
    private int tweet_id;
    
    
    public Heart(){
        //empty constructor
    }

    public Heart(int heart_id, int user_id, int tweet_id) {
        this.heart_id = heart_id;
        this.user_id = user_id;
        this.tweet_id = tweet_id;
    }

    public int getHeart_id() {
        return heart_id;
    }

    public void setHeart_id(int heart_id) {
        this.heart_id = heart_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(int tweet_id) {
        this.tweet_id = tweet_id;
    }
    

    
    
}

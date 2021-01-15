package tech.codingclub.helix.entity;

public class TweetUI extends Tweet{

    public String author_name;
    public String email;

    public TweetUI() {

    }

    public TweetUI(Member member, Tweet tweet){
        this.author_name = member.name;
        this.email = member.email;
        this.message = tweet.message;
        this.id = tweet.id;
        this.created_at = tweet.created_at;
        this.author_id = tweet.author_id;
    }


}

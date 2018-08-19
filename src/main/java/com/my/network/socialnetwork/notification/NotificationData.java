package com.my.network.socialnetwork.notification;

import com.my.network.socialnetwork.model.post.Comment;
import com.my.network.socialnetwork.model.post.Post;
import com.my.network.socialnetwork.model.post.PostLike;

public class NotificationData {

    private String title;
    private String body;
    private String screen;
    private String image;
    private PostLike postLike;
    private Post post;
    private Comment comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PostLike getPostLike() {
        return postLike;
    }

    public void setPostLike(PostLike postLike) {
        this.postLike = postLike;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

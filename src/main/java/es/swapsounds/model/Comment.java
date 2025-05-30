package es.swapsounds.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentId; // comment identification

    @ManyToOne
    private User user; // user who uploaded the comment

    private String content; // comment content

    @ManyToOne
    private Sound sound; // sound related to the comment

    private LocalDateTime created; // comment creation date
    private LocalDateTime modified; // comment modification date
    private long soundId;
    private String soundTitle;

    @Transient
    private boolean CommentOwner;

    public Comment() {
    }

    // Getters
    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getCreated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return created.format(formatter);
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public long getAuthorId() {
        return this.user.getUserId();
    }

    public long getSoundId() {
        return soundId;
    }

    public void setSoundId(long soundId) {
        this.soundId = soundId;
    }

    public String getSoundTitle() {
        return soundTitle;
    }

    public void setSoundTitle(String soundTitle) {
        this.soundTitle = soundTitle;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCommentOwner() {
        return CommentOwner;
    }

    public void setCommentOwner(boolean CommentOwner) {
        this.CommentOwner = CommentOwner;
    }
}
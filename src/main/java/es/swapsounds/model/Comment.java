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
    private User user; // user uploader of the comment

    private String content; // comment content

    @ManyToOne
    private Sound sound; // sound to which the comment is related

    private LocalDateTime created; // comment upload date
    private LocalDateTime modified; // comment modification date
    private long soundId;
    private String soundTitle;

    public Comment(Long commentId, String content, User user) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.created = LocalDateTime.now();
        this.modified = null;
    }

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
}
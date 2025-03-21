package es.swapsounds.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] audioFile;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageFile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
        name = "sound_category",
        joinColumns = @JoinColumn(name = "sound_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    private String duration;
    private LocalDateTime uploadDate;

    public Sound() {
     // JPA
    }
    
    public Sound(String title, String description, byte[] imageFile, byte[] audioFile, User user, String duration) {
        this.title = title;
        this.description = description;
        this.audioFile = audioFile;
        this.imageFile = imageFile;
        this.user = user;
        this.comments = new ArrayList<>();
        this.duration = duration;
        this.uploadDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
    }

    public Sound(long id, String title, String description, byte[] imageFile, byte[] audioFile, User user) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.audioFile = audioFile;
        this.imageFile = imageFile;
        this.user = user;
        this.uploadDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
    }

    public Sound(long id, String title, String description,byte[] imageFile, byte[] audioFile, String duration) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.audioFile = audioFile;
        this.imageFile = imageFile;
        this.duration = duration;
        this.uploadDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
    }
    
    public Sound(long i, String title2, String description2, byte[] imageFile, byte[] audioFile, User user, String duration2) {
        this.id = i;
        this.title = title2;
        this.description = description2;
        this.audioFile = audioFile;
        this.imageFile = imageFile;
        this.user = user;
        this.duration = duration2;
        this.uploadDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
    }

    public Sound(long i, String title, String description, byte[] imageFile, byte[] audioFile, User user, Category category, String duration) {
        this.id = i;
        this.title = title;
        this.description = description;
        this.audioFile = audioFile;
        this.imageFile = imageFile;
        this.duration = duration;
        this.uploadDate = LocalDateTime.now();
        this.categories = new ArrayList<>();
        this.categories.add(category);
        this.user = user;
    }

    public byte[] getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(byte[] audioFile) {
        this.audioFile = audioFile;
    }

    public byte[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public long getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    public long setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addToCategory(Category category) {
        this.categories.add(category); //This will add the category to the sound
        category.addSound(this); //This will add the sound to the category
    }
}
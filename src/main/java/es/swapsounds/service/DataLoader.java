package es.swapsounds.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.swapsounds.model.Category;
import es.swapsounds.model.Sound;
import es.swapsounds.model.User;
import es.swapsounds.repository.SoundRepository;
import es.swapsounds.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final SoundRepository soundRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(SoundRepository soundRepository, UserRepository userRepository, CategoryService categoryService,
            PasswordEncoder passwordEncoder) {
        this.soundRepository = soundRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0 && soundRepository.count() == 0) {

            Category football = categoryService.findOrCreateCategory("Football");
            Category meme = categoryService.findOrCreateCategory("Meme");
            Category ai = categoryService.findOrCreateCategory("AI");

            User user1 = new User("sofia", "sofia@example.com", passwordEncoder.encode("sofia123"), null);
            user1.setRoles(List.of("USER"));
            User user2 = new User("david", "david@example.com", passwordEncoder.encode("david123"), null);
            user2.setRoles(List.of("USER"));

            Blob adminProfilePictureBlob = null;
            try (InputStream adminImageStream = getClass().getResourceAsStream("/static/images/ozuna.jpeg")) {
                if (adminImageStream != null) {
                    adminProfilePictureBlob = new SerialBlob(adminImageStream.readAllBytes());
                } else {
                    System.err.println("Error loading admin image!");
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                System.err.println("Error converting admin image to Blob: " + e.getMessage());
            }

            User user3 = new User("admin", "tete@example.com", passwordEncoder.encode("admin"),
                    adminProfilePictureBlob);
            user3.setRoles(List.of("ADMIN", "USER"));

            userRepository.saveAll(List.of(user1, user2, user3));

            try {
                // Load files from classpath and convert them to Blob
                InputStream cr7AudioStream = getClass().getResourceAsStream("/static/audio/CR7.mp3");
                InputStream cr7ImageStream = getClass().getResourceAsStream("/static/images/CR7.jpg");
                InputStream betisAudioStream = getClass().getResourceAsStream("/static/audio/betis.mp3");
                InputStream betisImageStream = getClass().getResourceAsStream("/static/images/betis.png");
                InputStream diabloAudioStream = getClass()
                        .getResourceAsStream("/static/audio/el-diablo-que-malditos-tenis.mp3");
                InputStream diabloImageStream = getClass()
                        .getResourceAsStream("/static/images/el-diablo-que-malditos-tenis.png");
                InputStream senorNocheAudioStream = getClass()
                        .getResourceAsStream("/static/audio/ElSenorDeLaNoche.mp3");
                InputStream senorNocheImageStream = getClass()
                        .getResourceAsStream("/static/images/ElSenorDeLaNoche.jpg");

                Blob cr7AudioBlob = (cr7AudioStream != null) ? new SerialBlob(cr7AudioStream.readAllBytes()) : null;
                Blob cr7ImageBlob = (cr7ImageStream != null) ? new SerialBlob(cr7ImageStream.readAllBytes()) : null;
                Blob betisAudioBlob = (betisAudioStream != null) ? new SerialBlob(betisAudioStream.readAllBytes())
                        : null;
                Blob betisImageBlob = (betisImageStream != null) ? new SerialBlob(betisImageStream.readAllBytes())
                        : null;
                Blob diabloAudioBlob = (diabloAudioStream != null) ? new SerialBlob(diabloAudioStream.readAllBytes())
                        : null;
                Blob diabloImageBlob = (diabloImageStream != null) ? new SerialBlob(diabloImageStream.readAllBytes())
                        : null;
                Blob senorNocheAudioBlob = (senorNocheAudioStream != null)
                        ? new SerialBlob(senorNocheAudioStream.readAllBytes())
                        : null;
                Blob senorNocheImageBlob = (senorNocheImageStream != null)
                        ? new SerialBlob(senorNocheImageStream.readAllBytes())
                        : null;

                // Create sounds and associate them with users and categories
                Sound sound1 = new Sound();
                sound1.setTitle("CR7");
                sound1.setDescription("CR7 leaves the Simpsons");
                sound1.setAudioBlob(cr7AudioBlob); // Save Blob
                sound1.setImageBlob(cr7ImageBlob); // Save Blob
                sound1.setDuration("3:15");
                sound1.setUploadDate(LocalDateTime.now());
                sound1.setUserId(user1.getUserId()); // Assuming User has getUserId()
                sound1.setCategories(Arrays.asList(football));

                Sound sound2 = new Sound();
                sound2.setTitle("Betis");
                sound2.setDescription("Betis anthem");
                sound2.setAudioBlob(betisAudioBlob); // Save Blob
                sound2.setImageBlob(betisImageBlob); // Save Blob
                sound2.setDuration("5:00");
                sound2.setUploadDate(LocalDateTime.now());
                sound2.setUserId(user2.getUserId()); // Assuming User has getUserId()
                sound2.setCategories(Arrays.asList(football));

                Sound sound3 = new Sound();
                sound3.setTitle("El Diablo que malditos tenis");
                sound3.setDescription("Celebration over some awesome sneakers");
                sound3.setAudioBlob(diabloAudioBlob); // Save Blob
                sound3.setImageBlob(diabloImageBlob); // Save Blob
                sound3.setDuration("3:00");
                sound3.setUploadDate(LocalDateTime.now());
                sound3.setUserId(user2.getUserId()); // Assuming User has getUserId()
                sound3.setCategories(Arrays.asList(meme));

                Sound sound4 = new Sound();
                sound4.setTitle("El señor de la noche");
                sound4.setDescription("The lord of the night, goat");
                sound4.setAudioBlob(senorNocheAudioBlob); // Save Blob
                sound4.setImageBlob(senorNocheImageBlob); // Save Blob
                sound4.setDuration("3:00");
                sound4.setUploadDate(LocalDateTime.now());
                sound4.setUserId(user1.getUserId()); // Assuming User has getUserId()
                sound4.setCategories(Arrays.asList(meme, ai));

                soundRepository.saveAll(List.of(sound1, sound2, sound3, sound4));

                // Close the streams
                if (cr7AudioStream != null)
                    cr7AudioStream.close();
                if (cr7ImageStream != null)
                    cr7ImageStream.close();
                if (betisAudioStream != null)
                    betisAudioStream.close();
                if (betisImageStream != null)
                    betisImageStream.close();
                if (diabloAudioStream != null)
                    diabloAudioStream.close();
                if (diabloImageStream != null)
                    diabloImageStream.close();
                if (senorNocheAudioStream != null)
                    senorNocheAudioStream.close();
                if (senorNocheImageStream != null)
                    senorNocheImageStream.close();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
                System.err.println("Error loading or saving files as Blob: " + e.getMessage());
            }

            System.out.println("Test users, sounds, and categories loaded.");
        }
    }
}
//para almacenar los comentarios de los sonidos
package es.swapsounds.storage;

import org.springframework.stereotype.Repository;

import es.swapsounds.model.Comment;
import es.swapsounds.model.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {
    // Map: soundId -> Comment List
    private final Map<Long, List<Comment>> commentsBySoundId = new ConcurrentHashMap<>();

    public Comment addComment(long soundId, String soundTitle, String content, User user) {
        Comment comment = new Comment(
                UUID.randomUUID().getMostSignificantBits(), // ID único
                content, // Contenido del comentario
                user // Usuario que comenta
        );

        // Añadir información del sonido al comentario
        comment.setSoundId(soundId);
        comment.setSoundTitle(soundTitle);
        comment.setCreated(LocalDateTime.now()); // Fecha de creación

        // Almacenar el comentario
        commentsBySoundId
                .computeIfAbsent(soundId, k -> new CopyOnWriteArrayList<>())
                .add(comment);

        return comment;
    }

    public List<Comment> getCommentsBySoundId(long soundId) {
        return commentsBySoundId.getOrDefault(soundId, Collections.emptyList());
    }

    public boolean editComment(long soundId, long commentId, String newContent, User user) {
        return commentsBySoundId.getOrDefault(soundId, Collections.emptyList())
                .stream()
                .filter(comment -> comment.getCommentId() == commentId &&
                        comment.getUser().equals(user))
                .findFirst()
                .map(comment -> {
                    comment.setContent(newContent);
                    comment.setModified(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteComment(long commentId) {
        return commentsBySoundId.values().stream().anyMatch(comments -> comments.removeIf(c -> c.getCommentId() == commentId));
    }

    public void deleteCommentsByUserId(long userId) {
        commentsBySoundId.values()
                .forEach(comments -> comments
                .removeIf(comment -> comment.getUser().getUserId() == userId));
    }

    public Optional<Comment> findCommentById(long commentId) {
        return commentsBySoundId.values().stream() // Recorre todas las listas de comentarios
            .flatMap(List::stream) // Convierte las listas en un solo stream
            .filter(comment -> comment.getCommentId() == commentId)// Filtra por ID
            .findFirst(); // Devuelve el primer comentario que coincida
    }

    public List<Comment> getCommentsByUserId(long userId) {
    return commentsBySoundId.values().stream()
        .flatMap(List::stream)
        .filter(comment -> comment.getAuthorId() == userId)
        .collect(Collectors.toList());
}
}
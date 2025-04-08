package es.swapsounds.storage;

import es.swapsounds.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySoundId(long soundId);
    List<Comment> findByUserUserId(long userId);
    void deleteByUserUserId(long userId);
    void deleteBySoundId(long soundId);
}
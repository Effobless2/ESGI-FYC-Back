package fr.esgi.fyc.domain.repository;
import fr.esgi.fyc.domain.model.Post;
import java.util.List;

public interface IPostRepository {

  public int add(Post post);
  public Post selectPostById(int postId);
  public List<Post> selectAllPostsByUser(int userId);
  public int deletePost(int postId);

}

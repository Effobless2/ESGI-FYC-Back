package fr.esgi.fyc.domain.repository;
import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.model.User;

import java.util.List;

public interface IPostRepository {

  public int add(Post post);
  public Post selectPostById(int postId);
  public List<Post> selectAllPosts();
  public List<Post> selectAllPostsByUser(User user);
  public int deletePost(int postId);

}

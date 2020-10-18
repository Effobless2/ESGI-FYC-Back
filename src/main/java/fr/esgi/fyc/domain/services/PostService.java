package fr.esgi.fyc.domain.services;

import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  @Autowired
  private IPostRepository iPostRepository;

  public int add(Post post){ return iPostRepository.add(post); }

  public Post getById(int postId){ return  iPostRepository.selectPostById(postId); }

  public List<Post> getByUser(int userId) { return iPostRepository.selectAllPostsByUser(userId); }

  public int deletePost(int postId) { return iPostRepository.deletePost(postId);}

}

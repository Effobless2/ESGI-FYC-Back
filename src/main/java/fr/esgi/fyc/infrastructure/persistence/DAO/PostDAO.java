package fr.esgi.fyc.infrastructure.persistence.DAO;

import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.repository.IPostRepository;
import fr.esgi.fyc.infrastructure.persistence.parsers.PostRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDAO extends JdbcDaoSupport implements IPostRepository {

  @Autowired
  private DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @Override
  public int add(Post post) {
    try{
      final String POST_INSERT = "INSERT INTO posts (title, content, created_at, id_user) values (?,?,?,?)";

      getJdbcTemplate().update(POST_INSERT,
        post.getTitle(),
        post.getContent(),
        post.getCreatedAt(),
        post.getUser().getId()
      );

      return getJdbcTemplate().queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
      }catch (EmptyResultDataAccessException e) {
        System.out.println("ERROR IN POSTDAO.ADD : " + e);
        return -1;
      }
  }

  @Override
  public Post selectPostById(int id){
    final String POST_GET_BY_ID = "SELECT p.id, p.title, p.content, p.created_at, p.id_user, u.email, u.lastName, u.firstName FROM posts AS p JOIN users AS u WHERE p.id_user = u.id AND p.id = ?";
    try{
      return getJdbcTemplate().queryForObject(POST_GET_BY_ID, new Object[]{id}, new PostRowMapper());
    }catch (EmptyResultDataAccessException e) {
      System.out.println("ERROR IN POSTDAO.SELECT_POST_BY_ID : " + e);
      return null;
    }
  }

  @Override
  public List<Post> selectAllPosts(){
    final String POST_GET_ALL= "SELECT p.id, p.title, p.content, p.created_at, p.id_user, u.email, u.lastName, u.firstName FROM posts AS p JOIN users AS u WHERE p.id_user = u.id";
    List<Post> posts = getJdbcTemplate().query(POST_GET_ALL, new PostRowMapper());
    return posts;
  }

  @Override
  public List<Post> selectAllPostsByUser(User user){
    final String POST_GET_ALL_BY_USER = "SELECT p.id, p.title, p.content, p.created_at, p.id_user, u.email, u.lastName, u.firstName FROM posts AS p JOIN users AS u WHERE p.id_user = u.id AND u.id = ?";
    List<Post> posts = getJdbcTemplate().query(POST_GET_ALL_BY_USER, new Object[]{user.getId()}, new PostRowMapper());
    return posts;
  }

  @Override
  public int updatePost(Post post){
    final String POST_UPDATE = "UPDATE posts AS p SET p.title = ?, p.content = ? WHERE p.id = ?";
    try{
      return getJdbcTemplate().update(POST_UPDATE,
              post.getTitle(),
              post.getContent(),
              post.getId()
      );
    }catch (RuntimeException e) {
      System.out.println("ERROR IN POSTDAO.UPDATE_POST : " + e);
      return 0;
    }
  }

  @Override
  public int deletePost(int postId){
    final String POST_DELETE = "DELETE FROM posts AS p WHERE p.id = ?";
    try{
      return getJdbcTemplate().update(POST_DELETE, postId);
    }catch (RuntimeException e) {
      System.out.println("ERROR IN POSTDAO.POST_DELETE : " + e);
      return 0;
    }
  }

}

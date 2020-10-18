package fr.esgi.fyc.infrastructure.persistence.DAO;

import fr.esgi.fyc.domain.model.Post;
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
        post.getIdUser()
      );

      return getJdbcTemplate().queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
      }catch (EmptyResultDataAccessException e) {
        System.out.println("ERROR IN POSTDAO.ADD : " + e);
        return -1;
      }
  }

  @Override
  public List<Post> selectAllPostsByUser(int userId){
    final String POST_GET_ALL_BY_USER = "SELECT id, title, content, created_at, id_user FROM posts AS p WHERE p.id_user = ?";
    List<Post> posts = getJdbcTemplate().query(POST_GET_ALL_BY_USER, new Object[]{userId}, new PostRowMapper());
    return posts;
  }

}

package fr.esgi.fyc.infrastructure.persistence.DAO;

import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

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

    final String POST_INSERT = "INSERT INTO posts (title, content, created_at, id_user) values (?,?,?,?)";

    getJdbcTemplate().update(POST_INSERT,
      post.getTitle(),
      post.getContent(),
      post.getCreatedAt(),
      post.getIdUser()
    );

    return getJdbcTemplate().queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
  }

}

package fr.esgi.fyc.infrastructure.persistence.parsers;

import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post>{
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setId(rs.getInt("id_user"));
        user.setEmail(rs.getString("email"));
        user.setLastName(rs.getString("lastName"));
        user.setFirstName(rs.getString("firstName"));

        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCreatedAt(rs.getDate("created_at"));
        post.setUser(user);

        return post;
    }

}

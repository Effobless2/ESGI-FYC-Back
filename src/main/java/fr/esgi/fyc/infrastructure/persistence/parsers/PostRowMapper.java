package fr.esgi.fyc.infrastructure.persistence.parsers;

import fr.esgi.fyc.domain.model.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post>{
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {

        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCreatedAt(rs.getDate("created_at"));
        post.setIdUser(rs.getInt("id_user"));

        return post;
    }

}

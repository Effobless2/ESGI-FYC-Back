package fr.esgi.fyc.domain.model;

import java.time.Instant;
import java.util.Date;

public class Post {
  private int id;
  private String title;
  private String content;
  private Date createdAt;
  private int idUser;

  public Post(int id, String title, String content, Date createdAt, int idUser) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.idUser = idUser;
  }

  public Post(){}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }
}

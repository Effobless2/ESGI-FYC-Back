package fr.esgi.fyc.infrastructure.web.DTO;

public class PostDTO {
  private String title;
  private String content;
  private String createdAt;

  public PostDTO(String title, String content, String createdAt) {
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
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

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

}

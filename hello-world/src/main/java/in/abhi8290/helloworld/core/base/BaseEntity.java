package in.abhi8290.helloworld.core.base;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

  @Id
  private String id = UUID.randomUUID().toString(); // Avoid JPA generation for UUID

  @Column(updatable = false, nullable = false)
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  private OffsetDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    this.updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
  }

  public String getId() {
    return id;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}

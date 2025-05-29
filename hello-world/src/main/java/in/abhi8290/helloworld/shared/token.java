package in.abhi8290.helloworld.shared;

import in.abhi8290.helloworld.core.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import in.abhi8290.helloworld.user.User;

@Entity
@Table(name = "token")
public class Token extends BaseEntity {

  @Column(nullable = false)
  String token;

  @Column(nullable = false)
  String purpose;

  @Column(nullable = false)
  OffsetDateTime validTill;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  public Token() {
  }

  public Token(String token, String purpose, OffsetDateTime validTill, User user) {

    this.user = user;
    this.token = token;
    this.purpose = purpose;
    this.validTill = validTill;

  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

}

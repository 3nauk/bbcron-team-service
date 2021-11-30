package com.bbcron.team.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserDomain implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * User Id
   */
  @Id
  private String userId;

  private String userName;

}

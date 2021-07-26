package com.bbcron.team.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "teams")
@JsonPropertyOrder({"teamId", "teamName", "namespace"})
public class TeamDomain {

  /**
   * Team Id
   * 
   */
  @Id
  private String teamId;

  /**
   * Team name
   */
  private String teamName;

  /**
   * Team namespace
   */
  private String namespace;

  /**
   * Created date
   */
  @Default
  private Instant createdDate = Instant.now();

  /**
   * Created By userId
   */
  @DBRef
  private UserDomain createdBy;

  /**
   * has Teams
   */
  @DBRef
  @Default
  private Set<TeamDomain> teams = new HashSet<TeamDomain>();

  /**
   * has users
   */
  @DBRef
  @Default
  private Set<UserDomain> users = new HashSet<UserDomain>();

}

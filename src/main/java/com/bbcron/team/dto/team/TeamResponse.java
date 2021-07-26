package com.bbcron.team.dto.team;

import com.bbcron.team.dto.user.UserResponse;
import java.util.Set;
import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponse extends RepresentationModel<TeamResponse> {

  /**
   * Team Id
   */
  private String teamId;

  /**
   * Team Name
   */
  private String teamName;


  /**
   * Namespace
   */
  private String namespace;

  /**
   * Created by
   */
  private UserResponse createdBy;

  /**
   * Teams Includes
   */
  private Set<TeamBaseResponse> teams;

  /**
   * Users Includes
   */
  private Set<UserResponse> users;

}

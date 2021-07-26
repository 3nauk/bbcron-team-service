package com.bbcron.team.dto.team;

import com.bbcron.team.dto.user.UserRequest;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {

  /**
   * Team Name
   */
  @NotBlank
  private String name;


  /**
   * namespace
   * <p>
   * projectName/teamId
   * <p>
   * userName/projectName/teamName
   */
  @NotBlank
  private String namespace;

  /**
   * Team Created by User
   */
  @NotBlank
  private UserRequest createdBy;

}

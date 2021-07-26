package com.bbcron.team.dto.team;

import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamBaseResponse extends RepresentationModel<TeamBaseResponse> {

  /**
   * Team Id
   */
  private String teamId;

  /**
   * Team name
   */
  private String name;


}

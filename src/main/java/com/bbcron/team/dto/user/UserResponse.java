package com.bbcron.team.dto.user;

import javax.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends RepresentationModel<UserResponse> {
  @NotBlank
  private String userId;

}

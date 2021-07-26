package com.bbcron.team.dto.user;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO User
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
public class UserDto extends RepresentationModel<UserDto> {

  /**
   * User Id
   */
  private String userId;

  /**
   * UserName
   */
  @NotBlank
  private String username;

  /**
   * Password
   */
  @NotBlank
  @Size(min = 7, max = 12)
  private String password;

  /**
   * User email
   */
  @NotBlank
  @Size(max = 120)
  private String email;

  /**
   * First Name
   */
  private String firstName;

  /**
   * Second Name
   */
  private String secondName;

  /**
   * User Status {@link UserStatus}
   */
  private UserStatus status = UserStatus.active;

  /**
   * Creation date
   */
  private Instant creation_time = Instant.now();

  /***
   * Update Date
   */
  private Instant update_time = Instant.now();

  /**
   * Date to Deletion Account
   */
  private Instant deletion_time;

  private boolean isRoot = false;

  private int authType = 1;

  private int typeAccount = 1;

  private String billingCompanyName;

  private String billingContactName;

  private String billingVatNumber;

  private String billingAddress;

  private String billingEmailAddress;

  private int flavour = 1;

}

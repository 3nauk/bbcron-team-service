package com.bbcron.team.mapper;

import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.UserDomain;
import com.bbcron.team.dto.team.TeamBaseResponse;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserRequest;
import com.bbcron.team.dto.user.UserResponse;
import java.util.Set;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MapStructMapper {

  /**
   * Convert a teamDomain to TeamBase
   * 
   * @param team
   * @return
   */
  TeamBaseResponse teamToTeamBaseDto(TeamDomain team);

  /**
   * 
   * @param teamDomain
   * @return
   */
  TeamResponse teamToTeamResponse(TeamDomain teamDomain);

  /**
   * 
   * @param teamRequest
   * @return
   */
  TeamDomain teamRequestToDomain(TeamRequest teamRequest);


  /**
   * 
   * @param userDomain
   * @return
   */
  UserResponse userTo(UserDomain userDomain);

  /**
   * 
   * @param usersDomain
   * @return
   */
  Set<UserResponse> usersToUsersBase(Set<UserDomain> usersDomain);

  /**
   * 
   * @param teamsDomain
   * @return
   */
  Set<TeamBaseResponse> teamsToTeamBase(Set<TeamDomain> teamsDomain);

  /**
   * 
   * @param teamsDomain
   * @return
   */
  Set<TeamResponse> teamsToTeamResponse(Set<TeamDomain> teamsDomain);

  /**
   * 
   * @param userDto
   * @return
   */
  UserDomain userDtoToDomain(UserRequest userDto);

}

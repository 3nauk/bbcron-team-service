package com.bbcron.team.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.bbcron.team.controller.TeamController;
import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.UserDomain;
import com.bbcron.team.dto.team.TeamBaseResponse;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserRequest;
import com.bbcron.team.dto.user.UserResponse;
import com.bbcron.team.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
@Service
public class MapStructMapperImpl implements MapStructMapper {

  @Override
  public TeamBaseResponse teamToTeamBaseDto(TeamDomain team) {
    if (team == null)
      return null;
    TeamBaseResponse aTeamBase =
        TeamBaseResponse.builder().teamId(team.getTeamId()).name(team.getTeamName()).build();
    aTeamBase
        .add(linkTo(methodOn(TeamController.class).getTeam(aTeamBase.getTeamId())).withSelfRel());

    return aTeamBase;
  }

  @Override
  public TeamResponse teamToTeamResponse(TeamDomain teamDomain) {
    if (teamDomain == null)
      return null;
    TeamResponse teamResponse = TeamResponse.builder().teamId(teamDomain.getTeamId())
        .teamName(teamDomain.getTeamName()).createdBy(this.userTo(teamDomain.getCreatedBy()))
        .users(this.usersToUsersBase(teamDomain.getUsers())).teams(this.teamsToTeamBase(teamDomain.getTeams())).build();
    teamResponse.add(
        linkTo(methodOn(TeamController.class).getTeam(teamResponse.getTeamId())).withSelfRel());
    teamResponse
        .add(linkTo(methodOn(TeamController.class).getTeamsByTeamId(teamResponse.getTeamId()))
            .withRel("teams"));
    teamResponse.add(
        linkTo(methodOn(TeamController.class).getUsers(teamResponse.getTeamId())).withRel("users"));

    return teamResponse;
  }

  @Override
  public TeamDomain teamRequestToDomain(TeamRequest teamRequest) {
    return TeamDomain.builder().createdBy(this.userDtoToDomain(teamRequest.getCreatedBy()))
        .namespace(teamRequest.getNamespace()).teamName(teamRequest.getName()).build();
  }

  @Override
  public UserResponse userTo(UserDomain userDomain) {
    if (userDomain == null)
      return null;
    UserResponse anUser = UserResponse.builder().userId(userDomain.getUserId()).build();
    anUser
        .add(linkTo(methodOn(UserRepository.class).getUserById(anUser.getUserId())).withSelfRel());
    return anUser;
  }

  @Override
  public Set<UserResponse> usersToUsersBase(Set<UserDomain> usersDomain) {
    if (usersDomain == null)
      return null;
    return new HashSet<UserResponse>(
        usersDomain.stream().map(aUserDomain -> this.userTo(aUserDomain)).toList());
  }

  @Override
  public Set<TeamBaseResponse> teamsToTeamBase(Set<TeamDomain> teamsDomain) {
    if (teamsDomain == null)
      return null;
    return new HashSet<TeamBaseResponse>(
        teamsDomain.stream().map(aTeamDomain -> this.teamToTeamBaseDto(aTeamDomain)).toList());
  }

  @Override
  public Set<TeamResponse> teamsToTeamResponse(Set<TeamDomain> teamsDomain) {
    if (teamsDomain == null)
      return null;
    return new HashSet<TeamResponse>(
        teamsDomain.stream().map(aTeamDomain -> this.teamToTeamResponse(aTeamDomain)).toList());
  }

  @Override
  public UserDomain userDtoToDomain(UserRequest userDto) {
    return UserDomain.builder().userId(userDto.getUserId()).build();
  }

}

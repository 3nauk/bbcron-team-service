package com.bbcron.team.service.impl;

import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.UserDomain;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserResponse;
import com.bbcron.team.exceptions.TeamAlreadyExistException;
import com.bbcron.team.exceptions.TeamNotFoundException;
import com.bbcron.team.exceptions.UserAlreadyExistException;
import com.bbcron.team.exceptions.UserNotFoundException;
import com.bbcron.team.mapper.MapStructMapper;
import com.bbcron.team.repository.TeamRepository;
import com.bbcron.team.repository.UserRepository;
import com.bbcron.team.service.TeamService;
import com.bnauk.bbcron.controller.PageResponse;
import com.bnauk.bbcron.exceptions.BBCronError;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {


  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MapStructMapper mapper;


  @Override
  public ResponseEntity<Object> createTeam(TeamRequest teamRequest) {
    try {
      userRepository.getUserById(teamRequest.getCreatedBy().getUserId()).getBody();
    } catch (FeignException ex) {
      log.error("Error Creating the team", teamRequest.getName());
      return new ResponseEntity<Object>(BBCronError.builder().name(ex.getClass().getName())
          .message(ex.getMessage()).status(ex.status()).build(), HttpStatus.resolve(ex.status()));
    }
    TeamDomain domain = mapper.teamRequestToDomain(teamRequest);
    teamRepository.save(domain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(domain));
  }

  @Override
  public ResponseEntity<TeamResponse> getTeamById(String teamId) {
    TeamDomain domain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    TeamResponse response = mapper.teamToTeamResponse(domain);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Object> deleteTeam(String teamId) {
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    teamRepository.delete(teamDomain);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<TeamResponse>> getAllByQuery(Query query) {
    // TODO: Not implemented yet
    return null;
  }

  @Override
  public ResponseEntity<PageResponse<TeamResponse>> getPage(Query query, Pageable pageable) {
    // TODO: Not implemented yet
    return null;
  }

  @Override
  public ResponseEntity<TeamResponse> addUser(String teamId, String userId) {
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    userRepository.getUserById(userId).getBody();
    Set<UserDomain> users = teamDomain.getUsers();
    UserDomain newUser = UserDomain.builder().userId(userId).build();
    if (!users.contains(newUser))
      teamDomain.getUsers().add(newUser);
    else
      throw new UserAlreadyExistException("the user " + userId + " is already exist");
    teamRepository.save(teamDomain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(teamDomain));
  }

  @Override
  public ResponseEntity<TeamResponse> addUsers(String teamId, String... userId) {
    return null;
  }

  @Override
  public ResponseEntity<TeamResponse> addTeam(String teamIdParent, String teamId) {
    TeamDomain teamDomain =
        teamRepository.findById(teamIdParent).orElseThrow(TeamNotFoundException::new);
    teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    Set<TeamDomain> teams = teamDomain.getTeams();
    TeamDomain newTeam = TeamDomain.builder().teamId(teamId).build();
    if (!teams.contains(newTeam))
      teamDomain.getTeams().add(newTeam);
    else
      throw new TeamAlreadyExistException("The team " + teamId + " is already exist");
    teamRepository.save(teamDomain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(teamDomain));
  }

  @Override
  public ResponseEntity<TeamResponse> deleteUser(String teamId, String userId) {
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    userRepository.getUserById(userId).getBody();
    Set<UserDomain> users = teamDomain.getUsers();
    UserDomain newUser = UserDomain.builder().userId(userId).build();
    if (users.contains(newUser))
      teamDomain.getUsers().remove(newUser);
    else
      throw new UserNotFoundException("User " + userId + " is not exist in the team");
    teamRepository.save(teamDomain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(teamDomain));


  }

  @Override
  public ResponseEntity<List<UserResponse>> getUsersByTeamId(String teamId) {
    List<UserResponse> usersAsList = new ArrayList<>();
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    mapper.usersToUsersBase(teamDomain.getUsers()).forEach(usersAsList::add);
    return ResponseEntity.status(HttpStatus.OK).body(usersAsList);
  }

  @Override
  public ResponseEntity<List<TeamResponse>> getTeamsByTeamId(String teamId) {
    List<TeamResponse> teamsAsList = new ArrayList<>();
    TeamDomain domain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    mapper.teamsToTeamResponse(domain.getTeams()).forEach(teamsAsList::add);

    return ResponseEntity.status(HttpStatus.OK).body(teamsAsList);
  }

  @Override
  public ResponseEntity<Object> deleteTeam(String teamParentId, String teamId) {

    TeamDomain teamParentDomain = teamRepository.findById(teamParentId).orElseThrow(TeamNotFoundException::new);
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

    Set<TeamDomain> teams = teamParentDomain.getTeams();
    if (teams.contains(teamDomain))
      teamParentDomain.getTeams().remove(teamDomain);
    else
      throw new TeamNotFoundException("Team " + teamId + " is not exist in the team");
    teamRepository.save(teamParentDomain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(teamParentDomain));

  }



}

package com.bbcron.team.service.impl;

import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.TeamHasDomain;
import com.bbcron.team.domain.UserDomain;
import com.bbcron.team.dto.team.TeamBaseResponse;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserResponse;
import com.bbcron.team.exceptions.TeamAlreadyExistException;
import com.bbcron.team.exceptions.TeamNotFoundException;
import com.bbcron.team.exceptions.UserAlreadyExistException;
import com.bbcron.team.exceptions.UserNotFoundException;
import com.bbcron.team.mapper.MapStructMapper;
import com.bbcron.team.repository.TeamHasRepository;
import com.bbcron.team.repository.TeamRepository;
import com.bbcron.team.repository.UserRepository;
import com.bbcron.team.service.TeamService;
import com.bnauk.bbcron.user.controller.PageResponse;
import com.bnauk.bbcron.user.exceptions.BBCronError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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
  private TeamHasRepository teamHasRepository;

  @Autowired
  private MapStructMapper mapper;


  @Override
  public ResponseEntity<Object> createTeam(TeamRequest teamRequest) {
    try {
      userRepository.getUserById(teamRequest.getCreatedBy().getUserId()).getBody();
    } catch (FeignException ex) {
      log.error("Error Creating the team", teamRequest.getName());
      return new ResponseEntity<>(BBCronError.builder().name(ex.getClass().getName())
          .message(ex.getMessage()).status(ex.status()).build(), HttpStatus.resolve(ex.status()));
    }
    TeamDomain domain = mapper.teamRequestToDomain(teamRequest);
    teamRepository.save(domain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(domain));
  }

  @Override
  public ResponseEntity<TeamResponse> getTeamById(String teamId) {
    TeamDomain domain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    TeamHasDomain teamHas = teamHasRepository.findByTeamDomain(domain).orElse(TeamHasDomain.builder().build());
    TeamResponse response = mapper.teamToTeamResponse(domain);
    response.setUsers(mapper.usersToUsersBase(teamHas.getUsers()));
    response.setTeams(mapper.teamsToTeamBase(teamHas.getTeams()));
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
    List<TeamDomain> entities = teamRepository.findAll(query);
    List<TeamResponse> dtos = entities.stream().map(entity -> mapper.teamToTeamResponse(entity))
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<PageResponse<TeamResponse>> getPage(Query query, Pageable pageable) {
    PageResponse<TeamResponse> response = new PageResponse<>();
    Page<TeamDomain> entities = teamRepository.findAll(query, pageable);
    Page<TeamResponse> responsePagesDto = entities.map(new Function<TeamDomain, TeamResponse>() {

      @Override
      public TeamResponse apply(TeamDomain domain) {
        return mapper.teamToTeamResponse(domain);
      }

    });

    response.setPageStats(responsePagesDto, responsePagesDto.getContent());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<TeamResponse> addUser(String teamId, String userId) {

    TeamDomain teamDomain = teamRepository.findByTeamId(teamId).orElseThrow(TeamNotFoundException::new);
    TeamHasDomain hasUsers = teamHasRepository.findByTeamDomain(teamDomain).orElse(TeamHasDomain.builder().teamDomain(teamDomain).build());
    userRepository.getUserById(userId);
    UserDomain userDomain = UserDomain.builder().userId(userId).build();
    Set<UserDomain> users = hasUsers.getUsers();
    if (!users.contains(userDomain))
        users.add(userDomain);
    else
      throw new UserAlreadyExistException("the user " + userId + " is already exist");
    hasUsers.setUsers(users);
    teamHasRepository.save(hasUsers);
    return ResponseEntity.ok(mapper.teamToTeamResponse(teamDomain));
  }

  @Override
  public ResponseEntity<TeamResponse> addUsers(String teamId, String... userId) {
    return null;
  }

  @Override
  public ResponseEntity<TeamResponse> addTeam(String teamIdParent, String teamId) {
    //recuperamos el team padre
    TeamDomain teamDomain = teamRepository.findById(teamIdParent).orElseThrow(TeamNotFoundException::new);
    //Recuperamos las relaciones
    TeamHasDomain hasTeams = teamHasRepository.findByTeamDomain(teamDomain)
        .orElse(TeamHasDomain.builder().teamDomain(teamDomain).build());
    TeamDomain child = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    Set<TeamDomain> teams = hasTeams.getTeams();
    if (!teams.contains(child))
      teams.add(child);
    else
      throw new TeamAlreadyExistException("The team " + teamId + " is already exist");
    teamHasRepository.save(hasTeams);
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
    TeamHasDomain teamHas = teamHasRepository.findByTeamDomain(teamDomain).orElse(TeamHasDomain.builder().build());

    teamHas.getUsers().forEach(user ->
      usersAsList.add(mapper.userDtoToResponse(userRepository.getUserById(user.getUserId()).getBody())));
    return ResponseEntity.status(HttpStatus.OK).body(usersAsList);
  }

  @Override
  public ResponseEntity<List<TeamBaseResponse>> getTeamsByTeamId(String teamId) {
    List<TeamBaseResponse> teamsAsList = new ArrayList<>();
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    TeamHasDomain teamHas = teamHasRepository.findByTeamDomain(teamDomain).orElse(TeamHasDomain.builder().build());
    Set<TeamDomain> teamsDomain = new HashSet<>();
    teamHas.getTeams().forEach(aTeamDomain -> teamsDomain.add(teamRepository.findById(aTeamDomain.getTeamId()).get()));
    mapper.teamsToTeamBase(teamsDomain).forEach(teamsAsList::add);

    return ResponseEntity.status(HttpStatus.OK).body(teamsAsList);
  }

  @Override
  public ResponseEntity<Object> deleteTeam(String teamParentId, String teamId) {

    TeamDomain teamParentDomain =
        teamRepository.findById(teamParentId).orElseThrow(TeamNotFoundException::new);
    TeamDomain teamDomain = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

    Set<TeamDomain> teams = teamParentDomain.getTeams();
    if (teams.contains(teamDomain))
      teamParentDomain.getTeams().remove(teamDomain);
    else
      throw new TeamNotFoundException("Team " + teamId + " is not exist in the team");
    teamRepository.save(teamParentDomain);
    return ResponseEntity.ok(mapper.teamToTeamResponse(teamParentDomain));

  }

  @Override
  public ResponseEntity<List<TeamBaseResponse>> getTeamsByIdUser(String userId) {

    List<TeamBaseResponse> teams = new ArrayList<>();
    Set<TeamDomain> domains = new HashSet<>();
    Set<TeamHasDomain> teamHasDomains = teamHasRepository.findByUsersUserId(userId);
    teamHasDomains.forEach(hasDomain -> domains.add(teamRepository.findById(hasDomain.getTeamDomain().getTeamId()).get()));


    mapper.teamsToTeamBase(domains).forEach(teams::add);
    return ResponseEntity.ok(teams);

  }
}

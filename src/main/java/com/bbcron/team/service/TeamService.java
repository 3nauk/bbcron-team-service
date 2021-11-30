package com.bbcron.team.service;

import com.bbcron.team.dto.team.TeamBaseResponse;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserResponse;
import com.bnauk.bbcron.user.controller.PageResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;

public interface TeamService {

  /**
   * Create Team
   */
  ResponseEntity<Object> createTeam(TeamRequest teamRequest);

  /**
   * Get Team by teamId
   *
   * @param teamId the teamId
   */
  ResponseEntity<TeamResponse> getTeamById(String teamId);

  /**
   * Get Teams By Id User
   * @param userId, User Id
   * @return teams associatted to the userId
   */
  ResponseEntity<List<TeamBaseResponse>> getTeamsByIdUser(String userId);

  /**
   * Remove a Team By Id
   *
   * @param teamId, the teamId
   * @return responseEntity
   */
  ResponseEntity<Object> deleteTeam(String teamId);

  /**
   * Remove a Team of the Team Parent
   * @param teamParentId
   * @param teamId
   * @return
   */
  ResponseEntity<Object> deleteTeam(String teamParentId, String teamId);

  /**
   * Get Teams By Query
   *
   * @param query
   * @return teams {@link ResponseEntity<List<TeamResponse>>}
   */
  ResponseEntity<List<TeamResponse>> getAllByQuery(Query query);

  /**
   * Get Teams paginated By Query
   *
   * @param query query as {@link Query}
   * @param pageable as {@link Pageable}
   * @return teams {@link ResponseEntity<List<TeamResponse>>}
   */
  ResponseEntity<PageResponse<TeamResponse>> getPage(Query query, Pageable pageable);

  /**
   * Add an user to the teams
   */
  ResponseEntity<TeamResponse> addUser(String teamId, String userId);

  /**
   * Delete an User
   *
   * @param teamId
   * @param userId
   * @return
   */
  ResponseEntity<TeamResponse> deleteUser(String teamId, String userId);

  /**
   * Add an user list to the teams
   */
  ResponseEntity<TeamResponse> addUsers(String teamId, String... userId);

  /**
   * List users for a team
   *
   */
  ResponseEntity<List<UserResponse>> getUsersByTeamId(String teamId);

  /**
   * Add a new team to the team
   */
  ResponseEntity<TeamResponse> addTeam(String teamIdParent, String teamId);

  /**
   * List Teams for a team
   *
   * @param teamId team Id
   */
  ResponseEntity<List<TeamBaseResponse>> getTeamsByTeamId(String teamId);

}

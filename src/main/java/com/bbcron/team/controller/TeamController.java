package com.bbcron.team.controller;

import com.bbcron.team.dto.team.TeamBase;
import com.bbcron.team.dto.team.TeamBaseResponse;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserRequest;
import com.bbcron.team.dto.user.UserResponse;
import com.bbcron.team.service.TeamService;
import com.bnauk.bbcron.user.controller.PageResponse;
import com.bnauk.bbcron.user.dto.filter.FilterCondition;
import com.bnauk.bbcron.user.repository.support.GenericFilterCriteriaBuilder;
import com.bnauk.bbcron.user.service.FilterBuilderService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Team Controller
 */
@Import({FilterBuilderService.class})
@RestController
public class TeamController {

  @Autowired
  private TeamService teamService;

  @Autowired
  private FilterBuilderService filterBuilderService;
  
  @Operation(summary = "Create a Team", description = "Create a new Team",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          content = @Content(schema = @Schema(implementation = TeamRequest.class),
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      responses = {
          @ApiResponse(responseCode = "200", description = "Success",
              content = @Content(schema = @Schema(implementation = TeamResponse.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request. Invalid id supplied",
              content = @Content),
          @ApiResponse(responseCode = "409",
              description = "Team already exists with the same namespace ${ownerId}/${projectName}/${teamId}",
              content = @Content),
          @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content),
          @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)})
  @PostMapping(name = "Create a new Team", path = "/teams",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createTeam(@RequestBody TeamRequest teamRequest) {
    return teamService.createTeam(teamRequest);
  }

  @Operation(summary = "Get a Team by Id", description = "Get a Team by Id",
      parameters = {@Parameter(allowEmptyValue = false, example = "60eead54e2ecc90037cbd090",
          name = "teamId", required = true)},
      responses = {@ApiResponse(responseCode = "200", description = "Success",
          content = @Content(schema = @Schema(implementation = TeamResponse.class))),})
  @GetMapping(name = "Get a Team by Id", path = "/teams/{teamId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamResponse> getTeam(@PathVariable("teamId") String teamId) {
    return teamService.getTeamById(teamId);
  }

  @Operation(summary = "Update a Team by Id", description = "Update a Team by Id",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          content = @Content(schema = @Schema(implementation = String.class),
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      responses = {@ApiResponse(responseCode = "200", description = "Success",
          content = @Content(schema = @Schema(implementation = TeamResponse.class))),})
  @PutMapping(name = "Update Team by Id", path = "/teams/{teamId}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamResponse> update(@PathVariable(value = "teamId") String teamId,
      @Valid @RequestBody TeamRequest teamRequest) {
    return null;
  }

  @Operation(summary = "Delete Team", description = "Remove a Team", method = "DELETE",
      parameters = {@Parameter(allowEmptyValue = false, example = "60eead54e2ecc90037cbd090",
          name = "teamId", required = true)},
      responses = {@ApiResponse(responseCode = "204", description = "Success"),
          @ApiResponse(responseCode = "404",
              description = "Indicates the requested form deployment was not found.")})
  @DeleteMapping(name = "Delete a Team by Id", path = "/teams/{teamId}")
  public ResponseEntity<Object> removeTeam(@PathVariable(value = "teamId") String teamId) {
    // Como borrar una referencia cuando se borra el team id
    return teamService.deleteTeam(teamId);
  }

  @Operation(summary = "Get teams by team", method = "GET",
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Project Not found", responseCode = "404")})
  @GetMapping(name = "Get Teams", path = "/teams/{teamId}/teams",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TeamBaseResponse>> getTeamsByTeamId(
      @PathVariable("teamId") @NotNull String teamId) {
    return teamService.getTeamsByTeamId(teamId);
  }

  @Operation(summary = "Get users by team", method = "GET",
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Team Not found", responseCode = "404")})
  @GetMapping(name = "Get Teams", path = "/teams/{teamId}/users",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserResponse>> getUsers(
      @PathVariable("teamId") @NotNull String teamId) {
    return teamService.getUsersByTeamId(teamId);
  }

  @Operation(summary = "Add Teams into team", method = "PATCH",
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Conflict", responseCode = "409")})
  @PatchMapping(name = "Add Teams into a Team", path = "/teams/{teamId}/teams",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamResponse> createTeam(@PathVariable("teamId") @NotNull String teamId,
      @Valid @NotNull @RequestBody TeamBase teamRequest) {
    return teamService.addTeam(teamId, teamRequest.getTeamId());
  }

  @Operation(summary = "Add an User by teams", method = "PATCH",
      parameters = {@Parameter(allowEmptyValue = false, example = "60eead54e2ecc90037cbd090",
          name = "teamId", required = true)},
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Conflict", responseCode = "409")})
  @PatchMapping(name = "Add User Teams", path = "/teams/{teamId}/users",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamResponse> addUser(@PathVariable("teamId") @NotNull String teamId,
      @RequestBody UserRequest user) {
    return teamService.addUser(teamId, user.getUserId());
  }

  @Operation(summary = "Delete an User by teams", method = "DELETE",
      parameters = {@Parameter(allowEmptyValue = false, example = "60eead54e2ecc90037cbd090",
          name = "teamId", required = true)},
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Not Found", responseCode = "404")})
  @DeleteMapping(name = "Delete User Teams", path = "/teams/{teamId}/users",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TeamResponse> removeUser(@PathVariable("teamId") @NotNull String teamId,
      @RequestBody UserRequest user) {
    return teamService.deleteUser(teamId, user.getUserId());
  }


  @Operation(summary = "Delete a Team of the teams", method = "DELETE",
      parameters = {@Parameter(allowEmptyValue = false, example = "60eead54e2ecc90037cbd090",
          name = "teamId", required = true, description = "Team parent")},
      responses = {@ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Not Found", responseCode = "404")})
  @DeleteMapping(name = "Delete team of the Teams list", path = "/teams/{teamId}/teams",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> removeTeam(@PathVariable("teamId") @NotNull String teamId,
      @RequestBody TeamBase team) {
    return teamService.deleteTeam(teamId, team.getTeamId());
  }

  /**
   * Get Teams By Criteria
   *
   * @param page page number
   * @param size size count
   * @param filterOr string filter or conditions
   * @param filterAnd string filter and conditions
   * @param orders string orders
   * @return response as ResponseEntity<PageResponse<TeamResponse>>
   */
  @GetMapping(value = "/teams/page")
  public ResponseEntity<PageResponse<TeamResponse>> getSearchCriteriaPage(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "20") int size,
      @RequestParam(value = "filterOr", required = false) String filterOr,
      @RequestParam(value = "filterAnd", required = false) String filterAnd,
      @RequestParam(value = "orders", required = false) String orders) {

    Pageable pageable = filterBuilderService.getPageable(size, page, orders);
    
    GenericFilterCriteriaBuilder genericFilterCriteriaBuilder = new GenericFilterCriteriaBuilder();

    List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
    List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);

    Query query = genericFilterCriteriaBuilder.addCondition(andConditions, orConditions);

    return teamService.getPage(query, pageable);
  }

  /**
   * @param filterOr string filter or conditions
   * @param filterAnd string filter and conditions
   * @return list of teamResponse
   */
  @GetMapping(value = "/teams/")
  public ResponseEntity<List<TeamResponse>> getAllSearchCriteria(
      @RequestParam(value = "filterOr", required = false) String filterOr,
      @RequestParam(value = "filterAnd", required = false) String filterAnd) {

 
    GenericFilterCriteriaBuilder genericFilterCriteriaBuilder = new GenericFilterCriteriaBuilder();
    
    List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
    List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);
    
    Query query = genericFilterCriteriaBuilder.addCondition(andConditions, orConditions);
    return teamService.getAllByQuery(query);
  }

  @Operation(summary = "Get users by team", method = "GET", responses = {
          @ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Team Not found", responseCode = "404") })
  @GetMapping(name = "Get Teams By UserId", path = "/teams/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TeamBaseResponse>> getTeamsByUser(
          @PathVariable("userId") @NotNull String userId) {
     return teamService.getTeamsByIdUser(userId);
  }
}

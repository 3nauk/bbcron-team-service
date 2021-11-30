package com.bbcron.team.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.UserDomain;
import com.bbcron.team.dto.team.TeamRequest;
import com.bbcron.team.dto.team.TeamResponse;
import com.bbcron.team.dto.user.UserDto;
import com.bbcron.team.dto.user.UserRequest;
import com.bbcron.team.exceptions.TeamNotFoundException;
import com.bbcron.team.exceptions.UserNotFoundException;
import com.bbcron.team.mapper.MapStructMapperImpl;
import com.bbcron.team.repository.TeamHasRepository;
import com.bbcron.team.repository.TeamRepository;
import com.bbcron.team.repository.UserRepository;
import com.mongodb.assertions.Assertions;

public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamHasRepository teamHasRepository;

    @Mock
    private MapStructMapperImpl mapper;
    
    @InjectMocks
    public TeamServiceImpl teamServiceImpl;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testCreateTeamWhenUserNotFound() {
        when(userRepository.getUserById(any())).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> teamServiceImpl
                .createTeam(TeamRequest.builder().createdBy(UserRequest.builder().userId("userId").build()).build()));
    }

    @Test
    public void testCreateTeamWithUser() {
        when(userRepository.getUserById("userId"))
                .thenReturn(ResponseEntity.ok().body(UserDto.builder().userId("userId").build()));
        when(teamRepository.save(any())).thenReturn(TeamDomain.builder().createdBy(UserDomain.builder().build())
                .createdDate(Instant.now()).teamId("teamId").teamName("teamName").build());
        when(mapper.teamRequestToDomain(any())).thenReturn(TeamDomain.builder().build());
        when(mapper.teamToTeamResponse(any())).thenReturn(TeamResponse.builder().build());
        assertEquals(teamServiceImpl
                .createTeam(TeamRequest.builder().createdBy(UserRequest.builder().userId("userId").build()).build())
                .getStatusCodeValue(), HttpStatus.OK.value());
    }

    @Test
    public void testGetTeamByIdWhenTeamNotFound() {
        when(teamRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(TeamNotFoundException.class, () -> teamServiceImpl.getTeamById("teamId"));
    }
}
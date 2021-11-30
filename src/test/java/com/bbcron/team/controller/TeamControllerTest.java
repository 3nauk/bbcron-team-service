package com.bbcron.team.controller;

import com.bbcron.team.exceptions.UserNotFoundException;
import com.bbcron.team.service.TeamService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeamControllerTest {
    
    @Mock
    private TeamService teamService;

    @InjectMocks
    public TeamController teamController;

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
    public void test_get_teams_by_userId() {
        teamController.getTeamsByUser("userId");
        verify(teamService, times(1)).getTeamsByIdUser("userId");
    }

    @Test
    public void test_get_teams_by_userId_when_user_not_found() {
        when(teamService.getTeamsByIdUser("not_found")).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> teamController.getTeamsByUser("not_found"));
    }


}

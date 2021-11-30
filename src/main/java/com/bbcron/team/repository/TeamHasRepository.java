package com.bbcron.team.repository;

import java.util.Optional;
import java.util.Set;

import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.TeamHasDomain;
import com.bnauk.bbcron.user.repository.BBCronResourceRepository;


public  interface TeamHasRepository extends BBCronResourceRepository<TeamHasDomain, String>   {

    Optional<TeamHasDomain> findByTeamDomain(TeamDomain teamDomain);

    //1 - Incluye en el result,  0 - exclude
    //@Query(fields = "{ '_id': 0, 'users.$': 0, 'teams.$': 0, 'teamDomain': 1 }")
    Set<TeamHasDomain> findByUsersUserId(String userId);

}

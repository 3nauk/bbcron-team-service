package com.bbcron.team.repository;

import com.bbcron.team.domain.TeamDomain;
import com.bnauk.bbcron.repository.BBCronResourceRepository;
import java.util.Optional;

/**
 * Organization Repository
 */
public interface TeamRepository extends BBCronResourceRepository<TeamDomain, String> {

  Optional<TeamDomain> findByTeamName(String teamName, String userId);

  Optional<TeamDomain> findByTeamId(String teamId);

  Optional<TeamDomain> findByNamespace(String namespace);

}

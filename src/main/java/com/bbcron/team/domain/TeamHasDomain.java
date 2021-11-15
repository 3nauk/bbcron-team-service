package com.bbcron.team.domain;

import java.util.Set;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "team-has")
public class TeamHasDomain {

    /**
     * Team Id
     * 
     */
    @Id
    private String id;

    @DBRef()
    private TeamDomain teamDomain;

    /**
     * has Teams
     */
    @DBRef
    @Default
    private Set<TeamDomain> teams = new HashSet<>();

    /**
     * has users
     */
    @DBRef
    @Default
    private Set<UserDomain> users = new HashSet<>();
    
}

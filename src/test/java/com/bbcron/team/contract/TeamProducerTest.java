package com.bbcron.team.contract;

import java.io.IOException;

import com.bbcron.team.BBCronTeamMain;

import com.bbcron.team.domain.TeamDomain;
import com.bbcron.team.domain.UserDomain;
import com.bbcron.team.repository.TeamRepository;

import io.restassured.config.EncoderConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
@AutoConfigureDataMongo
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BBCronTeamMain.class})
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureStubRunner(ids = { "com.bnauk.bbcron:bbcron-user-service:+:stubs:8088" })
@Slf4j
public abstract class TeamProducerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeAll
    public void createMongo() throws IOException {

        log.info("=====     Creating database   ===============");
        UserDomain createdBy = UserDomain.builder().userId("6096daffb234a14b65265fa6").build();
        teamRepository.save(TeamDomain.builder().teamId("60fd4167b51b6159ee987460").teamName("team-01")
                .createdBy(createdBy).build());
        teamRepository.save(TeamDomain.builder().teamId("60fd42cfb51b6159ee987462").teamName("team-03")
                .createdBy(createdBy).build());
    }

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
        log.info("=====     Executing Test   ===============");
        EncoderConfig encoderConfig = new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssuredMockMvc.config = new RestAssuredMockMvcConfig().encoderConfig(encoderConfig);
        RestAssuredMockMvc.webAppContextSetup(this.webApplicationContext);
        log.info("=====     Finish Executing Test   ===============");

    }

}

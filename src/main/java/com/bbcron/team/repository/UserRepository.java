package com.bbcron.team.repository;

import com.bbcron.team.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import feign.Headers;

//TODO: Llevar a una librería

@FeignClient(value = "users", url = "http://localhost:8080")
public interface UserRepository {

  @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}", produces = "application/json")
  @Headers("Content-Type: application/json")
  public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId);

}

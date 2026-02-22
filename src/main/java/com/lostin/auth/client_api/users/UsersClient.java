package com.lostin.auth.client_api.users;

import com.lostin.auth.client_api.users.request.CreateUserRequest;
import com.lostin.auth.client_api.users.request.EmailRequest;
import com.lostin.auth.client_api.users.request.GetUserProfileRequest;
import com.lostin.auth.client_api.users.request.GetUserProfilesRequest;
import com.lostin.auth.client_api.users.response.EmailTakenResponse;
import com.lostin.auth.client_api.users.response.UserIdResponse;
import com.lostin.auth.config.feign.UsersFeignConfig;
import com.lostin.auth.dto.users_client.UserMinimalProfile;
import com.lostin.auth.exception.ConflictException;
import com.lostin.auth.exception.InternalBadRequestException;
import com.lostin.auth.exception.NotFoundException;
import com.lostin.auth.model.core.user.UserId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "users-service",
        url = "${users-service.url}",
        configuration = UsersFeignConfig.class
)
public interface UsersClient {

    @PostMapping(value = "/api/v1/users/basic-create")
    UserIdResponse createUser(CreateUserRequest request) throws ConflictException, InternalBadRequestException;

    @PostMapping(value = "/api/v1/users/is-email-taken")
    Boolean isEmailTaken(EmailRequest request) throws InternalBadRequestException;

    @PostMapping(value = "/api/v1/users/get-id-by-email")
    UserIdResponse findUserByEmail(EmailRequest request) throws InternalBadRequestException, NotFoundException;

    @PostMapping(value = "/api/v1/users/get-profile")
    UserMinimalProfile getUserProfile(GetUserProfileRequest request) throws InternalBadRequestException, NotFoundException;

    /* Done:
        write endpoint in users service that gives multiple users by its IDs
        Needed for SSO sessions (so if there are multiple sessions,
        there is no need to ask users service multiple times)
        return type should be List of minimal user data (username, email,logo uri)
     */
    @PostMapping(value = "/api/v1/users/get-profiles")
    List<UserMinimalProfile> getProfilesByIds(GetUserProfilesRequest request) throws InternalBadRequestException;

}

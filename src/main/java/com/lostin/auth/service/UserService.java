package com.lostin.auth.service;

import com.lostin.auth.client_api.users.request.CreateUserRequest;
import com.lostin.auth.client_api.users.request.EmailRequest;
import com.lostin.auth.client_api.users.request.GetUserProfileRequest;
import com.lostin.auth.client_api.users.request.GetUserProfilesRequest;
import com.lostin.auth.dto.users_client.UserMinimalProfile;
import com.lostin.auth.exception.*;
import com.lostin.auth.client_api.users.UsersClient;
import com.lostin.auth.model.core.user.Email;
import com.lostin.auth.model.core.user.UserId;
import com.lostin.auth.model.core.user.Username;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UsersClient usersClient;

    public UserId createUser(Email email, Username username) throws ConflictException {
        return new UserId(
                usersClient.createUser(new CreateUserRequest(email.value(), username.value())).user_id()
        );
    }

    public UserId findUserByEmail(Email email) throws NotFoundException {
        return new UserId(
            usersClient.findUserByEmail(new EmailRequest(email.value())).user_id()
        );
    }

    public boolean isEmailTaken(Email email) {
        return usersClient.isEmailTaken(new EmailRequest(email.value()));
    }

    public UserMinimalProfile getUserProfile(UserId userId) throws NotFoundException {
        return usersClient.getUserProfile(new GetUserProfileRequest(userId.value()));
    }

    /**
     * @param userIds list of user ids
     * @return list of {@link UserMinimalProfile} objects,
     * if no user found returns an empty list
     */
    public List<UserMinimalProfile> getUserProfilesByIds(List<UserId> userIds) {
        return usersClient.getProfilesByIds(new GetUserProfilesRequest(userIds.stream().map(UserId::value).toList()));
    }

}

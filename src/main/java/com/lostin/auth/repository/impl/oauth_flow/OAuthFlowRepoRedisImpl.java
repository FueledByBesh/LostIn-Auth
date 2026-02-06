package com.lostin.auth.repository.impl.oauth_flow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("redis-impl")
public class OAuthFlowRepoRedisImpl {

}

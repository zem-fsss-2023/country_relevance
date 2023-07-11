package com.example.demo;

import co.elastic.clients.elasticsearch.security.GetUserResponse;

public interface UserApi {

    GetUserResponse gather(Integer page)
}

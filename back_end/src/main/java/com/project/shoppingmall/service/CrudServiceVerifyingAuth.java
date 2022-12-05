package com.project.shoppingmall.service;

import com.project.shoppingmall.model.LoginDto;

public interface CrudServiceVerifyingAuth<Id, CreateRequest, ReadResponse, UpdateRequest> {
    void create(CreateRequest request);
    ReadResponse read(Id uid);
    void update(Id uid, UpdateRequest request, LoginDto principal);
    void delete(Id uid, LoginDto principal);
}

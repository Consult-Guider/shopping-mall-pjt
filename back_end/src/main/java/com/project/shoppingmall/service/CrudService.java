package com.project.shoppingmall.service;

public interface CrudService<Id, CreateRequest, ReadResponse, UpdateRequest> {
    void create(CreateRequest request);
    ReadResponse read(Id uid);
    void update(Id uid, UpdateRequest request);
    void delete(Id uid);
}

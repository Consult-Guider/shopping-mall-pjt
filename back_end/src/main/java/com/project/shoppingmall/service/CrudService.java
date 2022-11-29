package com.project.shoppingmall.service;

public interface CrudService<CreateRequest, ReadResponse, UpdateRequest> {
    void create(CreateRequest request);
    ReadResponse read(Long uid);
    void update(Long uid, UpdateRequest request);
    void delete(Long uid);
}

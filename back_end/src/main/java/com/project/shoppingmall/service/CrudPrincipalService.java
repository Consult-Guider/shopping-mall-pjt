package com.project.shoppingmall.service;

public interface CrudPrincipalService<Dto, UpdateRequest> {
    void updatePrincipal(Dto uid, UpdateRequest request);
    void deletePrincipal(Dto uid);
}

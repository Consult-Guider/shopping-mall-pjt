package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.model.response.SearchResponse;
import com.project.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Response<Void> createItem(@ModelAttribute ItemCreateRequest request) {
        itemService.create(request);
        return Response.success();
    }

    @GetMapping("/{id}")
    public Response<ItemReadResponse> readItem(@PathVariable String id) {
        return Response.success(itemService.read(id));
    }

    @GetMapping("/{id}/all")
    public Response<ItemReadResponse> readItemAll(@PathVariable String id) {
        return Response.success(itemService.readItemAll(id));
    }

    @PutMapping("/{id}")
    public Response<Void> updateItem(
            @PathVariable String id,
            @RequestBody ItemUpdateRequest request,
            @AuthenticationPrincipal LoginDto principal
            ) {
        itemService.update(id, request, principal);
        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteItem(@PathVariable String id, @AuthenticationPrincipal LoginDto principal) {
        itemService.delete(id, principal);
        return Response.success();
    }

    @GetMapping
    public Response<Page<SearchResponse>> searchItem(@RequestParam String keyword, @PageableDefault Pageable pageable) {
        return Response.success(itemService.searchByKeyword(keyword, pageable));
    }
}

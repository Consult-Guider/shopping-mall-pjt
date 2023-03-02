package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.model.response.TagReadResponse;
import com.project.shoppingmall.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public Response<Page<TagReadResponse>> search(@PageableDefault Pageable pageable) {
        Page<TagReadResponse> page = tagService.getRandomTags(pageable)
                .map(TagReadResponse::fromEntity);
        return Response.success(page);
    }

    @GetMapping("/{name}/item")
    public Response<Page<ItemReadResponse>> search(
            @PathVariable String name,
            @PageableDefault Pageable pageable
            ) {
        Page<ItemReadResponse> page = tagService.getItems(name, pageable);
        return Response.success(page);
    }
}

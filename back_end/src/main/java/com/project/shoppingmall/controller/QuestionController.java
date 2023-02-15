package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.QuestionCreateRequest;
import com.project.shoppingmall.model.request.QuestionUpdateRequest;
import com.project.shoppingmall.model.response.QuestionReadResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.model.response.SearchResponse;
import com.project.shoppingmall.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/item/{iid}/question")
    public Response<Void> create(
            @PathVariable String iid,
            @RequestBody QuestionCreateRequest request,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        questionService.create(iid, request, loginDto);
        return Response.success();
    }

    @GetMapping("/item/{iid}/question")
    public Response<Page<QuestionReadResponse>> readWithItem(
            @PathVariable String iid,
            @PageableDefault Pageable pageable
            ) {
        Page<QuestionReadResponse> pages = questionService.readByIid(iid, pageable);
        return Response.success(pages);
    }

    @GetMapping("/user/principal/question")
    public Response<Page<QuestionReadResponse>> readWithUser(
            @AuthenticationPrincipal LoginDto loginDto,
            @PageableDefault Pageable pageable
    ) {
        Page<QuestionReadResponse> pages = questionService.readByUid(loginDto.getId(), pageable);
        return Response.success(pages);
    }

    @GetMapping("/user/principal/item/{iid}/question")
    public Response<QuestionReadResponse> readWithUserAndItem(
            @PathVariable String iid,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        return Response.success(questionService.readByIidAndUid(iid, loginDto.getId()));
    }

    @GetMapping("/question")
    public Response<Page<SearchResponse>> search(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable
    ) {
        Page<SearchResponse> pages = questionService.searchByKeyword(keyword, pageable);
        return Response.success(pages);
    }

    @PostMapping("/question/{qid}/children")
    public Response<Void> createAsReply(
            @PathVariable String qid,
            @RequestBody QuestionCreateRequest request,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        questionService.createChildrenByQid(qid, request, loginDto);
        return Response.success();
    }

    @GetMapping("/question/{qid}/children")
    public Response<Page<QuestionReadResponse>> search(
            @PathVariable String qid
    ) {
        Page<QuestionReadResponse> pages = questionService.readChildrenByQid(qid);
        return Response.success(pages);
    }

    @PutMapping("/question/{qid}")
    public Response<Void> update(
            @AuthenticationPrincipal LoginDto loginDto,
            @PathVariable String qid,
            @ModelAttribute QuestionUpdateRequest request
            ) {
        questionService.update(qid, request, loginDto);
        return Response.success();
    }

    @DeleteMapping("/question/{qid}")
    public Response<Void> delete(
            @AuthenticationPrincipal LoginDto loginDto,
            @PathVariable String qid
    ) {
        questionService.delete(qid, loginDto);
        return Response.success();
    }
}

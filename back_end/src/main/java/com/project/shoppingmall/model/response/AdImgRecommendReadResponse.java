package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.AdImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class AdImgRecommendReadResponse {
    private String path;
    private String link;

    public static AdImgRecommendReadResponse fromEntity(AdImg entity) {
        AdImgRecommendReadResponse dto = new AdImgRecommendReadResponse();
        dto.setPath(entity.getPath());
        dto.setLink(entity.getLink());
        return dto;
    }
}

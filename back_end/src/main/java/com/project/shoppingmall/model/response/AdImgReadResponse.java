package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.AdImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class AdImgReadResponse {
    private Long id;

    private String itemName;
    private String companyName;
    private String path;
    private String link;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static AdImgReadResponse fromEntity(AdImg entity) {
        AdImgReadResponse dto = new AdImgReadResponse();
        dto.setId(entity.getId());
        dto.setItemName(entity.getItemName());
        dto.setCompanyName(entity.getCompanyName());
        dto.setPath(entity.getPath());
        dto.setLink(entity.getLink());
        dto.setStartAt(entity.getStartAt());
        dto.setEndAt(entity.getEndAt());
        return dto;
    }
}

package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.AdImg;
import com.project.shoppingmall.utils.AdImgDateTimeFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class AdImgCreateRequest {
    private String itemName;
    private String companyName;
    private MultipartFile path;
    @URL(protocol = "https")
    private String link;
    @AdImgDateTimeFormat
    private LocalDateTime startAt;
    @AdImgDateTimeFormat
    private LocalDateTime endAt;

    public AdImg toEntity() {
        AdImg entity = new AdImg();
        entity.setItemName(itemName);
        entity.setCompanyName(companyName);
        entity.setLink(link);
        entity.setStartAt(startAt);
        entity.setEndAt(endAt);
        return entity;
    }
}

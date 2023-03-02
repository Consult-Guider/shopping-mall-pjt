package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.AdImg;
import com.project.shoppingmall.utils.AdImgDateTimeFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor @Getter @Setter
public class AdImgUpdateRequest {
    private String itemName;
    private String companyName;
    private MultipartFile path;
    @URL(protocol = "https")
    private String link;
    @AdImgDateTimeFormat
    private LocalDateTime startAt;
    @AdImgDateTimeFormat
    private LocalDateTime endAt;

    public AdImg overwrite(AdImg trg) {
        AdImg entity = AdImg.of(trg);

        Optional.ofNullable(this.getItemName())
                .filter(s -> !s.isBlank()).ifPresent(entity::setItemName);
        Optional.ofNullable(this.getCompanyName())
                .filter(s -> !s.isBlank()).ifPresent(entity::setCompanyName);
        Optional.ofNullable(this.getLink())
                .filter(s -> !s.isBlank()).ifPresent(entity::setLink);
        Optional.ofNullable(this.getStartAt())
                .ifPresent(entity::setStartAt);
        Optional.ofNullable(this.getEndAt())
                .ifPresent(entity::setEndAt);
        return entity;
    }
}

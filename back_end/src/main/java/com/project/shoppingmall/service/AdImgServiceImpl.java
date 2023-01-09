package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.AdImg;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.request.AdImgCreateRequest;
import com.project.shoppingmall.model.request.AdImgUpdateRequest;
import com.project.shoppingmall.model.response.AdImgReadResponse;
import com.project.shoppingmall.repository.AdImgRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.ImageType;
import com.project.shoppingmall.utils.fileManager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.project.shoppingmall.utils.fileManager.S3FileNameManager.makeFileName;

@Service
@Transactional
@RequiredArgsConstructor
public class AdImgServiceImpl implements AdImgService {
    private final AdImgRepository adImgRepository;
    private final FileManager<MultipartFile, String> fileManager;

    public String saveFile(MultipartFile src, String fileName) {
        try {
            return fileManager.saveFile(src, fileName);
        } catch (IOException e) {
            throw new CrudException(ErrorCode.AD_BANNER_IOException);
        }
    }

    private AdImg loadAdImgById(Long aid) {
        return adImgRepository.findById(aid).orElseThrow(() ->
                new CrudException(
                        ErrorCode.AD_BANNER_NOT_FOUNDED,
                        String.format("조회에 사용된 ID: %s", aid)
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdImgReadResponse> read(Pageable pageable) {
        return adImgRepository.findAllValid(pageable).map(AdImgReadResponse::fromEntity);
    }

    @Override
    public void create(AdImgCreateRequest request) {
        AdImg entity = adImgRepository.save(request.toEntity());
        String id = String.valueOf(entity.getId());
        MultipartFile path = request.getPath();

        // 배너 이미지가 존재한다면, 이미지 저장 후, 이미지 링크를 DB에 저장.
        if (path != null && !path.isEmpty()) {
            String fileName = makeFileName(path, AdImg.class, id, ImageType.AD_BANNER.getName());
            entity.setPath(saveFile(path, fileName));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AdImgReadResponse read(Long aid) {
        return AdImgReadResponse.fromEntity(loadAdImgById(aid));
    }

    @Override
    public void update(Long aid, AdImgUpdateRequest request) {
        AdImg entity = loadAdImgById(aid);
        AdImg entityOverWritten = request.overwrite(entity);
        adImgRepository.save(entityOverWritten);
    }

    @Override
    public void delete(Long aid) {
        adImgRepository.delete(loadAdImgById(aid));
    }
}

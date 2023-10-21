package kr.inhatc.shop.service;

import jakarta.persistence.EntityNotFoundException;
import kr.inhatc.shop.entity.ItemImg;
import kr.inhatc.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    @Value(value = "${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String originalFileName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(originalFileName)) {
            imgName = fileService.uploadFile(itemImgLocation, originalFileName, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        itemImg.updateItemImg(originalFileName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long ItemImgId, MultipartFile itemImgFile) throws IOException {
        if(!itemImgFile.isEmpty()){
            ItemImg itemImg = itemImgRepository.findById(ItemImgId).orElseThrow(EntityNotFoundException::new);

            // 파일 이름이 존재하는 경우
            if(!StringUtils.isEmpty(itemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());   // 기존 파일 삭제
            }

            String originalFileName = itemImgFile.getOriginalFilename();
            String imgNamae = fileService.uploadFile(itemImgLocation, originalFileName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgNamae;

            // 더티체킹
            itemImg.updateItemImg(originalFileName, imgNamae, imgUrl);  // 파일 정보 업데이트
        }
    }
}

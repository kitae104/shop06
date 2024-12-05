package kr.inhatc.shop.service;

import jakarta.persistence.EntityNotFoundException;
import kr.inhatc.shop.dto.ItemFormDto;
import kr.inhatc.shop.dto.ItemImgDto;
import kr.inhatc.shop.dto.ItemSearchDto;
import kr.inhatc.shop.dto.MainItemDto;
import kr.inhatc.shop.entity.Item;
import kr.inhatc.shop.entity.ItemImg;
import kr.inhatc.shop.repository.ItemImgRepository;
import kr.inhatc.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // 트랜잭션 처리
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgRepository itemImgRepository;

    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {

        // 상품 등록
        Item item = itemFormDto.createItem(); // item으로부터 id 생성을 위해.
        itemRepository.save(item);

        // 이미지 등록
        for(int i = 0; i < itemImgFileList.size(); i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0) {   // 첫번째 이미지를 대표 이미지로 설정
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); // 이미지 저장
        }
        return item.getId();    // 상품 id 반환
    }

    /**
     * 상품 상세 조회
     * @param itemId
     * @return
     */
    public ItemFormDto getItemDetail(Long itemId){

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다. id=" + itemId));

        ItemFormDto itemFormDto = ItemFormDto.of(item);             // Entity -> Dto
        itemFormDto.setItemImgDtoList(itemImgDtoList);              // 이미지 리스트 추가
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {
        Item item = itemRepository
                .findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        // 변경 감지(Dirty Checking) : 자동으로 저장--> 따로 save()를 호출할 필요 없음.
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        // 이미지 수정 처리
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));  // 번호와 파일 전달 -> 수정
        }
        return item.getId();
    }

    /**
     * 관리자 상품 목록 조회
     * @param itemSearchDto
     * @param pageable
     * @return
     */
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    /**
     * 메인 페이지에 보여줄 상품 데이터 목록 조회
     * @param itemSearchDto
     * @param pageable
     * @return
     */
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}

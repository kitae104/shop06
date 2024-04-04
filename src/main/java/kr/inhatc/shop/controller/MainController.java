package kr.inhatc.shop.controller;

import kr.inhatc.shop.dto.ItemSearchDto;
import kr.inhatc.shop.dto.MainItemDto;
import kr.inhatc.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * 메인 컨트롤러
 * - 메인 페이지 관리
 */
@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 6);    // 페이지 관리(현재 페이지 또는 0)
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);         // 페인 페이지에 표현할 아이템 가져오기
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
}

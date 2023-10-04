package kr.inhatc.shop.thymeleaf.controller;

import kr.inhatc.shop.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
@Slf4j
public class ThymeleafController {

    @GetMapping("/ex1")
    public String ex1(Model model){
        // 문자열 전달
        model.addAttribute("data", "thymeleaf");

        // 객체 전달
        Point point = new Point(10, 20);
        model.addAttribute("point", point);
        return "thymeleaf/ex1";
    }

    @GetMapping("/ex2")
    public String ex2(Model model){
        ItemDto itemDto = ItemDto.builder()
                .itemNm("테스트 상품1")
                .price(10000)
                .itemDetail("테스트 상품 상세 설명")
                .regTime(LocalDateTime.now())
                .build();

        model.addAttribute("itemDto", itemDto);

        return "thymeleaf/ex2";
    }

    @GetMapping(value = {"/ex3", "/ex4"})
    public void ex3(Model model){

        List<ItemDto> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = ItemDto.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 * i)
                    .itemDetail("테스트 상품 상세 설명" + i)
                    .regTime(LocalDateTime.now())
                    .build();
            list.add(itemDto);
        }
        model.addAttribute("list", list);
    }

    @GetMapping("/ex5")
    public String ex5(Model model, String param1, @RequestParam("param2") String p2){
        log.info("p1 : " + param1 + ", p2 : " + p2);
        model.addAttribute("param1", param1);
        model.addAttribute("param2", p2);
        return "thymeleaf/ex5";
    }

    @GetMapping(value = {"/ex6", "/ex7"})
    public void ex6(){

    }
}

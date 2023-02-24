package com.example.configcenter.controller;

import com.example.configcenter.vo.test.TestVO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @PostMapping("/nacos/test")
    public String test(@Valid @RequestBody TestVO vo) {
        log.info("vo info:{}", vo);

        return "success";
    }
}

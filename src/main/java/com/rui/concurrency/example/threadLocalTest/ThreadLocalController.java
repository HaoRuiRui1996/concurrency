package com.rui.concurrency.example.threadLocalTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/threadlocal")
public class ThreadLocalController {

    @RequestMapping("/test")
    @ResponseBody
    public Long tesst() {
        return RequestHolder.getId();
    }
}

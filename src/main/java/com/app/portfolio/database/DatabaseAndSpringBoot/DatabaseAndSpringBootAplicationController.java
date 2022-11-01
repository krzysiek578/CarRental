package com.app.portfolio.database.DatabaseAndSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseAndSpringBootAplicationController {



    @GetMapping("/test")
    public int test() {
        return 1;
    }


}

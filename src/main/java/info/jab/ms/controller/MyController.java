package info.jab.ms.controller;

import info.jab.ms.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping("/gods/greek")
    List<String> getGreekGods() {
        return myService.getData();
    }

}

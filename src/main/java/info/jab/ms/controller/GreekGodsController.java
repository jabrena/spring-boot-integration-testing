package info.jab.ms.controller;

import info.jab.ms.service.GreekGodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreekGodsController {

    private final GreekGodsService greekGodsService;

    public GreekGodsController(GreekGodsService greekGodsService) {
        this.greekGodsService = greekGodsService;
    }

    @GetMapping("/gods/greek")
    List<String> getGreekGodNames() {
        return greekGodsService.getGreekGods();
    }
} 
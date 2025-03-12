package info.jab.ms.gods.controller;

import info.jab.ms.gods.service.GreekGodsService;
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
    List<String> getGreekGods() {
        return greekGodsService.getGreekGods();
    }

} 
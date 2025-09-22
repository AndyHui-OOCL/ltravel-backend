package oocl.ltravelbackend.controller;


import oocl.ltravelbackend.model.dto.AIChatDto;
import oocl.ltravelbackend.service.AIChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai-chat")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    @GetMapping
    public ResponseEntity<List<AIChatDto>> generateTravelPlan(@RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(aiChatService.getChatById(ids));
    }
}

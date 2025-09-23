package oocl.ltravelbackend.controller;


import oocl.ltravelbackend.model.dto.AIChatDto;
import oocl.ltravelbackend.service.AIChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai-chat")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    @PostMapping
    public ResponseEntity<List<AIChatDto>> generateAIChatPlans(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok().body(aiChatService.getAIChatByPrompt(request.get("prompt")));
    }
}

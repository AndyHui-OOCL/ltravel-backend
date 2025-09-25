package oocl.ltravelbackend.controller;


import oocl.ltravelbackend.model.dto.AIChatReqDto;
import oocl.ltravelbackend.model.dto.AIChatRespDto;
import oocl.ltravelbackend.service.AIChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai-chat")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    @PostMapping
    public ResponseEntity<List<AIChatRespDto>> generateAIChatPlans(@RequestBody AIChatReqDto chatReqDto) {
        return ResponseEntity.ok().body(aiChatService.getAIChatByPrompt(chatReqDto));
    }
}

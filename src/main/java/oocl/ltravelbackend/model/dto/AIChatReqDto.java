package oocl.ltravelbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AIChatReqDto {
    private List<ChatHistory> chatHistories;
    private String userInput;

    @Data
    @Builder
    public static class ChatHistory {
        private String userMessage;
        private String AIMessage;
    }
}

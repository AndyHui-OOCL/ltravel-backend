package oocl.ltravelbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ltravelbackend.common.exception.DeepSeekApiException;
import oocl.ltravelbackend.common.exception.PromptBuildException;
import oocl.ltravelbackend.model.dto.AIChatReqDto;
import oocl.ltravelbackend.model.dto.AIChatRespDto;
import oocl.ltravelbackend.model.dto.TravelPlanPromptDto;
import oocl.ltravelbackend.model.entity.PlanImage;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.AIChatRepository;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIChatService {

    private final ChatClient chatClient;
    private final AIChatRepository aiChatRepository;
    private final TravelPlanRepository travelPlanRepository;
    private static final String PROMPT_FILE_PATH = "src/main/resources/prompt/prompt.txt";
    private static final String INTENTION_PROMPT_FILE_PATH = "src/main/resources/prompt/intentionPrompt.txt";

    @Autowired
    public AIChatService(OpenAiChatModel chatModel,
                         AIChatRepository aiChatRepository,
                         TravelPlanRepository travelPlanRepository) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.aiChatRepository = aiChatRepository;
        this.travelPlanRepository = travelPlanRepository;
    }

    public List<AIChatRespDto> getAIChatByPrompt(AIChatReqDto chatReqDto) {

        String rawAIResponse = handleUserInput(chatReqDto);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(rawAIResponse);
            boolean isValid = rootNode.path("isValid").asBoolean();
            String reason = rootNode.path("reason").asText();
            if (!isValid) {
                AIChatRespDto aiChatRespDto = AIChatRespDto.builder()
                        .failMessage(reason)
                        .build();
                List<AIChatRespDto> result = new ArrayList<>();
                result.add(aiChatRespDto);
                return result;
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }

        List<Long> travelPlanIds = getTravelPlanIdsByAI(chatReqDto);

        List<AIChatRespDto> aiChatRespDtoList = new ArrayList<>();
        List<TravelPlan> travelPlans = aiChatRepository.findChatByIds(travelPlanIds);
        for (TravelPlan travelPlan : travelPlans) {
            PlanImage firstImage = null;
            List<PlanImage> images = travelPlan.getImages();
            if (images != null && !images.isEmpty()) {
                firstImage = images.get(0);
            }

            AIChatRespDto aiChatRespDto = AIChatRespDto.builder()
                    .travelId(travelPlan.getId())
                    .cityName(travelPlan.getCityName())
                    .description(travelPlan.getDescription())
                    .imageUrl(firstImage != null ? firstImage.getUrl() : null)
                    .build();
            aiChatRespDtoList.add(aiChatRespDto);
        }
        return aiChatRespDtoList;
    }

    private String handleUserInput(AIChatReqDto chatReqDto) {
        return callAI(buildIntentionPrompt(chatReqDto));
    }

    private List<Long> getTravelPlanIdsByAI(AIChatReqDto chatReqDto) {
        List<TravelPlan> travelPlans = travelPlanRepository.findAll();
        String prompt = buildPrompt(chatReqDto, travelPlans);

        try {
            String response = callAI(prompt);
            if (response == null || response.isEmpty()) {
                response = "[]";
            }
            return Arrays.stream(response.replaceAll("[\\[\\]\\s]", "").split(","))
                    .filter(s -> !s.isEmpty())
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } catch (DeepSeekApiException e) {
            return new ArrayList<>();
        }
    }

    private String callAI(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    private String buildPrompt(AIChatReqDto chatReqDto, List<TravelPlan> travelPlans) {
        try {
            List<TravelPlanPromptDto> travelPlanDtos = travelPlans.stream()
                    .map(travelPlan -> TravelPlanPromptDto.builder()
                            .id(travelPlan.getId())
                            .title(travelPlan.getTitle())
                            .cityName(travelPlan.getCityName())
                            .description(travelPlan.getDescription())
                            .tag(travelPlan.getTag())
                            .build())
                    .toList();

            ObjectMapper objectMapper = new ObjectMapper();
            String travelPlansJson = objectMapper.writeValueAsString(travelPlanDtos);
            String chatHistoryJson = objectMapper.writeValueAsString(chatReqDto.getChatHistories());

            String promptTemplate = Files.readString(Paths.get(AIChatService.PROMPT_FILE_PATH));
            promptTemplate = promptTemplate.replace("{{ $userInput }}", chatReqDto.getUserInput());
            promptTemplate = promptTemplate.replace("{{ $history }}", chatHistoryJson);
            promptTemplate = promptTemplate.replace("{{ $travelPlans }}", travelPlansJson);
            return promptTemplate;
        } catch (Exception e) {
            throw new PromptBuildException("Failed to build prompt");
        }
    }

    private String buildIntentionPrompt(AIChatReqDto chatReqDto) {
        try {
            // Format chat history as readable conversation text instead of JSON array
            String chatHistoryText = formatChatHistory(chatReqDto.getChatHistories());

            String promptTemplate = Files.readString(Paths.get(AIChatService.INTENTION_PROMPT_FILE_PATH));
            promptTemplate = promptTemplate.replace("{{ $userInput }}", chatReqDto.getUserInput());
            promptTemplate = promptTemplate.replace("{{ $history }}", chatHistoryText);
            return promptTemplate;
        } catch (Exception e) {
            throw new PromptBuildException("Failed to build prompt");
        }
    }

    private String formatChatHistory(List<AIChatReqDto.ChatHistory> chatHistories) {
        if (chatHistories == null || chatHistories.isEmpty()) {
            return "暂无对话历史";
        }

        StringBuilder historyBuilder = new StringBuilder();
        for (int i = 0; i < chatHistories.size(); i++) {
            AIChatReqDto.ChatHistory history = chatHistories.get(i);
            historyBuilder.append("对话 ").append(i + 1).append(":\n");
            historyBuilder.append("用户: ").append(history.getUserMessage()).append("\n");
            historyBuilder.append("AI: ").append(history.getAIMessage()).append("\n");
            if (i < chatHistories.size() - 1) {
                historyBuilder.append("\n");
            }
        }
        return historyBuilder.toString();
    }
}

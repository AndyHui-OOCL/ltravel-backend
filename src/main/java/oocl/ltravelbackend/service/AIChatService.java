package oocl.ltravelbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.ltravelbackend.model.dto.AIChatDto;
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

    @Autowired
    public AIChatService(OpenAiChatModel chatModel,
                         AIChatRepository aiChatRepository,
                         TravelPlanRepository travelPlanRepository) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.aiChatRepository = aiChatRepository;
        this.travelPlanRepository = travelPlanRepository;
    }

    public List<AIChatDto> getAIChatByPrompt(String userInput) {
        List<Long> travelPlanIds = getTravelPlanIdsByAI(userInput);

        List<AIChatDto> aiChatDtoList = new ArrayList<>();
        List<TravelPlan> travelPlans = aiChatRepository.findChatByIds(travelPlanIds);
        for (TravelPlan travelPlan : travelPlans) {
            PlanImage firstImage = null;
            List<PlanImage> images = travelPlan.getImages();
            if (images != null && !images.isEmpty()) {
                firstImage = images.get(0);
            }

            AIChatDto aiChatDto = AIChatDto.builder()
                    .travelId(travelPlan.getId())
                    .cityName(travelPlan.getCityName())
                    .description(travelPlan.getDescription())
                    .imageUrl(firstImage != null ? firstImage.getUrl() : null)
                    .build();
            aiChatDtoList.add(aiChatDto);
        }
        return aiChatDtoList;
    }

    private List<Long> getTravelPlanIdsByAI(String userInput) {
        List<TravelPlan> travelPlans = travelPlanRepository.findAll();
        String prompt = buildPrompt(userInput, travelPlans);

        try {
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            return Arrays.stream(response.replaceAll("[\\[\\]\\s]", "").split(","))
                    .filter(s -> !s.isEmpty())
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error calling AI API: " + e.getMessage());
            return List.of();
        }
    }

    private String buildPrompt(String userPrompt, List<TravelPlan> travelPlans) {
        try {
            String promptTemplate = Files.readString(Paths.get("src/main/resources/prompt/prompt.txt"));

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

            promptTemplate = promptTemplate.replace("{{ $userInput }}", userPrompt);
            promptTemplate = promptTemplate.replace("{{ $travelPlans }}", travelPlansJson);
            return promptTemplate;
        } catch (Exception e) {
            throw new RuntimeException("Failed to build prompt", e);
        }
    }
}

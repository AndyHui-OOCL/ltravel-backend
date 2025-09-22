package oocl.ltravelbackend.service;

import oocl.ltravelbackend.model.dto.AIChatDto;
import oocl.ltravelbackend.model.entity.PlanImage;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.AIChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIChatService {

    @Autowired
    private AIChatRepository aiChatRepository;

    public List<AIChatDto> getAIChatByPrompt(String prompt) {

        List<Long> travelPlanIds = getTravelPlanIdsByAI(prompt);

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

    private List<Long> getTravelPlanIdsByAI(String prompt) {
        if ("秋天哪个城市最漂亮？".equals(prompt)) {
            return List.of(1L, 2L);
        }
        return List.of();
    }
}

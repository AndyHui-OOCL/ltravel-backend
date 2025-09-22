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

    public List<AIChatDto> getChatById(List<Long> ids) {
        List<AIChatDto> aiChatDtoList = new ArrayList<>();
        List<TravelPlan> travelPlans = aiChatRepository.findChatByIds(ids);

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
}

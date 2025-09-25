package oocl.ltravelbackend.service;

import oocl.ltravelbackend.common.exception.TravelPlanNotFoundException;
import oocl.ltravelbackend.model.dto.TravelPlanOverviewDto;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.model.entity.User;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import oocl.ltravelbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String likeTravelPlan(Long planId, Long userId) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(planId);
        User user = userRepository.findUserById(userId);
        if (travelPlan == null) {
            throw new TravelPlanNotFoundException("Travel Plan not found");
        }
        user.getSavedTravelPlans().add(travelPlan);
        userRepository.save(user);
        return "Travel Plan is saved successfully";
    }

    @Transactional
    public String cancelLikeTravelPlan(Long planId, Long userId) {
        TravelPlan travelPlan = travelPlanRepository.getTravelPlanDetailById(planId);
        User user = userRepository.findUserById(userId);
        if (travelPlan == null) {
            throw new TravelPlanNotFoundException("Travel Plan not found");
        }
        user.getSavedTravelPlans().remove(travelPlan);
        userRepository.save(user);
        return "Travel Plan is removed successfully";
    }

    public Page<TravelPlanOverviewDto> getFavoritePlan(int page, int size, Long userId) {
        User user = userRepository.findUserById(userId);
        List<TravelPlan> savedTravelPlans = user.getSavedTravelPlans();

        int totalElements = savedTravelPlans.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElements);
        if (fromIndex >= totalElements) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), totalElements);
        }

        List<TravelPlanOverviewDto> pagedData = savedTravelPlans.subList(fromIndex, toIndex)
                .stream()
                .map(travelPlan -> {
                    int highestDay = travelPlan.getTravelDays().stream()
                            .mapToInt(TravelDay::getDayNum)
                            .max()
                            .orElse(0);
                    return TravelPlanOverviewDto.builder()
                            .id(travelPlan.getId())
                            .cityName(travelPlan.getCityName())
                            .title(travelPlan.getTitle())
                            .totalTravelDay(highestDay)
                            .totalTravelComponent(travelPlan.getTravelDays().size())
                            .travePlanPlanImages(travelPlan.getImages())
                            .build();
                }).toList();
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(pagedData, pageable, totalElements);
    }

    public boolean getLikeStatus(Long planId, Long userId) {
        User user = userRepository.findUserById(userId);
        return user.getSavedTravelPlans().stream().anyMatch(plan -> plan.getId().equals(planId));
    }
}

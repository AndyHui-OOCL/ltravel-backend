package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.model.entity.PlanImage;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.dao.TravelPlanJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TravelPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TravelPlanJpaRepository travelPlanJpaRepository;

    @BeforeEach
    void setUp() {
        travelPlanJpaRepository.deleteAll();
    }

    @Test
    void should_return_paginated_travel_plans_when_get_overview_given_default_pagination() throws Exception {
        // Given
        TravelPlan plan1 = createTestTravelPlan("Tokyo", "Explore modern Japan", 3, 3);
        TravelPlan plan2 = createTestTravelPlan("Paris", "City of lights adventure", 5, 5);

        long id1 = travelPlanJpaRepository.save(plan1).getId();
        long id2 = travelPlanJpaRepository.save(plan2).getId();

        // When & Then
        mockMvc.perform(get("/travel-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].cityName").value("Tokyo"))
                .andExpect(jsonPath("$[0].description").value("Explore modern Japan"))
                .andExpect(jsonPath("$[0].totalTravelDay").value(3))
                .andExpect(jsonPath("$[0].totalTravelComponent").value(3))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].cityName").value("Paris"))
                .andExpect(jsonPath("$[1].description").value("City of lights adventure"))
                .andExpect(jsonPath("$[1].totalTravelDay").value(5))
                .andExpect(jsonPath("$[1].totalTravelComponent").value(5));
    }

    @Test
    void should_return_paginated_travel_plans_when_get_overview_given_custom_pagination_parameters() throws Exception {
        // Given
        for (int i = 1; i <= 15; i++) {
            TravelPlan plan = createTestTravelPlan("City" + i, "Description" + i, i, 1);
            travelPlanJpaRepository.save(plan);
        }

        // When & Then
        mockMvc.perform(get("/travel-plans")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void should_return_empty_list_when_get_overview_given_no_travel_plans_exist() throws Exception {
        mockMvc.perform(get("/travel-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_return_bad_request_when_get_overview_given_negative_page_number() throws Exception {
        mockMvc.perform(get("/travel-plans")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_bad_request_when_get_overview_given_zero_or_negative_page_size() throws Exception {
        mockMvc.perform(get("/travel-plans")
                        .param("page", "1")
                        .param("size", "0"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/travel-plans")
                        .param("page", "1")
                        .param("size", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_correct_total_travel_day_when_get_overview_given_travel_plan_with_multiple_days() throws Exception {
        TravelPlan plan = TravelPlan.builder()
                .cityName("Rome")
                .description("Ancient city exploration")
                .travelDays(List.of(
                        TravelDay.builder().dayNum(1).build(),
                        TravelDay.builder().dayNum(2).build(),
                        TravelDay.builder().dayNum(5).build() // Highest day number
                ))
                .images(List.of())
                .build();

        long planId = travelPlanJpaRepository.save(plan).getId();

        // When & Then
        mockMvc.perform(get("/travel-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(planId))
                .andExpect(jsonPath("$[0].totalTravelDay").value(5))
                .andExpect(jsonPath("$[0].totalTravelComponent").value(3));
    }

    @Test
    void should_return_travel_plans_with_images_when_get_overview_given_plans_have_images() throws Exception {
        // Given
        PlanImage image1 = PlanImage.builder().url("http://example.com/image1.jpg").build();
        PlanImage image2 = PlanImage.builder().url("http://example.com/image2.jpg").build();

        TravelPlan plan = TravelPlan.builder()
                .cityName("Barcelona")
                .description("Mediterranean adventure")
                .travelDays(List.of(TravelDay.builder().dayNum(1).build()))
                .images(List.of(image1, image2))
                .build();

        long planId = travelPlanJpaRepository.save(plan).getId();

        // When & Then
        mockMvc.perform(get("/travel-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(planId))
                .andExpect(jsonPath("$[0].travePlanPlanImages").isArray())
                .andExpect(jsonPath("$[0].travePlanPlanImages.length()").value(2));
    }

    private TravelPlan createTestTravelPlan(String cityName, String description, int maxDayNum, int totalDays) {
        List<TravelDay> travelDays = new ArrayList<>();
        for (int i = 1; i <= totalDays; i++) {
            travelDays.add(TravelDay.builder().dayNum(Math.min(i + 1, maxDayNum)).build());
        }

        return TravelPlan.builder()
                .cityName(cityName)
                .description(description)
                .travelDays(travelDays)
                .images(new ArrayList<>())
                .build();
    }
}

package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.model.entity.PlanImage;
import oocl.ltravelbackend.model.entity.TravelDay;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.dao.OfficialCommentJPARepository;
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

    @Autowired
    private OfficialCommentJPARepository officialCommentJPARepository;

    @BeforeEach
    void setUp() {
        officialCommentJPARepository.deleteAll();
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

    @Test
    void should_return_0_when_get_num_travel_plan_given_0_travel_plan() throws Exception {
        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
    }

    @Test
    void should_return_2_when_get_num_travel_plan_given_2_travel_plan() throws Exception {
        // Given
        TravelPlan plan1 = createTestTravelPlan("Tokyo", "Explore modern Japan", 3, 3);
        TravelPlan plan2 = createTestTravelPlan("Paris", "City of lights adventure", 5, 5);

        travelPlanJpaRepository.save(plan1);
        travelPlanJpaRepository.save(plan2);

        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }


    @Test
    void should_return_filtered_plans_when_get_overview_given_city_filter() throws Exception {
        // Given
        TravelPlan tokyoPlan = createTestTravelPlanWithTag("Tokyo", "Explore Tokyo", 3, 3, "culture");
        TravelPlan parisPlan = createTestTravelPlanWithTag("Paris", "City of lights", 5, 5, "romance");
        TravelPlan tokyoPlan2 = createTestTravelPlanWithTag("Tokyo", "Modern Tokyo", 2, 2, "modern");

        travelPlanJpaRepository.save(tokyoPlan);
        travelPlanJpaRepository.save(parisPlan);
        travelPlanJpaRepository.save(tokyoPlan2);

        // When & Then
        mockMvc.perform(get("/travel-plans")
                        .param("city", "Tokyo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].cityName").value("Tokyo"))
                .andExpect(jsonPath("$[1].cityName").value("Tokyo"));
    }

    @Test
    void should_return_filtered_plans_when_get_overview_given_travel_days_filter() throws Exception {
        // Given
        TravelPlan plan3Days = createTestTravelPlanWithTag("Tokyo", "Short trip", 3, 3, "culture");
        TravelPlan plan5Days = createTestTravelPlanWithTag("Paris", "Long trip", 5, 5, "romance");
        TravelPlan plan3Days2 = createTestTravelPlanWithTag("London", "Weekend trip", 3, 3, "history");

        travelPlanJpaRepository.save(plan3Days);
        travelPlanJpaRepository.save(plan5Days);
        travelPlanJpaRepository.save(plan3Days2);

        // When & Then
        mockMvc.perform(get("/travel-plans")
                        .param("travelDays", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].totalTravelDay").value(3))
                .andExpect(jsonPath("$[1].totalTravelDay").value(3));
    }

    @Test
    void should_return_filtered_plans_when_get_overview_given_tag_filter() throws Exception {
        // Given
        TravelPlan culturePlan1 = createTestTravelPlanWithTag("Tokyo", "Cultural Tokyo", 3, 3, "culture");
        TravelPlan romancePlan = createTestTravelPlanWithTag("Paris", "Romantic Paris", 5, 5, "romance");
        TravelPlan culturePlan2 = createTestTravelPlanWithTag("Rome", "Cultural Rome", 4, 4, "culture");

        long id1 = travelPlanJpaRepository.save(culturePlan1).getId();
        travelPlanJpaRepository.save(romancePlan);
        long id3 = travelPlanJpaRepository.save(culturePlan2).getId();

        // When & Then
        mockMvc.perform(get("/travel-plans")
                        .param("tag", "culture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[1].id").value(id3));
    }

    @Test
    void should_return_filtered_plans_when_get_overview_given_multiple_filters() throws Exception {
        // Given
        TravelPlan matchingPlan = createTestTravelPlanWithTag("Tokyo", "Cultural Tokyo", 3, 3, "culture");
        TravelPlan wrongCity = createTestTravelPlanWithTag("Paris", "Cultural Paris", 3, 3, "culture");
        TravelPlan wrongDays = createTestTravelPlanWithTag("Tokyo", "Long Tokyo", 5, 5, "culture");
        TravelPlan wrongTag = createTestTravelPlanWithTag("Tokyo", "Modern Tokyo", 3, 3, "modern");

        long matchingId = travelPlanJpaRepository.save(matchingPlan).getId();
        travelPlanJpaRepository.save(wrongCity);
        travelPlanJpaRepository.save(wrongDays);
        travelPlanJpaRepository.save(wrongTag);

        // When & Then
        mockMvc.perform(get("/travel-plans")
                        .param("city", "Tokyo")
                        .param("travelDays", "3")
                        .param("tag", "culture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(matchingId))
                .andExpect(jsonPath("$[0].cityName").value("Tokyo"))
                .andExpect(jsonPath("$[0].totalTravelDay").value(3));
    }

    @Test
    void should_return_empty_list_when_get_overview_given_filters_with_no_matches() throws Exception {
        // Given
        TravelPlan plan = createTestTravelPlanWithTag("Tokyo", "Cultural Tokyo", 3, 3, "culture");
        travelPlanJpaRepository.save(plan);

        // When & Then
        mockMvc.perform(get("/travel-plans")
                        .param("city", "NonExistentCity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_return_filtered_count_when_get_plan_num_given_city_filter() throws Exception {
        // Given
        TravelPlan tokyoPlan1 = createTestTravelPlanWithTag("Tokyo", "Tokyo trip 1", 3, 3, "culture");
        TravelPlan parisPlan = createTestTravelPlanWithTag("Paris", "Paris trip", 5, 5, "romance");
        TravelPlan tokyoPlan2 = createTestTravelPlanWithTag("Tokyo", "Tokyo trip 2", 2, 2, "modern");

        travelPlanJpaRepository.save(tokyoPlan1);
        travelPlanJpaRepository.save(parisPlan);
        travelPlanJpaRepository.save(tokyoPlan2);

        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num")
                        .param("city", "Tokyo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    void should_return_filtered_count_when_get_plan_num_given_travel_days_filter() throws Exception {
        // Given
        TravelPlan plan3Days1 = createTestTravelPlanWithTag("Tokyo", "Short trip 1", 3, 3, "culture");
        TravelPlan plan5Days = createTestTravelPlanWithTag("Paris", "Long trip", 5, 5, "romance");
        TravelPlan plan3Days2 = createTestTravelPlanWithTag("London", "Short trip 2", 3, 3, "history");

        travelPlanJpaRepository.save(plan3Days1);
        travelPlanJpaRepository.save(plan5Days);
        travelPlanJpaRepository.save(plan3Days2);

        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num")
                        .param("travelDays", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    void should_return_filtered_count_when_get_plan_num_given_tag_filter() throws Exception {
        // Given
        TravelPlan culturePlan1 = createTestTravelPlanWithTag("Tokyo", "Cultural Tokyo", 3, 3, "culture");
        TravelPlan romancePlan = createTestTravelPlanWithTag("Paris", "Romantic Paris", 5, 5, "romance");
        TravelPlan culturePlan2 = createTestTravelPlanWithTag("Rome", "Cultural Rome", 4, 4, "culture");

        travelPlanJpaRepository.save(culturePlan1);
        travelPlanJpaRepository.save(romancePlan);
        travelPlanJpaRepository.save(culturePlan2);

        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num")
                        .param("tag", "culture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    void should_return_filtered_count_when_get_plan_num_given_multiple_filters() throws Exception {
        // Given
        TravelPlan matchingPlan = createTestTravelPlanWithTag("Tokyo", "Cultural Tokyo", 3, 3, "culture");
        TravelPlan wrongCity = createTestTravelPlanWithTag("Paris", "Cultural Paris", 3, 3, "culture");
        TravelPlan wrongDays = createTestTravelPlanWithTag("Tokyo", "Long Tokyo", 5, 5, "culture");
        TravelPlan wrongTag = createTestTravelPlanWithTag("Tokyo", "Modern Tokyo", 3, 3, "modern");

        travelPlanJpaRepository.save(matchingPlan);
        travelPlanJpaRepository.save(wrongCity);
        travelPlanJpaRepository.save(wrongDays);
        travelPlanJpaRepository.save(wrongTag);

        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num")
                        .param("city", "Tokyo")
                        .param("travelDays", "3")
                        .param("tag", "culture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void should_return_zero_when_get_plan_num_given_filters_with_no_matches() throws Exception {
        // Given
        TravelPlan plan = createTestTravelPlanWithTag("Tokyo", "Cultural Tokyo", 3, 3, "culture");
        travelPlanJpaRepository.save(plan);

        // When & Then
        mockMvc.perform(get("/travel-plans/plan-num")
                        .param("city", "NonExistentCity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
    }

    @Test
    void should_return_filtered_plans_with_pagination_when_get_overview_given_filters_and_pagination() throws Exception {
        // Given
        for (int i = 1; i <= 10; i++) {
            TravelPlan plan = createTestTravelPlanWithTag("Tokyo", "Tokyo trip " + i, 3, 3, "culture");
            travelPlanJpaRepository.save(plan);
        }
        // Add some plans that don't match the filter
        TravelPlan parisPlan = createTestTravelPlanWithTag("Paris", "Paris trip", 5, 5, "romance");
        travelPlanJpaRepository.save(parisPlan);

        // When & Then - Get first page
        mockMvc.perform(get("/travel-plans")
                        .param("city", "Tokyo")
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].cityName").value("Tokyo"))
                .andExpect(jsonPath("$[1].cityName").value("Tokyo"))
                .andExpect(jsonPath("$[2].cityName").value("Tokyo"));
    }

    private TravelPlan createTestTravelPlanWithTag(String cityName, String description, int maxDayNum, int totalDays, String tag) {
        List<TravelDay> travelDays = new ArrayList<>();
        for (int i = 1; i <= totalDays; i++) {
            travelDays.add(TravelDay.builder().dayNum(Math.min(i + 1, maxDayNum)).build());
        }

        return TravelPlan.builder()
                .cityName(cityName)
                .description(description)
                .travelDays(travelDays)
                .images(new ArrayList<>())
                .tag(tag)
                .build();
    }
}

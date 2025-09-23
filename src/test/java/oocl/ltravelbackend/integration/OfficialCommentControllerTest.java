package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.controller.OfficialCommentController;
import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.model.entity.TravelPlan;
import oocl.ltravelbackend.repository.OfficialCommentRepository;
import oocl.ltravelbackend.repository.TravelPlanRepository;
import oocl.ltravelbackend.repository.dao.TravelPlanJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class OfficialCommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OfficialCommentController officialCommentController;
    @Autowired
    private OfficialCommentRepository officialCommentRepository;
    @Autowired
    private TravelPlanJpaRepository travelPlanRepository;

    private Long testTravelPlanId;

    @BeforeEach
    void setUp() {
        officialCommentRepository.deleteAll();
        travelPlanRepository.deleteAll();

        // Create a test travel plan that the official comment can reference
        TravelPlan testTravelPlan = TravelPlan.builder()
                .title("Test Travel Plan")
                .description("Test Description")
                .cityName("Test City")
                .isPopular(false)
                .isLocalTravel(true)
                .build();
        testTravelPlanId = travelPlanRepository.save(testTravelPlan).getId();
    }

    @Test
    void should_return_a_official_comment_when_find_given_a_travel_plan_id() throws Exception {
        OfficialComment officialComment = OfficialComment.builder()
                .eventComment("demo")
                .overallComment("demo")
                .rating(5.0)
                .promoteReason("demo")
                .travelPlanId(testTravelPlanId)
                .build();
        long id = officialCommentRepository.save(officialComment).getId();
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/official-comment/" + testTravelPlanId)
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("""
                        {
                            "id": %d,
                            "eventComment": "demo",
                            "overallComment": "demo",
                            "rating": 5.0,
                            "promoteReason": "demo",
                            "travelPlanId": %d
                        }
                        """.formatted(id, testTravelPlanId)));
    }

    @Test
    void should_return_400_when_find_given_a_invalid_travel_plan_id() throws Exception {
        //test implement
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/official-comment/-1")
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }


}
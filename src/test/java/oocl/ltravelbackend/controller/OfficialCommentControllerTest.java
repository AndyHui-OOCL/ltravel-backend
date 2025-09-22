package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.entity.OfficialComment;
import oocl.ltravelbackend.repository.OfficialCommentRepository;
import oocl.ltravelbackend.service.OfficialCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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


    @BeforeEach
    void setUp() {
        officialCommentRepository.deleteAll();

    }

    @Test
    void should_return_a_official_comment_when_find_given_a_travel_plan_id() throws Exception {
        OfficialComment officialComment=OfficialComment.builder()
                .eventComment("demo")
                .overallComment("demo")
                .rating(5.0)
                .promoteReason("demo")
                .travelPlanId(1L)
                .build();
        long id=officialCommentRepository.save(officialComment).getId();
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/official-comment/1")
                        )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("""
                        {
                            "id": %d,
                            "eventComment": "demo",
                            "overallComment": "demo",
                            "rating": 5.0,
                            "promoteReason": "demo",
                            "travelPlanId": 1
                        }
                        """.formatted(id)));
    }
}
package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.User;
import oocl.ltravelbackend.repository.CommentRepository;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.dao.UserJpaRepository;
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
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TravelComponentRepository travelComponentRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    private Long testUser1Id;
    private Long testUser2Id;
    private Long testComponentId;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        userJpaRepository.deleteAll();
        travelComponentRepository.deleteAll();

        // Create test user
        User testUser1 = new User();
        testUser1.setUserName("testUser1");
        testUser1.setPassword("password123");
        testUser1Id = userJpaRepository.save(testUser1).getId();

        User testUser2 = new User();
        testUser2.setUserName("testUser2");
        testUser2.setPassword("password456");
        testUser2Id = userJpaRepository.save(testUser2).getId();

        // Create test travel component
        TravelComponent testComponent = TravelComponent.builder()
                .name("Test Component")
                .description("Test Description")
                .rating(4.5f)
                .suggestionPlayTime(120)
                .build();
        testComponentId = travelComponentRepository.save(testComponent).getId();
    }

    @Test
    void should_return_list_of_comments_when_find_given_a_travel_component_id() throws Exception {
        long id1 = commentRepository.create(Comment.builder()
                .description("demo")
                .travelComponentId(testComponentId)
                .userId(testUser1Id)
                .isLike(true)
                .build()).getId();
        long id2 = commentRepository.create(Comment.builder()
                .description("demo2")
                .travelComponentId(testComponentId)
                .userId(testUser2Id)
                .isLike(false)
                .build()).getId();
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/" + testComponentId)
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("""
                        [
                            {
                                "id": %d,
                                "description": "demo",
                                "travelComponentId": %d,
                                "userId": %d,
                                "isLike": true
                            },
                            {
                                "id": %d,
                                "description": "demo2",
                                "travelComponentId": %d,
                                "userId": %d,
                                "isLike": false
                            }
                        ]
                        """.formatted(id1, testComponentId, testUser1Id, id2, testComponentId, testUser2Id)));
    }



    @Test
    void should_return_400_when_find_given_a_invalid_travel_component_id() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/-1")
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }
}
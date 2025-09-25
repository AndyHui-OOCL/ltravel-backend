package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.model.entity.User;
import oocl.ltravelbackend.repository.CommentRepository;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.dao.TravelDayJpaRepository;
import oocl.ltravelbackend.repository.dao.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    private User testUser10;
    private Long testUser2Id;
    private User testUser20;
    private Long testComponentId;
    private TravelComponent testComponentAfter;
    @Autowired
    private TravelDayJpaRepository travelDayJpaRepository;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        userJpaRepository.deleteAll();
        travelDayJpaRepository.deleteAll();
        travelComponentRepository.deleteAll();

        // Create test user
        User testUser1 = new User();
        testUser1.setUserName("testUser1");
        testUser1.setPassword("password123");
        testUser10 = userJpaRepository.save(testUser1);
        testUser1Id = testUser10.getId();

        User testUser2 = new User();
        testUser2.setUserName("testUser2");
        testUser2.setPassword("password456");
        testUser20 = userJpaRepository.save(testUser2);
        testUser2Id = testUser20.getId();

        // Create test travel component
        TravelComponent testComponent = TravelComponent.builder()
                .name("Test Component")
                .description("Test Description")
                .rating(4.5f)
                .suggestionPlayTime(120)
                .build();
        testComponentAfter = travelComponentRepository.save(testComponent);
        testComponentId = testComponentAfter.getId();
    }

    @Test
    void should_return_list_of_comments_when_find_given_a_travel_component_id() throws Exception {
        long id1 = commentRepository.create(Comment.builder()
                .description("demo")
                .travelComponent(travelComponentRepository.findById(testComponentId))
                .user(userJpaRepository.findById(testUser1Id).get())
                .isLike(true)
                .build()).getId();
        long id2 = commentRepository.create(Comment.builder()
                .description("demo2")
                .travelComponent(travelComponentRepository.findById(testComponentId))
                .user(userJpaRepository.findById(testUser2Id).get())
                .isLike(false)
                .build()).getId();
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/" + testComponentId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].description").value("demo"))
                .andExpect(jsonPath("$[0].travelComponentName").value(testComponentAfter.getName()))
                .andExpect(jsonPath("$[0].username").value(testUser10.getUserName()))
                .andExpect(jsonPath("$[0].isLike").value(true))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].description").value("demo2"))
                .andExpect(jsonPath("$[1].travelComponentName").value(testComponentAfter.getName()))
                .andExpect(jsonPath("$[1].username").value(testUser20.getUserName()))
                .andExpect(jsonPath("$[1].isLike").value(false));
    }


    @Test
    void should_return_400_when_find_given_a_invalid_travel_component_id() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/-1")
                )
                .andExpect(status().isBadRequest());
    }
}
package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.model.entity.*;
import oocl.ltravelbackend.repository.CommentRepository;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.dao.OfficialCommentJPARepository;
import oocl.ltravelbackend.repository.dao.TravelDayJpaRepository;
import oocl.ltravelbackend.repository.dao.TravelPlanJpaRepository;
import oocl.ltravelbackend.repository.dao.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private TravelPlan testPlan01;
    @Autowired
    private TravelDayJpaRepository travelDayJpaRepository;
    @Autowired
    private TravelPlanJpaRepository travelPlanJpaRepository;
    @Autowired
    private OfficialCommentJPARepository officialCommentJPARepository;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        officialCommentJPARepository.deleteAll();
        travelDayJpaRepository.deleteAll();
        travelComponentRepository.deleteAll();
        travelPlanJpaRepository.deleteAll();
        userJpaRepository.deleteAll();

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

        TravelPlan testPlan = TravelPlan.builder()
                .cityName("Test Plan")
                .isLocalTravel(true)
                .title("Test Plan Title")
                .tag("Test Tag")
                .isPopular(true)
                .description("Test Plan Description")
                .build();
        testPlan01 = travelPlanJpaRepository.save(testPlan);
        TravelDay travelDay = TravelDay.builder()
                .dayNum(1)
                .componentOrder(1)
                .travelPlanId(testPlan01.getId())
                .travelComponent(testComponentAfter)
                .build();
        travelDayJpaRepository.save(travelDay);

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
    void should_return_list_of_comments_when_find_given_a_travel_plan_id() throws Exception {
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

        mockMvc.perform(get("/comments")
                        .param("travelPlanId", String.valueOf(testPlan01.getId()))
                        .param("page", "1")
                        .param("size", "5")
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content[0].id").value(id1))
                .andExpect(jsonPath("$.content[0].description").value("demo"))
                .andExpect(jsonPath("$.content[0].travelComponentName").value(testComponentAfter.getName()))
                .andExpect(jsonPath("$.content[0].username").value(testUser10.getUserName()))
                .andExpect(jsonPath("$.content[0].isLike").value(true))
                .andExpect(jsonPath("$.content[1].id").value(id2))
                .andExpect(jsonPath("$.content[1].description").value("demo2"))
                .andExpect(jsonPath("$.content[1].travelComponentName").value(testComponentAfter.getName()))
                .andExpect(jsonPath("$.content[1].username").value(testUser20.getUserName()))
                .andExpect(jsonPath("$.content[1].isLike").value(false))
                .andExpect(jsonPath("$.total").value(2));
    }


    @Test
    void should_return_400_when_find_given_a_invalid_travel_component_id() throws Exception {
        mockMvc.perform(get("/comments")
                        .param("travelPlanId", "-1")
                        .param("page", "1")
                        .param("size", "5")
                )
                .andExpect(status().isBadRequest());
    }
}
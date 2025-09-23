package oocl.ltravelbackend.integration;

import oocl.ltravelbackend.model.entity.TravelComponent;
import oocl.ltravelbackend.repository.CommentRepository;
import oocl.ltravelbackend.repository.TravelComponentRepository;
import oocl.ltravelbackend.repository.dao.TravelDayJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TravelDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TravelComponentRepository travelComponentRepository;
    @Autowired
    private TravelDayJpaRepository travelDayJpaRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        travelDayJpaRepository.deleteAll();
        travelComponentRepository.deleteAll();
    }

    @Test
    void should_return_a_travel_plan_when_get_travel_plan_given_a_travel_plan_id() throws Exception {
        TravelComponent travelComponent = TravelComponent.builder()
                .name("大连星海湾广场")
                .description("亚洲最大的城市广场")
                .rating(5)
                .openTime("09:00")
                .address("demo")
                .suggestionPlayTime(60)
                .wayOfCommute("demo")
                .currentOccupation("demo")
                .futureOccupation("demo")
                .images(null)
                .location(true)
                .build();
        long id = travelComponentRepository.save(travelComponent).getId();
        mockMvc.perform(get("/travel-detail/component/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": %d,
                            "name": "大连星海湾广场",
                            "description": "亚洲最大的城市广场",
                            "rating": 5.0,
                            "openTime": "09:00",
                            "address": "demo",
                            "suggestionPlayTime": 60,
                            "wayOfCommute": "demo",
                            "currentOccupation": "demo",
                            "futureOccupation": "demo",
                            "images": [],
                            "location": true
                        }
                        """.formatted(id)));
    }

}
package oocl.ltravelbackend.controller;

import oocl.ltravelbackend.model.entity.Comment;
import oocl.ltravelbackend.repository.CommentRepository;
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
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentController commentController;
    @Autowired
    private CommentRepository commentRepository;
    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
    }
    @Test
    void should_return_list_of_comments_when_find_given_a_travel_component_id() throws Exception {
        long id1=commentRepository.create(Comment.builder()
                        .description("demo")
                        .travelComponentId(1L)
                        .userId(1L)
                        .isLike(true)
                .build()).getId();
        long id2=commentRepository.create(Comment.builder()
                        .description("demo2")
                        .travelComponentId(1L)
                        .userId(2L)
                        .isLike(false)
                .build()).getId();
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/1")
                        )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("""
                        [
                            {
                                "id": %d,
                                "description": "demo",
                                "travelComponentId": 1,
                                "userId": 1,
                                "isLike": true
                            },
                            {
                                "id": %d,
                                "description": "demo2",
                                "travelComponentId": 1,
                                "userId": 2,
                                "isLike": false
                            }
                        ]
                        """.formatted(id1,id2)));
    }
    @Test
    void should_return_204_when_find_given_a_non_exist_travel_component_id() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/1")
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    void should_return_400_when_find_given_a_invalid_travel_component_id() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/comments/-1")
                )
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
    }
}
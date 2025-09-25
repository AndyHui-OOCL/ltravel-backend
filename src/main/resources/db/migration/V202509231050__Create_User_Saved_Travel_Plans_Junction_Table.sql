CREATE TABLE user_saved_travel_plans (
    user_id BIGINT NOT NULL,
    travel_plan_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, travel_plan_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (travel_plan_id) REFERENCES travel_plan(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_saved_travel_plans_user_id ON user_saved_travel_plans(user_id);
CREATE INDEX idx_user_saved_travel_plans_travel_plan_id ON user_saved_travel_plans(travel_plan_id);

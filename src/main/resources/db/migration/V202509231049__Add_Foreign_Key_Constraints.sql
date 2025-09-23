-- Add foreign key constraints after both tables are created
ALTER TABLE travel_days
ADD CONSTRAINT fk_travel_days_travel_components
FOREIGN KEY (travel_component_id) REFERENCES travel_components (id);

ALTER TABLE travel_components
ADD CONSTRAINT fk_travel_components_travel_days
FOREIGN KEY (travel_component_id) REFERENCES travel_days (id);

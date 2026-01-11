-- INDEXES for Query Optimization (lookups)
CREATE INDEX idx_coffee_roaster ON coffees(roaster_id);
CREATE INDEX idx_coffee_origin ON coffees(origin_country);
CREATE INDEX idx_coffee_roast_level ON coffees(roast_level);
CREATE INDEX idx_coffee_active ON coffees(is_active);
CREATE INDEX idx_reviews_coffee ON reviews(coffee_id);
CREATE INDEX idx_reviews_user ON reviews(user_id);
CREATE INDEX idx_reviews_rating ON reviews(rating);
CREATE INDEX idx_favorites_user ON favorites(user_id);
CREATE INDEX idx_roasters_verified ON roasters(is_verified);
CREATE INDEX idx_users_email ON users(email);

-- Composite index for common search queries
CREATE INDEX idx_coffee_beans_search ON coffees(origin_country, roast_level, is_active);
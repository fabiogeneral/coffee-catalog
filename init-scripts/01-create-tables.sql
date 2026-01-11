-- USERS TABLE
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(50) NOT NULL DEFAULT 'USER', -- USER, ROASTER, ADMIN
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ROASTERS TABLE
CREATE TABLE roasters (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    company_name VARCHAR(255) NOT NULL,
    description TEXT,
    website VARCHAR(255),
    location VARCHAR(255),
    founded_year INTEGER,
    logo_url VARCHAR(500),
    is_verified BOOLEAN DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- COFFEE TABLE
CREATE TABLE coffees (
    id BIGSERIAL PRIMARY KEY,
    roaster_id BIGINT NOT NULL REFERENCES roasters(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    origin_country VARCHAR(100) NOT NULL,
    origin_region VARCHAR(255),
    altitude_meters INTEGER,
    varietal VARCHAR(255), -- e.g., Arabica, Robusta, Typica
    processing_method VARCHAR(100), -- Washed, Natural, Honey, etc.
    roast_level VARCHAR(50) NOT NULL, -- Light, Medium, Medium-Dark, Dark
    roast_date DATE,
    price DECIMAL(10, 2),
    weight_grams INTEGER, -- package weight
    acidity_level INTEGER CHECK (acidity_level >= 1 AND acidity_level <= 10), -- 1-10 scale
    sweetness_level INTEGER CHECK (sweetness_level >= 1 AND sweetness_level <= 10), -- 1-10 scale
    bitterness_level INTEGER CHECK (bitterness_level >= 1 AND bitterness_level <= 10), -- 1-10 scale
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- FLAVOR_NOTES TABLE (Many-to-Many with Coffee)
CREATE TABLE flavor_notes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(50), -- Fruity, Nutty, Chocolatey, Floral, Spicy, etc.
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- COFFEE_FLAVOR_NOTES (Join Table)
CREATE TABLE coffee_flavor_notes (
    coffee_id BIGINT REFERENCES coffees(id) ON DELETE CASCADE,
    flavor_note_id BIGINT REFERENCES flavor_notes(id) ON DELETE CASCADE,
    PRIMARY KEY (coffee_id, flavor_note_id)
);

-- INVENTORY TABLE
CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    coffee_id BIGINT UNIQUE NOT NULL REFERENCES coffees(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL DEFAULT 0,
    low_stock_threshold INTEGER DEFAULT 10,
    last_restocked_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- REVIEWS TABLE
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    coffee_id BIGINT NOT NULL REFERENCES coffees(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(255),
    comment TEXT,
    brewing_method VARCHAR(100), -- Espresso, Pour Over, French Press, etc.
    grind_size VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(coffee_id, user_id) -- One review per user per coffee
);

-- FAVORITES TABLE
CREATE TABLE favorites (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    coffee_id BIGINT NOT NULL REFERENCES coffees(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, coffee_id)
);
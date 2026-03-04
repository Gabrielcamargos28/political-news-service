CREATE TABLE articles (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(2048) UNIQUE NOT NULL,
    title VARCHAR(512) NOT NULL,
    source_name VARCHAR(100) NOT NULL,
    political_bias VARCHAR(50) NOT NULL,
    original_text TEXT,
    gemini_summary TEXT,
    published_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_articles_url ON articles(url);

CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS quote (
   id SERIAL PRIMARY KEY,
   author TEXT NOT NULL,
   content TEXT NOT NULL,
   category_id BIGINT,
   CONSTRAINT fk_quote_category
       FOREIGN KEY (category_id)
       REFERENCES category(id)
       ON DELETE SET NULL
);

INSERT INTO category (name) VALUES
('FUNNY'),
('MOTIVATIONAL'),
('PHILOSOPHICAL')
ON CONFLICT(name) DO NOTHING;

DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM quote) THEN
      INSERT INTO quote (author, content, category_id) VALUES
      ('Oscar Wilde', 'Be yourself; everyone else is already taken.', (SELECT id FROM category WHERE name = 'FUNNY')),
      ('Lao Tzu', 'The journey of a thousand miles begins with one step.', (SELECT id FROM category WHERE name = 'MOTIVATIONAL')),
      ('Socrates', 'The unexamined life is not worth living.', (SELECT id FROM category WHERE name = 'PHILOSOPHICAL'));
   END IF;
END;
$$;

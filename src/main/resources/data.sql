INSERT INTO users (name, email) VALUES ('Alice',   'alice@example.com')   ON CONFLICT (email) DO NOTHING;
INSERT INTO users (name, email) VALUES ('Bob',     'bob@example.com')     ON CONFLICT (email) DO NOTHING;
INSERT INTO users (name, email) VALUES ('Charlie', 'charlie@example.com') ON CONFLICT (email) DO NOTHING;
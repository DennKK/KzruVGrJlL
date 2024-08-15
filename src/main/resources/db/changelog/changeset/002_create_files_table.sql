CREATE TABLE IF NOT EXISTS files
(
    id            SERIAL PRIMARY KEY,
    file_data     OID         NOT NULL,
    title         VARCHAR(32) NOT NULL,
    description   VARCHAR(500),
    creation_date TIMESTAMP   NOT NULL DEFAULT NOW()
);

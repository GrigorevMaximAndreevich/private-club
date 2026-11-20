CREATE TABLE IF NOT EXISTS guests (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS qr_codes (
    id BIGSERIAL PRIMARY KEY,
    code UUID NOT NULL UNIQUE,
    created_time TIMESTAMP NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    guest_id BIGINT NOT NULL REFERENCES guests(id) ON DELETE CASCADE
);

INSERT INTO guests (first_name, last_name, patronymic) VALUES
    ('Имя1', 'Фамилия1', 'Отчество1'),
    ('Имя2', 'Фамилия2', 'Отчество2'),
    ('Имя3', 'Фамилия3', 'Отчество3'),
    ('Имя4', 'Фамилия4', 'Отчество4'),
    ('Имя5', 'Фамилия5', 'Отчество5');

INSERT INTO qr_codes (code, created_time, is_active, guest_id) VALUES
    ('550e8400-e29b-41d4-a716-446655440001', NOW(), TRUE, 1),
    ('550e8400-e29b-41d4-a716-446655440002', NOW(), TRUE, 2),
    ('550e8400-e29b-41d4-a716-446655440003', NOW(), TRUE, 3),
    ('550e8400-e29b-41d4-a716-446655440004', NOW(), TRUE, 4),
    ('550e8400-e29b-41d4-a716-446655440005', NOW(), TRUE, 5);
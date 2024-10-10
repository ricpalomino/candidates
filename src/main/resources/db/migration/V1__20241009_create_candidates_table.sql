CREATE TABLE candidates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    gender VARCHAR(10),
    country VARCHAR(100),
    currency VARCHAR(10),
    expected_salary DECIMAL(10, 2),
    position_applied VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO candidates (name, email, gender, country, currency, expected_salary, position_applied)
VALUES 
    ('Richard Palomino', 'rpalomino@gmail.com', 'Male', 'Perú', 'PEN', 80000, 'Software Engineer'),
    ('Debbie Navarro', 'dnavarro@gmail.com', 'Female', 'Estados Unidos', 'USD', 80000, 'Talent adquisition'),
    ('Luis Ayala', 'layala@hotmail.com', 'Male', 'Colombia', 'COP', 80000, 'QA Engineer'),
    ('Enrique Basurto', 'ebasurto@gmail.com', 'Male', 'Chile', 'CLP', 80000, 'DevOps Engineer'),
    ('William Aliaga', 'waliaga@gmail.com', 'Male', 'Mexico', 'MXN', 80000, 'FullStack Developer');
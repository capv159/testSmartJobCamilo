CREATE TABLE IF NOT EXISTS user_test (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    modified TIMESTAMP NOT NULL,
    created TIMESTAMP NOT NULL,
    lastlogin TIMESTAMP NOT NULL,
    token VARCHAR(1024) NOT NULL,
    isactive BIT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS phone_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(20),
    citycode VARCHAR(20),
    countrycode VARCHAR(20),
    user_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_test (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS parameter_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20),
    valueparameter VARCHAR(100)     
);
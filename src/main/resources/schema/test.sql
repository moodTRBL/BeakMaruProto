SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS member;
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    student_number DECIMAL NOT NULL,
    create_time DATETIME,
    update_time DATETIME
);

SET FOREIGN_KEY_CHECKS=1;

-- DROP procedure IF EXISTS initMember;
--
-- DELIMITER $$
-- CREATE PROCEDURE initMember()
-- BEGIN
--     DECLARE i INT DEFAULT 1;
--     WHILE (i <= 100) DO
--         INSERT INTO member(username, password, email)
--         VALUES (concat('username', i), concat('password', i), concat('email', i));
--         SET i = i + 1;
--     END WHILE;
-- END$$
-- DELIMITER ;
--
-- CALL initMember();

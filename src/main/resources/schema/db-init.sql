SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS member;
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    student_number DECIMAL NOT NULL,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL
);

DROP TABLE IF EXISTS follow;
CREATE TABLE follow (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_id BIGINT NOT NULL,
    to_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    FOREIGN KEY (from_id) REFERENCES member(id),
    FOREIGN KEY (to_id) REFERENCES member(id)
);

DROP TABLE IF EXISTS chat_room;
CREATE TABLE chat_room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board(id)
);

DROP TABLE IF EXISTS board;
CREATE TABLE board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    is_anonymous BOOLEAN NOT NULL,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id)
);


SET FOREIGN_KEY_CHECKS=1;
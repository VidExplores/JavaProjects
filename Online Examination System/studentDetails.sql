CREATE TABLE users (
    user_id NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    password VARCHAR2(50)
);

CREATE TABLE questions (
    question_id NUMBER PRIMARY KEY,
    question_text VARCHAR2(255),
    option_a VARCHAR2(50),
    option_b VARCHAR2(50),
    option_c VARCHAR2(50),
    option_d VARCHAR2(50),
    correct_option CHAR(1)
);

INSERT INTO users VALUES (1, 'student1', 'password1');
INSERT INTO questions VALUES (1, 'What is 2 + 2?', '3', '4', '5', '6', 'B');
INSERT INTO questions VALUES (2, 'What is the capital of France?', 'Berlin', 'Paris', 'Rome', 'Madrid', 'B');
COMMIT;

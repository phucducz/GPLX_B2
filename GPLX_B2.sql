CREATE DATABASE GPLX_B2;
GO

-- DROP DATABASE
-- USE master
-- GO
-- ALTER DATABASE [GPLX_B2] SET SINGLE_USER WITH ROLLBACK IMMEDIATE
-- DROP DATABASE [GPLX_B2]
-- GO
-- END DROP

USE GPLX_B2;
GO

CREATE TABLE questions
(
    id BIGINT PRIMARY KEY NOT NULL,
    question NVARCHAR(200) NOT NULL,
    answer_a NVARCHAR(100),
    answer_b NVARCHAR(100),
    answer_c NVARCHAR(100),
    answer_d NVARCHAR(100),
    answer_correct NVARCHAR(100) NOT NULL,
    thumbnail VARCHAR(100),
    traffic_signs_id INT
);
GO

CREATE TABLE traffic_signs
(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(100) NOT NULL
);
GO

CREATE TABLE exams
(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(10) NOT NULL
);
GO

CREATE TABLE question_exams
(
    questions_id BIGINT NOT NULL,
    exams_id BIGINT NOT NULL,
    PRIMARY KEY(questions_id, exams_id)
);
GO

CREATE TABLE users
(
    id BIGINT PRIMARY KEY NOT NULL,
    email VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    phone_number VARCHAR(11) NOT NULL,
    thumbnail VARCHAR(50) NOT NULL
);
GO

CREATE TABLE scores
(
    id BIGINT PRIMARY KEY NOT NULL,
    score FLOAT NOT NULL,
    users_id BIGINT NOT NULL,
    exams_id BIGINT NOT null
);
GO

CREATE TABLE topics
(
    id BIGINT PRIMARY KEY NOT NULL,
    title NVARCHAR(50) NOT NULL,
    imageFileName VARCHAR(100)
);
GO

CREATE TABLE question_topics
(
    questions_id BIGINT NOT NULL,
    topics_id BIGINT NOT NULL,
    PRIMARY KEY(questions_id, topics_id)
);
GO

-- FOREIGN KEYS
ALTER TABLE questions 
    ADD CONSTRAINT fk_question_trafficsigns FOREIGN KEY (traffic_signs_id) REFERENCES traffic_signs(id);
ALTER TABLE question_exams 
    ADD CONSTRAINT fk_questionexam_questions FOREIGN KEY (questions_id) REFERENCES questions(id);
ALTER TABLE question_exams 
    ADD CONSTRAINT fk_questionexam_exams FOREIGN KEY (exams_id) REFERENCES exams(id);
ALTER TABLE scores 
    ADD CONSTRAINT fk_scores_users FOREIGN KEY (users_id) REFERENCES users(id);
ALTER TABLE scores 
    ADD CONSTRAINT fk_scores_exams FOREIGN KEY (exams_id) REFERENCES exams(id);
ALTER TABLE question_topics 
    ADD CONSTRAINT fk_questiontopics_questions FOREIGN KEY (questions_id) REFERENCES questions(id);
ALTER TABLE question_topics 
    ADD CONSTRAINT fk_questiontopics_topics FOREIGN KEY (topics_id) REFERENCES topics(id);
GO

-- INSERT
INSERT INTO topics
VALUES(1, N'Câu hỏi điểm liệt', 'fire'),
    (2, N'Khái niệm và quy tắc', 'traffic_lights'),
    (3, N'Nghiệp vụ vận tải', 'delivery_truck'),
    (4, N'Văn hoá và đạo đức', 'man'),
    (5, N'Kỹ thuật lái xe', 'driving_methods'),
    (6, N'Cấu tạo và sửa chữa', 'electric_drill'),
    (7, N'Biển báo đường bộ', 'attention'),
    (8, N'Sa hình', 'warning_pile')
GO

INSERT INTO questions
VALUES
    (1,
        N'Hành vi nào dưới đây bị nghiêm cấm?',
        N'Đỗ xe trên đường phố',
        N'Sử dụng xe đạp đi trên các tuyến quốc lộ có tốc độ cao',
        N'Làm hỏng (cố ý) cọc tiêu, gương cầu, dải phân cách',
        N'Sử dụng còi và quay đâu xe trong khu dân cư',
        N'Làm hỏng (cố ý) cọc tiêu, gương cầu, dải phân cách',
        NULL,
        NULL),
    (2,
        N'Hành vi đưa xe cơ giới, xe máy chuyên dùng không bảo đảm tiêu chuẩn an toàn kỹ thuật và bảo vệ môi trường vào tham gia giao thông đường bộ có bị nghiêm cấm hay không?',
        N'Không nghiêm cấm',
        N'Bị nghiêm cấm',
        N'Bị nghiêm cấm tùy theo các tuyến đường',
        N'Bị nghiêm cấm tuỳ theo loại xe',
        N'Bị nghiêm cấm',
        NULL,
        NULL),
    (3,
        N'Cuộc đua xe chỉ được thực hiện khi nào?',
        N'Diễn ra trên đường phố không có người qua lại',
        N'Được người dân ủng hộ',
        N'Được cơ quan có thẩm quyền cấp phép',
        NULL,
        N'Được cơ quan có thẩm quyền cấp phép',
        NULL,
        NULL)
GO

INSERT INTO question_topics
VALUES
    (1, 1),
    (2, 1),
    (3, 1)
GO

-- SELECT
SELECT * FROM topics

SELECT *
FROM questions

SELECT question, answer_a, answer_b, answer_c, answer_d, answer_correct
FROM questions

SELECT T.id, T.title, T.imageFileName, COUNT(Q.id) AS quantity
FROM topics AS T, questions AS Q, question_topics AS QT
WHERE T.id = QT.topics_id AND Q.id = QT.questions_id
GROUP BY T.id, T.title, T.imageFileName

--DELETE
DELETE FROM questions
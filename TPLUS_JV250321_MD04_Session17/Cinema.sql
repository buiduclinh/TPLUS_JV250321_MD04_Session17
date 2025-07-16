-- Tạo Database
CREATE DATABASE IF NOT EXISTS Cinema;
USE Cinema;

-- Tạo bảng Movies
CREATE TABLE Movies
(
    movie_id     INT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    director     VARCHAR(255),
    duration     INT,
    release_date DATE,
    status       BIT DEFAULT 1 -- 1: active, 0: inactive
);

-- Tạo bảng Showtimes
CREATE TABLE Showtimes
(
    showtime_id     INT PRIMARY KEY AUTO_INCREMENT,
    movie_id        INT,
    show_time       DATETIME,
    total_seats     INT,
    available_seats INT,
    status          BIT DEFAULT 1,
    FOREIGN KEY (movie_id) REFERENCES Movies (movie_id)
);

-- Tạo bảng Tickets
CREATE TABLE Tickets
(
    ticket_id     INT PRIMARY KEY AUTO_INCREMENT,
    showtime_id   INT,
    seat_number   VARCHAR(10),
    customer_name VARCHAR(255),
    FOREIGN KEY (showtime_id) REFERENCES Showtimes (showtime_id)
);

-- Stored Procedure: Add movie
DELIMITER //
CREATE PROCEDURE add_movie(
    IN p_title VARCHAR(255),
    IN p_director VARCHAR(255),
    IN p_duration INT,
    IN p_release_date DATE
)
BEGIN
    INSERT INTO Movies (title, director, duration, release_date, status)
    VALUES (p_title, p_director, p_duration, p_release_date, 1);
END //
DELIMITER ;

-- Stored Procedure: Update movie
DELIMITER //
CREATE PROCEDURE update_movie(
    IN p_movie_id INT,
    IN p_title VARCHAR(255),
    IN p_director VARCHAR(255),
    IN p_duration INT,
    IN p_release_date DATE
)
BEGIN
    UPDATE Movies
    SET title        = p_title,
        director     = p_director,
        duration     = p_duration,
        release_date = p_release_date
    WHERE movie_id = p_movie_id;
END //
DELIMITER ;

-- Stored Procedure: Delete movie
DELIMITER //
CREATE PROCEDURE delete_movie(
    IN p_movie_id INT
)
BEGIN
    -- Kiểm tra ràng buộc
    IF EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = p_movie_id) THEN
        UPDATE Movies SET status = 0 WHERE movie_id = p_movie_id;
    ELSE
        DELETE FROM Movies WHERE movie_id = p_movie_id;
    END IF;
END //
DELIMITER ;

-- Stored Procedure: Get all active movies
DELIMITER //
CREATE PROCEDURE get_active_movies()
BEGIN
    SELECT * FROM Movies WHERE status = 1;
END //
DELIMITER ;
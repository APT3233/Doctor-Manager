
-- Example database

CREATE DATABASE IF NOT EXISTS user_db;

USE user_db;

CREATE TABLE IF NOT EXISTS doctors (
    code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL,
    availability VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO doctors (code, name, specialty, availability, email) VALUES
('1', 'Nguyễn Văn An', 'Nội khoa', 'Available', 'nguyenvana@example.com'),
('2', 'Trần Thị Bình', 'Nhi khoa', 'Unavailable', 'tranthib@example.com'),
('3', 'Lê Văn Cường', 'Ngoại khoa', 'Available', 'levanc@example.com'),
('4', 'Phạm Thị Dương', 'Da liễu', 'Unavailable', 'phamthid@example.com'),
('5', 'Hoàng Văn Linh', 'Tai Mũi Họng', 'Available', 'hoangvane@example.com'),
('6', 'Vũ Thị Lành', 'Mắt', 'Unavailable', 'vuthif@example.com'),
('7', 'Đặng Văn Linh', 'Tim mạch', 'Available', 'dangvang@example.com'),
('8', 'Phùng Thị Hạnh', 'Sản phụ khoa', 'Unavailable', 'phungthih@example.com'),
('9', 'Phan Văn Thắng', 'Thần kinh', 'Available', 'phanvani@example.com'),
('10', 'Nguyễn Thị Nhi', 'Chỉnh hình', 'Unavailable', 'buithik@example.com');


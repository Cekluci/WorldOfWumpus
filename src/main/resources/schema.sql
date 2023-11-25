CREATE TABLE IF NOT EXISTS tiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    row INT NOT NULL,
    column INT NOT NULL,
    content CHAR(1),
    mapName varchar(30),
    UNIQUE(row, column, mapName)
);
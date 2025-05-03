CREATE TABLE IF NOT EXISTS schools (
    id VARCHAR(200) PRIMARY KEY NOT NULL ,
    cn_name VARCHAR(100) NOT NULL,
    jp_name VARCHAR(100) NULL,
    kr_name VARCHAR(100) NULL,
    en_name VARCHAR(255) NULL,
    introduce TEXT NULL,
    logo VARCHAR(255) NULL,
    beautify_logo VARCHAR(255) NULL,
    bg VARCHAR(255) NULL
);
CREATE TABLE IF NOT EXISTS school_clubs (
    id VARCHAR(200) PRIMARY KEY NOT NULL,
    school VARCHAR(200) NULL,
    cn_name VARCHAR(100) NOT NULL,
    jp_name VARCHAR(100) NULL,
    kr_name VARCHAR(100) NULL,
    en_name VARCHAR(255) NULL,
    logo VARCHAR(255) NULL,
    bg VARCHAR(255) NULL
);
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    cn_name VARCHAR(50) NOT NULL ,
    jp_name VARCHAR(100) NULL ,
    kr_name VARCHAR(100) NULL ,
    en_name VARCHAR(255) NOT NULL ,
    introduce TEXT NULL ,
    avatar_square VARCHAR(255) NULL ,
    avatar_rectangle VARCHAR(255) NOT NULL ,
    body_image VARCHAR(255) NOT NULL ,
    school VARCHAR(100) NULL ,
    club VARCHAR(100) NOT NULL
)
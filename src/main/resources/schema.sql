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
);
CREATE TABLE IF NOT EXISTS user_basic(
    uuid VARCHAR(32) PRIMARY KEY NOT NULL ,
    username VARCHAR(50) UNIQUE NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    password_version TINYINT DEFAULT 1 ,
    email VARCHAR(255) UNIQUE NOT NULL ,
    email_verified BOOLEAN DEFAULT FALSE ,
    telephone VARCHAR(20) NULL ,
    safe_level TINYINT DEFAULT 100 ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS user_archive(
    uuid VARCHAR(32) PRIMARY KEY NOT NULL ,
    nickname VARCHAR(50) NOT NULL DEFAULT '用户' ,
    avatar VARCHAR(255) NOT NULL DEFAULT 'https://file.naigos.cn:52011/avatar/dc4d81a09fb2728a3c7d028b035652fa' ,
    gender TINYINT DEFAULT 0,
    birthday DATE NULL ,
    age INT NULL ,
    permission INT NOT NULL DEFAULT 1 ,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    FOREIGN KEY (uuid) REFERENCES user_basic(uuid) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS verification_code (
    unique_id VARCHAR(36) PRIMARY KEY NOT NULL ,
    code VARCHAR(6) NOT NULL ,
    username VARCHAR(50) NOT NULL ,
    scenario VARCHAR(20) NOT NULL ,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    expiration_at TIMESTAMP NOT NULL ,
    used BOOLEAN DEFAULT FALSE
)
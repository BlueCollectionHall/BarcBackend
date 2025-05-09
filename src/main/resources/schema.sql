# CREATE TABLE IF NOT EXISTS schools (
#     id VARCHAR(200) PRIMARY KEY NOT NULL ,
#     cn_name VARCHAR(100) NOT NULL,
#     jp_name VARCHAR(100) NULL,
#     kr_name VARCHAR(100) NULL,
#     en_name VARCHAR(255) NULL,
#     introduce TEXT NULL,
#     logo VARCHAR(255) NULL,
#     beautify_logo VARCHAR(255) NULL,
#     bg VARCHAR(255) NULL
# );
# CREATE TABLE IF NOT EXISTS school_clubs (
#     id VARCHAR(200) PRIMARY KEY NOT NULL,
#     school VARCHAR(200) NULL,
#     cn_name VARCHAR(100) NOT NULL,
#     jp_name VARCHAR(100) NULL,
#     kr_name VARCHAR(100) NULL,
#     en_name VARCHAR(255) NULL,
#     logo VARCHAR(255) NULL,
#     bg VARCHAR(255) NULL
# );
# CREATE TABLE IF NOT EXISTS students (
#     id VARCHAR(255) PRIMARY KEY NOT NULL,
#     cn_name VARCHAR(50) NOT NULL ,
#     jp_name VARCHAR(100) NULL ,
#     kr_name VARCHAR(100) NULL ,
#     en_name VARCHAR(255) NOT NULL ,
#     introduce TEXT NULL ,
#     avatar_square VARCHAR(255) NULL ,
#     avatar_rectangle VARCHAR(255) NOT NULL ,
#     body_image VARCHAR(255) NOT NULL ,
#     school VARCHAR(100) NULL ,
#     club VARCHAR(100) NOT NULL
# );
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
);
CREATE TABLE IF NOT EXISTS category(
    id VARCHAR(50) PRIMARY KEY NOT NULL ,
    parent_id VARCHAR(50) NULL ,
    name VARCHAR(20) NOT NULL ,
    level TINYINT NOT NULL ,
    sort INT DEFAULT 0 ,
    is_enabled BOOLEAN DEFAULT TRUE ,
    icon TEXT NULL ,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE CASCADE ,
    INDEX idx_parent (parent_id),
    INDEX idx_level (level)
);
CREATE TABLE IF NOT EXISTS work(
    id VARCHAR(100) PRIMARY KEY NOT NULL ,
    title VARCHAR(50) NOT NULL ,
    description TEXT NULL ,
    content LONGTEXT NULL ,
    banner_image VARCHAR(255) NOT NULL ,
    cover_image VARCHAR(255) NOT NULL ,
    view_count INT DEFAULT 0 ,
    like_count INT DEFAULT 0 ,
    author VARCHAR(32) NULL ,
    author_nickname VARCHAR(50) NULL ,
    source VARCHAR(50) NOT NULL ,
    is_claim BOOLEAN DEFAULT FALSE ,
    status TINYINT DEFAULT 0 ,
    student VARCHAR(255) NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    INDEX idx_creator (author),
    INDEX idx_status (status)
);
CREATE TABLE IF NOT EXISTS work_category(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    work_id VARCHAR(100) NOT NULL ,
    category_id VARCHAR(50) NOT NULL ,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_work_category (work_id, category_id) ,
    FOREIGN KEY (work_id) REFERENCES work(id) ON DELETE CASCADE ,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS school(
    id VARCHAR(200) PRIMARY KEY NOT NULL ,
    cn_name VARCHAR(100) NOT NULL,
    jp_name VARCHAR(100) NULL,
    kr_name VARCHAR(100) NULL,
    en_name VARCHAR(255) NULL,
    introduce TEXT NULL,
    logo VARCHAR(255) NULL,
    beautify_logo VARCHAR(255) NULL,
    bg VARCHAR(255) NULL,
    UNIQUE KEY uk_name (cn_name)
);
CREATE TABLE IF NOT EXISTS club(
    id VARCHAR(200) PRIMARY KEY NOT NULL,
    school VARCHAR(200) NULL,
    cn_name VARCHAR(100) NOT NULL,
    jp_name VARCHAR(100) NULL,
    kr_name VARCHAR(100) NULL,
    en_name VARCHAR(255) NULL,
    logo VARCHAR(255) NULL,
    bg VARCHAR(255) NULL,
    UNIQUE KEY uk_name (cn_name)
);
CREATE TABLE IF NOT EXISTS school_club(
    id INT PRIMARY KEY AUTO_INCREMENT,
    school_id VARCHAR(200) NOT NULL ,
    club_id VARCHAR(200) NOT NULL ,
    FOREIGN KEY (school_id) REFERENCES school(id) ON DELETE CASCADE ,
    FOREIGN KEY (club_id) REFERENCES club(id) ON DELETE CASCADE ,
    UNIQUE KEY uk_school_club (school_id, club_id)
);
CREATE TABLE IF NOT EXISTS student(
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
    club VARCHAR(100) NOT NULL,
    INDEX idx_club (club),
    INDEX idx_school (school) ,
    FOREIGN KEY student(club) REFERENCES club(id) ON DELETE CASCADE ,
    FOREIGN KEY student(school) REFERENCES school(id) ON DELETE RESTRICT
);
CREATE TABLE IF NOT EXISTS barc_naigos_uuid(
    uuid VARCHAR(32) PRIMARY KEY NOT NULL ,
    naigos_uuid VARCHAR(36) UNIQUE NOT NULL ,
    FOREIGN KEY barc_naigos_uuid(uuid) REFERENCES user_basic(uuid) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS work_claim(
    work_id VARCHAR(100) PRIMARY KEY NOT NULL ,
    initiator_uuid VARCHAR(32) NOT NULL ,
    recipient_uuid VARCHAR(32) NOT NULL ,
    status INT DEFAULT 0 ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (work_id) REFERENCES work(id) ON DELETE CASCADE ,
    FOREIGN KEY (initiator_uuid) REFERENCES user_basic(uuid) ON DELETE CASCADE ,
    FOREIGN KEY (recipient_uuid) REFERENCES user_basic(uuid) ON DELETE CASCADE ,
    UNIQUE KEY (work_id, initiator_uuid) ,
    CONSTRAINT chk_no_self_claim CHECK (initiator_uuid <> recipient_uuid)
);
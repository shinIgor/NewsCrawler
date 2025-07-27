use
news_crawler;
#
# ---------------------------------------------------------------------------------------------------------------------------


drop table if exists news_crawler.account_ut;
create table news_crawler.account_ut
(
    account_ut_id     INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '유저 계정 고유 ID',
    user_id           VARCHAR(20) NOT NULL COMMENT '유저 사용자 ID',
    user_password     VARCHAR(64) NOT NULL COMMENT '유저 사용자 비밀번호 (암호화 후 저장)',
    user_name         VARCHAR(10) NOT NULL COMMENT '유저 이름',
    account_role      TINYINT     NOT NULL DEFAULT 0 COMMENT '유저 권한: 0불명, 1유저, 2:관리자',
    last_processed_at TIMESTAMP   NOT NULL COMMENT '마지막 접속 시간',
    updated_at        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 시간',
    created_at        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB CHARACTER SET utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '유저의 계정 테이블';
show
full
columns from news_crawler.account_ut;


CREATE INDEX idx_userid_n1 ON news_crawler.account_ut (user_id);
CREATE INDEX idx_lastprocessedat_n1 ON news_crawler.account_ut (last_processed_at);
CREATE INDEX idx_updatedat_n2 ON news_crawler.account_ut (updated_at, created_at);

#
# ---------------------------------------------------------------------------------------------------------------------------

drop table if exists news_crawler.crawler_mt;
create table news_crawler.crawler_mt
(
    crawler_mt_id      INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '크롤링 서비스 ID',
    crawler_site_mt_id INT UNSIGNED NOT NULL COMMENT '크롤링 API 호출 사이트 ID',
    key_word           VARCHAR(50) NOT NULL COMMENT '뉴스 수집 키워드',
    duration_at        INT UNSIGNED NOT NULL DEFAULT 86400 COMMENT '수집 Duration (Unix TimeStamp)',
    is_scrab           TINYINT     NOT NULL COMMENT '수집 여부 (0: 수집 X, 1: 수집 O)',
    updated_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 시간',
    created_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '뉴스 스크랩 메타테이블';

CREATE INDEX idx_keyword_n2 ON news_crawler.crawler_mt (key_word, crawler_site_mt_id);
CREATE INDEX idx_durationat_n1 ON news_crawler.crawler_mt (duration_at);
CREATE INDEX idx_isscrab_n1 ON news_crawler.crawler_mt (is_scrab);

# ---------------------------------------------------------------------------------------------------------------------------

drop table if exists news_crawler.crawler_lt;
create table news_crawler.crawler_lt
(
    crawler_lt_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '크롤링 서비스 로그 ID',
    crawler_mt_id INT UNSIGNED NOT NULL COMMENT '크롤링 서비스 ID',
    before_data   JSON      NOT NULL COMMENT '변경 전 데이터',
    after_data    JSON      NOT NULL COMMENT '변경 후 데이터',
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '뉴스 스크랩 변경 데이터';

CREATE INDEX idx_crawlermtid_n1 ON news_crawler.crawler_lt (crawler_mt_id);

# ---------------------------------------------------------------------------------------------------------------------------

drop table if exists news_crawler.crawler_site_lt;
create table news_crawler.crawler_site_lt
(
    crawler_site_lt_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '크롤링 서비스 로그 ID',
    crawler_site_mt_id INT UNSIGNED NOT NULL COMMENT '크롤링 API 사이트 ID',
    before_data        JSON      NOT NULL COMMENT '변경 전 데이터',
    after_data         JSON      NOT NULL COMMENT '변경 후 데이터',
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '뉴스 스크랩 변경 데이터';
show
full
columns from news_crawler.crawler_site_lt;

CREATE INDEX idx_crawlerapimtid_n1 ON news_crawler.crawler_site_lt (crawler_site_mt_id);

# ---------------------------------------------------------------------------------------------------------------------------

drop table if exists news_crawler.crawler_site_mt;
create table news_crawler.crawler_site_mt
(
    crawler_site_mt_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '크롤링 API 사이트 ID',
    site_name          VARCHAR(20) NOT NULL COMMENT '크롤링 사이트 이름',
    url                TEXT        NOT NULL COMMENT '사이트 URL',
    header             JSON        NOT NULL COMMENT 'URL Header',
    body               JSON        NOT NULL COMMENT 'URL Body',
    updated_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 시간',
    created_at         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '크롤링 사이트 정보';
show
full
columns from news_crawler.crawler_site_mt;

CREATE INDEX idx_sitename_n1 ON news_crawler.crawler_site_mt (site_name);

# ---------------------------------------------------------------------------------------------------------------------------

drop table if exists news_crawler.user_service_ut;
create table news_crawler.user_service_ut
(
    user_service_ut_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '유저 서비스 테이블 ID',
    account_ut_id      INT UNSIGNED NOT NULL COMMENT '유저 테이블 ID',
    crawler_mt_id      INT UNSIGNED NOT NULL COMMENT '크롤링 ID',
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 시간',
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '유저의 뉴스 서비스 테이블';

CREATE INDEX idx_accountutid_n1 ON news_crawler.user_service_ut (account_ut_id);
CREATE INDEX idx_crawler_mt_id_n1 ON news_crawler.user_service_ut (crawler_mt_id);

# ---------------------------------------------------------------------------------------------------------------------------


drop table if exists news_crawler.news_lt;
create table news_crawler.news_lt
(
    news_lt_id        INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '뉴스 스크랩 ID',
    crawler_mt_id     INT UNSIGNED COMMENT '크롤링 메타 데이터 ID',
    crawler_api_mt_id INT UNSIGNED COMMENT '크롤링 사이트 데이터 ID',
    news_title        VARCHAR(20) NOT NULL COMMENT '뉴스 제목',
    news_post         VARCHAR(64) NOT NULL COMMENT '뉴스 내용',
    updated_at        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 시간',
    created_at        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간'
) ENGINE = INNODB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '뉴스 스크랩 데이터';

CREATE INDEX idx_crawlermtid_n1 ON news_crawler.news_lt (crawler_mt_id);

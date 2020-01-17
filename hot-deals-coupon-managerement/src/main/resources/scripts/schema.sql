/* 사용여부 코드 Table */
CREATE TABLE IF NOT EXISTS USE_CODE (
      CODE                      VARCHAR(2)                                  NOT NULL                                    COMMENT '사용여부 코드'
    , DESCRIPTION               VARCHAR(255)                                NULL                                        COMMENT '설명'
    , EN_DESCRIPTION            VARCHAR(255)                                NULL                                        COMMENT '영문 설명'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , PRIMARY KEY (CODE)
) COMMENT = '사용여부 코드';


/* 권한 정보 Table */
CREATE TABLE IF NOT EXISTS ROLE (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '권한 일련번호'
    , ROLE_ID                   VARCHAR(50)                                 NOT NULL                                    COMMENT '권한 아이디'
    , PARENT_ROLE_SEQ           INT         UNSIGNED                        NOT NULL    DEFAULT 'ROLE_ADMIN'            COMMENT '상위 권한 일련번'
    , DESCRIPTION               VARCHAR(255)                                NOT NULL                                    COMMENT '권한 설명'
    , PRIORITY                  INT                                         NOT NULL    DEFAULT 99                      COMMENT '우선순위 - 1이 가장 높음'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_ROLE PRIMARY KEY (SEQ)
    , UNIQUE INDEX UK_ROLE01 (ROLE_ID, USE_CODE)
    , INDEX IDX_ROLE99 (USE_CODE)
    , CONSTRAINT FK_ROLE99
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '권한 정보';


/* 그룹 정보 Table */
CREATE TABLE IF NOT EXISTS GROUPS (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '그룹 일련번호'
    , GROUP_ID                  VARCHAR(50)                                 NOT NULL                                    COMMENT '그룹 아이디'
    , GROUP_NAME                VARCHAR(255)                                NOT NULL                                    COMMENT '그룹명'
    , PRIORITY                  INT                                         NOT NULL    DEFAULT 99                      COMMENT '우선순위 - 1이 가장 높음'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_GROUPS PRIMARY KEY (SEQ)
    , UNIQUE INDEX UK_GROUPS01 (GROUP_ID, USE_CODE)
    , INDEX IDX_GROUPS01 (USE_CODE)
    , CONSTRAINT FK_GROUPS01
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '그룹 정보';


/* 사용자 정보 Table */
CREATE TABLE IF NOT EXISTS USER (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '사용자 일련번호'
    , USER_ID                   VARCHAR(20)                                 NOT NULL                                    COMMENT '사용자 아이디'
    , PASSWD                    VARCHAR(100)                                NOT NULL                                    COMMENT '사용자 비밀번호'
    , USER_NAME                 VARCHAR(20)                                 NOT NULL                                    COMMENT '사용자명'
    , GROUP_SEQ                 INT         UNSIGNED                        NOT NULL                                    COMMENT '그룹 일련번호'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_USER PRIMARY KEY (SEQ)
    , UNIQUE INDEX UK_USER01 (USER_ID, GROUP_SEQ, USE_CODE)
    , INDEX IDX_USER01 (USER_ID)
    , INDEX IDX_USER02 (GROUP_SEQ)
    , INDEX IDX_USER99 (USE_CODE)
    , CONSTRAINT FK_USER02
        FOREIGN KEY (GROUP_SEQ)
        REFERENCES GROUPS (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_USER99
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '사용자 정보';


/* 사용자 권한 정보 Table */
CREATE TABLE IF NOT EXISTS USER_AUTHORITY (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '사용자 권한 일련번호'
    , GROUP_SEQ                 INT         UNSIGNED                        NOT NULL                                    COMMENT '그룹 일련번호'
    , USER_SEQ                  INT         UNSIGNED                        NOT NULL                                    COMMENT '사용자 일련번호'
    , ROLE_SEQ                  INT         UNSIGNED                        NOT NULL                                    COMMENT '사용자 권한'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_USER_AUTHORITY PRIMARY KEY (SEQ)
    , UNIQUE INDEX UK_USER_AUTHORITY01 (GROUP_SEQ, USER_SEQ, ROLE_SEQ, USE_CODE)
    , INDEX IDX_USER_AUTHORITY01 (GROUP_SEQ)
    , INDEX IDX_USER_AUTHORITY02 (USER_SEQ)
    , INDEX IDX_USER_AUTHORITY03 (ROLE_SEQ)
    , INDEX IDX_USER_AUTHORITY99 (USE_CODE)
    , CONSTRAINT FK_USER_AUTHORITY01
        FOREIGN KEY (GROUP_SEQ)
        REFERENCES GROUPS (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_USER_AUTHORITY02
        FOREIGN KEY (USER_SEQ)
        REFERENCES USER (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_USER_AUTHORITY03
        FOREIGN KEY (ROLE_SEQ)
        REFERENCES ROLE (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_USER_AUTHORITY99
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '사용자 권한 정보';


/* 메뉴 테이블 */
CREATE TABLE IF NOT EXISTS MENU (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '메뉴 일련번호'
    , MENU_NAME                 VARCHAR(255)                                NOT NULL                                    COMMENT '메뉴명'
    , PARENT_MENU_SEQ           INT         UNSIGNED                        NOT NULL    DEFAULT 0                       COMMENT '상위 메뉴 일련번호'
    , PRIORITY                  INT         UNSIGNED                        NOT NULL    DEFAULT 1                       COMMENT '우선순위 - 1이 가장 높음'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_MENU PRIMARY KEY (SEQ)
    , INDEX IDX_MENU01 (PARENT_MENU_SEQ)
    , INDEX IDX_MENU02 (PRIORITY)
    , INDEX IDX_MENU99 (USE_CODE)
    , CONSTRAINT FK_MENU99
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '메뉴 정보';


/* 프로그램 API 테이블 */
CREATE TABLE IF NOT EXISTS PROGRAM_API (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '프로그램 API 일련번호'
    , API_URL                   VARCHAR(500)                                NOT NULL                                    COMMENT '프로그램 URL'
    , API_METHOD                VARCHAR(10)                                 NOT NULL                                    COMMENT '프로그램 URL Request Method'
    , API_NAME                  VARCHAR(255)                                NOT NULL                                    COMMENT '프로그램명'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_PROGRAM_API PRIMARY KEY (SEQ)
    , UNIQUE INDEX UK_PROGRAM_API01 (API_URL, API_METHOD)
    , INDEX IDX_PROGRAM_API01 (API_URL, USE_CODE)
    , INDEX IDX_PROGRAM_API99 (USE_CODE)
    , CONSTRAINT FK_PROGRAM_API99
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '프로그램 API 정보';


/* 메뉴 권한 정보 Table */
CREATE TABLE IF NOT EXISTS MENU_AUTHORITY (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '메뉴 권한 일련번호'
    , MENU_SEQ                  INT         UNSIGNED                        NOT NULL                                    COMMENT '메뉴 일련번호'
    , API_SEQ                   INT         UNSIGNED                        NOT NULL                                    COMMENT '프로그램 API 일련번호'
    , ROLE_SEQ                  INT         UNSIGNED                        NOT NULL                                    COMMENT '권한 일련번호'
    , USE_CODE                  VARCHAR(2)                                  NOT NULL    DEFAULT 'Y'                     COMMENT '사용여부'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , CONSTRAINT PK_MENU_AUTHORITY PRIMARY KEY (SEQ)
    , UNIQUE INDEX UK_MENU_AUTHORITY01 (MENU_SEQ, API_SEQ, ROLE_SEQ, USE_CODE)
    , INDEX IDX_MENU_AUTHORITY01 (MENU_SEQ)
    , INDEX IDX_MENU_AUTHORITY02 (API_SEQ)
    , INDEX IDX_MENU_AUTHORITY03 (ROLE_SEQ)
    , INDEX IDX_MENU_AUTHORITY99 (USE_CODE)
    , CONSTRAINT FK_MENU_AUTHORITY01
        FOREIGN KEY (MENU_SEQ)
        REFERENCES MENU (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_MENU_AUTHORITY02
        FOREIGN KEY (API_SEQ)
        REFERENCES PROGRAM_API (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_MENU_AUTHORITY03
        FOREIGN KEY (ROLE_SEQ)
        REFERENCES ROLE (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
    , CONSTRAINT FK_MENU_AUTHORITY99
        FOREIGN KEY (USE_CODE)
        REFERENCES USE_CODE (CODE)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) COMMENT = '메뉴 권한 정보';






/* 로그인 인증 Table (싱글 로그인 테이블) */
/*
CREATE TABLE IF NOT EXISTS AUTHORIZATION (
      USER_ID                   VARCHAR(20)                                 NOT NULL                                    COMMENT '사용자 아이디'
    , SECRET_KEY                VARCHAR(50)                                 NOT NULL                                    COMMENT '암/복호화키'
    , TOKEN_KEY                 VARCHAR(50)                                 NOT NULL                                    COMMENT '토큰키'
    , REMOTE_ADDR               VARCHAR(20)                                 NOT NULL                                    COMMENT '사용자 아이피'
    , AUTHORIZATION             TEXT                                        NOT NULL                                    COMMENT '인증 정보'
    , EXPIRE_DATE               TIMESTAMP                                   NOT NULL                                    COMMENT '만료일'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , PRIMARY KEY (USER_ID)
    , INDEX IDX_TOKEN02 (SECRET_KEY, TOKEN_KEY)
    , CONSTRAINT FK_TOKEN01
        FOREIGN KEY (USER_ID)
        REFERENCES USER (USER_ID)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
) COMMENT = '싱글 로그인 인증 정보';
*/


/* 로그인 인증 Table (멀티 로그인 테이블) */
CREATE TABLE IF NOT EXISTS AUTHORIZATION (
      SEQ                       INT         UNSIGNED    AUTO_INCREMENT      NOT NULL                                    COMMENT '로그인 인증 일련번호'
    , USER_SEQ                  INT         UNSIGNED                        NOT NULL                                    COMMENT '사용자 일련번호'
    , SECRET_KEY                VARCHAR(50)                                 NOT NULL                                    COMMENT '암/복호화키'
    , TOKEN_KEY                 VARCHAR(50)                                 NOT NULL                                    COMMENT '토큰키'
    , REMOTE_ADDR               VARCHAR(20)                                 NOT NULL                                    COMMENT '사용자 아이피'
    , TOKEN                     TEXT                                        NOT NULL                                    COMMENT '인증 토큰 정보'
    , EXPIRE_DATE               TIMESTAMP                                   NOT NULL                                    COMMENT '만료일'
    , UPDATE_DATE               TIMESTAMP                                   NULL                                        COMMENT '최종 수정일'
    , CREATE_DATE               TIMESTAMP                                   NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '최초 등록일'
    , PRIMARY KEY (SEQ)
    , INDEX IDX_TOKEN01 (USER_SEQ)
    , INDEX IDX_TOKEN02 (SECRET_KEY, TOKEN_KEY, REMOTE_ADDR)
    , CONSTRAINT FK_TOKEN01
        FOREIGN KEY (USER_SEQ)
        REFERENCES USER (SEQ)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
) COMMENT = '멀티 로그인 인증 정보';

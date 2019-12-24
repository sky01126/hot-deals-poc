/* 사용여부 코드 Data */
INSERT INTO USE_CODE (CODE, DESCRIPTION, EN_DESCRIPTION, UPDATE_DATE) VALUES
                     ('Y',  '사용',       'YES',          NOW())
                   , ('N',  '사용정지',    'NO',           NOW())
                   , ('T',  '임시',       'TEMP',         NOW())
, ('D', '삭제',    'DELETE', NOW())
ON DUPLICATE KEY UPDATE
    DESCRIPTION         = VALUES(DESCRIPTION)
  , EN_DESCRIPTION      = VALUES(EN_DESCRIPTION)
;


/* 권한 정보 Data */
INSERT INTO ROLE (SEQ, ROLE_ID,      USE_CODE, PARENT_ROLE_SEQ, DESCRIPTION, PRIORITY, UPDATE_DATE) VALUES
                 (1,   'ROLE_ADMIN', 'Y',      1,               '어드민',      1,        NOW())
               , (2,   'ROLE_USER',  'Y',      1,               '일반 사용자',  2,        NOW())
               , (3,   'ROLE_GUEST', 'Y',      1,               '게스트 사용자', 99,       NOW())
ON DUPLICATE KEY UPDATE
    PARENT_ROLE_SEQ     = VALUES(PARENT_ROLE_SEQ)
  , DESCRIPTION         = VALUES(DESCRIPTION)
;



/* 그룹 정보 Data */
INSERT INTO GROUPS (SEQ, GROUP_ID,   GROUP_NAME, PRIORITY, UPDATE_DATE) VALUES
                   (1,   'ADMIN',    '어드민',     1,        NOW())
                 , (2,   'CUSTOMER', '회원고객',    2,        NOW())
ON DUPLICATE KEY UPDATE
    GROUP_NAME          = VALUES(GROUP_NAME)
;


/* 사용자 정보 Data */
INSERT INTO USER (SEQ, USER_ID, USE_CODE, PASSWD,     GROUP_SEQ, USER_NAME, UPDATE_DATE) VALUES
                 (1,   'admin', 'Y',      'qwer1234', 1,         '어드민',    NOW())
               , (2,   'test1', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (3,   'test2', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (4,   'test3', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (5,   'test4', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (6,   'test5', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (7,   'test6', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (8,   'test7', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (9,   'test8', 'Y',      'qwer1234', 1,         '사용자',    NOW())
               , (10,  'user1', 'Y',      'qwer1234', 2,         '사용자',    NOW())
ON DUPLICATE KEY UPDATE
    PASSWD              = VALUES(PASSWD)
  , GROUP_SEQ           = VALUES(GROUP_SEQ)
  , USER_NAME           = VALUES(USER_NAME)
;


/* 사용자 권한 정보 Data */
INSERT INTO USER_AUTHORITY (SEQ, GROUP_SEQ, USER_SEQ, ROLE_SEQ, UPDATE_DATE) VALUES
                           (1,   1,         1,        1,        NOW())
                         , (2,   1,         2,        1,        NOW())
                         , (3,   1,         2,        2,        NOW())
                         , (4,   1,         3,        2,        NOW())
                         , (5,   1,         4,        2,        NOW())
                         , (6,   1,         5,        2,        NOW())
ON DUPLICATE KEY UPDATE
    GROUP_SEQ           = VALUES(GROUP_SEQ)
  , USER_SEQ            = VALUES(USER_SEQ)
  , ROLE_SEQ            = VALUES(ROLE_SEQ)
;


/* 메뉴 정보 Data */
INSERT INTO MENU (SEQ, MENU_NAME, UPDATE_DATE) VALUES
                 (1,   '테스트 #1', NOW())
               , (2,   '테스트 #2', NOW())
ON DUPLICATE KEY UPDATE
    MENU_NAME           = VALUES(MENU_NAME)
;


/* 프로그램 API 정보 Data */
INSERT INTO PROGRAM_API (SEQ, API_URL,         API_METHOD, API_NAME,           UPDATE_DATE) VALUES
                        (1,   '/home',         'GET',      'HOME 테스트 API #1', NOW())
                      , (2,   '/home/{name}',  'GET',      'HOME 테스트 API #2', NOW())
ON DUPLICATE KEY UPDATE
    UPDATE_DATE         = VALUES(UPDATE_DATE)
;


/* 메뉴 정보 Data */
INSERT INTO MENU_AUTHORITY (SEQ, MENU_SEQ, API_SEQ, ROLE_SEQ, UPDATE_DATE) VALUES
                           (1,   1,        1,       1,        NOW())
                         , (2,   1,        2,       1,        NOW())
                         , (3,   2,        1,       2,        NOW())
ON DUPLICATE KEY UPDATE
    UPDATE_DATE         = VALUES(UPDATE_DATE)
;


/* 로그인  토큰 Table (멀티 로그인 테이블) */
/*
 * admin 사용자 테스트 인증 토큰 : CPoUV5fVHzDO7N9SZO+QGOxxdK1QhDUW8PkP/KmBLMZM5T1g03UJjHPv93RgvwG4+Qeu9iTIZGlMO7pyNVvXZScJlka3N+XJQf2+EL4aq+8Uz2drnSm4gh0CO1oNe5Sl
 * test1 사용자 테스트 인증 토큰 : ulPEcqaBOMQZder3i8JValdp75NtPPwjLhPVBVrqnoSb611e/VLechTGOGLQm3aCZI1RqMv5tdI/uqHvnghOP10ybMBiqubdFZN+yRoOoB7Z2oacF0k+WXznqqzs5NlD
 * test2 사용자 테스트 인증 토큰 : J/S8SegciZpaMw9EzKe/66JqAyqhU1MuMSpFi+5H3NlAU/OGwRJKnY+66k6zacv89px8+ZSabbAEVeuMpT1RWIbJFSKdQDp8bDGve/PX/lOTx3bFuc5T1OauIlXIK83K
 */
INSERT INTO AUTHORIZATION (SEQ, USER_SEQ, SECRET_KEY, TOKEN_KEY, REMOTE_ADDR, TOKEN, EXPIRE_DATE) VALUES
    (1, 1, 'a123bc50-5996-d062-472f-c6b2-6a0613f4d7342261', 'ttL_xepg14XXlPSk07pBjcR-mS0QRdL2FTdI6baJaA4', '127.0.0.1', 'eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKsrPSVWyilYK8vdxjXd08fX0U4rVUSotTi3KTFGyUkpMyc3MU6oFADxbPeUoAAAA.ttL_xepg14XXlPSk07pBjcR-mS0QRdL2FTdI6baJaA4',           PARSEDATETIME('2099-12-31', 'yyyy-MM-dd'))
  , (2, 2, '453a2fe8-7111-2c72-196a-fb3d-69bebb0f4d62c11a', '9MK6ENAoASoY2uhpazEosyGWjqU4XJHCOTAcT-oL1nU', '127.0.0.1', 'eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKsrPSVWyilYK8vdxjXd08fX0U9KBcEKDXYOUYnWUSotTizJTlKyUSlKLSwyVagFa5PCgNAAAAA.9MK6ENAoASoY2uhpazEosyGWjqU4XJHCOTAcT-oL1nU', PARSEDATETIME('2099-12-31', 'yyyy-MM-dd'))
  , (3, 3, 'e728dce8-c60e-b2eb-b5f9-7722-260bbe88e2f97e64', '13_Hb3NMFfgpk_fXFmpL35T8Upg8S5K9gweXTQFKfAQ', '127.0.0.1', 'eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKsrPSVWyilYK8vdxjQ8Ndg1SitVRKi1OLcpMUbJSKkktLjFSqgUAIesJtCcAAAA.13_Hb3NMFfgpk_fXFmpL35T8Upg8S5K9gweXTQFKfAQ',            PARSEDATETIME('2099-12-31', 'yyyy-MM-dd'))
ON DUPLICATE KEY UPDATE
    SECRET_KEY          = VALUES(SECRET_KEY)
  , TOKEN_KEY           = VALUES(TOKEN_KEY)
  , REMOTE_ADDR         = VALUES(REMOTE_ADDR)
  , TOKEN               = VALUES(TOKEN)
  , EXPIRE_DATE         = VALUES(EXPIRE_DATE)
;
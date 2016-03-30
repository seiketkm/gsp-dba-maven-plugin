CREATE TABLE gspanother.TEST_TBL1 (
  TEST_TBL1_ID BIGINT NOT NULL,
  NAME VARCHAR(30),
  PRIMARY KEY (TEST_TBL1_ID)
);
COMMENT ON table gspanother.TEST_TBL1 is 'テストテーブル1';
COMMENT ON column gspanother.TEST_TBL1.TEST_TBL1_ID is 'TEST_TBL1_ID';
COMMENT ON column gspanother.TEST_TBL1.NAME is 'NAME';
CREATE SEQUENCE gspanother.TEST_TBL1_ID_SEQ increment by 1 start with 1;

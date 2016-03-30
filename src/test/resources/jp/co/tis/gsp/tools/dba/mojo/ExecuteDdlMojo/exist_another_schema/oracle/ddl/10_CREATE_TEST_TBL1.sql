CREATE TABLE GSPANOTHER.TEST_TBL1 (
  TEST_TBL1_ID NUMBER NOT NULL ,
  NAME VARCHAR2(30 CHAR)
);
COMMENT ON table GSPANOTHER.TEST_TBL1 is 'テストテーブル1';
COMMENT ON column GSPANOTHER.TEST_TBL1.TEST_TBL1_ID is 'TEST_TBL1_ID';
COMMENT ON column GSPANOTHER.TEST_TBL1.NAME is 'NAME';
CREATE SEQUENCE GSPANOTHER.TEST_TBL1_ID_SEQ increment by 1 start with 1;

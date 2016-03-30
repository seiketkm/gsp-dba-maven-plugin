CREATE TABLE GSPANOTHER.C_TABLE (
  C_ID NUMBER NOT NULL ,
  NAME VARCHAR2(100 CHAR),
  TEST1 VARCHAR2(100 CHAR),
  TEST2 VARCHAR2(500 CHAR),
  TEST3 VARCHAR2(500 CHAR)
);
COMMENT ON table GSPANOTHER.C_TABLE is 'C_TABLE';
COMMENT ON column GSPANOTHER.C_TABLE.C_ID is 'C_ID';
COMMENT ON column GSPANOTHER.C_TABLE.NAME is 'NAME';
COMMENT ON column GSPANOTHER.C_TABLE.TEST1 is 'TEST1';
COMMENT ON column GSPANOTHER.C_TABLE.TEST2 is 'TEST2';
COMMENT ON column GSPANOTHER.C_TABLE.TEST3 is 'TEST3';
CREATE SEQUENCE GSPANOTHER.C_ID_SEQ increment by 1 start with 1;

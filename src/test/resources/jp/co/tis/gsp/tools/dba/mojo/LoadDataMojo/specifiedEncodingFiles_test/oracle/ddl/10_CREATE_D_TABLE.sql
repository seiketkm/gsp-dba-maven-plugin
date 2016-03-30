CREATE TABLE D_TABLE (
  D_ID NUMBER NOT NULL ,
  NAME VARCHAR2(100 CHAR),
  TEST1 VARCHAR2(100 CHAR),
  TEST2 VARCHAR2(500 CHAR),
  TEST3 VARCHAR2(500 CHAR)
);
COMMENT ON table D_TABLE is 'D_TABLE';
COMMENT ON column D_TABLE.D_ID is 'D_ID';
COMMENT ON column D_TABLE.NAME is 'NAME';
COMMENT ON column D_TABLE.TEST1 is 'TEST1';
COMMENT ON column D_TABLE.TEST2 is 'TEST2';
COMMENT ON column D_TABLE.TEST3 is 'TEST3';
CREATE SEQUENCE D_ID_SEQ increment by 1 start with 1;

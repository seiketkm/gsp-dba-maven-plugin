CREATE TABLE GSPANOTHER.A_TABLE (
  A_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  B_ID BIGINT,
  C_ID BIGINT,
  NAME VARCHAR(100),
  TEST1 VARCHAR(100),
  TEST2 VARCHAR(500),
  TEST3 VARCHAR(500)
);
COMMENT ON table GSPANOTHER.A_TABLE is 'A_TABLE';
COMMENT ON column GSPANOTHER.A_TABLE.A_ID is 'A_ID';
COMMENT ON column GSPANOTHER.A_TABLE.B_ID is 'B_ID';
COMMENT ON column GSPANOTHER.A_TABLE.C_ID is 'C_ID';
COMMENT ON column GSPANOTHER.A_TABLE.NAME is 'NAME';
COMMENT ON column GSPANOTHER.A_TABLE.TEST1 is 'TEST1';
COMMENT ON column GSPANOTHER.A_TABLE.TEST2 is 'TEST2';
COMMENT ON column GSPANOTHER.A_TABLE.TEST3 is 'TEST3';

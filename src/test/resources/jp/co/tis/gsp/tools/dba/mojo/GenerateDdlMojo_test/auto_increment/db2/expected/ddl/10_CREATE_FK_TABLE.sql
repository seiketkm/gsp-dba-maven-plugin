CREATE TABLE FK_TABLE (
  FK_TABLE_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  TEST_NAME VARCHAR(30)
);
COMMENT ON table FK_TABLE is '外部テーブル';
COMMENT ON column FK_TABLE.FK_TABLE_ID is 'FK_TABLE_ID';
COMMENT ON column FK_TABLE.TEST_NAME is 'TEST_NAME';
CREATE TABLE TYPETEST (
  TYPE_1 BIGINT NOT NULL ,
  TYPE_3 CHAR(8) NOT NULL ,
  TYPE_4 DATETIME NOT NULL  DEFAULT 1 ,
  TYPE_5 DATE NULL ,
  TYPE_6 DECIMAL(10,2) NULL ,
  TYPE_7 INT NULL ,
  TYPE_8 NVARCHAR(20) NULL ,
  TYPE_9 TEXT NULL ,
  TYPE_10 TIMESTAMP NULL ,
  TYPE_11 VARCHAR(20) NULL ,
  TYPE_12 BINARY NULL ,
  TYPE_13 VARBINARY(2000) NULL ,
  TYPE_14 IMAGE NULL 
);
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプテスト', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ１', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_1;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ３', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_3;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ４', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_4;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ５', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_5;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ６', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_6;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ７', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_7;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ８', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_8;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ９', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_9;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ１０', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_10;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ１１', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_11;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ１２', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_12;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ１３', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_13;
EXEC sys.sp_addextendedproperty @name = N'Description', @value = N'タイプ１４', @level0type = N'SCHEMA', @level0name = gsptest, @level1type = N'TABLE',  @level1name = TYPETEST, @level2type = N'COLUMN',  @level2name = TYPE_14;

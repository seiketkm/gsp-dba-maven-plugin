CREATE TABLE CODE_NAME (
  CODE_ID VARCHAR(32) NOT NULL,
  CODE_VALUE VARCHAR(20) NOT NULL,
  LANG CHAR(2) NOT NULL,
  SORT_ORDER NUMERIC(2) NOT NULL,
  CODE_NAME VARCHAR(50) NOT NULL,
  SHORT_NAME VARCHAR(50),
  OPTION01 VARCHAR(50),
  OPTION02 VARCHAR(50),
  OPTION03 VARCHAR(50),
  OPTION04 VARCHAR(50),
  OPTION05 VARCHAR(50),
  OPTION06 VARCHAR(50),
  OPTION07 VARCHAR(50),
  OPTION08 VARCHAR(50),
  OPTION09 VARCHAR(50),
  OPTION10 VARCHAR(50),
  PRIMARY KEY (CODE_ID, CODE_VALUE, LANG)
);
COMMENT ON table CODE_NAME is 'コード名称';
COMMENT ON column CODE_NAME.CODE_ID is 'コードID';
COMMENT ON column CODE_NAME.CODE_VALUE is 'コード値';
COMMENT ON column CODE_NAME.LANG is '言語';
COMMENT ON column CODE_NAME.SORT_ORDER is 'ソート順';
COMMENT ON column CODE_NAME.CODE_NAME is '名称';
COMMENT ON column CODE_NAME.SHORT_NAME is '略称';
COMMENT ON column CODE_NAME.OPTION01 is 'オプション名称01';
COMMENT ON column CODE_NAME.OPTION02 is 'オプション名称02';
COMMENT ON column CODE_NAME.OPTION03 is 'オプション名称03';
COMMENT ON column CODE_NAME.OPTION04 is 'オプション名称04';
COMMENT ON column CODE_NAME.OPTION05 is 'オプション名称05';
COMMENT ON column CODE_NAME.OPTION06 is 'オプション名称06';
COMMENT ON column CODE_NAME.OPTION07 is 'オプション名称07';
COMMENT ON column CODE_NAME.OPTION08 is 'オプション名称08';
COMMENT ON column CODE_NAME.OPTION09 is 'オプション名称09';
COMMENT ON column CODE_NAME.OPTION10 is 'オプション名称10';

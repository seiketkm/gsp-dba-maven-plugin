CREATE TABLE REQUEST (
  REQUEST_ID VARCHAR(2000) NOT NULL,
  REQUEST_NAME VARCHAR(50) NOT NULL,
  SERVICE_AVAILABLE NUMERIC(1) NOT NULL,
  PRIMARY KEY (REQUEST_ID)
);
COMMENT ON table REQUEST is 'リクエスト';
COMMENT ON column REQUEST.REQUEST_ID is 'リクエストID';
COMMENT ON column REQUEST.REQUEST_NAME is 'リクエスト名';
COMMENT ON column REQUEST.SERVICE_AVAILABLE is 'サービス利用可否';

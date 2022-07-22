DROP PROCEDURE IF EXISTS PR_EDIT_TABLE_REMARK;
DELIMITER //
/**
	修改表备注
		V_TABLE_SCHEMA 数据库名
		V_TABLE_NAME 表名
		V_TABLE_REMARK 表备注
*/
CREATE PROCEDURE PR_EDIT_TABLE_REMARK(
	IN V_TABLE_SCHEMA VARCHAR(50),
	IN V_TABLE_NAME VARCHAR(40),
	IN V_TABLE_REMARK TEXT
)
BEGIN
	-- 声名变量
 	DECLARE V_COUNT INT;
  
    -- 查询表是否存在
 	SELECT COUNT(1) INTO V_COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = V_TABLE_NAME AND TABLE_SCHEMA = V_TABLE_SCHEMA;
	
	-- 表存在，修改表备注
	IF V_COUNT = 1 THEN
		SET @V_SQL = CONCAT(" ALTER TABLE ", V_TABLE_SCHEMA, ".", V_TABLE_NAME, " COMMENT '", V_TABLE_REMARK, "'");
		PREPARE sqlStmt FROM @V_SQL;
		EXECUTE sqlStmt;
    END IF;
END;
//
DELIMITER ;

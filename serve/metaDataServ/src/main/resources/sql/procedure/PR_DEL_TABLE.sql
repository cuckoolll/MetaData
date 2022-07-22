DROP PROCEDURE IF EXISTS PR_DEL_TABLE;
DELIMITER //
/**
	删除表，以备份方式删除表
		V_TABLE_SCHEMA 数据库名
		V_TABLE_NAME 表名
*/
CREATE PROCEDURE PR_DEL_TABLE(
	IN V_TABLE_SCHEMA VARCHAR(50),
	IN V_TABLE_NAME VARCHAR(40)
)
BEGIN
	-- 声名变量
 	DECLARE V_COUNT INT;
    DECLARE V_BAK_TABLE_NAME VARCHAR(50);
    DECLARE V_DATE_STR VARCHAR(8); 
    
    -- 生成备份表名TABLE_NAME_YYYYMMDD
    SELECT CONCAT(DATE_FORMAT(SYSDATE(), '%Y%m%d')) INTO V_DATE_STR;
    SET V_BAK_TABLE_NAME = CONCAT(V_TABLE_NAME, '_', V_DATE_STR);
    
    -- 查询表是否存在
 	SELECT COUNT(1) INTO V_COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = V_TABLE_NAME AND TABLE_SCHEMA = V_TABLE_SCHEMA;
	IF V_COUNT = 1 THEN
		-- 如果备份表已存在则删除备份表
		SET @V_SQL = CONCAT(" DROP TABLE IF EXISTS ", V_TABLE_SCHEMA, ".", V_BAK_TABLE_NAME);
		PREPARE sqlStmt FROM @V_SQL;
		EXECUTE sqlStmt;
	
		-- 存在则备份表
		SET @V_SQL = CONCAT(" ALTER TABLE ", V_TABLE_SCHEMA, ".", V_TABLE_NAME, " RENAME TO ", V_TABLE_SCHEMA, ".", V_BAK_TABLE_NAME);
        PREPARE sqlStmt FROM @V_SQL;
		EXECUTE sqlStmt;
    END IF;
END;
//
DELIMITER ;

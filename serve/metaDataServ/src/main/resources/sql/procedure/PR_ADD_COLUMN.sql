DROP PROCEDURE IF EXISTS PR_ADD_COLUMN;
DELIMITER //
/** 
	新增字段
		V_TABLE_NAME 表名
		V_COL_NAME 字段名
		V_COL_DATA_TYPE 数据类型
		V_COL_LENGTH 字段长度
		V_COL_ACCURACY 字段精度
		V_COL_DEFAULT 默认值
		V_COL_REMARK 备注
*/
CREATE PROCEDURE PR_ADD_COLUMN(
	IN V_TABLE_NAME VARCHAR(40),
    IN V_COL_NAME VARCHAR(30),
    IN V_COL_DATA_TYPE VARCHAR(20),
    IN V_COL_LENGTH BIGINT,
    IN V_COL_ACCURACY BIGINT,
	IN V_COL_DEFAULT TEXT,
	IN V_COL_REMARK TEXT
)
BEGIN
	-- 声名变量
 	DECLARE V_COUNT INT;
    DECLARE V_COL_FINAL_SCALE VARCHAR(20);
	DECLARE V_COL_DEFAULT_SQL TEXT;
  
    -- 查询表是否存在
 	SELECT COUNT(1) INTO V_COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = V_TABLE_NAME;
	IF V_COUNT = 1 THEN
		-- 查询字段列是否存在
		SELECT COUNT(1) INTO V_COUNT FROM INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_NAME = V_TABLE_NAME AND COLUMN_NAME = V_COL_NAME;
		-- 字段不存在则创建字段
		IF V_COUNT = 0 THEN
		    SET V_COL_FINAL_SCALE = '';
			IF V_COL_LENGTH IS NOT NULL THEN
				SET V_COL_FINAL_SCALE = CONCAT('(', V_COL_LENGTH);
				-- 如果字段为decimal类型时，组装长度和精度
				IF V_COL_DATA_TYPE = 'decimal' THEN
					SET V_COL_FINAL_SCALE = CONCAT(V_COL_FINAL_SCALE, ",", V_COL_ACCURACY);
				END IF;
				SET V_COL_FINAL_SCALE = CONCAT(V_COL_FINAL_SCALE, ')');
			END IF;
            
		    SET V_COL_DEFAULT_SQL = '';
		    -- 默认值为空时，不设置默认值
			IF V_COL_DEFAULT != '' AND V_COL_DEFAULT IS NOT NULL THEN
				IF V_COL_DATA_TYPE = 'varchar' OR V_COL_DATA_TYPE = 'text' OR V_COL_DATA_TYPE = 'longtext' THEN
					SET V_COL_DEFAULT_SQL = CONCAT("DEFAULT '", V_COL_DEFAULT, "'");
				ELSE
					SET V_COL_DEFAULT_SQL = CONCAT("DEFAULT ", V_COL_DEFAULT);
                END IF;
			END IF;
		   
			SET @V_SQL = CONCAT(" ALTER TABLE ", V_TABLE_NAME, " ADD COLUMN ", V_COL_NAME, " ", V_COL_DATA_TYPE, V_COL_FINAL_SCALE, " ", V_COL_DEFAULT_SQL, " COMMENT '", V_COL_REMARK, "'");
            PREPARE sqlStmt FROM @V_SQL;
			EXECUTE sqlStmt;
        END IF;    
    END IF;
END;
//
DELIMITER ;

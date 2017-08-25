package com.zhaoyao.zhaoyaoba.jdbc;

public interface JDBCinterface {
	public final String SELECT = " SELECT ";
	public final String INSERT_INTO = " INSERT INTO ";
	/**UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值**/
	public final String UPDATE = " UPDATE ";
	public final String WHERE = " WHERE ";
	public final String AND = " AND ";
	public final String SET = " SET ";
	/**DELETE FROM 表名称 WHERE 列名称 = 值**/
	public final String DELETE = " DELETE ";
	/**分页查  SELECT * FROM 表名称 LIMIT 从第几条开始,总共好多条*/
	public final String LIMIT = " LIMIT ";
	
	/**包含 数据的的行   FROM 表名称 WHERE ID LIKE '%数据%'  */
	public final String LIKE = " LIKE ";
	
	/**不包含 数据的的行   FROM 表名称 WHERE ID  NOT LIKE '%数据%'  */
	public final String NOT_LIKE = " NOT LIKE ";
	
	/**SELECT * FROM 表名称 WHERE 字段名 IN ('Adams','Carter')*/
	public final String IN = " IN ";
	
	public final String UNION = " UNION ";
	
	public final String DISTINCT = " DISTINCT ";
	public final String FROM = " FROM ";
	public final String ORDER_BY  = " ORDER BY ";
	public final String DESC  = " DESC ";
	public final String ASC  = " ASC ";
	/**逗号  " , "*/
	public final String COMMA  = " , ";
	/**空格		" "  */
	public final String SPACE  = " ";
	/**星号键  " * "*/
	public final String STAR_KEY  = " * ";
	
	/***单引号	" ' "*/
	public final String SINGLE_MARK  = " ' ";
	/***双 引号	" \" "*/
	public final String DOUBLE_MARKS  = " \" ";
	
	/**左小括号 " ( "*/
	public final String LEFT_BRACKET  = " ( ";
	/**右小括号	" ) "*/
	public final String RIGHT_BRACKET  = " ) ";
	/**		" { " **/
	public final String LEFT_LARGE_BRACKET  = " { ";
	/**		" } " **/
	public final String RIGHT_LARGE_BRACKET  = " } ";
}

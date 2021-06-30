package com.zhaoyao.zhaoyaoba.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.DatabaseMetaData;

/***
 * mysql 工具类
 * @author lw
 * 编写时间: 2016年8月15日下午4:39:47
 * 类说明 :
 * <li>mysql-connector-java-5.1.7-bin.jar
 * <li>
 * {@linkplain  使用前初始化}
 * <li>
 * {@link #intance(String, String, String)}
 */
public class JDBC implements JDBCinterface {

    private JDBC(String url, String userName, String passWord) {
        URL = url;
        username = userName;
        password = passWord;
    }

    private static JDBC jdbc;

    public static JDBC intance(String URL, String username, String password) {
        if (jdbc == null) jdbc = new JDBC(URL, username, password);
        return jdbc;
    }

    /***
     *
     * @param ip   默认：localhost
     * @param port    默认：3306
     * @param databaseName  默认：test   必填
     * @return jdbc:mysql://localhost:3306/test
     */
    public static String createURL(String ip, String port, String databaseName) {
        return "jdbc:mysql://" + (ip == null || ip.equals("") ? "localhost" : ip) + ":" + (port == null || port.equals("") ? "3306" : port) + "/" + (databaseName == null || databaseName.equals("") ? "test" : databaseName);
    }

    public static String jdbc_url = "jdbc:mysql://";
    // 连接MySql数据库，用户名和密码都是root
    private static String URL = "jdbc:mysql://localhost:3306/zyb";
    //		private static String URL = "jdbc:mysql://localhost:3306/test";
//		static String url = "jdbc:mysql://localhost:3306/miaomiao";
    private static String username = "root";
    private static String password = "liuwei";

    /**
     * 驱动是否注册成功
     */
    public static boolean jdbcDriver = false;

    public static Connection conn;

    /**注册驱动  */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            jdbcDriver = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接MySql数据库，用户名和密码都是root
     *
     * @param static String url = "jdbc:mysql://localhost:3306/miaomiao";
     * @param static String username = "root";
     * @param static String password = "liuwei";
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String... URL_username_password) throws SQLException {
        if (conn != null) return conn;
        if (URL_username_password.length == 3) {
            return conn = DriverManager.getConnection(URL_username_password[0], URL_username_password[1], URL_username_password[2]);
        } else if (URL_username_password.length == 2) {
            return conn = DriverManager.getConnection(URL_username_password[0], URL_username_password[1], password);
        } else if (URL_username_password.length == 1) {
            return conn = DriverManager.getConnection(URL_username_password[0], username, password);
        }
        return conn = DriverManager.getConnection(URL, username, password);
    }

    public static Statement createStatement(Connection con) throws SQLException {
        return con.createStatement();
    }

    public static Statement createStatement() throws SQLException {
        return getConnection(URL, username, password).createStatement();
    }

    public static DatabaseMetaData getDatabaseMetaData(Connection conn) throws SQLException {
        return (DatabaseMetaData) conn.getMetaData();
    }

    public synchronized static ResultSetMetaData getMetaData(ResultSet rs) throws SQLException {
        return rs.getMetaData();
    }

    public static ResultSet getResultSetByQuery(Statement stmt, String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }

    public static Statement getStatement(Connection con) throws SQLException {
        return con.createStatement();
    }

    public synchronized static <T> String createTableSql(Class<T> cls) throws InstantiationException, IllegalAccessException {
        String sql = "";
        Object newInstance = cls.newInstance();
        Class<? extends Object> clazz = newInstance.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String typeName = field.getType().getName();
            if (typeName.equals(String.class.getName().toString())) {
                typeName = "VARCHAR" + "(" + 20 + ")";
            } else if (typeName.equals(Integer.class.getName().toString())) {
                typeName = "int";
            } else if (typeName.equals(Date.class.getName().toString())
                    || typeName.equals(java.util.Date.class.getName().toString())) {
                typeName = "DATE";
            } else if (typeName.equals(Float.class.getName().toString())) {
                typeName = "FLOAT";
            }
            if (field.isAnnotationPresent(InjectTable.class)) {
                InjectTable annotation = field.getAnnotation(InjectTable.class);
                if (typeName.equals(String.class.getName().toString())) {
                    int length = annotation.length();
                    typeName = "VARCHAR" + "(" + (length < 0 ? length = 20 : length) + ")";
                }
                if (!annotation.primarykey()) {
                    String columnName = annotation.ColumnName();
                    if (columnName != null && !columnName.equals("")) {
                        sql += " " + columnName + " " + typeName + ",";
                    } else {
                        String name = field.getName();
                        sql += " " + name + " " + typeName + ",";
                    }
                } else {
                    String columnName = annotation.ColumnName();
                    if (columnName != null && !columnName.equals("")) {
                        sql += " " + columnName + " " + typeName + " AUTO_INCREMENT PRIMARY  key,";
                    } else {
                        String name = field.getName();
                        sql += " " + name + " " + typeName + " AUTO_INCREMENT PRIMARY  key,";
                    }
                }
            } else {
                String name = field.getName();
                sql += " " + name + " " + typeName + ",";
            }
        }
        int lastIndexOf = sql.lastIndexOf(",");
        sql = sql.substring(0, lastIndexOf);
        sql = "(" + sql + ")";
        boolean annotationPresent = clazz.isAnnotationPresent(InjectTable.class);
        if (annotationPresent) {
            InjectTable annotation = clazz.getAnnotation(InjectTable.class);
            String tableName = annotation.tableName();
            sql = "create  table " + tableName + " " + sql;
        }
        return sql;
    }

    public static boolean createTable(String sql) throws SQLException {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection(URL, username, password);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
        }
        return false;
    }

    /***
     *
     * @param <T>
     * @param tableName  gubiao
     * @param conditionColumnName   ID   就是条件字段 名字
     * @param arg   只能是String[] 或 int[]  即 -->(4,3)  或 ('4','3')
     * <li> 最后形成     SELECT * FROM gubiao WHERE ID IN (4,3)
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public synchronized static <T extends Object> List<T> query(
            Class<T> clszz, String tableName, String conditionColumnName,
            Object[] arg) throws InstantiationException, IllegalAccessException, SQLException {
        return query(clszz, tableName, null, conditionColumnName, arg);
    }

    /***
     *
     * @param tableName  表名    gubiao
     * @param queryColumnName    如果为 null  那就是全部字段  --> *
     * @param conditionColumnName   ID   就是条件字段 名字
     * @param arg   只能是String[] 或 Integer[]  即 -->('4','3') 或  (4,3)
     * <li> 最后形成     SELECT * FROM gubiao WHERE ID IN (4,3)
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public synchronized static <T extends Object> List<T> query(
            Class<T> clszz,
            String tableName,
            String[] queryColumnName,
            String conditionColumnName,
            Object[] arg) throws SQLException, InstantiationException, IllegalAccessException {
        String sql = createQuerySql(tableName, queryColumnName, conditionColumnName, arg);
        return query(clszz, sql);
    }

    public synchronized static <T extends Object> List<T> query(Class<T> clszz, String sql) throws SQLException, InstantiationException, IllegalAccessException {
        Statement statement = createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<T> list = new ArrayList<T>();
        Field[] fields = clszz.newInstance().getClass().getDeclaredFields();
        Map<Object, Integer> fieldsMap = new HashMap<Object, Integer>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (Modifier.isFinal(fields[i].getModifiers())) {
                continue;
            }
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            if ("serialVersionUID".equals(fields[i].getName())) {
                continue;
            }
            if (field.isAnnotationPresent(InjectTable.class)) {
                InjectTable annotation = field.getAnnotation(InjectTable.class);
                if (null != annotation.ColumnName() && !"".equals(annotation.ColumnName())) {
                    fieldsMap.put(annotation.ColumnName(), i);
                    continue;
                }
            }
            fieldsMap.put(fields[i].getName(), i);
        }
        while (resultSet.next()) {
            T newInstance = clszz.newInstance();
            Field[] declaredFields = newInstance.getClass().getDeclaredFields();
            for (int i = 1; i <= columnCount; i++) {
                int columnType = metaData.getColumnType(i);
                String columnName = metaData.getColumnName(i);
                if (!fieldsMap.containsKey(columnName)) continue;
                Field field = declaredFields[fieldsMap.get(columnName)];
                field.setAccessible(true);
                if (columnType == 12) {//VARCHAR:
                    field.set(newInstance, resultSet.getString(i));
                } else if (columnType == 4) {//INT:MEDIUMINT
                    if ("int".equals(field.getType().getName()) || "java.lang.Integer".equals(field.getType().getName()))
                        field.set(newInstance, resultSet.getInt(i));
                    continue;
                } else if (columnType == 8) {//DOUBLE
                    field.set(newInstance, resultSet.getDouble(i));
                } else if (columnType == 1) {//CHAR:
                    Reader reader = resultSet.getCharacterStream(i);
                    if (reader != null) {
                        try {
                            BufferedReader br = new BufferedReader(reader);
                            String ch = "";
                            String data = "";
                            while ((ch = br.readLine()) != null) {
                                data += ch;
                            }
                            char[] charArray = data.toCharArray();
                            if (charArray.length > 0) {
                                field.set(newInstance, charArray[0]);
                            } else field.set(newInstance, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                } else if (columnType == 7) {//FLOAT
                    if ("java.lang.Float".equals(field.getType().getName()))
                        field.set(newInstance, resultSet.getFloat(i));
                    continue;
                } else if (columnType == 91) {//DATE://YEAR
                    if ("java.sql.Date".equals(field.getType().getName()) || "java.util.Date".equals(field.getType().getName()))
                        field.set(newInstance, resultSet.getDate(i));
                    continue;
                } else if (columnType == 92) {//TIME
                    field.set(newInstance, resultSet.getTime(i));
                    continue;
                }
            }
            list.add(newInstance);
        }
        return list;
    }

    public synchronized static <T extends Object> List<Object> query(
            String[] resultFieldName,
            String tableName,
            String[] queryColumnName,
            String conditionColumnName,
            Object[] arg) throws SQLException, InstantiationException, IllegalAccessException {
        Statement statement = createStatement();
        String sql = createQuerySql(tableName, queryColumnName, conditionColumnName,
                arg);
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Object> list = new ArrayList<Object>();
        final Map<Object, Object> resultFieldNameMap = new HashMap<Object, Object>();
        for (int i = 0; i < resultFieldName.length; i++) {
            resultFieldNameMap.put(resultFieldName[i], resultFieldName[i]);
        }
        while (resultSet.next()) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            for (int i = 1; i <= columnCount; i++) {
//				String columnTypeName = metaData.getColumnTypeName(i);
                int columnType = metaData.getColumnType(i);
                String columnName = metaData.getColumnName(i);
                if (!resultFieldNameMap.containsKey(columnName)) continue;
                if (columnType == 12) {//VARCHAR:
                    map.put(resultFieldNameMap.get(columnName), resultSet.getString(i));
                } else if (columnType == 4) {//INT:MEDIUMINT
                    map.put(resultFieldNameMap.get(columnName), resultSet.getInt(i));
                } else if (columnType == 8) {//DOUBLE
                    map.put(resultFieldNameMap.get(columnName), resultSet.getDouble(i));
                } else if (columnType == 1) {//CHAR:
                    Reader reader = resultSet.getCharacterStream(i);
                    if (reader != null) {
                        try {
                            BufferedReader br = new BufferedReader(reader);
                            String ch = "";
                            String data = "";
                            while ((ch = br.readLine()) != null) {
                                data += ch;
                            }
                            char[] charArray = data.toCharArray();
                            if (charArray.length > 0) {
                                map.put(resultFieldNameMap.get(columnName), charArray[0]);
                            } else map.put(resultFieldNameMap.get(columnName), null);
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                } else if (columnType == 7) {//FLOAT
                    map.put(resultFieldNameMap.get(columnName), resultSet.getFloat(i));
                } else if (columnType == 91) {//DATE://YEAR
                    map.put(resultFieldNameMap.get(columnName), resultSet.getDate(i));
                } else if (columnType == 92) {//TIME
                    map.put(resultFieldNameMap.get(columnName), resultSet.getTime(i));
                } else {
                    continue;
                }
            }
            list.add(map);
        }
        return list;
    }

    private static String createQuerySql(String tableName, String[] queryColumnName,
                                         String conditionColumnName, Object[] arg) {
        String sql = "";
        if (queryColumnName == null || queryColumnName.length == 0) {
            sql = SELECT + STAR_KEY + FROM + tableName + SPACE;
        } else {
            sql = SELECT;
            for (int i = 0; i < queryColumnName.length; i++) {
                sql += queryColumnName[i] + COMMA;
            }
            sql = sql.substring(0, sql.lastIndexOf(COMMA)) + SPACE + FROM + tableName + SPACE;
        }
        if (arg != null && arg.length != 0 && conditionColumnName != null && !"".equals(conditionColumnName)) {
            sql += WHERE + SPACE + conditionColumnName + SPACE + IN + LEFT_BRACKET;
            if (arg[0] instanceof String)
                for (int i = 0; i < arg.length; i++) {
                    sql += arg[i] + COMMA;
                }
            if (arg[0] instanceof Integer)
                for (int i = 0; i < arg.length; i++) {
                    sql += SINGLE_MARK + arg[i] + SINGLE_MARK + COMMA;
                }
            sql = sql.substring(0, sql.lastIndexOf(COMMA)) + RIGHT_BRACKET;
        }
        return sql;
    }

    public static boolean DeleteManyDataById(String tableName, String columnName, int... id) throws SQLException {
        Statement statement = createStatement();
        return statement.execute(createDeleteManyDataByIdSql(tableName, columnName, id));
    }

    /***
     * 创建删除多条数据的sql语句
     * @param tableName   表名
     * @param columnName  字段的名字
     * @param id  字段的名字的值
     * @return SELECT * FROM 表名称 WHERE 字段名 IN ('Adams','Carter')
     */
    public synchronized static String createDeleteManyDataByIdSql(String tableName, String columnName, int... id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " ";
        String s = "";
        for (int i = 0; i < id.length; i++) {
            s += id[i] + ",";
        }
        if (s.contains(","))
            s = s.substring(0, s.lastIndexOf(","));
        sql += " IN(" + s + ")";
        return sql;
    }

    /***
     * 删除表里面的全部数据
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static boolean deleteTableData(String tableName) throws SQLException {
        Statement statement = createStatement(getConnection(URL, username, password));
        String sql = "DELETE FROM " + tableName;//sql = "DELETE * FROM "+tableName;
        return statement.execute(sql);
    }

    /***
     * 删除指定的表
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static boolean deleteTable(String tableName) throws SQLException {
        Statement statement = createStatement(getConnection(URL, username, password));
        String sql = "DROP TABLE " + tableName;
        return statement.execute(sql);
    }

    public synchronized static <T> boolean createTable(Class<T> cls) throws InstantiationException, IllegalAccessException {
        String sql = createTableSql(cls);
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getConnection(URL, username, password);
            stmt = conn.createStatement();
            int end = sql.indexOf("(");
            int start = sql.indexOf("table");
            String table = sql.substring(start, end).replace("table", "").trim();
            boolean tableIsExist = tableIsExist(stmt, table);
            if (tableIsExist) {

//	      		stmt.executeQuery("");
            } else {
                stmt.executeUpdate(sql);
            }
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
        }
        return false;
    }

    private static boolean tableIsExist(Statement stmt, String table) throws SQLException {
        String sqlString = "show tables like '" + table + "'";
        int execute = stmt.executeUpdate(sqlString);
        return execute == 0 ? false : true;
    }

    public static int insert(String sql) throws SQLException {
        Statement statement = createStatement();
        int executeUpdate = statement.executeUpdate(sql);
        return executeUpdate;
    }

    /***
     * 创建批量插入数据  sql
     * @param tableName  表名字  gubiao
     * @param columnName  new String[]{"low" ,"high"}
     * @param columnContent  new String[]{里面填写  columnName  对应的值};
     * @return insert into gubiao(low ,high) select 1,2 UNION select 1,3
     */
    public static String createInsertSql(final String tableName, final String[] columnName, final List<Object[]> columnContent) {
        String sql = INSERT_INTO + tableName;
        if (columnName == null || columnName.length == 0)
            throw new NullPointerException("columnName  null  || columnName.length == 0");
        String columnNames = "";
        for (int i = 0; i < columnName.length; i++) {
            columnNames += columnName[i] + COMMA;
        }
        columnNames = LEFT_BRACKET + columnNames.substring(0, columnNames.lastIndexOf(",")) + RIGHT_BRACKET;
        sql += columnNames;
        if (columnContent == null || columnContent.size() == 0)
            throw new NullPointerException("columnContent  null  || columnContent.size == 0");
        String columnContents = "__";
        for (int i = 0; i < columnContent.size(); i++) {
            Object[] array = columnContent.get(i);
            columnContents += UNION + SELECT;
            String contents = "";
            for (int j = 0; j < array.length; j++) {
                contents += " " + SINGLE_MARK.trim() + array[j] + SINGLE_MARK.trim() + COMMA;
            }
            contents = contents.substring(0, contents.lastIndexOf(","));
            columnContents += contents;
        }
        columnContents = columnContents.replace("__" + UNION, "");
        sql += columnContents;
        return sql;
    }

    public void update() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, username, password);
        //查询数据库并把数据表的内容输出到屏幕上
        Statement s = conn.createStatement();
        String sql = "UPDATE  gubiao  set money=? where  zeh =?";
        s.executeUpdate(sql);
        ResultSet rs = s.executeQuery("select * from  gubiao");
        while (rs.next()) {
            System.out.println(rs.getString("") + "\t" + rs.getString("") + "\t" + rs.getString("") + "\t" + rs.getString("") + "\t" + rs.getString("") + "\t" + rs.getString("") + "\t" + rs.getString("") + "\t" + rs.getString(""));
            //输出该行数据  
        }

        s.close();
        conn.close();
    }

    public static String createQuerySpecifiedColumn(final String table_name, final String... column_name) {
        if (column_name == null || column_name.length == 0) return "SELECT * FROM " + table_name;
        String sql = SELECT;
        for (int i = 0; i < column_name.length; i++) {
            sql += column_name[i] + COMMA;
        }
        return sql.substring(0, sql.lastIndexOf(",")) + FROM + table_name;
    }

    /***
     * 创建查询指定列的数据
     * @param table_name  表名字
     * @param query_column_name  要查询的字段名字  ，为  null 是查询全部字段
     * @param conditionName   条件字段名字      如果为 null  参数：condition 也应该为null  就是无条件查询
     * @param condition        条件  有几个 conditionName  就要几个condition
     * <li>{@inheritDoc String columnBy = JDBC.createQuerySpecifiedColumnBy("table_name", null, new String[]{"code","id"}, new Object[]{3,"5"},new Object[]{4,"6"});}
     * @return SELECT  *  FROM table_name WHERE code IN  ( '3' , '5' )  AND id IN  ( '4' , '6' )
     */
    public static String createQuerySpecifiedColumnBy(final String table_name, final String[] query_column_name, final String[] conditionName, final Object[]... condition) {
        String sql = SELECT;
        if (query_column_name == null || query_column_name.length == 0) {
            sql = SELECT + STAR_KEY + FROM + table_name;
        } else {
            if ("".equals(query_column_name[0]))
                throw new NullPointerException("query_column_name  不能填入空字符串   如果不填就填入  null");
            for (int i = 0; i < query_column_name.length; i++) {
                sql += query_column_name[i] + COMMA;
            }
            sql += table_name;
        }
        String s = WHERE + "__";
        if (condition.length != conditionName.length)
            throw new IndexOutOfBoundsException("condition.length != conditionName.length   两者是对应的 必须相等");
        for (int i = 0; i < conditionName.length; i++) {
            s += AND + conditionName[i] + IN + LEFT_BRACKET;
            Object[] objects = condition[i];
            for (int j = 0; j < objects.length; j++) {
                s += SINGLE_MARK.trim() + objects[j] + SINGLE_MARK.trim() + COMMA;
            }
            s = s.substring(0, s.lastIndexOf(COMMA)) + RIGHT_BRACKET;
        }
        return sql += s.replace("__" + AND, "");
    }

    /***
     *
     * @param table_name  表名字 testTable
     * @param column_name    字段名字  id
     * @param column_content    字段名字 对应的值   id ---->对应的值
     * <li>JDBC.createDeleteSql("testTable", "id", 1,2,3,4,5,6)
     * @return DELETE FROM testTable WHERE id IN(1,2,3,4,5,6);
     */
    public static String createDeleteSql(String table_name, String column_name, Object[] column_content) {
        String sql = " DELETE " + " FROM " + table_name + " WHERE " + column_name + " IN " + "(";
        for (int i = 0; i < column_content.length; i++) {
            sql += "'" + column_content[i] + "',";
        }
        return sql.substring(0, sql.lastIndexOf(",")) + ")";
    }

    /***
     *
     * @param tableName
     * @param columnName
     * @param columnValue
     * @param whereColumnName
     * @param whereColumnValue
     * @return UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
     */
    @SuppressWarnings("null")
    public static String createUpdateSql(String tableName, String[] columnName, Object[] columnValue, String[] whereColumnName, Object[] whereColumnValue) {
        String sql = "UPDATE " + tableName + " SET ";
        if (columnName != null ? columnValue != null : columnName.length == columnValue.length ? columnName.length > 0 : false)
            for (int i = 0; i < columnName.length; i++) {
                sql += columnName[i] + " = '" + columnValue[i] + "'" + ",";
            }
        sql = sql.substring(0, sql.lastIndexOf(","));
        if (whereColumnName != null && whereColumnName.length != 0) {
            String ws = " WHERE ";
            for (int i = 0; i < whereColumnName.length; i++) {
                ws += whereColumnName[i] + " = '" + whereColumnValue[i] + "' AND ";
            }
            sql += ws.substring(0, ws.lastIndexOf("AND"));
        }
        return sql;
    }

    public static ResultSet querySpecifiedColumn(final String sql) throws SQLException {
        Statement createStatement = createStatement();
        ResultSet executeQuery = createStatement.executeQuery(sql);
        return executeQuery;
    }

    public static String createDistinctSql(String table_name, String column_name) {
        String sql = "";
        sql = SELECT + DISTINCT + column_name + FROM + table_name;
        return sql;
    }

    /***
     * 创建分页查询的  sql
     * @param tableName        表名字
     * {@link #createLimitSql(String, String[], Integer, Integer)}
     * @param start        开始数目
     * @param end  每页好多条
     * <li>分页查询   每页五条 (0,5] (5,5]  (10,5],(15,5]
     * <li>{@inheritDoc String createLIMIT = JDBC.createLIMIT("table_name", null, 5, 5);}
     * @return SELECT  *  FROM table_name LIMIT 5 , 5
     */
    public static String createLimitSql(final String tableName, final Integer start, final Integer end) {
        return createLimitSql(tableName, null, start, end);
    }

    /***
     * 创建分页查询的  sql
     * @param tableName        表名字
     * @param query_column_name  要查询的字段名字  ，为  null 是查询全部字段
     * @param start        开始数目
     * @param end  每页好多条
     * <li>分页查询   每页五条 (0,5] (5,5]  (10,5],(15,5]
     * <li>{@inheritDoc String createLIMIT = JDBC.createLIMIT("table_name", null, 5, 5);}
     * @return SELECT  *  FROM table_name LIMIT 5 , 5
     */
    public static String createLimitSql(final String tableName, final String[] query_column_name, final Integer start, final Integer end) {
        String sql = SELECT;
        if (query_column_name == null || query_column_name.length == 0) {
            sql += STAR_KEY + FROM + tableName + LIMIT + start + COMMA + end;
        } else {
            for (int i = 0; i < query_column_name.length; i++) {
                sql += query_column_name[i] + COMMA;
            }
            sql = sql.substring(0, sql.lastIndexOf(COMMA)) + FROM + tableName + LIMIT + start + COMMA + end;
        }
        return sql;
    }

    /***
     *
     * @param tableName
     * @param JOIN_right_name
     * @param ON_condition
     * @return
     */
    public static String createJoinSql(final String tableName, String[] JOIN_right_name, String[]... ON_condition) {
        String sql = SELECT + "*" + FROM + tableName;
        for (int i = 0; i < JOIN_right_name.length; i++) {
            sql += " INNER JOIN " + JOIN_right_name[i] + " ON " + ON_condition[i][0] + " = " + ON_condition[i][1];
        }
        return sql;
    }

    public static String createJoinSql(final String tableName, String[] JOIN_right_name, String[] where_condition_name, String[] where_condition_content, String[]... ON_condition_name) {
        String sql = SELECT + "*" + FROM + tableName;
        for (int i = 0; i < JOIN_right_name.length; i++) {
            sql += " INNER JOIN " + JOIN_right_name[i] + " ON " + ON_condition_name[i][0] + " = " + ON_condition_name[i][1] + " ";
        }
        if (where_condition_name != null && where_condition_name.length != 0) {
            sql += WHERE;
            for (int i = 0; i < where_condition_name.length; i++) {
                String name = where_condition_name[i];
                String content = where_condition_content[i];
                sql += name + " = " + SINGLE_MARK + content + SINGLE_MARK + AND;
            }
            sql = sql.substring(0, sql.lastIndexOf(AND));
        }
        return sql;
    }

    public static String createSelectIntoSql(String column_name, String new_table_name, String old_tablename) {
        String sql = SELECT + column_name + " INTO " + new_table_name + " [IN externaldatabase] " + FROM + old_tablename;
        return sql;
    }

    /***
     * 查询某一列不出现重复的数据
     * @param table_name
     * @param column_name
     * @return
     * @throws SQLException
     */
    public static ResultSet columnQuery_DISTINCT(String sql) throws SQLException {
        Statement createStatement = createStatement();
        return createStatement.executeQuery(sql);
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

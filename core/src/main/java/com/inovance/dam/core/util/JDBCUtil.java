package com.inovance.dam.core.util;

import org.apache.commons.collections4.CollectionUtils;
import org.osgi.service.component.annotations.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.*;


@Component
public class JDBCUtil {

    //获取数据库连接
    public static Connection getConnection(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //关闭流   增删改
    public static void close(Connection connection, PreparedStatement ps) {
        if (connection != null) {
            //alt+enter
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            //alt+enter
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭流   增删改
    public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        if (connection != null) {
            //alt+enter
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            //alt+enter
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (rs != null) {
            //alt+enter
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //增删改通用方法
    public static boolean update(Connection connection, String sql, Object... params) {
        //获取连接
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            //设置参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int result = ps.executeUpdate();

            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //通用的查询方法   单表不带条件的,带条件的
    public static <T> List<T> queryByCondition(DataSource dataSource, Class<T> clazz, String sql, List<Object> params) {
        Connection conn = getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            //设置参数
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            rs = ps.executeQuery();
            //需要知道查询结果集的列名
            ResultSetMetaData metaData = rs.getMetaData();
            //用于存储每次查询列的结果集
            Map<String, Object> data = new HashMap<>();
            //遍历查询的结果集
            while (rs.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String name = metaData.getColumnName(i + 1).toLowerCase();
                    Object value = rs.getObject(name);
                    data.put(name, value);
                }

                //将Map中的数据放入到对象中
                T t = JsonUtil.mapToBean(data, clazz);
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          close(conn, ps, rs);
        }
        return list;
    }

    //根据主键查询
    //Serializable:可以序列化 可以通过网络传输对象
    public static <T> T queryByPrimaryKey(DataSource dataSource, Class<T> clazz, String sql, Serializable primaryKey) {
        Connection connection = getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        T t = null;
        try {
            //根据字节码得到该对象的实例
            //要求:原类的构造方法必须是public
//            t = clazz.newInstance();
            //如果构造方法私有化
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            t = constructor.newInstance();
            //  System.out.println("t======"+t);
            ps = connection.prepareStatement(sql);
            ps.setObject(1, primaryKey);
            rs = ps.executeQuery();
            //元数据
            ResultSetMetaData metaData = rs.getMetaData();


            if (!rs.next())
                return null;
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i + 1).toLowerCase();
                Object value = rs.getObject(columnName);
//                BeanUtils.setProperty(t, columnName, value);
                // 将value放入 实体类中
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("t="+t);
        //t是通过反射
        return t;
    }

    //查询总记录数(包含条件) 为分页服务的 limit a,b
    //a = (page - 1) * b
    public static int count(DataSource dataSource, String sql, Object... params) {
        Connection connection = getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
    多表带条件和不带条件的查询结果集封装
    该查询结果集全部封装到map中，和实体类没有关系
     */
    public static List<Map<String, Object>> queryByCondition(DataSource dataSource, String sql, Object... params) {
        Connection connection = getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            //设置参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            rs = ps.executeQuery();
            //需要知道查询结果集的列名
            ResultSetMetaData metaData = rs.getMetaData();

            //遍历查询的结果集
            while (rs.next()) {
                //用于存储每次查询列的结果集
                Map<String, Object> data = new HashMap<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String name = metaData.getColumnName(i + 1).toLowerCase();
                    Object value = rs.getObject(name);
                    data.put(name, value);
                }
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //分页多条件复杂查询结果集封装，适合单表和多表查询
    //将所有查询结果集都放到一个List<Map<>>
    public static List<Map<String, Object>> pagination(DataSource dataSource, String page, int pageSize, String sql, Object... params) {
        int pageNum = 0;
        if (page == null) {
            pageNum = 1;
        } else {
            pageNum = Integer.parseInt(page);
        }
        sql += " limit " + (pageNum - 1) * pageSize + "," + pageSize + "";
        return queryByCondition(dataSource, sql, params);
    }

}


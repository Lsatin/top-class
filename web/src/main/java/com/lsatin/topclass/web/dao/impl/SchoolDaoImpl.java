package com.lsatin.topclass.web.dao.impl;

import com.lsatin.topclass.web.dao.SchoolDao;
import com.lsatin.topclass.web.dao.utils.JdbcUtil;
import com.lsatin.topclass.web.pojo.School;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

public class SchoolDaoImpl implements SchoolDao {

    @Override
    public School selectByPrimaryKey(String id) {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        String sql = "select " + SQL_COLUMN_PREFIX + ", " + SQL_COLUMN_SUFFIX + " from top_class.school where id = ?";
        return jdbcUtil.executeQuery(School.class, sql, id);
    }

    @Override
    public List<School> select() {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        String sql = "select " + SQL_COLUMN_PREFIX + ", " + SQL_COLUMN_SUFFIX + " from top_class.school";
        return (List<School>) jdbcUtil.executeQuery(School.class, sql);
    }

    @Override
    public List<School> select(School school) {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        StringBuilder sql =  new StringBuilder("select " + SQL_COLUMN_PREFIX + ", " + SQL_COLUMN_SUFFIX + " from top_class.school ");
        if (StringUtils.isNotEmpty(school.getName())) {
            sql.append("where `name` like ? ");
        }
        if (StringUtils.isNotEmpty(school.getAddress())) {
            if (StringUtils.isNotEmpty(school.getName())) {
                sql.append("or `address` like ? ");
            } else {
                sql.append("where `address` like ? ");
            }
        }
        if (StringUtils.isNotEmpty(school.getZipCode())) {
            if (StringUtils.isNotEmpty(school.getName()) || StringUtils.isNotEmpty(school.getAddress())) {
                sql.append("or zip_code like ? ");
            } else {
                sql.append("where zip_code like ? ");
            }
        }
        if (StringUtils.isNotEmpty(school.getZipCode()) && StringUtils.isNotEmpty(school.getAddress()) && StringUtils.isNotEmpty(school.getName())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getName(), school.getAddress(), school.getZipCode());
        }

        if (StringUtils.isNotEmpty(school.getZipCode()) && StringUtils.isNotEmpty(school.getName())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getName(), school.getZipCode());
        }
        if (StringUtils.isNotEmpty(school.getZipCode()) && StringUtils.isNotEmpty(school.getAddress())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getAddress(), school.getZipCode());
        }
        if (StringUtils.isNotEmpty(school.getAddress()) && StringUtils.isNotEmpty(school.getName())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getName(), school.getAddress());
        }

        if (StringUtils.isNotEmpty(school.getName())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getName());
        }
        if (StringUtils.isNotEmpty(school.getAddress())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getAddress());
        }
        if (StringUtils.isNotEmpty(school.getZipCode())) {
            return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString(), school.getZipCode());
        }
        return (List<School>) jdbcUtil.executeFuzzyQuery(School.class, sql.toString());
    }

    @Override
    public int updateByPrimaryKey(School school) {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        StringBuilder sql = new StringBuilder("update top_class.school set");
        if (StringUtils.isNotEmpty(school.getName())) {
            sql.append(" name = ?");
        }
        if (StringUtils.isNotEmpty(school.getAddress())) {
            if (StringUtils.isNotEmpty(school.getName())) {
                sql.append(SQL_SEPARATOR);
            }
            sql.append(" address = ?");
        }
        if (StringUtils.isNotEmpty(school.getZipCode())) {
            if (StringUtils.isNotEmpty(school.getName()) || StringUtils.isNotEmpty(school.getAddress())) {
                sql.append(SQL_SEPARATOR);
            }
            sql.append(" zip_code = ?");
        }
        sql.append(" where id = ?");
        return jdbcUtil.executeUpdate(sql.toString(), school.getName(), school.getAddress(), school.getZipCode(), school.getId());
    }

    @Override
    public void update(List<School> list) {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        for (School school : list) {
            StringBuilder sql = new StringBuilder("update top_class.school set");
            if (StringUtils.isNotEmpty(school.getName())) {
                sql.append(" name = ");
                sql.append(school.getName());
            }
            if (StringUtils.isNotEmpty(school.getAddress())) {
                if (StringUtils.isNotEmpty(school.getName())) {
                    sql.append(SQL_SEPARATOR);
                }
                sql.append(" address = ");
                sql.append(school.getAddress());
            }
            if (StringUtils.isNotEmpty(school.getZipCode())) {
                if (StringUtils.isNotEmpty(school.getName()) || StringUtils.isNotEmpty(school.getAddress())) {
                    sql.append(SQL_SEPARATOR);
                }
                sql.append(" zip_code = ?");
                sql.append(school.getZipCode());
            }
            sql.append(" where id = ");
            sql.append(school.getId());
            jdbcUtil.executeUpdate(sql.toString());
        }
    }

    @Override
    public int insert(School school) {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        StringBuilder sql = new StringBuilder("insert into top_class.school(create_date, creator, modify_date, modifier");
        if (StringUtils.isNotEmpty(school.getName())) {
            sql.append(", `name`");
        }
        if (StringUtils.isNotEmpty(school.getAddress())) {
            sql.append(", `address`");
        }
        if (StringUtils.isNotEmpty(school.getZipCode())) {
            sql.append(", zip_code");
        }
        sql.append(") values(");
        String[] strs = sql.toString().split(SQL_SEPARATOR);
        for (int i = 0, l = strs.length; i < l; i++) {
            if (i > 0)
                sql.append(SQL_SEPARATOR);
            sql.append(" ?");
        }
        sql.append(")");
        return jdbcUtil.execute(sql.toString(), new Date(), "admin", new Date(), "admin", school.getName(), school.getAddress(), school.getZipCode()) ? 1 : 0;
    }

    @Override
    public int delete(School school) {
        JdbcUtil jdbcUtil = new JdbcUtil.Builder().driver("com.mysql.cj.jdbc.Driver").url("jdbc:mysql://192.168.1.195:3306/top_class?characterEncoding=utf-8").username("noneplus").password("noneplus").build();
        StringBuilder sql = new StringBuilder("delete from top_class.school where ");
        if (StringUtils.isNotEmpty(school.getId())) {
            sql.append("id = ?");
        }
        if (StringUtils.isNotEmpty(school.getName())) {
            if (StringUtils.isNotEmpty(school.getId())) {
                sql.append(SQL_AND);
            }
            sql.append("name = ?");
        }
        if (StringUtils.isNotEmpty(school.getAddress())) {
            if (StringUtils.isNotEmpty(school.getId()) || StringUtils.isNotEmpty(school.getName())) {
                sql.append(SQL_AND);
            }
            sql.append("address = ?");
        }
        if (StringUtils.isNotEmpty(school.getZipCode())) {
            if (StringUtils.isNotEmpty(school.getId()) || StringUtils.isNotEmpty(school.getName()) || StringUtils.isNotEmpty(school.getAddress())) {
                sql.append(SQL_AND);
            }
            sql.append("zip_code = ?");
        }
        if (StringUtils.isNotEmpty(school.getId()) && StringUtils.isNotEmpty(school.getName()) && StringUtils.isNotEmpty(school.getAddress()) && StringUtils.isNotEmpty(school.getZipCode())) {
            return jdbcUtil.execute(sql.toString(), school.getId(), school.getName(), school.getAddress(), school.getZipCode()) ? 1 : 0;
        }
        if (StringUtils.isNotEmpty(school.getId()) && StringUtils.isNotEmpty(school.getName()) && StringUtils.isNotEmpty(school.getAddress())) {
            return jdbcUtil.execute(sql.toString(), school.getId(), school.getName(), school.getAddress()) ? 1 : 0;
        }
        if (StringUtils.isNotEmpty(school.getId()) && StringUtils.isNotEmpty(school.getName())) {
            return jdbcUtil.execute(sql.toString(), school.getId(), school.getName()) ? 1 : 0;
        }
        if (StringUtils.isNotEmpty(school.getId())) {
            return jdbcUtil.execute(sql.toString(), school.getId()) ? 1 : 0;
        }
        return jdbcUtil.execute(sql.toString()) ? 1 : 0;
    }
}

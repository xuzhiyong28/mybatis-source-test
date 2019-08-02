package xyz.coolblog.chapter1.customTypeHandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/***
 * 自定义类型转换器
 * 将List<String>转成varchar
 */
public class CustomeListTypeHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for(String s : parameter){
            sb.append(s).append(",");
        }
        ps.setString(i, sb.toString().substring(0,sb.toString().length() - 1));
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String[] arr = rs.getString(columnName).split(",");
        return Arrays.asList(arr);
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String[] arr = rs.getString(columnIndex).split(",");
        return Arrays.asList(arr);
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String[] arr = cs.getString(columnIndex).split(",");
        return Arrays.asList(arr);
    }
}

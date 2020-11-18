package db;

import model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBUtil {
    static JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
    public static boolean login(User user){
        String sql="select * from login_user where username=? and password=?";
        try {
            User query = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class), user.getUsername(),user.getPassword());
            if(query==null){
                return false;
            }
            else{
                return true;
            }
        }catch (EmptyResultDataAccessException e){
            return false;
        }

    }

    public static User getByUsername(String username){
        String sql="select * from login_user where username=?";
        try {
            User query = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
            return query;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public static boolean register(String username){
        User login = getByUsername(username);
        if(login==null){
            return true;
        }
        else{
            return false;
        }
    }

    public static void addUser(User user) {
        String sql="insert into login_user(username,password,email) values(?,?,?)";
        int len = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(),user.getEmail());
    }

    public static void update(User user){
        String sql="update login_user set password=?where username=?";
        jdbcTemplate.update(sql,user.getPassword(),user.getUsername());
    }

//    public static void main(String[] args) {
//        User user = new User();
//        user.setUsername("123");
//        user.setPassword("567");
//        user.setEmail("123");
//        update(user);
//    }

}

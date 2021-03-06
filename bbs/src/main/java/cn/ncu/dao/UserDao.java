package cn.ncu.dao;

import cn.ncu.domain.SecretProtection;
import cn.ncu.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    /**
     * 查询所有
     * @return
     */
    @Select("select * from user")
    @Results(id = "userMap", value = {
            @Result(id = true, column = "uid", property = "uid"),
            @Result(column = "username", property = "username"),
            @Result(column = "nickname", property = "nickname"),
            @Result(column = "password", property = "password"),
            @Result(column = "email", property = "email"),
            @Result(column = "credit", property = "credit"),
            @Result(column = "photo", property = "photo"),
            @Result(column = "tel", property = "tel"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "description", property = "description"),
            @Result(column = "job", property = "job"),
            @Result(column = "company", property = "company"),
            @Result(column = "exp", property = "exp"),
            @Result(column = "rank", property = "rank"),
//            一对多连接查询
//            @Result(property = "accounts", column = "id", many = @Many(select = "cn.ncu.dao.AccountDao.findAccountByUid", fetchType = FetchType.LAZY))
    })
    List<User> findAll();

    @Select("select* from user where uid = #{uid}")
    @ResultMap("userMap")
    User findById(Integer id);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Select( "select * from user where username = #{username} and password = #{password}")
    public User findUser(@Param("username") String username, @Param("password") String password);

    @Select("select * from secret_protection where username = #{username}")
    SecretProtection findSecretProtection(String username);

    /**
     * 注册
     * @param user
     */
    @Insert("insert into user(username,nickname,password,email,credit,photo,tel,sex,description,job,company)" + "values(#{username},#{nickname},#{password},#{email},100,'default_photo.png',#{tel},#{sex},#{description},#{job},#{company})")
    void register(User user);


    @Insert("insert into secret_protection values(#{username}, #{question}, #{answer})")
    void addSecretProtection(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 重置密码
     * @param user
     */
    @Update("update user set password = #{password) where ")
    public void reset(User user);


    /**
     * 查询是由存在邮箱，然后是否给予修改密码的权限
     * @param email
     * @return
     */
    @Select( "select * from user where email = #{email}")
    public User findEmail(String email);


    /**
     * 用户积分加上add增量
     * @param uid
     * @param add
     */
    @Update("update user set credit = credit + #{add} where uid = #{uid}")
    @ResultMap("userMap")
    void addCredit(@Param("uid") Integer uid, @Param("add") Integer add);

    /**
     * 更新用户的等级和经验
     * @param user
     */
    @Update("update user set exp = #{exp}, rank = #{rank} where uid = #{uid}")
    void setRankAndExp(User user);


    @Update("update user set password = #{password} where username = #{username}")
    void updatePassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from user where username = #{username}")
    User findUserByUsername(String username);

    /**
     * 更新用户的个人信息
     * @param user
     */
    @Update("update user set nickname = #{nickname}, email = #{email}, tel = #{tel}, sex = #{sex}, " +
            "job = #{job}, company = #{company}, description = #{description} where uid = #{uid}")
    void updateInfo(User user);

    /**
     * 根据用户id查找用户
     * @param uid
     */
    @Select("select * from user where uid = #{uid}")
    User findUserById(Integer uid);


    /**
     * 存入头像的相对地址
     * @param uid
     * @param photo
     */
    @Update("update user set photo = #{photo} where uid = #{uid}")
    void photoUpload(@Param("uid") Integer uid, @Param("photo")String photo);
}

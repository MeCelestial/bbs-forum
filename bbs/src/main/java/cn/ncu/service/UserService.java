package cn.ncu.service;

import cn.ncu.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public User findUser(String username,String password);

    /**
     * 注册
     * @param user
     */
    void register(User user);

    void reset(User user);

    User findEmail(String email);

    /**
     * 用户积分加上add增量
     * @param uid
     * @param add
     */
    void addCredit(Integer uid, Integer add);

    /**
     * 设置用户的等级经验
     * @param user
     */
    public void setRankAndExp(User user);
}

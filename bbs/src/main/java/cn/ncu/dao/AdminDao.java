package cn.ncu.dao;

import cn.ncu.domain.Notice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminDao {

    /**
     * 置顶
     * @param pid
     */
    @Update("update post set is_top = 1 where pid = #{pid}")
    void isTopPost(@Param("pid")Integer pid);

    /**
     * 取消置顶
     * @param pid
     */
    @Update("update post set is_top = 0 where pid = #{pid}")
    void notTopPost(@Param("pid")Integer pid);

    /**
     * 加精
     * @param pid
     */
    @Update("update post set is_good = 1 where pid = #{pid}")
    void isGoodPost(@Param("pid")Integer pid);

    /**
     * 取消加精
     * @param pid
     */
    @Update("update post set is_good = 0 where pid = #{pid}")
    void notGoodPost(@Param("pid")Integer pid);


    /**
     * 查找所有公告
     * @return
     */
    @Select("select * from notice order by notice_time desc")
    @Results(id="noticeMap",
            value = {
                @Result(id = true, column = "nid", property = "nid"),
                @Result(column = "nid", property = "nid"),
                @Result(column = "notice_time", property = "noticeTime"),
                @Result(column = "title", property = "title"),
                @Result(column = "content", property = "content"),
            })
    public List<Notice> findNotice();


    /**
     * 发布一条公告
     * @param notice
     */

    @Insert("insert into notice values(null,#{noticeTime},#{title},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "nid", keyColumn = "nid")
    void addNotice(Notice notice);

}

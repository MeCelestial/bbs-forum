package cn.ncu.controller;

import cn.ncu.domain.Floor;
import cn.ncu.domain.Post;
import cn.ncu.domain.PostFloor;
import cn.ncu.domain.User;
import cn.ncu.service.PostFloorService;
import cn.ncu.service.PostService;
import cn.ncu.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/post")
@SessionAttributes("postWithAllFloor")               // 把postWithAllFloor作为session存入model中
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostFloorService postFloorService;


    /**
     * 发帖
     * @param post
     * @return
     */
    @RequestMapping("/addPost")
    public String  addPost(Post post, Model model, HttpSession session){

        System.out.println("id是啥");
        // 发帖的时候要更新三张表

        /*Post{pid=null, uid=null, postTime=null, title='aaa', content='傻逼
', currentFloor=null, likenum=null, isGood=null, isTop=null, kind=1,*/

        // 获取到当前登录者的user对象
        User user = (User) session.getAttribute("USER_SESSION");

        // 更新post表
        post.setUid(user.getUid());
        Date date = new Date();
        Timestamp postTime = new Timestamp(date.getTime());
        post.setPostTime(postTime);
        post.setCurrentFloor(1);
        post.setLikenum(0);
        post.setIsGood(0);                          // 是否是精品
        post.setIsTop(0);                           // 是否置顶
        post.setLastActiveTime(postTime);
        post.setNickName(user.getNickname());       // 获取昵称
        postService.addPost(post);
        System.out.println(post);

         // 更新post_floor表
        PostFloor postFloor = new PostFloor();

        postFloor.setPid(post.getPid());
        postFloor.setUid(user.getUid());
        postFloor.setReplyTime(postTime);
        postFloor.setContent(post.getContent());
        postFloor.setFid(1);


        postFloorService.addOneFloor(postFloor);


        // 发帖之后给用户加经验，加等级
        int exp = user.getExp() + 5;
        int rank = user.getRank();
        user.setExp(exp);

        user.setRank(calRank(exp));

        // 把等级放到model中，如果为负数，那么没有升级，如果为整数，则是升级到了对应的级数
        model.addAttribute("rank", rank * (-1));

        if(rank < user.getRank()){
            model.addAttribute("rank", rank);
        }

        // 更新User表
        userService.setRankAndExp(user);
        return "post_success";
    }

    /**
     * 将种类存入session,并转到发帖页面
     * @param kind
     * @param model
     * @return
     */
    @RequestMapping("/transitKind")
    public String transitKind(@Param("kind")Integer kind, Model model){
        System.out.println(kind);
        model.addAttribute("kind", kind);
        return "post_post";
    }

    /**
     * 显示指定种类的所有帖子
     * @param kind
     * @param model
     * @return
     */
    @RequestMapping("/showPosts")
    public String showPosts(@Param("kind")Integer kind, Model model){
        List<Post> list = postService.findPostsByKind(kind);
        model.addAttribute("postsList", list);

        return "posts_show";
    }

    /**
     * 查询当前用户的所有发帖
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/findAllPostByMe")
    public String findAllPostByMe(HttpSession session, Model model){
        User user = (User) session.getAttribute("USER_SESSION");
        List<Post> list = postService.findAllPostByUid(user.getUid());

        model.addAttribute("postListByMe", list);
        return "posts_by_me";
    }

    /**
     * 根据uid查找所有回复的帖子， 并根据回复时间排序
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/findAllReplyByMe")
    public String findAllReplyByMe(HttpSession session, Model model){
        User user = (User) session.getAttribute("USER_SESSION");
        // 获取到所有回复的帖子
        List<Post> list = postService.findAllReplyByUid(user.getUid());
      /*  // 获取所有回复的时间
        List<Timestamp> times = postService.findAllReplyTimeByUid(user.getUid());

        Map<Post, Timestamp> replyMap = new HashMap<>();*/

        for(Post p : list){
            System.out.println(p);
        }
        model.addAttribute("replyListByMe", list);

        return "post_reply_by_me";
    }



    /**
     * 显示指定pid的所有楼层信息
     * @param pid
     * @return
     */
    @RequestMapping("/showAllFloors")
    public String showAllFloors(Integer pid, Model model){
        // 根据pid获取帖子对象
        Post post = postService.findPostByPid(pid);
        // 查找这个帖子的所有楼层信息
        System.out.println("1-----------------------------------------");
        List<Floor> list = postService.findFloorsByPid(pid);
        for(Floor f : list){
            System.out.println(f);
            System.out.println("2----------------------------------");
        }
        System.out.println("3-------------------------------------------");
        post.setFloors(list);
        model.addAttribute("postWithAllFloor", post);

        return "post_all_floors";
    }

    /**
     * 回帖功能
     * @param post_Floor
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/addReply", params = {"pid"})
    public String addReply(PostFloor post_Floor, Model model, HttpSession session){
           /* private Integer pid;   //帖子id
    private Integer fid;    //楼层号
    private Integer uid;    //回帖人或发帖人id
    private Timestamp replyTime;   //回帖时间
    private String content;    //回帖内容*/

        // 从session中获取当前用户
        User user = (User) session.getAttribute("USER_SESSION");
        post_Floor.setUid(user.getUid());
        // 从model中取出postWithAllFloor,获取楼层数 并加一
        Post p = (Post) session.getAttribute("postWithAllFloor");
        post_Floor.setFid(p.getCurrentFloor() + 1);

        // 获取一个时间
        Timestamp replyTime = new Timestamp(new Date().getTime());
        post_Floor.setReplyTime(replyTime);


        // 将对象存入post_floor表
        postFloorService.addOneFloor(post_Floor);

        // 更新post表中的当前楼层收
        postService.updateCurrentFloor(p.getPid(), p.getCurrentFloor() + 1);

        // 重新展示所有楼层信息
        return "forward:showAllFloors";
    }


    /**
     * 删除pid这个帖子
     * @param pid
     * @return
     */
    @RequestMapping("/deletePost")
    public String deletePost(Integer pid){

        postService.deletePost(pid);

        return "forward:findAllPostByMe";
    }

    /**
     * 删除某个用户的某个pid帖子的所有回复
     * @param pid
     * @return
     */
    @RequestMapping("/deleteReply")
    public String deleteReply(Integer pid, Integer fid, HttpSession session){
        User user = (User) session.getAttribute("USER_SESSION");

        postFloorService.deleteReply(pid, user.getUid(), fid);


        return "forward:findAllReplyByMe";
    }

    // 计算等级，2个指数
    public int calRank(int exp){
        int rank = 0;
        while(exp / 2 > 0){
            rank += 1;
            exp /= 2;
        }
        return rank;
    }
}
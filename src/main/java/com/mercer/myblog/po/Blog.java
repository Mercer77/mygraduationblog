package com.mercer.myblog.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Date:2019/8/18
 * Auther:Mercer
 * Auther:麻前程
 */
@Entity
@Table(name = "t_blog")
public class Blog implements Serializable {

    @Id
    @GeneratedValue
    private Long id; //主键
    private String title; //标题
    @Basic(fetch = FetchType.LAZY)
    @Lob    //大数据
    private String content; //内容
    private String firstPicture; //首图
    private String flag;    //标记
    private String description; //博客描述
    private Integer views = 0;  //浏览次数
    private boolean appreciation;   //赞赏开关
    private boolean shareStatement; //转载声明开关
    private boolean commentabled;   //评论开关
    private boolean published;  //发布开关
    private boolean recommend;  //推荐开关
    @Transient
    private String tagsId;      //标签id字符串 如 1，2，3
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;    //更新时间
    @ManyToOne
    private Type type;  //博客类型
    @ManyToMany(cascade = CascadeType.PERSIST)  //级联新增
    private List<Tag> tags; //标签
    @ManyToOne
    private User user;  //用户
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

//    初始化tagIds字符串
    public void initTagIds(){
        this.tagsId = tagsToIds(this.getTags());

    }

    //将List<Tag> tags的id转换为 1，2，3形式 给tagsId
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags){
                if (flag){
                    ids.append(",");
                }else {
                    flag=true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else
            return tagsId;

    }

    public String getTagsId() {
        return tagsId;
    }

    public void setTagsId(String tagsId) {
        this.tagsId = tagsId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", tagsId='" + tagsId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type.getId() +
                ", tags=" + tags.size() +
                ", user=" + user.getId() +
                ", comments=" + comments.size() +
                '}';
    }
}

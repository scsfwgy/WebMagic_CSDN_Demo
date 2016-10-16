
/*Mine.按照粉丝数降序排列*/
create  view v_mine_descByFansNums
as
select *
from mine
order by cast(fansNums as SIGNED INTEGER) desc;

/*Expert.按照文章阅读量降序排列*/
create  view v_expert_descByReadNums
as
select *
from Expert
order by cast(readNums as SIGNED INTEGER) desc;

/*Expert.按照文章数量降序排列*/
create  view v_expert_descByArticleNums
as
select *
from Expert
order by cast(articleNums as SIGNED INTEGER) desc;

/*Blog.按照文章阅读量降序排列*/
create  view v_blog_descByViewNums
as
select *
from Blog
order by cast(ViewNums as SIGNED INTEGER) desc;


/*Blog.按照评论量降序排列*/
create  view v_blog_descByCommentNums
as
select *
from Blog
order by cast(CommentNums as SIGNED INTEGER) desc;

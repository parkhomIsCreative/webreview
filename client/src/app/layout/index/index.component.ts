import {Component, OnInit} from '@angular/core';
import {Post} from '../../model/Post';
import {User} from '../../model/User';
import {PostService} from '../../service/post.service';
import {UserService} from '../../service/user.service';
import {CommentService} from '../../service/comment.service';
import {NotificationService} from '../../service/notification.service';
import {ImageUploadService} from '../../service/image-upload.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  isPostsLoaded = false;
  posts!: Post[];
  isUserDataLoaded = false;
  user!: User;
  isImage = false;

  constructor(private postService: PostService,
              private userService: UserService,
              private commentService: CommentService,
              private notificationService: NotificationService,
              private imageService: ImageUploadService
  ) {
  }

  ngOnInit(): void {
    this.postService.getAllPosts()
      .subscribe(data => {
        console.log(data);
        this.posts = data;
        this.getImagesToPosts(this.posts);
        this.getCommentsToPosts(this.posts);
        this.isPostsLoaded = true;
      });

    this.userService.getCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.user = data;
        this.isUserDataLoaded = true;
      })
  }

  getImagesToPosts(posts: Post[]): void {

    posts.forEach(p => {
      this.imageService.getImageToPost(p.id!)
        .subscribe((data: any) => {
          p.imageURL = data.imageURL;
          if (p.imageURL != undefined) {
            this.isImage = true;
          }
        })
    });
  }

  getCommentsToPosts(posts: Post[]): void {
    posts.forEach(p => {
      this.commentService.getCommentsToPost(p.id!)
        .subscribe(data => {
          p.comments = data
        })
    });
  }

  likePost(postId: any, postIndex: number): void {
    const post = this.posts![postIndex];
    console.log(post);
    if (!post.usersLiked!.includes(this.user.username)) {
      this.postService.likePost(postId, this.user.username)
        .subscribe(() => {
          post.usersLiked?.push(this.user.username);
          this.notificationService.showSnackBar('Liked!');
        });
    } else {
      this.postService.likePost(postId, this.user.username)
        .subscribe(() => {
          const index = post.usersLiked!.indexOf(this.user.username!, 0);
          if (index > -1) {
            post.usersLiked!.splice(index, 1);
            this.notificationService.showSnackBar('Disliked!');
          }
        });
    }
  }

  postComment(message: any, postId: number, postIndex: number): void {
    const post = this.posts![postIndex];
    console.log(post);
    this.commentService.addCommentToPost(postId, message.target.value)
      .subscribe(data => {
        console.log(data);
        post.comments!.push(data);
      });
  }

}

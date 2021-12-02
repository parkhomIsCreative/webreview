import {Comment} from "./Comment";

export interface Post {
  id?: number;
  title: string;
  postgroup: string;
  tag: string;
  rating?: number;
  imageURL?: string;
  likes?: number;
  usersLiked?: string[];
  comments?: Comment[];
  username?: string;
}

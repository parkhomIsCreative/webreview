import {Comment} from "./Comment";
export interface Post{
  id? : number;
  title : string;
  caption : string;
  location : string;
  imageURL? : string;
  likes? : number;
  usersLiked? : string[];
  comments? : Comment[];
  username? : string;
}

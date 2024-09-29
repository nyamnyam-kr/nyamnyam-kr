import { deletePost, fetchPost } from "src/app/api/post/post.api";
import { getLikeCount, hasLikedPost } from "src/app/api/upvote/upvote.api";
import { PostModel } from "src/app/model/post.model";
import { fetchImageService } from "../image/image.service";

export const fetchPostService = async (restaurantId: number) => {
  try {
    const posts: PostModel[] = await fetchPost(restaurantId);

    const likeStatusPromise = posts.map(async (post) => {
      const liked = await hasLikedPost({ id: 0, giveId: 1, postId: post.id, haveId: 0 });
      const count = await getLikeCount(post.id);
      const images = await fetchImageService(post.id);

      return {post, liked, count, images}
    })
    const result = await Promise.all(likeStatusPromise);
    return result;
  } catch (error) {
    console.error("fetchPostsService error:", error);
    throw error;
  }
}

export const deletePostService = async (postId: number) => {
  try {
    const response = await deletePost(postId);
    if (response.status === 200 || response.status === 204) {
      return true;
    }
    return false;
  } catch (error) {
    console.error('Error in deletePostService:', error);
    return false;
  }
};
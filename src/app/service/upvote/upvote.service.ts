import { hasLikedPost, likePost, unLikePost } from "src/app/api/upvote/upvote.api";
import { initialUpvote, UpvoteModel } from "src/app/model/upvote.model";

export const checkLikedService = async (postId: number, userId: number) => {
    const upvote: UpvoteModel = {
        ...initialUpvote,
        giveId: userId,
        postId, 
        haveId: 0
    }
    return await hasLikedPost(upvote);
};

export const toggleLikeService = async ( postId: number,userId: number,likedPost: number[]
  ): Promise<{ likedPost: number[]; likeCountDelta: number }> => {
    const upvote: UpvoteModel = { id: 0, giveId: userId, postId, haveId: 0 };

    if (likedPost.includes(postId)) {
        const success = await unLikePost(upvote);
        return success
          ? { likedPost: likedPost.filter((id) => id !== postId), likeCountDelta: -1 }
          : { likedPost, likeCountDelta: 0 };
      } else {
        const success = await likePost(upvote);
        return success
          ? { likedPost: [...likedPost, postId], likeCountDelta: 1 }
          : { likedPost, likeCountDelta: 0 };
      }
    };
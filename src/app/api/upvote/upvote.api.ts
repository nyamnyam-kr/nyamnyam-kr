import { UpvoteModel } from "src/app/model/upvote.model";
import instance from "../axios";
import { api } from "../request";


export const likePost = async (upvote: UpvoteModel): Promise<boolean> => {
    try{
        const response = await instance.post(`${api.post}/${upvote.postId}/like`, null,{
            params: {userId: upvote.giveId}
        });
        return response.data;
    } catch (error) {
        console.error("likePost API error:", error);
        return false;
    }
};

export const unLikePost = async (upvote: UpvoteModel):Promise<boolean> => {
    try{
        const response = await instance.post(`${api.post}/${upvote.postId}/unlike`, null, {
            params:{userId: upvote.giveId}
        });
        return response.data;
    }catch(error){
        console.error("unLikePost API error:", error);
        return false;
    }
}

export const hasLikedPost = async (upvote: UpvoteModel): Promise<boolean> => {
    try{
        const response = await instance.get(`${api.post}/${upvote.postId}/hasLiked`, {
            params: { userId: upvote.giveId }
        });
        return response.data;
    }catch(error){
        console.error("hasLikePost API error:", error);
        return false;
    }
   
};

export const getLikeCount = async (postId:number): Promise<number> => {
    try{
        const response = await instance.get(`${api.post}/${postId}/like-count`);
        return response.data;
    }catch(error){
        console.error("getLikePost API error:", error);
        return 0;
    }
};


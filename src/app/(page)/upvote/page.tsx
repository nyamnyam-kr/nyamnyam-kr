import axios from "axios"


export const likePost = async (upvote: UpvoteModel): Promise<boolean> => {
    const response = await axios.post(`http://localhost:8080/api/posts/${upvote.postId}/like`, null,{
        params: {userId: upvote.giveId}
    });
    return response.data;
};

export const unLikePost = async (upvote: UpvoteModel):Promise<boolean> => {
    const response = await axios.post(`http://localhost:8080/api/posts/${upvote.postId}/unlike`, null, {
        params:{userId: upvote.giveId}
    });
    return response.data;
}

export const hasLikedPost = async (upvote: UpvoteModel): Promise<boolean> => {
    const response = await axios.get(`http://localhost:8080/api/posts/${upvote.postId}/hasLiked`, {
        params: { userId: upvote.giveId }
    });
    return response.data;
};

export const getLikeCount = async (postId:number): Promise<number> => {
    const response = await axios.get(`http://localhost:8080/api/posts/${postId}/like-count`);
    return response.data;
};


import { deletePost, detailsPost, getPostById, getPostsByRestaurant, insertPost, updatePost } from "src/app/api/post/post.api";
import { getLikeCount, hasLikedPost } from "src/app/api/upvote/upvote.api";
import { PostModel } from "src/app/model/post.model";
import { getImageService } from "../image/image.service";
import { deleteImageById, getImage, getImageByPostId, uploadPostImages } from "src/app/api/image/image.api";

export const updatePostService = async (postId: number, postData: any, images: File[], imagesToDelete: number[]): Promise<void> => {
 try{
  await updatePost(postId, postData); 

  if(imagesToDelete.length > 0) {
    const imageIds = await getImageByPostId(postId); 
    for(const imageId of imagesToDelete){
      if(imageIds.includes(imageId)){
        await deleteImageById(imageId);
      }
    }
  }
  if(images.length > 0) {
    await uploadPostImages(postId, images);
  }
 } catch(error){
  console.error('Error in updatePostService:', error);
  throw error;
 }
}

// 하나의 post 데이터 가져오기 
export const getPostDetails = async (id:number): Promise<PostModel> => {
  try{
    const post = await getPostById(id); 
    return post;
  }catch(error){
    console.error("Error in fetchPostData:", error);
    throw error;
  }
}

export const detailsPostAndImages = async (postId: number):  Promise<{ post: any; images: string[] }> => {
  try {
    const post = await detailsPost(postId); 
    const images = await getImage(postId);
    return {post, images};
  } catch (error) {
    console.error("Error loading post and images:", error);
    throw error;
  }
}

export const insertPostService = async (postData: Partial<PostModel>, images: File[]): Promise<number> => {
  try {
    const postId = await insertPost(postData);

    if (images && images.length > 0) {
      await uploadPostImages(postId, images);
    }
    return postId;
  } catch (error) {
    console.error('Error occurred while inserting post:', error);
    throw error;
  }
}

export const fetchPostService = async (restaurantId: number) => {
  try {
    const posts: PostModel[] = await getPostsByRestaurant(restaurantId);

    const likeStatusPromise = posts.map(async (post) => {
      const liked = await hasLikedPost({ id: 0, giveId: 1, postId: post.id, haveId: 0 });
      const count = await getLikeCount(post.id);
      const images = await getImageService(post.id);

      return { post, liked, count, images }
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
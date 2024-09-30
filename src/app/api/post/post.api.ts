import axios from "axios";
import { PostModel } from "src/app/model/post.model";
import instance from "../axios";
import { api } from "../request";

export const updatePost = async (id: number, postData: any): Promise<any> => {
  try{
    const response = await instance.put(`${api.post}/${id}`, postData)
    return response.data;
  } catch(error){
    console.error("Failed to update post:", error);
    throw error;
  }
}

export const getPostById = async (id: number): Promise<PostModel> => {
  try {
    const response = await fetch(`http://localhost:8080/api/posts/${id}`);

    if (!response.ok) {
      throw new Error(`Failed to fetch post details: ${response.statusText}`);
    }
    const post: PostModel = await response.json();
    console.log("Fetched PostModel data: ", post); 

    return post;
  } catch (error) {
    console.error("Failed to fetch post details:", error);
    throw error;
  }
};

export const insertPost = async (postData: Partial<PostModel>): Promise<number> => {
  try {
    const response = await instance.post(api.post, postData); // 고정경로
    return response.data;
  } catch (error) {
    console.error('Post insert failed:', error);
    throw error;
  }
}

export const getPostsByRestaurant = async (restaurantId: number) => {
  try {
    const response = await instance.get(`${api.post}/${restaurantId}/group`);
    return response.data;
  } catch (error) {
    console.error("getPostsByRestaurant API error:", error);
    throw error;
  }
}

export const deletePost = async (postId: number) => {
  try {
    const response = await instance.delete(`${api.post}/${postId}`);
    return response;
  } catch (error) {
    console.error('Delete operation failed:', error);
    throw error;
  }
};


// likeCount, likeChecked, getimg 한번에 가져오기 
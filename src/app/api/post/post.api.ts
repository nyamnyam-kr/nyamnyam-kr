import { PostModel } from "src/app/model/post.model";
import { api } from "../request";
import { strategy } from "../api.strategy";
import {List} from "postcss/lib/list";

const getById = async (id:number): Promise<PostModel> =>{
    const response = await strategy.GET(`${api.post}/${id}`)
    return response.data;
};

const getByRestaurant = async (restaurantId: number) => {
    const response = await strategy.GET(`${api.post}/${restaurantId}/group`);
    return response.data;
};

const insert = async (postData: Partial<PostModel>): Promise<number> => {
  const response = await strategy.POST(api.post, postData); // 고정경로
  return response.data;
};

const update = async (id: number, postData: any): Promise<PostModel> => {
  const response = await strategy.PUT(`${api.post}/${id}`, postData);
  return response.data;
};

const remove = async (postId: number) => {
    const response = await strategy.DELETE(`${api.post}/${postId}`);
    return response;
};

const listById = async (userId:number) => {
    const response = await strategy.GET(`${api.post}/list/${userId}`);
    return response.data;

}

export const post = { getById, getByRestaurant, insert, update, remove, listById };


//src/app/api/image
import { strategy } from "../api.strategy";
import instance from "../axios";
import { api } from "../request";

export const getByPostId = async (postId: number): Promise<string[]> => {
    const response = await strategy.GET(`${api.image}/post/${postId}`);
    return response.data.map((image: any) => image.uploadURL);
};

export const getByImgId = async (postId: number): Promise<number[]> => {
    const response = await strategy.GET(`${api.image}/post/${postId}/imageIds`);
    return response.data;
};

export const upload = async (postId: number, images: File[]): Promise<void> => {
    const imageData = new FormData();
    images.forEach((file) => imageData.append('files', file));
    imageData.append('postId', postId.toString());

    await strategy.POST(`/api/images/upload/${postId}`, imageData);
};

export const update = async (postId: number, images: File[]): Promise<void> => {
    const imageData = new FormData();
    images.forEach((file) => imageData.append('files', file));
    imageData.append('postId', postId.toString());

    await strategy.PUT(`${api.image}/${postId}`, imageData);
};

export const remove = async (imageId: number): Promise<void> => {
    await instance.delete(`/api/images/${imageId}`);
};

export const image = {getByPostId, getByImgId, upload, update, remove};
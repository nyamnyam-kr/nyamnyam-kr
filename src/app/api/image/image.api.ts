//src/app/api/image
import instance from "../axios";
import { api } from "../request";

export const deleteImageById = async (imageId: number): Promise<void> => {
    try {
      await instance.delete(`/api/images/${imageId}`);
    } catch (error) {
      console.error(`Failed to delete image with ID: ${imageId}`, error);
      throw error;
    }
  };
  
export const getImageByPostId = async (postId: number): Promise<number[]> => {
    try {
        const response = await instance.get(`${api.image}/post/${postId}/imageIds`);
        return response.data;
    } catch (error) {
        console.error("Failed to fetch image IDs:", error);
        throw error;
    }
}

export const updateImages = async (postId: number, images:File[]): Promise<void> => {
    try {
        const imageData = new FormData(); 
        images.forEach((file) => imageData.append('files', file));
        imageData.append('postId', postId.toString()); 

        await instance.put(`${api.image}/${postId}`, imageData); 
    } catch (error) {
        console.error('Image upload failed:', error);
        throw error;
    }
}

export const uploadPostImages = async (postId: number, images: File[]): Promise<void> => {
    try {
        const imageData = new FormData();
        images.forEach((file) => imageData.append('files', file));
        imageData.append('postId', postId.toString());

        await instance.post(`/api/images/upload/${postId}`, imageData);
    } catch (error) {
        console.error('Image upload failed:', error);
        throw error;
    }
};

export const fetchImage = async (postId: number): Promise<string[]> => {
    try {
        const response = await instance.get(`${api.image}/post/${postId}`);
        return response.data.map((image: any) => image.uploadURL);
    } catch (error) {
        console.error("Image fetch fail:", error);
        throw error;
    }

}
//src/app/api/image
import instance from "../axios";
import { api } from "../request";

export const fetchImage = async (postId: number): Promise<string[]> => {
    try {
        const response = await instance.get(`${api.image}/post/${postId}`);
        console.log("Image API Response:", response.data);
        return response.data.map((image: any) => image.uploadURL);
    } catch (error) {
        console.error("Image fetch fail:", error);
        throw error;
    }

}
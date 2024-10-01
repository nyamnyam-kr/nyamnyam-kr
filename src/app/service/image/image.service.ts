import { image } from "src/app/api/image/image.api";

export async function insertImageService(postId: number, images: File[]): Promise<any | {status: number}> {
  try {
    await image.upload(postId, images); 
    return {status: 200};
  } catch (error) {
    console.error('Error occurred while inserting image:', error);
    return {status: 500};
  }
}

export const getImageService = async (postId: number): Promise<string[]> => {
  try{
    const imageURLs = await image.getByPostId(postId); 
    return imageURLs;
  }catch(error){
    console.error("Error fetching images:", error);
    return [];
  }
};

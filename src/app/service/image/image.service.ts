import { fetchImage } from "src/app/api/image/image.api";

export async function insertImage(formData: FormData): Promise<any | {status: number}> {
  try {
    const response = await fetch('http://localhost:8080/api/images/upload', {
      method: 'POST',
      body: formData, 
    });

    const contentType = response.headers.get('content-type');

    if (response.ok && contentType?.includes('application/json')) {
      const data: any = await response.json();
      return data;
    } else {
      const errorMessage = await response.text();
      throw new Error(`Server returned non-JSON response: ${errorMessage}`);
    }
  } catch (error) {
    console.error('Error occurred while inserting image:', error);
    return {status: 500};
  }
}

export const fetchImageService = async (postId: number): Promise<string[]> => {
  try{
    const imageURLs = await fetchImage(postId); 
    console.log("Image URLs in Service:", imageURLs);

    return imageURLs;
  }catch(error){
    console.error("Error fetching images:", error);
    return [];
  }
};

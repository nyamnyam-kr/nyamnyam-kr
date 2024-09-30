import { TagModel } from "src/app/model/tag.model";
import instance from "../axios";
import { api } from "../request";

export const insertTag = async (formData: TagModel) => {
  try {
    const response = await instance.post(api.tag, formData); 
    return response.data;
  } catch (error) {
    console.error('Error inserting tag:', error);
    throw error;
  }
};

export const deleteTag = async (name: string) => {
  try {
    const response = await instance.delete(`${api.tag}/${name}`);
    return response;
  } catch (error) {
    console.error('Delete operation failed:', error);
    throw error;
  }
};

export const updateTag = async (name: string, formData: TagModel) => {
  try {
    const response = await instance.put(`${api.tag}/${name}`, formData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error updating tag:', error);
    throw error;
  }
};

// 모든 태그 반환
export const fetchAllTags = async () => {
  try {
    const response = await instance.get(`${api.tag}/group`);
    return response.data;
  } catch (error) {
    console.error("Fetch error:", error);
    return [];
  }
};

// 카테고리별 리스트 
export const fetchTagsByCategories = async () => {
  try {
    const response = await instance.get(`${api.tag}/category`);
    return response.data;
  } catch (error) {
    console.error("태그 목록을 불러오는데 실패했습니다.", error);
    return [];
  }
};

// 태그의 카테고리만 반환 
export const fetchCategoryNames = async () => {
  try {
    const response = await instance.get(`${api.tag}/tag-category`)
    return response.data;
  } catch (error) {
    console.error("태그 카테고리를 불러오는데 실패했습니다.", error);
    return [];
  }
};

// 특정 이름의 태그 조회
export const fetchTagByName = async (name: string) => {
  try {
    const response = await instance.get(`${api.tag}/${name}`)
    return response.data;
  } catch (error) {
    console.error("fetchTag failed", error);
    throw error;
  }
};
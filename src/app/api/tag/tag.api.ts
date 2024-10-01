import { TagModel } from "src/app/model/tag.model";
import instance from "../axios";
import { api } from "../request";
import { strategy } from "../api.strategy";

// 모든 태그 반환
export const getAllTags = async () => {
    const response = await strategy.GET(`${api.tag}/group`);
    return response.data;
};

// 카테고리별 리스트 
export const getByCategories = async () => {
    const response = await strategy.GET(`${api.tag}/category`);
    return response.data;
};

// 태그의 카테고리만 반환 
export const getCategoryNames = async () => {
    const response = await strategy.GET(`${api.tag}/tag-category`)
    return response.data;
};

// 특정 이름의 태그 조회
export const getTagByName = async (name: string) => {
    const response = await strategy.GET(`${api.tag}/${name}`)
    return response.data;
};

export const insert= async (formData: TagModel) => {
  const response = await strategy.POST(api.tag, formData); 
  return response.data;
};

export const update = async (name: string, formData: TagModel) => {
  const response = await strategy.PUT(`${api.tag}/${name}`, formData) 
  return response.data;
};

export const remove= async (name: string) => {
  const response = await strategy.DELETE(`${api.tag}/${name}`);
  return response;
};

export const tag = {getAllTags, getByCategories, getCategoryNames, getTagByName, insert, update, remove}; 
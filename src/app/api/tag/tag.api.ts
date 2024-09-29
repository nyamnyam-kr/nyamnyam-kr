import instance from "../axios";
import { api } from "../request";

export const tagCategoryApi = async () => {
    try {
      const response = await instance.get(`${api.tag}/category`);
      return response.data;
    } catch (error) {
      console.error("태그 목록을 불러오는데 실패했습니다.", error);
    }
  };
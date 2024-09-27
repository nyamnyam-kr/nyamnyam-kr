// src/app/api/reply/reply.api.ts

import { instance } from "../axios";
import { api } from "../request";

export const fetchReply = async (postId: number) => {
  try {
    const response = await instance.get(`${api.reply}/post/${postId}`);
    return response.data;
  } catch (error) {
    console.error("reply fetch fail:", error);
    throw error;
  }
};

export const insertReply = async (replyData: any) => {
  try{
    const response = await instance.post(api.reply, replyData) // 고정 경로
    return response.data;
  } catch(error) {
    console.error("reply insert fail: ", error);
    throw error;
  }
};

export const deleteReply = async (replyId: number) => {
  try{
    const response = await instance.delete(`${api.reply}/${replyId}`);
    return response;
  } catch(error) {
    console.error("reply delete fail: ", error);
    throw error;
  }
};




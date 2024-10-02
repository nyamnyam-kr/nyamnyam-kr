import { ChatRoomModel } from "src/app/model/chatRoom.model";
import { strategy1 } from "../api.strategy";
import { api } from "../request";
import axios from "axios";

// 챗룸 출력(해당 유저가 참여한으로 수정 필요)
const findAllByNickname = async (nickname: any): Promise<ChatRoomModel[]> => {
  const response = await strategy1.GET(`${api.chatRoom}/findAll/${nickname}`);
  
  // 응답 데이터가 배열이 아닐 경우, 배열로 강제 변환
  return Array.isArray(response.data) ? response.data : [response.data];
};

 const findById = async (chatRoomId: any): Promise<ChatRoomModel> => {
  const reponse = await strategy1.GET(`${api.chatRoom}/${chatRoomId}`);
  return reponse.data;
 }


 const deleteById = async (chatRoomId:string) => {
  const reponse = await strategy1.DELETE(`${api.chatRoom}/deleteById/${chatRoomId}`);
 }






export const chatRoom={findAllByNickname, findById,deleteById};
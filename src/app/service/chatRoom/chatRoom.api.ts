// /src/app/service/chatRoom/chatRoom.api.ts
import { deleteChatRoomApi, fetchChatRoomById, fetchChatRoomCount, fetchChatRooms } from "src/app/api/chatRoom/chatRoom.api";
import { ChatRoomModel } from "src/app/model/chatRoom.model";

export async function insertChatRoom(chatRoom: ChatRoomModel): Promise<any | { status: number }> {
  try {


    const response = await fetch('http://localhost:8081/api/chatRoom/save', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(chatRoom)
    });
    console.log(chatRoom)


    if (!response.ok) {
      const errorData = await response.json(); // 오류 메시지 확인
      console.error('Error response:', errorData);
      return { status: response.status }; // 상태 코드 반환
    }

    const data = await response.json();
    return data;

  } catch (e) {
    console.log('There has been a problem with your fetch operation', e);
    return { status: 500 };
  }
}

export const getChatRoomData = async (nickname:string) => {
  try {
    const chatRooms = await fetchChatRooms(nickname); // API 호출
    const totalCount = await fetchChatRoomCount(); // API 호출
    const totalPages = Math.ceil(totalCount / 10); // 페이지 계산
    return { chatRooms, totalPages };
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
};

export const getChatRoomDetails = async (chatRoomId: any) => {
  try {
    const chatRoomData = await fetchChatRoomById(chatRoomId);
    return chatRoomData;
  } catch (error) {
    console.error("채팅방 정보를 가져오는 중 오류 발생:", error);
    throw error;
  }
};

export const deleteChatRoomsService = async (chatRoomIds: string[]) => {
  if (chatRoomIds.length === 0) {
    throw new Error("삭제할 채팅방이 없습니다.");
  }

  await Promise.all(chatRoomIds.map(id => deleteChatRoomApi(id)));
};
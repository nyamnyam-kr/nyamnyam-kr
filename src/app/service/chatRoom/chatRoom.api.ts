import { deleteChatRoomApi, fetchChatRoomById, fetchChatRoomCount, fetchChatRooms } from "src/app/api/chatRoom/chatRoom.api";

export async function insertChatRoom(chatRoom: ChatRoomModel): Promise<any | { status: number }> {
    try {
        const ChannelModel = {
            name: chatRoom.name
        }
        const response = await fetch('http://localhost:8080/api/chatRoom/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ChannelModel)
        });
        const data: any = await response.json();
        return data;

    } catch (e) {
        console.log('There has been a problem with your fetch operation', e);
        return { status: 500 };
    }
}

export const getChatRoomData = async () => {
    try {
      const chatRooms = await fetchChatRooms(); // API 호출
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
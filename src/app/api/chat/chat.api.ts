import axios from "axios";

// 채팅 메시지 스트리밍 API
export const subscribeToChats = (chatRoomId: any, onMessageReceived: (arg0: any) => void) => {
  const eventSource = new EventSource(`http://localhost:8081/api/chats/${chatRoomId}`);


  eventSource.onmessage = (event) => {
    const data = JSON.parse(event.data);
    onMessageReceived(data);
  };

  return eventSource; // 나중에 닫기 위해 EventSource 반환
};


export const sendChat = async (chatRoomId: any, chat: any) => {
  const response = await fetch(`http://localhost:8081/api/chats/${chatRoomId}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(chat),
  });

  if (!response.ok) {
    throw new Error("메시지 전송 실패");
  }

  return response.json(); // 필요하면 응답 데이터를 반환
};


// 채팅방 별 안읽은 메시지 수 조회
export const getUnreadCountByChatRoom = async (chatRoomId: string, nickname: string): Promise<number> => {
  try {
    const response = await axios.get(`http://localhost:8081/api/chats/${chatRoomId}/unreadCount/${nickname}`);
    return response.data; // 안 읽은 메시지 수 반환
  } catch (error) {
    console.error('Error fetching unread count:', error);
    return 0; // 오류 발생 시 0 반환
  }
};

// 특정 메시지에서 읽지 않은 참가자 수 조회
export const getNotReadParticipantsCount = async (chatId: string): Promise<number> => {
  try {
    const response = await axios.get(`http://localhost:8081/api/chats/${chatId}/notReadParticipantsCount`);
    return response.data; // 읽지 않은 참가자 수 반환
  } catch (error) {
    console.error('Error fetching not read participants count:', error);
    return 0; // 오류 발생 시 0 반환
  }
};


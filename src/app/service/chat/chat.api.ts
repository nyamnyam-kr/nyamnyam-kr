import { sendChat, subscribeToChats } from "src/app/api/chat/chat.api";

// 채팅 메시지 스트리밍 서비스 함수
export const subscribeMessages = (chatRoomId: any, onMessageReceived: (arg0: any) => void) => {
    try {
      return subscribeToChats(chatRoomId, onMessageReceived);
    } catch (error) {
      console.error("메시지 구독 중 오류 발생:", error);
      throw error;
    }
  };

  // 메시지 전송 서비스 함수
export const sendMessageService = async (chatRoomId: any, sender: string, newMessage: string) => {
    if (newMessage.trim() === "" || sender.trim() === "") {
      alert("메시지와 사용자 이름을 입력해주세요.");
      return;
    }
  
    const chat = {
      sender: sender,
      message: newMessage,
      chatRoomId: chatRoomId,
    };
  
    try {
      await sendChat(chatRoomId, chat); // API 호출
    } catch (error) {
      console.error("메시지 전송 중 오류 발생:", error);
      throw error;
    }
  };


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
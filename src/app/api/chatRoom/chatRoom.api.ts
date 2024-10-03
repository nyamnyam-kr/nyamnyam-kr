// /src/app/api/chatRoom/chatRoom.api.ts
// 챗룸 출력(해당 유저가 참여한으로 수정 필요)
export const fetchChatRooms = async (nickname:any) => {
  const response = await fetch(`http://localhost:8081/api/chatRoom/findAll/${nickname}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json(); // ChatRooms 데이터 반환
};

// 챗룸 갯수 세는건데 나중에 페이지 할까봐
export const fetchChatRoomCount = async () => {
  const response = await fetch('http://localhost:8081/api/chatRoom/count');
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json(); // 채팅방 총 개수 반환
};

export const fetchChatRoomById = async (chatRoomId: any) => {
  const response = await fetch(`http://localhost:8081/api/chatRoom/${chatRoomId}`);
  if (!response.ok) {
    throw new Error("채팅방 정보를 가져오는 중 오류 발생");
  }
  return response.json();
};


// api/chatRoomApi.ts
export const deleteChatRoomApi = async (chatRoomId: string) => {
  const response = await fetch(`http://localhost:8081/api/chatRoom/deleteById/${chatRoomId}`, { method: 'DELETE' });
  if (!response.ok) {
    throw new Error("채팅방 삭제 실패");
  }
};
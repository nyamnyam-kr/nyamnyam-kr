"use client";

import Head from "next/head";
import Link from "next/link";
import Image from 'next/image';
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { deleteChatRoomsService, getChatRoomData, getChatRoomDetails, insertChatRoom } from "src/app/service/chatRoom/chatRoom.api";
import { sendMessageService, subscribeMessages } from "src/app/service/chat/chat.api";
import { ChatRoomModel } from "src/app/model/chatRoom.model";
import { getUnreadCountByChatRoom, subscribeToChats } from "src/app/api/chat/chat.api";





export default function Home1() {
  const [chatRooms, setChatRooms] = useState<ChatRoomModel[]>([]);
  const [selectedChatRoomId, setSelectedChatRoomId] = useState<string | null>(null);
  const [selectedChatRoom, setSelectedChatRoom] = useState<ChatRoomModel | null>(null); // 채팅방 정보
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [messages, setMessages] = useState<ChatModel[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const [unreadMessageCounts, setUnreadMessageCounts] = useState<{ [key: string]: number }>({});// 안 읽은 메시지 개수 상태 추가
  const [unreadParticipants, setUnreadParticipants] = useState<{ [messageId: string]: any[] }>({});
  const [sender, setSender] = useState(""); // 사용자 ID
  const [selectChatRooms, setSelectChatRooms] = useState<any[]>([]);
  const router = useRouter();


  const [user, setUser] = useState(null);
  const [chatRoomName, setChatRoomName] = useState<string>(""); // 채팅방 이름
  const [participantNames, setParticipantNames] = useState<string[]>(() => {
    if (typeof window !== 'undefined') { // 클라이언트에서만 접근
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        const parsedUser = JSON.parse(storedUser); // 유저 정보를 JSON으로 파싱
        return [parsedUser.nickname]; // 닉네임만 배열로 반환
      }
    }
    return []; // 서버 사이드에서는 빈 배열 반환
  });
  const [newParticipantName, setNewParticipantName] = useState<string>(""); // 입력받은 참가자 이름



  // 기본 상태
  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser);
      setSender(parsedUser.nickname); // 로그인된 사용자의 닉네임으로 sender 초기화
      fetchData(parsedUser.nickname); // user.nickname으로 파라미터 전달
    }
  }, []);



  const fetchData = async (nickname: string) => {
    if (!nickname) return; // 닉네임이 없으면 함수 종료
    setLoading(true); // 로딩 상태 시작

    try {
      const { chatRooms } = await getChatRoomData(nickname); // 유저 닉네임을 파라미터로 전달
      setChatRooms(chatRooms);

      // 각 채팅방의 안 읽은 메시지 개수 조회 및 상태 업데이트
      const counts = await Promise.all(
        chatRooms.map((room) => getUnreadCountByChatRoom(room.id, nickname))
      );
      const countsMap = chatRooms.reduce((acc, room, idx) => {
        acc[room.id] = counts[idx]; // 각 채팅방 ID를 키로 하여 안 읽은 메시지 개수를 상태에 저장
        return acc;
      }, {} as { [key: string]: number });

      setUnreadMessageCounts(countsMap); // 안 읽은 메시지 개수 상태 설정
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false); // 로딩 상태 종료
    }
  };

  // 채팅방 검색어에 따른 필터링
  const filteredChatRooms = chatRooms.filter((room) =>
    room.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleDetails = (id: any) => {
    router.push(`/channel/details/${id}`);
  };

  const handleCheck = (id: any) => {
    setSelectChatRooms((prevSelected: string[]) =>
      prevSelected.includes(id)
        ? prevSelected.filter((channelId: string) => channelId !== id)
        : [...prevSelected, id]
    )
  };

  useEffect(() => {
    if (selectedChatRoomId) {
      setMessages([]);
      setNewMessage("");
    }
  }, [selectedChatRoomId]);

  const handleDelete = async () => {
    if (selectChatRooms.length === 0) {
      alert("삭제할 채팅방을 선택해주세요.");
      return;
    }
    if (window.confirm("선택한 채팅방을 삭제하시겠습니까?")) {
      try {
        await deleteChatRoomsService(selectChatRooms);
        alert("채팅방이 삭제되었습니다.");
        setChatRooms((prevChatRooms) =>
          prevChatRooms.filter((room) => !selectChatRooms.includes(room.id))
        );
        setSelectChatRooms([]);

        const storedUser = localStorage.getItem("user");
        if (storedUser) {
          const parsedUser = JSON.parse(storedUser);
          fetchData(parsedUser.nickname);
        }
      } catch (error) {
        console.error("Delete operation failed:", error);
        alert("삭제 중 오류가 발생했습니다.");
      }
    }
  };

  const handleCreateChatRoom = async (e: React.FormEvent) => {
    e.preventDefault(); // 페이지 새로고침 방지

    // ChatRoom 객체 생성
    const newChatRoom: any = {
      name: chatRoomName, // 입력된 채팅방 이름
      participants: [...participantNames, newParticipantName.trim()], // 초기 참가자 목록에 입력된 참가자 추가
    };

    const result = await insertChatRoom(newChatRoom);

    if (result.status === 200) {
      alert("채팅방이 성공적으로 생성되었습니다.");
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        const parsedUser = JSON.parse(storedUser);
        fetchData(parsedUser.nickname); // user.nickname으로 파라미터 전달
      }
    }

    // 채팅방 이름과 참가자 목록 초기화
    setChatRoomName(""); // 채팅방 이름 초기화
    setNewParticipantName(""); // 입력 필드 초기화
  };

  const sendMessage = async () => {
    try {
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        const parsedUser = JSON.parse(storedUser);
        setSender(parsedUser.nickname); // 로그인된 사용자의 닉네임으로 sender 초기화
      }
      await sendMessageService(selectedChatRoomId, sender, newMessage);
      setNewMessage(""); // 메시지 입력 필드 초기화
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await sendMessage(); // 메시지 전송 함수 호출
  };

  const handleSelectChatRoom = async (roomId: string) => {
    setSelectedChatRoomId(roomId);
    setMessages([]);

    try {
      const chatRoomData = await getChatRoomDetails(roomId);
      setSelectedChatRoom(chatRoomData);

      const fetchedMessages = chatRoomData.messages || [];
      setMessages(fetchedMessages);

      // 메시지 스트리밍 구독
      const eventSource = subscribeToChats(roomId, (newMessage) => {
        setMessages((prevMessages) => [...prevMessages, newMessage]);

        // 새로운 메시지를 수신했을 때 안 읽은 메시지 수를 0으로 업데이트
        setUnreadMessageCounts((prevCounts) => ({
          ...prevCounts,
          [roomId]: 0,
        }));
      });

      // 안 읽은 메시지 수 초기화
      setUnreadMessageCounts((prevCounts) => ({
        ...prevCounts,
        [roomId]: 0,
      }));
      return () => {
        eventSource.close(); // 컴포넌트 언마운트 시 EventSource 닫기
      };
    } catch (error) {
      console.error("Error selecting chat room:", error);
    }
  };
   // 새로운 메시지 읽음 표시 함수 추가
   const handleMessageRead = async (messageId: string) => {
    // 읽음 표시 로직 (API 호출 등)
    // 예시로 안 읽은 참가자 목록에서 해당 메시지를 제거하는 로직 추가
    setUnreadParticipants((prev) => ({
      ...prev,
      [messageId]: [], // 메시지를 읽었으므로 빈 배열로 업데이트
    }));
  };
  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <title>냠냠</title>
        <meta name="author" content="Templines" />
        <meta name="description" content="TeamHost" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
      </Head>

      <div className="container mx-auto">
        <h1 className="text-3xl font-bold">채팅방</h1>
        <input
          type="text"
          placeholder="채팅방 검색"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="border rounded p-2 mb-4"
        />
        {loading ? (
          <p>Loading...</p>
        ) : (
          <ul>
            {filteredChatRooms.map((room) => (
              <li key={room.id} className="flex justify-between items-center border-b p-2">
                <span className="text-xl cursor-pointer" onClick={() => handleSelectChatRoom(room.id)}>
                  {room.name}
                </span>
                {/* 안 읽은 메시지 개수 표시 */}
                {unreadMessageCounts[room.id] > 0 && (
                  <span className="text-red-500">{unreadMessageCounts[room.id]} unread</span>
                )}
              </li>
            ))}
          </ul>
        )}
        <h2 className="text-2xl font-bold mt-5">채팅방 생성</h2>
        <form onSubmit={handleCreateChatRoom}>
          <input
            type="text"
            placeholder="채팅방 이름"
            value={chatRoomName}
            onChange={(e) => setChatRoomName(e.target.value)}
            className="border rounded p-2 mb-2 w-full"
            required
          />
          <input
            type="text"
            placeholder="참가자 이름"
            value={newParticipantName}
            onChange={(e) => setNewParticipantName(e.target.value)}
            className="border rounded p-2 mb-2 w-full"
          />
          <button type="submit" className="bg-blue-500 text-white p-2 rounded">채팅방 생성</button>
        </form>
        <h2 className="text-2xl font-bold mt-5">선택한 채팅방 삭제</h2>
        <button onClick={handleDelete} className="bg-red-500 text-white p-2 rounded">채팅방 삭제</button>

        {selectedChatRoom && (
          <div>
            <h2 className="text-2xl font-bold mt-5">채팅방: {selectedChatRoom.name}</h2>
            <div className="border p-2 mb-4">
              <ul>
                {messages.map((message) => (
                  <li key={message.id} className="border-b p-2">
                    <span>{message.sender}: {message.message}</span>
                    <button onClick={() => handleMessageRead(message.id)} className="text-blue-500 ml-2">읽음 표시</button>
                  </li>
                ))}
              </ul>
            </div>
            <form onSubmit={handleSubmit}>
              <input
                type="text"
                placeholder="메시지 입력"
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                className="border rounded p-2 mb-2 w-full"
                required
              />
              <button type="submit" className="bg-blue-500 text-white p-2 rounded">메시지 보내기</button>
            </form>
          </div>
        )}
      </div>
    </>
  );
}
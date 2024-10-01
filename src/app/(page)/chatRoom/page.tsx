"use client";

import Head from "next/head";
import Link from "next/link";
import Image from 'next/image';
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { deleteChatRoomsService, getChatRoomData, getChatRoomDetails, insertChatRoom } from "src/app/service/chatRoom/chatRoom.api";
import { sendMessageService, subscribeMessages } from "src/app/service/chat/chat.api";
import axios from "axios";





export default function Home1() {
  const [chatRooms, setChatRooms] = useState<ChatRoomModel[]>([]);
  const [selectedChatRoomId, setSelectedChatRoomId] = useState(String || null);
  const [selectedChatRoom, setSelectedChatRoom] = useState<ChatRoomModel | null>(null); // 채팅방 정보
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [messages, setMessages] = useState<ChatModel[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const [sender, setSender] = useState(""); // 사용자 ID
  const [newMessageCounts, setNewMessageCounts] = useState<Record<string, { newMessageCount: number; totalMessageCount: number }>>({});


  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 상태 추가
  const [totalPages, setTotalPages] = useState(1); // 총 페이지 수 상태 추가

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
      const { chatRooms, totalPages } = await getChatRoomData(nickname); // 유저 닉네임을 파라미터로 전달
      setChatRooms(chatRooms);
      setTotalPages(totalPages);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false); // 로딩 상태 종료
    }
  };

  // 디테일 원
  const handleDetails = (id: any) => {
    router.push('/channel/details/${id}');
  };

  const handleCheck = (id: any) => {
    setSelectChatRooms((prevSelected: string[]) =>
      prevSelected.includes(id)
        ? prevSelected.filter((channelId: string) => channelId !== id)
        : [...prevSelected, id]
    )
  };


  useEffect(() => {
    if (!selectedChatRoomId) return;

    // 채팅방 정보 가져오기
    getChatRoomDetails(selectedChatRoomId)
      .then((data) => {
        setSelectedChatRoom(data);
      })
      .catch((error) => console.error(error));

    // 메시지 스트리밍 구독
    const eventSource = subscribeMessages(selectedChatRoomId, (newMessage) => {
      setMessages((prevMessages) => [...prevMessages, newMessage]);
    });

    return () => {
      eventSource.close(); // 컴포넌트 언마운트 시 EventSource 닫기
    };
  }, [selectedChatRoomId]);

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

  useEffect(() => {
    if (selectedChatRoomId) {
      // 채팅방이 변경되었을 때 메시지 초기화 및 입력 필드 비우기
      setMessages([]); // 메시지 초기화
      setNewMessage(""); // 메시지 입력 필드 초기화
    }
  }, [selectedChatRoomId]);

  const handleDelete = async () => {
    if (selectChatRooms.length === 0) {
      alert("삭제할 채팅방을 선택해주세요.");
      return;
    }
    if (window.confirm("선택한 채팅방을 삭제하시겠습니까?")) {
      try {
        await deleteChatRoomsService(selectChatRooms); // 서비스 호출
        alert("채팅방이 삭제되었습니다.");
        setChatRooms(prevChatRooms =>
          prevChatRooms.filter(room => !selectChatRooms.includes(room.id)) // 삭제한 방을 제외
        );
        setSelectChatRooms([]); // 선택 초기화

        const storedUser = localStorage.getItem('user');
        if (storedUser) {
          const parsedUser = JSON.parse(storedUser);
          fetchData(parsedUser.nickname); // user.nickname으로 파라미터 전달
        }

      } catch (error) {
        console.error('Delete operation failed:', error);
        alert("삭제 중 오류가 발생했습니다.");
      }
    }
  };

  // 검색어에 따른 필터링
  const filteredChatRooms = chatRooms.filter((room) =>
    room.name.toLowerCase().includes(searchTerm.toLowerCase())
  );


  //===========================================여기 까지 serviceInsertReply,api 끝!!!!=============================================

  const handleCreateChatRoom = async (e: React.FormEvent) => {
    e.preventDefault(); // 페이지 새로고침 방지

    // ChatRoom 객체 생성
    const newChatRoomData = {
      name: chatRoomName,
      participants: [...participantNames, newParticipantName.trim()],
    };

    // 참가자 목록 체크
    const participantsList = newChatRoomData.participants.length > 0
      ? newChatRoomData.participants.join(", ")
      : "참가자가 없습니다"; // 참가자가 없을 경우 기본 메시지

    const result = await insertChatRoom(newChatRoomData);

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

  const fetchNewMessageCounts = async () => {
    try {
      const counts = await Promise.all(
        chatRooms.map(async (room) => {
          const lastSeenMessageId = localStorage.getItem(`lastSeenMessageId_${room.id}`) || "";

          // 새로운 메시지 수 요청
          const newMessageResponse = await axios.get(`http://localhost:8081/api/chatRoom/${room.id}/newMessages`, {
            params: { lastSeenMessageId },
          });

          // 총 메시지 수 요청
          const messageCountResponse = await axios.get(`http://localhost:8081/api/chatRoom/${room.id}/messageCount`);

          return {
            roomId: room.id,
            newMessageCount: newMessageResponse.data,
            totalMessageCount: messageCountResponse.data,
          };
        })
      );

      // 새로운 메시지 수와 총 메시지 수를 상태에 저장
      const countsObj = counts.reduce((acc, { roomId, newMessageCount, totalMessageCount }) => {
        if (roomId) {
          acc[roomId] = {
            newMessageCount,
            totalMessageCount,
          };
        }
        return acc;
      }, {} as Record<string, { newMessageCount: number; totalMessageCount: number }>);

      // 상태 업데이트
      setNewMessageCounts(countsObj);
    } catch (error) {
      console.error("Error fetching new message counts:", error);
    }
  };

  useEffect(() => {
    if (chatRooms.length > 0) {
      fetchNewMessageCounts(); // 채팅방 목록이 로드되었을 때 호출
    }
  }, [chatRooms]);


  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <title>냠냠</title>
        <meta name="author" content="Templines" />
        <meta name="description" content="TeamHost" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="HandheldFriendly" content="true" />
        <meta name="format-detection" content="telephone=no" />
        <meta httpEquiv="X-UA-Compatible" content="IE=edge" />
        <link rel="shortcut icon" href="/assets/img/favicon.png" type="image/x-icon" />

        {/* CSS Files */}
        <link rel="stylesheet" href="/assets/css/libs.min.css" />
        <link rel="stylesheet" href="/assets/css/main.css" />

        {/* Google Fonts */}
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Marcellus&display=swap" rel="stylesheet" />
      </Head>
      <main className="page-main">
        <h3 className="uk-text-lead">Chats</h3>
        {/* 채팅방 생성 폼 */}
        <div className="chat-room-create">
          <form onSubmit={handleCreateChatRoom}>
            <div>
              <label>채팅방 이름: </label>
              <input
                type="text"
                value={chatRoomName}
                onChange={(e) => setChatRoomName(e.target.value)} // 입력값 상태 업데이트
                placeholder="채팅방 이름을 입력하세요"
              />
            </div>

            {/* 참가자 닉네임 입력 필드 */}
            <div>
              <label>참가자 닉네임: </label>
              <input
                type="text"
                value={newParticipantName}
                onChange={(e) => setNewParticipantName(e.target.value)} // 입력값 상태 업데이트
                placeholder="참가자 닉네임을 입력하세요"
              />
            </div>
            {/* 채팅방 생성 버튼 */}
            <button type="submit">채팅방 생성</button>
          </form>
          {/* 채팅방 삭제 버튼 */}
          <div style={{ marginBottom: '20px' }}>
            <button
              type="button"
              onClick={handleDelete}
              className="bg-red-500 hover:bg-red-600 text-white font-semibold px-4 py-2 rounded-lg"
            >
              선택한 채팅방 삭제
            </button>
          </div>
        </div>
        <div className="uk-grid uk-grid-small" data-uk-grid>
          <div className="uk-width-1-3@l">
            <div className="chat-user-list">
              <div className="chat-user-list__box" style={{ width: '90%' }}>
                <div className="chat-user-list__head">
                  <div className="avatar">
                    <Image src="/assets/img/profile.png" alt="profile" width={40} height={40} />
                  </div>
                  <a className="ico_edit" href="/06_chats"></a>
                  <a className="ico_more" href="/06_chats"></a>
                </div>
                <div className="chat-user-list__search">
                  <div className="search">
                    <div className="search__input">
                      <i className="ico_search"></i>
                      <input
                        type="search"
                        name="search"
                        placeholder="Search"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                      />
                    </div>
                  </div>
                </div>
                <div className="chat-user-list__body">
                  <ul>
                    {filteredChatRooms.map((room) => (
                      <li key={room.id}>
                        <div className="user-item --active">
                          <div className="user-item__avatar">
                            <Image src="/assets/img/user-list-1.png" alt="user" width={40} height={40} />
                          </div>

                          {/* 채팅방 이름과 참가자 목록을 담고 있는 부분 */}
                          <div className="user-item__desc" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%' }}>
                            {/* 채팅방 이름: 2 비율로 넓게 차지하도록 설정 */}
                            <a
                              href="#"
                              onClick={(e) => {
                                e.preventDefault(); // 기본 동작 방지
                                if (room && room.id) { // room 객체와 id가 유효한지 확인
                                  setSelectedChatRoomId(room.id); // 선택된 채팅방 ID 업데이트
                                }
                              }}
                              style={{ textDecoration: 'none', color: 'inherit', flexGrow: 2, marginRight: '10px' }}
                            >
                              <div className="user-item__name">
                                {room.name}
                              </div>
                              <div style={{ textAlign: 'right' }}>
                                <span style={{ color: 'red', fontWeight: 'bold' }}>
                                  {newMessageCounts[room.id]?.newMessageCount || 0} new
                                </span>
                                <span style={{ marginLeft: '10px', color: 'gray' }}>
                                  {newMessageCounts[room.id]?.totalMessageCount || 0} total
                                </span>
                              </div>
                            </a>
                            {/* 참가자 목록: 1 비율로 출력 */}
                            <div style={{ flexGrow: 1, flexShrink: 1, textAlign: 'right', marginRight: '10px', maxWidth: '150px' }}>
                              {room.participants && room.participants.length > 0 ? (
                                <ul style={{ listStyleType: 'none', padding: 0, margin: 0 }}>
                                  {room.participants.map((participant: string, index: number) => (
                                    <li
                                      key={index}
                                      style={{
                                        marginLeft: '10px',
                                        whiteSpace: 'nowrap',
                                        overflow: 'hidden',
                                        textOverflow: 'ellipsis',
                                        display: 'inline-block', // inline-block으로 변경
                                        maxWidth: '100%', // 최대 너비 설정
                                        width: 'auto' // 자동 너비 설정
                                      }}
                                    >
                                      {participant || "No Nickname"}
                                    </li>
                                  ))}
                                </ul>
                              ) : (
                                "No Participants"
                              )}
                            </div>
                          </div>
                          {/* 체크박스: 고정된 크기로 오른쪽에 배치 */}
                          <div className="user-item__info" style={{ marginLeft: 'auto' }}>
                            <input
                              type="checkbox"
                              checked={selectChatRooms.includes(room.id)}
                              onChange={(e) => handleCheck(room.id)}
                            />
                          </div>
                        </div>
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div className="uk-width-2-3@l">
            <div className="chat-messages-box">
              <div className="chat-messages-head">
                {selectedChatRoomId ? ( // 채팅방이 선택되었는지 확인
                  <div className="user-item">
                    <div className="user-item__avatar">
                      <Image src="/assets/img/user-list-4.png" alt="user" width={40} height={40} />
                    </div>
                    <div className="user-item__desc" style={{ width: 'full' }}>
                      <div className="user-item__name" style={{ textAlign: 'center', fontSize: '1.5rem' }}>
                        {filteredChatRooms.find(room => room.id === selectedChatRoomId)?.name || "Unknown Room"}
                      </div> {/* 선택된 채팅방 이름 표시 */}
                    </div>
                  </div>
                ) : (
                  <h3>선택된 채팅방이 없습니다.</h3> // 채팅방이 선택되지 않은 경우 메시지 표시
                )}
              </div>
              {selectedChatRoomId ? ( // 채팅방이 선택되었을 때만 메시지 표시
                <>
                  <div className="chat-messages-body flex-1 overflow-y-auto p-4 bg-white shadow-md rounded-lg space-y-4">
                    {messages.map((msg, index) => (
                      <div
                        key={index}
                        className={`w-full messages-item ${msg.sender !== sender ? '--your-message' : '--friend-message'} flex`}
                      >
                        <div className="messages-item__avatar flex items-center mr-2">
                          {msg.sender !== sender ? (
                            <Image src="/assets/img/user-list-3.png" alt="img" width={40} height={40} />
                          ) : (
                            <Image src="/assets/img/user-list-4.png" alt="img" width={40} height={40} />
                          )}
                        </div>
                        <div className="flex flex-col justify-start">
                          <div className="flex items-center">
                            <p className="text-sm font-semibold">{msg.sender}</p> {/* 닉네임 표시 */}
                          </div>
                          <div className="messages-item__text">{msg.message}</div> {/* 메시지 텍스트 */}
                          {msg.sender !== sender ? (
                            <div className="messages-item__time text-gray-500 text-xs">{new Date(msg.createdAt).toLocaleTimeString()}</div>
                          ) : (
                            <div className="messages-item__time text-gray-500 text-xs ml-auto">{new Date(msg.createdAt).toLocaleTimeString()}</div>
                          )}
                        </div>
                      </div>
                    ))}
                  </div>
                  <div className="chat-messages-footer">
                    <form onSubmit={handleSubmit} className="chat-messages-form flex mt-4">
                      <div className="chat-messages-form-btns">
                        <button className="ico_emoji-happy" type="button"></button>
                        <button className="ico_gallery" type="button"></button>
                      </div>

                      <div className="chat-messages-form-controls flex-grow">
                        <input
                          type="text"
                          placeholder="Type your message..."
                          value={newMessage}
                          onChange={(e) => setNewMessage(e.target.value)}
                          className="chat-messages-input border border-gray-300 p-2"
                          required
                        />
                      </div>

                      <button
                        type="submit"
                        className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded-r-lg"
                      >
                        보내기
                      </button>

                      <div className="chat-messages-form-btn">
                        <button className="ico_microphone" type="button"></button>
                      </div>
                    </form>
                  </div>
                </>
              ) : null} {/* 선택된 채팅방이 없을 경우 메시지 표시 */}
            </div>
          </div>
        </div>
      </main>

    </>
  );
}
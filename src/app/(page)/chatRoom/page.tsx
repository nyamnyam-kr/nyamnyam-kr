"use client";

import Head from "next/head";
import Link from "next/link";
import Image from 'next/image';
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Html } from "next/document";





export default function Home1() {
  const [chatRooms, setChatRooms] = useState<ChatRoomModel[]>([]);
  const [selectedChatRoomId, setSelectedChatRoomId] = useState(String || null);
  const [selectedChatRoom, setSelectedChatRoom] = useState<ChatRoomModel | null>(null); // 채팅방 정보
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [messages, setMessages] = useState<ChatModel[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const [sender, setSender] = useState(""); // 사용자 ID

  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 상태 추가
  const [totalPages, setTotalPages] = useState(1); // 총 페이지 수 상태 추가

  const [selectChatRooms, setSelectChatRooms] = useState<any[]>([]);
  const router = useRouter();

  // 기본 상태
  useEffect(() => {
    fetchData();
  }, []); // currentPage가 변경될 때마다 데이터 새로 고침

  
  const fetchData = async () => {
    setLoading(true); // 로딩 상태 시작

    try {
      const response = await fetch(`http://localhost:8081/api/chatRoom/findAll`);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      setChatRooms(data);

      const totalCountResponse = await fetch('http://localhost:8081/api/chatRoom/count');
      const totalCount = await totalCountResponse.json();
      setTotalPages(Math.ceil(totalCount / 10));
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false); // 로딩 상태 종료
    }
  };



  // 디테일 원
  const handleDetails = (id: any) => {
    console.log(id)
    router.push('/channel/details/${id}');
  };

  const handleCheck = (id: any) => {
    setSelectChatRooms((prevSelected: string[]) =>
      prevSelected.includes(id)
        ? prevSelected.filter((channelId: string) => channelId !== id)
        : [...prevSelected, id]
    );
  };

  useEffect(() => {
    if (!selectedChatRoomId) return;

    // 선택된 채팅방 정보 및 메시지 가져오기
    fetch(`http://localhost:8081/api/chatRoom/${selectedChatRoomId}`)
      .then(response => response.json())
      .then((data: ChatRoomModel) => {
        setSelectedChatRoom(data);
      })
      .catch(error => console.error("채팅방 정보를 가져오는 중 오류 발생:", error));

    const eventSource = new EventSource(`http://localhost:8081/api/chats/${selectedChatRoomId}`);

    eventSource.onmessage = (event) => {
      const data: ChatModel = JSON.parse(event.data);
      setMessages((prevMessages) => [...prevMessages, data]);
    };

    return () => {
      eventSource.close(); // 컴포넌트 언마운트 시 EventSource 닫기
    };
  }, [selectedChatRoomId]);

  const sendMessage = async () => {
    if (newMessage.trim() === "" || sender.trim() === "") {
      alert("메시지와 사용자 이름을 입력해주세요.");
      return;
    }

    const chat = {
      sender: sender,
      message: newMessage,
      chatRoomId: selectedChatRoomId,
    };

    try {
      const response = await fetch(`http://localhost:8081/api/chats/${selectedChatRoomId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(chat),
      });

      if (!response.ok) {
        throw new Error("메시지 전송 실패");
      }

      setNewMessage(""); // 메시지 입력 필드 초기화
    } catch (error) {
      console.error("메시지 전송 중 오류 발생:", error);
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
      setSender(""); // 사용자 이름 입력 필드 초기화
    }
  }, [selectedChatRoomId]);




  const handleDelete = () => {
    if (selectChatRooms.length === 0) {
      alert("삭제할 게시글을 선택해주세요.");
      return;
    }
    if (window.confirm("선택한 게시글을 삭제하시겠습니까?")) {
      Promise.all(selectChatRooms.map((id: any) =>
        fetch(`http://localhost:8081/api/chatRoom/deleteById/${id}`, { method: 'DELETE' })
      ))
        .then(() => {
          alert("게시글이 삭제되었습니다.");
          setChatRooms(prevChatRooms =>
            prevChatRooms.filter(room => !selectChatRooms.includes(room.id)) // 삭제한 방을 제외
          );
          setSelectChatRooms([]); // 선택 초기화
        })
        .catch(error => {
          console.error('Delete operation failed:', error);
          alert("삭제 중 오류가 발생했습니다.");
        });
    }
  };

  // 검색어에 따른 필터링
  const filteredChatRooms = chatRooms.filter((room) =>
    room.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <html lang="en">
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
                            </a>
                            {/* 참가자 목록: 1 비율로 출력 */}
                            <div style={{ flexGrow: 1, flexShrink: 1, textAlign: 'right', marginRight: '10px', maxWidth: '150px' }}>
                              {room.participants && room.participants.length > 0 ? (
                                <ul style={{ listStyleType: 'none', padding: 0, margin: 0 }}>
                                  {room.participants.map((participant: any, index: number) => (
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
                                      {participant.nickname || "No Nickname"}
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
                <div className="user-item">
                  <div className="user-item__avatar">
                    <Image src="/assets/img/user-list-4.png" alt="user" width={40} height={40} />
                  </div>
                  <div className="user-item__desc">
                    <div className="user-item__name">Sofia Dior</div>
                  </div>
                </div>
                <div>
                  <a className="ico_call" href="#!"></a>
                  <a className="ico_info-circle" href="#!"></a>
                </div>
              </div>
              <div className="chat-messages-body flex-1 overflow-y-auto p-4 bg-white shadow-md rounded-lg space-y-4">
                {messages.map((msg, index) => (
                  <div
                    key={index}
                    className={`w-full messages-item ${msg.sender !== sender ? '--your-message' : '--friend-message'} flex`}
                  >
                    {msg.sender === sender ? (
                      <div>
                        <div className="messages-item__avatar flex items-center">
                          <Image src="/assets/img/user-list-4.png" alt="img" width={40} height={40} />
                          <p className="ml-auto inline">{msg.sender}</p>
                        </div>
                        <div className="messages-item__text">{msg.message}</div>
                      </div>
                    ) : (
                      <div>
                        <div className="messages-item__avatar flex items-center">
                          <Image src="/assets/img/user-list-3.png" alt="img" width={40} height={40} />
                          <p className="ml-auto">{msg.sender}</p>
                        </div>
                        <div className="messages-item__text">{msg.message}</div>
                      </div>
                    )}
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
                      placeholder="Your name"
                      value={sender}
                      onChange={(e) => setSender(e.target.value)}
                      className="input border border-gray-300 p-2 rounded-l-lg"
                      required
                    />
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
            </div>
          </div>
        </div>
      </main>

    </html>
  );
}
"use client";

import { useEffect, useState } from "react";

interface Chat {
  id: string;
  sender: string;
  message: string;
  createdAt: string;
}

export default function ChatRoom({ params }: { params: { id: string } }) {
  const [messages, setMessages] = useState<Chat[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const [sender, setSender] = useState(""); // 사용자 ID를 설정할 수 있습니다.
  const chatRoomId = params.id;

  useEffect(() => {
    const eventSource = new EventSource(`http://localhost:8080/api/chats/${chatRoomId}`);
    
    eventSource.onmessage = (event) => {
      const data: Chat = JSON.parse(event.data);
      setMessages((prevMessages) => [...prevMessages, data]);
    };

    return () => {
      eventSource.close(); // 컴포넌트가 언마운트 될 때 EventSource 닫기
    };
  }, [chatRoomId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

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
      const response = await fetch(`http://localhost:8080/api/chats/${chatRoomId}`, {
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
      console.error("Error sending message:", error);
    }
  };

  return (
    <div className="chat-room flex flex-col h-screen p-4 bg-gray-100">
      <h1 className="text-2xl font-bold text-center mb-4">Chat Room {chatRoomId}</h1>

      <div className="messages flex-1 overflow-y-auto p-4 bg-white shadow-md rounded-lg space-y-4">
        {messages.map((msg, index) => (
          <div
            key={index}
            className={`flex ${msg.sender === sender ? 'justify-end' : 'justify-start'}`}
          >
            <div className={`p-3 rounded-lg shadow-md ${msg.sender === sender ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-900'} max-w-xs`}>
              <p className="font-semibold">{msg.sender === sender ? "You" : msg.sender}</p>
              <p>{msg.message}</p>
              <small className="block text-xs mt-2">{new Date(msg.createdAt).toLocaleTimeString()}</small>
            </div>
          </div>
        ))}
      </div>

      <form onSubmit={handleSubmit} className="message-form flex mt-4">
        <input
          type="text"
          placeholder="Your name"
          value={sender}
          onChange={(e) => setSender(e.target.value)}
          className="input flex-grow border border-gray-300 p-2 rounded-l-lg"
          required
        />
        <input
          type="text"
          placeholder="Type your message..."
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          className="input flex-grow border border-gray-300 p-2"
          required
        />
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded-r-lg"
        >
          Send
        </button>
      </form>
    </div>
  );
}

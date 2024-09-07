"use client";

import { insertReply } from "@/app/service/reply/reply.api";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useState } from "react";

export default function ReplyRegister() {
  const router = useRouter();
  const [formData, setFormData] = useState<ReplyModel>({
    id: 0,
    content: '',
    entryDate: '',
    modifyDate: ''
  });

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const {name, value} = e.target as HTMLInputElement;
    setFormData({
      ...formData,
      [name]: value,
    });
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await insertReply(formData);
    router.push('/reply/replies')
  };


  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1>[댓글 등록]</h1>
      <form onSubmit={handleSubmit} className="space-y-4 p-4">
        <div>
          <label>ID:</label>
          <input 
            type="number"
            name="id"
            value={formData.id}
            onChange={handleChange}
            className="border rounded p-2 w-full"
          />
        </div>
        <div>
          <label>내용</label>
          <input 
            type="text"
            name="content"
            value={formData.content}
            onChange={handleChange}
            className="border rounded p-2 w-full" 
          />
        </div>
        <div>
          <label>작성일</label>
          <input 
            type="date"
            name="entryDate"
            value={formData.entryDate}
            onChange={handleChange} 
            className="border rounded p-2 w-full"
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
        등록하기
        </button>
      </form>
    </main>
  );
}
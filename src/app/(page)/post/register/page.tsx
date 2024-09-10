"use client";
import { insertPost } from "@/app/service/post/post.api";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useState } from "react";

export default function PostRegister() {
  const router = useRouter();
  const [formData, setFormData] = useState<PostModel>({
    id: 0,
    content: '',
    taste: 0,
    clean: 0,
    service: 0,
    entryDate: '',
    modifyDate: ''
  });

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value, type, checked } = e.target as HTMLInputElement;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await insertPost(formData);
    router.push('/')
  };
  const imageRegister = () => {
    router.push('/image/register');
   }

  return (
    <main className="flex min-h-screen flex-col items-center p-6">
      <h1>[게시글 등록]</h1>
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
        <label>내용:</label>
        <input
          type="text"
          name="content"
          value={formData.content}
          onChange={handleChange}
          className="border rounded p-2 w-full"
        />
      </div>
      <div>
        <label className="block mb-1 font-medium">맛:</label>
        <input
          type="number"
          name="taste"
          value={formData.taste}
          onChange={handleChange}
          className="border rounded p-2 w-full"
        />
      </div>
      <div>
        <label>청결:</label>
        <input
          type="number"
          name="clean"
          value={formData.clean}
          onChange={handleChange}
          className="border rounded p-2 w-full"
        />
      </div>
      <div>
        <label>서비스:</label>
        <input
          type="number"
          name="service"
          value={formData.service}
          onChange={handleChange}
          className="border rounded p-2 w-full"
        />
      </div>
      <div>
        <label>작성일:</label>
        <input
          type="date"
          name="entryDate"
          value={formData.entryDate}
          onChange={handleChange}
          className="border rounded p-2 w-full"
        />
      </div>
      <div>
        <label>수정일:</label>
        <input
          type="date"
          name="modifyDate"
          value={formData.modifyDate}
          onChange={handleChange}
          className="border rounded p-2 w-full"
        />
      </div>
      <button
         className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
         onClick={imageRegister}>  
         첨부파일
    </button>
      <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
        등록하기
      </button>
      </form>
    </main>
  );
}
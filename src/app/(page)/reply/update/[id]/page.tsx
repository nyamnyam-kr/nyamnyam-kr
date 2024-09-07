"use client";
import { useParams, useRouter } from "next/navigation";
import React, { FormEvent, useEffect, useState } from "react";

export default function ReplyUpdate() {
  const {id} = useParams();
  const router = useRouter();

  const [formData, setFormData] = useState<ReplyModel>({
    id: 0,
    content: '',
    entryDate: '',
    modifyDate:''
  });

  useEffect (() => {
      const fetchReply = async () => {
        try{
          const response = await fetch(`http://localhost:8080/replies/${id}`)
          if(!response.ok){
            throw new Error('Failed to fetch reply');
          }
          const data: ReplyModel = await response.json();
          setFormData(data);
        }catch(error){
          console.error('Error fetching reply:', error);
        }
      };
      if(id){
        fetchReply();
      }
  },[id]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/replies/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error('Failed to update reply');
      }

      router.push(`/reply/details/${id}`);
    } catch (error) {
      console.error('Error updating reply:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">댓글 수정</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-2xl">
        <div className="mb-4">
          <label htmlFor="content" className="block mb-2">내용</label>
          <textarea 
            id="content"
            name="content"
            value={formData.content}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            rows={1}
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
          수정
        </button>
      </form>
    </main>
  );
}
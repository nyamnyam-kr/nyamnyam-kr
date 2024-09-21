"use client";
import { useParams, useRouter } from "next/navigation";
import React, { FormEvent, useEffect, useState } from "react";

export default function ReplyUpdate() {
  const { restaurantId, id, replyId } = useParams();
  const router = useRouter();

  const [formData, setFormData] = useState<ReplyModel>({
    id: 0,
    content: '',
    postId: Number(id),
    userId: 1 // 강제값으로 수정 필요 
  });

  useEffect(() => {
    const fetchReply = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/replies/${replyId}`);

        if (!response.ok) {
          throw new Error('Failed to fetch reply');
        }

        const contentType = response.headers.get('content-type');

        if (contentType && contentType.includes('application/json')) {
          const data: ReplyModel = await response.json();
          console.log("Fetched data:", data); 
          setFormData({
            ...data,
            postId: Number(id), 
            userId: 1
          });
        } else {
          const errorText = await response.text();
          console.error('Response is not JSON format:', errorText);
          throw new Error('Response is not JSON');
        }
      } catch (error) {
        console.error('Error fetching reply:', error);
      }
    };

    if (replyId) {
      fetchReply();
    }
  }, [id, replyId]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const { content, postId, userId } = formData;
    const replyUpdateData = { content, postId, userId }; 
  
    try {
      const response = await fetch(`http://localhost:8080/api/replies/${replyId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(replyUpdateData) 
      });
  
      if (response.ok) {
        console.log('Reply updated successfully.');
        router.push(`/post/${restaurantId}/${id}/reply`);
      } else {
        console.error('Failed to update reply');
      }
    } catch (error) {
      console.error("Error during reply update:", error);
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
        <div className="flex gap-4">
          <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
            수정
          </button>
          <button
            type="button"
            className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
            onClick={()=> router.push(`/post/${restaurantId}/${id}/reply`)}
          >
            뒤로가기
          </button>
        </div>
      </form>
    </main>
  );
}

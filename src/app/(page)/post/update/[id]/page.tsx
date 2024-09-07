"use client";
import { useParams, useRouter } from 'next/navigation';
import { FormEvent, useEffect, useState } from 'react'

export default function PostUpdate() {
  const router = useRouter();
  const {id} = useParams();

  const [formData, setFormData] = useState<PostModel>({
    id: 0,
    taste: 0,
    clean: 0,
    service: 0,
    content: '',
    entryDate: '',
    modifyDate: ''
  });

  useEffect(()=> {
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:8080/posts/${id}`);
        if (!response.ok) {
          throw new Error('Failed to fetch post');
        }
        const data: PostModel = await response.json();
        setFormData(data);
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    if (id) {
      fetchPost();
    }
  },[id]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/posts/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error('Failed to update post');
      }

      router.push(`/post/details/${id}`);
    } catch (error) {
      console.error('Error updating post:', error);
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
    <h1 className="text-2xl font-bold mb-6">게시글 수정</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-2xl">
        <div className="mb-4">
          <label htmlFor="taste" className="block mb-2">맛</label>
          <input
            type="number"
            id="taste"
            name="taste"
            value={formData.taste}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          />
        </div>
        <div className="mb-4">
          <label htmlFor="clean" className="block mb-2">청결</label>
          <input
            type="number"
            id="clean"
            name="clean"
            value={formData.clean}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          />
        </div>
        <div className="mb-4">
          <label htmlFor="service" className="block mb-2">서비스</label>
          <input
            type="number"
            id="service"
            name="service"
            value={formData.service}
            onChange={handleChange}
            className="w-full p-2 border rounded"
          />
        </div>
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
"use client"
import { useParams, useRouter } from "next/navigation";
import { FormEvent, useEffect, useState } from "react";

export default function TagUpdate() {
  const { id } = useParams();
  const router = useRouter();

  const [formData, setFormData] = useState<TagModel>({
    tagCategory: '',
    name: '',
    postTags:[]
  });

  useEffect(() => {
    const fetchTag = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/tags/${id}`)
        if (!response.ok) {
          throw new Error('Failed to fetch tag');
        }
        const data: TagModel = await response.json();
        setFormData(data);
      } catch (error) {
        console.error('Error fetching tag:', error);
      }
    };
    if (id) {
      fetchTag();
    }
  }, [id]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/tags/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error('Failed to update tag');
      }
      router.push(`/tag/details/${id}`);
    } catch (error) {
      console.error('Error updating tag:', error);
    }

  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">[Tag 수정]</h1>
      <div>
      <p><strong>Category: </strong>{formData?.tagCategory}</p>
      </div>
      <form onSubmit={handleSubmit} className="w-full max-w-2xl">
        <div className="mb-4">
            <label htmlFor="name" className="block mb-2">Tag</label>
            <textarea 
              name="name" 
              id="name"
              value={formData.name}
              onChange={handleChange}
              className="w-full p-2 border rounded"
              rows={1}
              >
            </textarea>
        </div>
        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
          수정
        </button>
      </form>
    </main>
  );
}
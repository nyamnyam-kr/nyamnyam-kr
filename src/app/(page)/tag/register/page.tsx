"use client";

import { insertTag } from "@/app/service/tag/tag.api";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useState } from "react";

export default function TagRegister() {
  const router = useRouter();
  const [formData, setFormData] = useState<TagModel>({
    name: '',
    tagCategory: ''
  });

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await insertTag(formData);
    router.push('/tag/tags')
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const {name, value} = e.target as HTMLInputElement;
    setFormData({
      ...formData,
      [name]: value,
    });
  }

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1>[태그 등록]</h1>
      <form onSubmit={handleSubmit} className="space-y-4 p-4">
          <div>
            <label>Tag</label>
            <input 
              type="text" 
              name="name"
              value={formData.name}
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
"use client";

import { insertTag } from "@/app/service/tag/tag.api";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";

export default function TagRegister() {
  const router = useRouter();
  // tagCategory의 초기값을 빈 배열로 설정합니다.
  const [tagCategory, setTagCategory] = useState<string[]>([]);
  const [formData, setFormData] = useState<TagModel>({
    name: '',
    tagCategory: '',
    postTags: []
  });

  useEffect(() => {
    const fetchTagCategory = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/tags/tag-category'); // 카테고리 API URL
        const data = await response.json();
        // tagCategory 배열에 데이터가 없을 경우 빈 배열로 설정
        if (Array.isArray(data)) {
          setTagCategory(data); 
        } else {
          setTagCategory([]); // 배열이 아닌 값이 들어오면 빈 배열로 설정
        }
      } catch (error) {
        console.error("태그 카테고리를 불러오는데 실패했습니다.", error);
        setTagCategory([]); // 오류 발생 시 빈 배열로 설정
      }
    };
    fetchTagCategory();
  }, []); 

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await insertTag(formData); 
    router.push('/tag/tags'); 
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1>[태그 등록]</h1>
      <form onSubmit={handleSubmit} className="space-y-4 p-4">
      <div>
          <label>태그 카테고리</label>
          <select
            name="tagCategory"
            value={formData.tagCategory}
            onChange={handleChange}
            className="border rounded p-2 w-full"
          >
            <option value="">선택</option>
            {tagCategory.map((category) => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>
        
        <div>
          <label>태그명</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className="border rounded p-2 w-full"
          />
        </div>

        <button
          type="submit"
          className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          등록하기
        </button>
      </form>
    </main>
  );
}

"use client";

import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import { tag } from "src/app/api/tag/tag.api";
import { initialTag, TagModel } from "src/app/model/tag.model";
import { tagService } from "src/app/service/tag/tag.service";

export default function TagRegister() {
  const router = useRouter();
  const [tagCategory, setTagCategory] = useState<string[]>([]);
  const [formData, setFormData] = useState<TagModel>(initialTag);

  useEffect(() => {
    fetchTagCategory();
  }, []); 

  const fetchTagCategory = async () => {
    const data = await tag.getCategoryNames(); 
    setTagCategory(data);
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await tagService.insert(formData); 
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
    <main className="flex min-h-screen flex-col items-center" style={{ marginTop: '30px' }}>
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
          className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2"
        >
          등록하기
        </button>
      </form>
    </main>
  );
}

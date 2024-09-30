"use client"
import { useParams, useRouter } from "next/navigation";
import { FormEvent, useEffect, useState } from "react";
import { fetchTagByName, updateTag } from "src/app/api/tag/tag.api";
import { initialTag, TagModel } from "src/app/model/tag.model";

export default function TagUpdate() {
  const { name } = useParams() as { name: string };
  const router = useRouter();
  const [formData, setFormData] = useState<TagModel>(initialTag);

  useEffect(() => {
    fetchTag(name);
  }, [name]);

  const fetchTag = async (name: string) => {
    const data = await fetchTagByName(name);
    setFormData(data);
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await updateTag(name, formData);
    router.push(`/tag/details/${name}`);
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

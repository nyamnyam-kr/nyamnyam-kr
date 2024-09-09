"use client";
import { error } from "console";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function TagDetail() {
  const [tags, setTags] = useState<TagModel | null>(null);
  const { id } = useParams();
  const router = useRouter();

  useEffect(() => {
    if (id) {
      fetch(`http://localhost:8080/api/tags/${id}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to fetch tag details");
          }
          return response.json();
        })
        .then((data) => setTags(data))
        .catch((error) => {
          console.error("Fetch error:", error);
        })
    }
  }, [id]);

  const handleUpdate = () => {
    router.push(`/tag/update/${id}`);
  }

  const handleTagList = () => {
    router.push('/tag/tags');
  }

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">Tag 상세화면</h1>
      <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl">
        <div className="space-y-4">
          <p><strong>NO: </strong>{tags?.id}</p>
          <p><strong>Tag: </strong>{tags?.name}</p>
        </div>
      </div>
      <div className="mt-4">
        <button
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
          onClick={handleUpdate}
        >
          수정하기
        </button>
        <button
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
          onClick={handleTagList}
        >
          목록
        </button>
      </div>
    </main>
  );
}
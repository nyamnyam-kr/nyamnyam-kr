"use client";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { tag } from "src/app/api/tag/tag.api";
import { TagModel } from "src/app/model/tag.model";

export default function TagDetail() {
  const [tags, setTags] = useState<TagModel | null>(null);
  const { name } = useParams() as {name:string};
  const router = useRouter();

  useEffect(() => {
    fetchTag(name);
  }, [name]);

  const fetchTag = async (name: string) => {
    const data = await tag.getTagByName(name);
    setTags(data);
  }

  return (
    <main className="flex min-h-screen flex-col items-center" style={{ marginTop: '30px' }}>
      <h1 className="text-2xl font-bold mb-6">Tag 상세화면</h1>
      <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl">
        <div className="space-y-4">
          <p><strong>Category: </strong>{tags?.tagCategory}</p>
          <p><strong>Tag: </strong>{tags?.name}</p>
        </div>
      </div>
      <div className="mt-4">
        {/* <button
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
          onClick={() => router.push(`/tag/update/${name}`)}
        >
          수정하기
        </button> */}
        <button
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
          onClick={() => router.push(`/tag/tags`)}
        >
          목록
        </button>
      </div>
    </main>
  );
}
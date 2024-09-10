"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation"; // useRouter를 사용합니다


export default function GroupDetail() {
  const [group, setGroup] = useState<GroupModel | null>(null);
  const { id } = useParams();
  const router = useRouter(); // URL 파라미터를 가져옵니다

  useEffect(() => {
    if (id) {
      fetch(`http://localhost:8080/groups/findById/${id}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to fetch group details");
          }
          return response.json();
        })
        .then((data) => setGroup(data))
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    }
  }, [id]);

  if (!group) return <p>Loading...</p>;

  const handleEdit = () => {
    router.push(`/group/update/${id}`); // 수정 페이지로 이동
  };

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <button onClick={() => router.back()} className="bg-blue-500 active:bg-blue-600 ...">
        Go Back
      </button>
      <h1 className="text-2xl font-bold mb-4">Group Details</h1>
      <div className="bg-white shadow-md rounded p-6">
        <p><strong>ID:</strong> {group.id}</p>
        <p><strong>Group Name:</strong> {group.name}</p>
        <p><strong>Group Content:</strong> {group.content}</p>
        <p><strong>Entry Date:</strong> {group.entryDate}</p>
        <p><strong>Modify Date:</strong> {group.modifiyDate}</p>
        <p><strong>D-Day:</strong> {group.dDay}</p>
        <p><strong>People:</strong> {group.people}</p>
      </div>
      <button 
        onClick={handleEdit} 
        className="bg-green-500 text-white py-2 px-4 rounded mt-4">
        수정하기
      </button>
    </main>
  );
}

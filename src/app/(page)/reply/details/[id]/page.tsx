"use client";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function ReplyDetail() {
  const [replies, setReplies] = useState<ReplyModel | null>(null);
  const {id} = useParams();
  const router = useRouter();

  useEffect(()=> {
    if (id) {
      fetch(`http://localhost:8080/api/replies/${id}`)
      .then((response)=>{
        if(!response.ok){
          throw new Error("Failed to fetch reply details");
        }
        return response.json();
      })
      .then((data)=>setReplies(data))
      .catch((error)=> {
        console.error("Fetch error:", error);
      })
    }
},[id]);

  const handleUpdate = () => {
    router.push(`/api/reply/update/${id}`);
  }

  const handleReplyList = () => {
    router.push('/api/reply/replies');
  }

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">댓글 상세화면</h1>
      <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl">
        <div className="space-y-4">
          <p><strong>NO: </strong>{replies?.id}</p>
          <p><strong>내용: </strong>{replies?.content}</p>
          <p><strong>작성일: </strong>{replies?.entryDate}</p>
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
          onClick={handleReplyList}
        >
         목록
        </button>
      </div>
    </main>
  );
}
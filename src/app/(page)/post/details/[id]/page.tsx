"use client";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";


export default function PostDetail() {
    const [posts, setPosts] = useState<PostModel | null>(null);
    const {id} = useParams();
    const router = useRouter();

    useEffect(()=> {
        if(id){
          fetch(`http://localhost:8080/api/posts/${id}`)
          .then((response)=>{
            if(!response.ok){
              throw new Error("Failed to fetch group details");
            }
            return response.json();
          })
          .then((data)=>setPosts(data))
          .catch((error)=> {
            console.error("Fetch error:", error);
          })
        }
    },[id]);

    const handleUpdate = () => {
      router.push(`/post/update/${id}`);
    }
    const handleHome = () => {
      router.push('/');
    }
    

  return (
    <main className="flex min-h-screen flex-col items-center p-6">
    <h1 className="text-2xl font-bold mb-6">게시글 상세화면</h1>
    <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl">
      <div className="space-y-4">
        <p><strong>NO: </strong> {posts?.id}</p>
        <p><strong>맛: </strong> {posts?.taste}</p>
        <p><strong>청결: </strong> {posts?.clean}</p>
        <p><strong>서비스: </strong> {posts?.service}</p>
        <p><strong>내용: </strong> {posts?.content}</p>
        <p><strong>작성일: </strong> {posts?.entryDate}</p>
      </div>
    </div>
    <div className="mt-4">
    <button
         className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
         onClick={handleUpdate}>
         수정하기
    </button>
    <button
         className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
         onClick={handleHome}>
         목록
    </button>
    </div>
  </main>
  
  );
}
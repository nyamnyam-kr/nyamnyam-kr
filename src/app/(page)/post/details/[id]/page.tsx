"use client";
import Star from "@/app/(page)/star/page";
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
    const handleReply = () => {
      router.push(`/post/${id}/reply`)
    }

    const formDate = (dateString: string) => {
      const date = new Date(dateString);
      const options: Intl.DateTimeFormatOptions = { year: '2-digit', month: '2-digit'}
      const formattedDate = new Intl.DateTimeFormat('ko-KR', options).format(date);
      const [year, month] = formattedDate.split('.').map(part => part.trim());
      return `${year}년 ${month}월`;
    }
    

  return (
    <main className="flex min-h-screen flex-col items-center p-6">
    <h1 className="text-2xl font-bold mb-6">게시글 상세화면</h1>
    <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl">
      <div className="space-y-4">
        <div className="flex items-center">
        <strong className="w-16">맛: </strong> 
        {posts?.taste !== undefined && (
          <Star w="w-6" h="h-6" readonly={true} rate={posts.taste}/>
        )}
        </div>
        <div className="flex items-center">
        <strong className="w-16">청결: </strong> 
        {posts?.taste !== undefined && (
          <Star w="w-6" h="h-6" readonly={true} rate={posts.clean}/>
        )}
        </div>
        <div className="flex items-center">
        <strong className="w-16">서비스: </strong> 
        {posts?.taste !== undefined && (
          <Star w="w-6" h="h-6" readonly={true} rate={posts.service}/>
        )}
        </div>
        <p><strong>내용: </strong> {posts?.content}</p>
        <p><strong>태그: </strong> {posts?.tags && posts?.tags.length > 0 ? posts?.tags.join(","): "태그 없음"}</p>
        <p><strong>작성일: </strong> {posts?.entryDate && formDate(posts.entryDate)}</p>
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
    <button
         className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
         onClick={handleReply}>
         댓글
    </button>
    </div>
  </main>
  
  );
}
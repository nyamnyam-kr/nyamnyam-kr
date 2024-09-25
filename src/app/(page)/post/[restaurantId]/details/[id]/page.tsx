"use client";
import Star from "@/app/(page)/star/page";
import { PostModel } from "@/app/model/post.model";
import { useParams, useRouter } from "next/navigation";
import { list } from "postcss";
import { useEffect, useState } from "react";

export default function PostDetail() {
  const [posts, setPosts] = useState<PostModel | null>(null);
  const [images, setImages] = useState<string[]>([]);
  const { id, restaurantId } = useParams();
  const router = useRouter();
  const currentUserId = 1; // 수정 필요 !!! 

  useEffect(() => {
    if (id) {
      fetch(`http://localhost:8080/api/posts/${id}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to fetch post details");
          }
          return response.json();
        })
        .then((data) => setPosts(data))
        .catch((error) => {
          console.error("Fetch error:", error);
        });

      fetch(`http://localhost:8080/api/images/post/${id}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to fetch images");
          }
          return response.json();
        })
        .then((data) => {
          const imageURLs = data.map((image: any) => image.uploadURL);
          setImages(imageURLs);
        })
        .catch((error) => {
          console.error("Failed to fetch images:", error);
        });
    }
  }, [id]);

  const formDate = (dateString: string) => {
    const date = new Date(dateString);
    const options: Intl.DateTimeFormatOptions = { year: "2-digit", month: "2-digit", day: "2-digit" };
    const formattedDate = new Intl.DateTimeFormat("ko-KR", options).format(date);
    const [year, month, day] = formattedDate.split(".").map((part) => part.trim());
    return `${year}년 ${month}월 ${day}일`;
  };

  const handleDelete = () => {
    if (window.confirm("게시글을 삭제하시겠습니까?")) {
      fetch(`http://localhost:8080/api/posts/${id}`, { method: 'DELETE' })
        .then(() => {
          alert("게시글이 삭제되었습니다.");
          router.push(`/post/${restaurantId}`);
        })
        .catch(error => {
          console.error('Delete operation failed:', error);
          alert("삭제 중 오류가 발생했습니다.");
        });
    }
  };

  return (
    <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
      <h1 className="text-2xl font-bold mb-6">게시글 상세화면</h1>
      <div className="bg-white shadow-lg rounded-lg p-6 w-full max-w-4xl">
        <div className="flex flex-col space-y-4">
          <div className="flex flex-col md:flex-row border border-indigo-600 rounded-lg p-4 shadow-lg bg-white">
            <div className="md:w-full">
              <div className="flex justify-between items-center mb-2">
                <h2 className="text-xl font-semibold mb-2">닉네임: {posts?.nickname}</h2>
              </div>
              <div className="inline-flex space-x-2 mb-2">
                <Star w="w-6" h="h-6" readonly={true} rate={posts?.averageRating} />
                <h1>{posts?.averageRating.toFixed(1)} / 5</h1>
              </div>
              <div className="mb-2">
                <p className="text-gray-700">{posts?.content}</p>
              </div>
              <div className="flex flex-col space-y-2 mb-2">
                <div className="inline-flex space-x-2">
                  <p>맛: </p>
                  <Star w="w-4" h="h-4" readonly={true} rate={posts?.taste} />
                </div>
                <div className="inline-flex space-x-2">
                  <p>청결: </p>
                  <Star w="w-4" h="h-4" readonly={true} rate={posts?.clean} />
                </div>
                <div className="inline-flex space-x-2">
                  <p>서비스: </p>
                  <Star w="w-4" h="h-4" readonly={true} rate={posts?.service} />
                </div>
              </div>
              <div className="mb-4">
                {images.length > 0 ? (
                  <div className="flex flex-wrap gap-4">
                    {images.map((url, index) => (
                      <img
                        key={index}
                        src={url}
                        alt={`이미지 ${index + 1}`}
                        className="w-48 h-auto rounded-lg shadow-lg"
                      />
                    ))}
                  </div>
                ) : (
                  <p>이미지 없음</p>
                )}
              </div>
              <div className="mb-2 flex items-center">
                <h2 className="text-lg font-bold mb-2 flex-shrink-0 self-center">태그: </h2>
                {posts?.tags && posts.tags.length > 0 ? (
                  <ul className="flex flex-wrap gap-2 ml-2 items-center">
                    {posts.tags.map((tag, index) => (
                      <li
                        key={index}
                        className="rounded-full border border-sky-100 bg-sky-50 px-2 py-1 text-sky-700"
                      >
                        {tag}
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p>태그 없음</p>
                )}
              </div>
              <div className="flex justify-between items-center mb-2 text-gray-500">
              {posts?.entryDate && formDate(posts.entryDate)}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="mt-4">
        <button
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
          onClick={() => router.push(`/post/${restaurantId}`)}
        >
          목록
        </button>
        {posts?.userId === currentUserId && (
          <>
            <button
              className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
              onClick={() => router.push(`/post/${restaurantId}/update/${id}`)}
            >
              수정하기
            </button>
            <button
              className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded mr-2"
              onClick={handleDelete}
            >
              삭제하기
            </button>
          </>
        )}
      </div>
    </main>
  );  
}
"use client";
import Star from "@/app/(page)/star/page";
import { useParams, useRouter } from "next/navigation";
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
    const options: Intl.DateTimeFormatOptions = { year: "2-digit", month: "2-digit" };
    const formattedDate = new Intl.DateTimeFormat("ko-KR", options).format(date);
    const [year, month] = formattedDate.split(".").map((part) => part.trim());
    return `${year}년 ${month}월`;
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
    <main className="flex min-h-screen flex-col items-center p-6">
      <h1 className="text-2xl font-bold mb-6">게시글 상세화면</h1>
      <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl">
        <div className="space-y-4">
          <div className="flex items-center">
            <strong className="w-16">맛: </strong>
            {posts?.taste !== undefined && (
              <Star w="w-6" h="h-6" readonly={true} rate={posts.taste} />
            )}
          </div>
          <div className="flex items-center">
            <strong className="w-16">청결: </strong>
            {posts?.taste !== undefined && (
              <Star w="w-6" h="h-6" readonly={true} rate={posts.clean} />
            )}
          </div>
          <div className="flex items-center">
            <strong className="w-16">서비스: </strong>
            {posts?.taste !== undefined && (
              <Star w="w-6" h="h-6" readonly={true} rate={posts.service} />
            )}
          </div>
          <p><strong>내용: </strong> {posts?.content}</p>
          <p><strong>태그: </strong> {posts?.tags && posts?.tags.length > 0 ? posts?.tags.join(",") : "태그 없음"}</p>
          <p><strong>작성일: </strong> {posts?.entryDate && formDate(posts.entryDate)}</p>

          <div>
            <strong>이미지:</strong>
            <div>
              {images.length > 0 ? (
                images.map((url, index) => (
                  <img
                    key={index}
                    src={url}
                    alt={`이미지 ${index + 1}`}
                    style={{ width: "200px", height: "auto" }}
                  />
                ))
              ) : (
                <p>이미지 없음</p>
              )}
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
        <button
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
          onClick={() => router.push(`/post/${restaurantId}/${id}/reply`)}
        >
          댓글
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
              onClick={handleDelete}>
              삭제하기
            </button>
          </>
        )}
      </div>
    </main>
  );
}

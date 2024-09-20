"use client"

import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";


export default function Reply() {
    const { id, postId, restaurantId } = useParams();
    const [replies, setReplies] = useState<ReplyModel[]>([]);
    const router = useRouter();

    useEffect(() => {
        if (id) {
            fetch(`http://localhost:8080/api/replies/post/${id}`)
                .then((response) => response.json())
                .then((data) => setReplies(data))
                .catch((error) => console.error("Error fetching comments:", error));
        }
    }, [id]);

    const formDate = (dateString: string) => {
        const date = new Date(dateString);
        const options: Intl.DateTimeFormatOptions = { year: '2-digit', month: '2-digit' }
        const formattedDate = new Intl.DateTimeFormat('ko-KR', options).format(date);
        const [year, month] = formattedDate.split('.').map(part => part.trim());
        return `${year}년 ${month}월`;
    }

    const handleDelete = async (replyId: number) => {
        if (window.confirm("삭제하시겠습니까?")) {
            try {
                const response = await fetch(`http://localhost:8080/api/replies/${replyId}`, { method: 'DELETE' });
                if (response.ok) {
                    setReplies(replies.filter(reply => reply.id !== replyId));
                    alert("댓글이 삭제되었습니다.");
                } else {
                    alert("댓글 삭제에 실패했습니다.");
                }
            } catch {
                alert("댓글 삭제 중 문제가 발생했습니다.");
            }
        }
    };

    const handleUpdate = (replyId: number) => {
        router.push(`/post/${id}/reply/${replyId}/update`)
    }


    return (
        <main className="flex min-h-screen flex-col items-center p-6">
            <h1 className="text-2xl font-bold mb-6">댓글</h1>
            <div className="bg-white shadow-md rounded p-6 w-full max-w-2xl space-y-4">
                {replies.length > 0 ? (
                    replies.map((r: ReplyModel) => (
                        <div key={r.id} className="border-b pb-4 mb-4">
                            <div className="flex justify-between items-center">
                                <p>{r.content}</p>
                                <small>{r.entryDate ? formDate(r.entryDate) : "날짜 없음"}</small>
                            </div>
                            <div className="mt-2 flex justify-end gap-4">
                                <button
                                    className="text-blue-500 hover:text-blue-700"
                                    onClick={() => handleUpdate(r.id!)}>
                                    수정
                                </button>
                                <button
                                    className="text-red-500 hover:text-red-700"
                                    onClick={() => handleDelete(r.id!)}>
                                    삭제
                                </button>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>댓글이 없습니다.</p>
                )}
            </div>
            <div className="flex gap-4 mt-6">
                <button
                    className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                    onClick={()=> router.push(`/post/${restaurantId}/${postId}/reply/register`)}>
                    댓글 작성
                </button>
                <button
                    className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                    onClick={() => router.push(`/post/details/${postId}`)}>
                    뒤로가기
                </button>
            </div>
        </main>
    );
}
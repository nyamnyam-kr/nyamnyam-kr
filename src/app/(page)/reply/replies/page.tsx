"use client"
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function ReplyList() {
    const [replies, setReplies] = useState<ReplyModel[]>([]);
    const [selectReplies, setSelectReplies] = useState<number[]>([]);
    const router = useRouter();

    useEffect(() => {
        fetchReply();
    }, []);

    const fetchReply = () => {
        fetch('http://localhost:8080/replies/group')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setReplies(data);
            })
            .catch((error) => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    };

    const handleCheck = (id: number) => {
        setSelectReplies(prevSelected =>
            prevSelected.includes(id)
                ? prevSelected.filter(replyId => replyId !== id)
                : [...prevSelected, id]
        );
    };

    const handleDetails = (id: number) => {
        router.push('/reply/replies/${id}');
    };

    const handleDelete = () => {
        if (selectReplies.length == 0) {
            alert("삭제할 댓글을 선택해주세요.")
            return;
        }
        if (window.confirm("선택한 댓글을 삭제하시겠습니까?")) {
            Promise.all(selectReplies.map(id =>
                fetch(`http://localhost:8080/replies/${id}`,
                    { method: 'DELETE' })
            ))
                .then(() => {
                    alert("댓글이 삭제되었습니다.");
                    setSelectReplies([]);
                    fetchReply();
                })
                .catch(error => {
                    alert("삭제 중 오류가 발생했습니다.");
                });
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                        <tr className="bg-blue-600 text-white">
                            <th className="py-3 px-4 border-b"></th>
                            <th className="py-3 px-4 border-b">No</th>
                            <th className="py-3 px-4 border-b">내용</th>
                            <th className="py-3 px-4 border-b">작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        {replies.map((r) => (
                            <tr key={r.id} className="border border-indigo-600">
                                <td className="py-3 px-4 border-b">
                                    <input
                                        type="checkbox"
                                        checked={selectReplies.includes(r.id)}
                                        onChange={() => handleCheck(r.id)}
                                    />
                                </td>
                                <td className="py-3 px-4 border-b">
                                <Link
                                        href={`/reply/details/${r.id}`}
                                        className="text-blue-600 hover:underline"
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleDetails(r.id ?? 0);
                                        }}
                                    >
                                        {r.id}
                                    </Link>
                                </td>
                                <td className="py-3 px-4 border-b">{r.content}</td>
                                <td className="py-3 px-4 border-b">{r.entryDate}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="mt-4">
                <button
                        className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                        onClick={() => router.push('/reply/register')}>
                        등록하기
                    </button>
                <button
                    className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded"
                    onClick={handleDelete}>
                    삭제하기
                </button>
                </div>
            </div>
        </main>
    );
}
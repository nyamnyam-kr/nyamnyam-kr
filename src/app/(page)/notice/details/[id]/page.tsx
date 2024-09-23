"use client";
import React, {useEffect, useState} from "react";
import {useParams, useRouter} from "next/navigation";

interface Notice {
    id: number;
    title: string;
    content: string;
    date: string;
    hits: number;
}

export default function ShowNotice() {
    const [notice, setNotice] = useState<Notice | null>(null);
    const router = useRouter();
    const {id} = useParams();

    useEffect(() => {
        const fetchNotice = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/notice/${id}`, {method: 'GET'});
                if (!response.ok) {
                    throw new Error("Failed to fetch notice details");
                }
                const data = await response.json();
                setNotice(data);
            } catch (error) {
                console.error("Fetch error:", error);
            }
        };

        fetchNotice();
    }, [id]);

    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">

                    {notice ? (
                        <>
                            <tr className="bg-blue-600 text-white">
                                <th className="py-3 px-4 border-b">제목</th>
                                <td className="py-3 px-4 border-b">{notice.title}</td>
                            </tr>
                            <tr>
                                <th className="py-3 px-4 border-b">조회수</th>
                                <td className="py-3 px-4 border-b">{notice.hits}</td>
                            </tr>
                            <tr>
                                <th className="py-3 px-4 border-b">작성일</th>
                                <td className="py-3 px-4 border-b">{notice.date}</td>
                            </tr>
                            <tr>
                                <th className="py-3 px-4 border-b">내용</th>
                                <td className="py-3 px-4 border-b">{notice.content}</td>
                            </tr>


                        </>
                    ) : (

                        <tr>
                            <td className="py-3 px-4 border-b" colSpan={3}>로딩 중...</td>
                        </tr>

                    )}
                </table>
                {notice && (
                    <div className="mt-4">
                        <button
                            onClick={() => router.push(`/notice/update/${id}`)}
                            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                        >
                            수정하기
                        </button>
                    </div>
                )}
            </div>
        </main>
    );
}

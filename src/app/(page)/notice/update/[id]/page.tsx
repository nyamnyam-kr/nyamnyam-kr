"use client";
import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";

interface Notice {
    id: number;
    title: string;
    content: string;
    date: string;
    hits: number;
}

export default function UpdateNotice() {
    const [notice, setNotice] = useState<Notice | null>(null);
    const [title, setTitle] = useState<string>('');
    const [content, setContent] = useState<string>('');
    const router = useRouter();
    const { id } = useParams();

    useEffect(() => {
        const fetchNotice = async () => {
            if (!id) return; // ID가 null이 아닐 때만 실행
            try {
                const response = await fetch(`http://localhost:8080/api/notice/${id}`, { method: 'GET' });
                if (!response.ok) {
                    throw new Error("Failed to fetch notice details");
                }
                const data : Notice = await response.json();
                setNotice(data);
                setTitle(data.title);
                setContent(data.content);
            } catch (error) {
                console.error("Fetch error:", error);
            }
        };

        fetchNotice();
    }, [id]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!id) {
            console.error("Notice ID is missing");
            return; // ID가 없으면 제출을 막음
        }
        try {
            const response = await fetch(`http://localhost:8080/api/notice/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ id, title, content }),
            });

            if (!response.ok) {
                throw new Error("Failed to update notice");
            }

            alert('공지사항이 성공적으로 업데이트되었습니다!');
            router.push(`/notice/details/${id}`);
        } catch (error) {
            console.error("Update error:", error);
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <h2 className="text-lg font-bold mb-4">공지사항 수정</h2>
                {notice ? (
                    <form onSubmit={handleSubmit}>
                        <div className="mb-4">
                            <label className="block text-sm font-medium mb-2">제목</label>
                            <input
                                type="text"
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                className="w-full p-2 border border-gray-300 rounded"
                                required
                            />
                        </div>
                        <div className="mb-4">
                            <label className="block text-sm font-medium mb-2">내용</label>
                            <textarea
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                                className="w-full p-2 border border-gray-300 rounded"
                                required
                            />
                        </div>
                        <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded">
                            업데이트
                        </button>
                    </form>
                ) : (
                    <p>로딩 중...</p>
                )}
            </div>
        </main>
    );
}

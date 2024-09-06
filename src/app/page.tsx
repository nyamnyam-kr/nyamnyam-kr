"use client";
import React, { useEffect, useState } from "react";
import { useRouter } from 'next/navigation';
import Link from "next/link";

interface PostModel {
    id: number;
    taste: number;
    clean: number;
    service: number;
    content: string;
    entryDate: string;
}

export default function Home() {
    const [posts, setPosts] = useState<PostModel[]>([]);
    const [selectPosts, setSelectPosts] = useState<number[]>([]);
    const router = useRouter();

    useEffect(() => {
        fetchPosts();
    }, []);

    const fetchPosts = () => {
        fetch('http://localhost:8080/posts/group')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setPosts(data);
            })
            .catch((error) => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    };

    const handleDetails = (id: number) => {
        router.push('/post/details/${id}');
    };

    const handleCheck = (id: number) => {
        setSelectPosts(prevSelected =>
            prevSelected.includes(id)
                ? prevSelected.filter(postId => postId !== id)
                : [...prevSelected, id]
        );
    };

    const handleDelete = () => {
        if (selectPosts.length === 0) {
            alert("삭제할 게시글을 선택해주세요.");
            return;
        }

        if (window.confirm("선택한 게시글을 삭제하시겠습니까?")) {
            Promise.all(selectPosts.map(id =>
                fetch(`http://localhost:8080/posts/${id}`, { method: 'DELETE' })
            ))
                .then(() => {
                    alert("선택한 게시글이 삭제되었습니다.");
                    setSelectPosts([]);
                    fetchPosts();
                })
                .catch(error => {
                    console.error('Delete operation failed:', error);
                    alert("삭제 중 오류가 발생했습니다.");
                });
        }
    };


    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                        <tr className="bg-blue-600 text-white">
                            <th className="py-3 px-4 border-b"></th>
                            <th className="py-3 px-4 border-b">No</th>
                            <th className="py-3 px-4 border-b">맛</th>
                            <th className="py-3 px-4 border-b">청결</th>
                            <th className="py-3 px-4 border-b">친절</th>
                            <th className="py-3 px-4 border-b">내용</th>
                            <th className="py-3 px-4 border-b">작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        {posts.map((p) => (
                            <tr key={p.id} className="border border-indigo-600">
                                <td className="py-3 px-4 border-b">
                                    <input
                                        type="checkbox"
                                        checked={selectPosts.includes(p.id)}
                                        onChange={() => handleCheck(p.id)}
                                    />
                                </td>
                                <td className="py-3 px-4 border-b">
                                    <Link
                                        href={`/post/details/${p.id}`}
                                        className="text-blue-600 hover:underline"
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleDetails(p.id ?? 0);
                                        }}
                                    >
                                        {p.id}
                                    </Link>
                                </td>
                                <td className="py-3 px-4 border-b">{p.taste}</td>
                                <td className="py-3 px-4 border-b">{p.clean}</td>
                                <td className="py-3 px-4 border-b">{p.service}</td>
                                <td className="py-3 px-4 border-b">{p.content}</td>
                                <td className="py-3 px-4 border-b">{p.entryDate}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="mt-4">
                    <button
                        className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                        onClick={() => router.push('post/register')}>
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
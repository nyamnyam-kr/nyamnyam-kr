"use client";
import React, { useEffect, useState } from "react";
import { useRouter } from 'next/navigation';
import Link from "next/link";
import Star from "./(page)/star/page";

export default function Home() {
    const [posts, setPosts] = useState<PostModel[]>([]);
    const [selectPosts, setSelectPosts] = useState<number[]>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const router = useRouter();

    useEffect(() => {
        fetchPosts();
    }, []);

    const fetchPosts = () => {
        fetch('http://localhost:8080/api/posts/group/1')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
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
                fetch(`http://localhost:8080/api/posts/${id}`, { method: 'DELETE' })
            ))
                .then(() => {
                    alert("게시글이 삭제되었습니다.");
                    setSelectPosts([]);
                    fetchPosts();
                })
                .catch(error => {
                    console.error('Delete operation failed:', error);
                    alert("삭제 중 오류가 발생했습니다.");
                });
        }
    };

    const handleCrawling = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/posts/crawling`, { method: 'GET' });
            if (response.ok) {
                const data = await response.json();
                if (data.length > 0) {
                    alert(`크롤링 결과를 받았습니다: ${data.length}개의 항목`);
                } else {
                    alert('크롤링 결과가 없습니다.');
                }
            } else {
                throw new Error('응답 오류');
            }
        } catch (error: any) {
            alert(`크롤링 오류 발생: ${error.message}`);
        }
    };

    const handleNone = async () => {
        alert('크롤링 막았놓았습니다.')
    }

    const handlePage = async (pageNo: number) => {
        try {
            const response = await fetch(`http://localhost:8080/api/posts/group/${pageNo}`, { method: 'GET' });
            if (response.ok) {
                const data = await response.json();
                setPosts(data);
                setCurrentPage(pageNo);
            } else {
                throw new Error('응답 오류');
            }
        } catch (error: any) {
            alert(`페이지 오류 발생: ${error.message}`);
        }
    };

    const formatDate = (dateString: string) => {
        if (!dateString) return '';

        const date = new Date(dateString);
        const options: Intl.DateTimeFormatOptions = { year: '2-digit', month: '2-digit' };
        const formattedDate = new Intl.DateTimeFormat('ko-KR', options).format(date);

        const [year, month] = formattedDate.split('.').map(part => part.trim());
        return `${year}년 ${month}월`;
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
                            <th className="py-3 px-4 border-b">평균평점</th>
                            <th className="py-3 px-4 border-b">태그</th>
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
                                <td className="py-3 px-4 border-b">{formatDate(p.entryDate)}</td>
                                <td className="py-3 px-4 border-b"><Star w="w-4" h="h-4" readonly={true} rate={p.averageRating} /></td>
                                <td className="py-3 px-4 border-b">{p.tags && p.tags.length > 0 ? p.tags.join(", ") : "태그 없음"}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="mt-4">
                    <button
                        className="bg-transparent hover:bg-green-500 text-green-700 font-semibold hover:text-white py-2 px-4 border border-green-500 hover:border-transparent rounded mr-2"
                        onClick={handleNone}>
                        크롤링
                    </button>
                    <button
                        className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                        onClick={() => router.push('post/register')}>
                        등록하기
                    </button>
                    <button
                        className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded mr-2"
                        onClick={handleDelete}>
                        삭제하기
                    </button>
                    <button
                        className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                        onClick={() => handlePage(1)}>
                        첫 페이지
                    </button>
                </div>
            </div>
            <nav aria-label="Page navigation example">
                <ul className="flex items-center -space-x-px h-8 text-sm mt-4">
                    <li>
                        <a href="#" className="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                            <span className="sr-only">Previous</span>
                            <svg className="w-2.5 h-2.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4" />
                            </svg>
                        </a>
                    </li>
                    <li>
                        <a href="#" className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">1</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => handlePage(2)} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">2</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => handlePage(3)} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">3</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => handlePage(4)} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">4</a>
                    </li>
                    <li>
                        <a href="#" onClick={() => handlePage(5)} className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">5</a>
                    </li>
                    <li>
                        <a href="#" className="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
                            <span className="sr-only">Next</span>
                            <svg className="w-2.5 h-2.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4" />
                            </svg>
                        </a>
                    </li>
                </ul>
            </nav>
        </main>
    );
}
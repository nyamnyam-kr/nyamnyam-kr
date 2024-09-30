"use client";
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { NoticeModel } from "src/app/model/notice.model";
import { fetchNoticeList } from "src/app/service/notice/notice.service";
import Link from "next/link";
import Image from "next/image";

export default function ShowNotice() {
    const [notice, setNotice] = useState<NoticeModel[]>([]);
    const router = useRouter();

    useEffect(() => {
        const loadNotices = async () => {
            try {
                const notices = await fetchNoticeList();
                setNotice(notices);
            } catch (error) {
                console.error("공지사항을 불러오는 데 오류가 발생했습니다:", error);
            }
        };

        loadNotices(); // 함수 호출
    }, []);

    const moveToOne = (id: number) => {
        router.push(`/notice/details/${id}`);
    };

    const moveToInsert = () => {
        router.push('/notice/register'); // 공지사항 추가 페이지로 이동
    };

    const moveDashboard = () => {
        router.push('/admin/dashboard')
    }

    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100 mt-20">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full bg-white rounded-lg text-center">
                    <thead className="bg-gray-100 border-b border-gray-300 text-center">
                    <tr>
                        <th scope="col" className="py-3 text-left text-sm font-bold uppercase text-secondary">번호</th>
                        <th scope="col" className="py-3 text-left text-sm font-bold uppercase text-secondary">제목</th>
                        <th scope="col" className="py-3 text-left text-sm font-bold uppercase text-secondary">조회수</th>
                        <th scope="col" className="py-3 text-right text-sm font-bold uppercase text-secondary">날짜</th>
                    </tr>
                    </thead>
                    <tbody>
                    {notice.map((n) => (
                        <tr key={n.id}
                            className="item duration-300 border-b border-gray-200 hover:bg-gray-50 cursor-pointer"
                            onClick={() => moveToOne(n.id)}>
                            <td className="py-3 text-left">
                                <strong className="text-title">{n.id}</strong>
                            </td>
                            <td className="py-3">
                                <Link href={`/product/default/${n.id}`} className="product flex items-center gap-3">
                                    <div className="info flex flex-col">
                                        <strong className="product_name text-button">{n.title}</strong>
                                        <span className="product_tag caption1 text-secondary"></span>
                                    </div>
                                </Link>
                            </td>
                            <td className="py-3">{n.hits}</td>
                            <td className="py-3 text-right">
                                <span
                                    className="tag px-4 py-1.5 rounded-full bg-opacity-10 bg-purple text-purple caption1 font-semibold">{n.date}</span>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <div className="flex flex-col mt-6 space-y-4">
                <button onClick={moveToInsert}
                        className="p-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition duration-200">
                    공지사항 추가하기
                </button>
                <button onClick={moveDashboard}
                        className="p-2 bg-green-600 text-white rounded hover:bg-green-700 transition duration-200">
                    관리자 페이지로 이동
                </button>
            </div>
        </main>

    );
}

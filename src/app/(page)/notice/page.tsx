'use client';
import React, { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useDispatch, useSelector } from 'react-redux';
import {getNotice, selectNotices} from 'src/lib/features/notice.slice';
import { noticeList } from 'src/app/service/notice/notice.api';

export default function ShowNotice() {
    const dispatch = useDispatch();
    const notice = useSelector(getNotice); // 공지사항 상태 가져오기
    const router = useRouter();


    const moveToOne = (id: number) => {


        router.push(`/notice/details/${id}`);
    };

    const moveToInsert = () => {
        router.push('/notice/register');
    };


    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                    <tr className="bg-blue-600 text-white">
                        <th className="py-3 px-4 border-b">제목</th>
                        <th className="py-3 px-4 border-b">조회수</th>
                        <th className="py-3 px-4 border-b">작성일</th>
                    </tr>
                    </thead>
                    <tbody>
                    {Array.isArray(notice) && notice.length > 0 ? (
                        notice.map((n) => (
                            <tr key={n.id} className="text-black cursor-pointer" onClick={() => moveToOne(n.id)}>
                                <td className="py-3 px-4 border-b">{n.title}</td>
                                <td className="py-3 px-4 border-b">{n.hits}</td>
                                <td className="py-3 px-4 border-b">{new Date(n.date).toLocaleDateString()}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={3} className="text-center py-3">공지사항이 없습니다.</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
            <div className="mt-4">
                <button onClick={moveToInsert} className="bg-blue-600 text-white py-2 px-4 rounded">
                    공지사항 추가하기
                </button>
            </div>
        </main>
    );
}

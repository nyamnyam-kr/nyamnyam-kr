"use client";
import React, {useEffect, useState} from "react";
import {router} from "next/client";

export default function showReport() {
    const [report, setReport] = useState<ReportModel[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/report')
            .then((resp) => {
                if(!resp.ok) {
                    throw new Error("Failed to fetch group details");

                }
                return resp.json();
            })
            .then((data) => {
                setReport(data)
            })
    }, []);

    const moveToOne = (id : number) => {
        router.push(`/report/details/${id}`)
    }

    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                    <tr className="bg-blue-600 text-white">
                        <th className="py-3 px-4 border-b">제목</th>
                        <th className="py-3 px-4 border-b">사용자ID</th>
                        <th className="py-3 px-4 border-b">작성일</th>
                    </tr>
                    </thead>
                    <tbody>
                    {report.map((r) => (
                        <tr key={r.id} className=" text-black" onClick={() => moveToOne(r.id)}>
                            <th className="py-3 px-4 border-b">{r.id}</th>
                            <th className="py-3 px-4 border-b">{r.userId}</th>
                            <th className="py-3 px-4 border-b">{r.content}</th>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </main>
    )
}
"use client";
import React, {useEffect, useState} from "react";
import {router} from "next/client";

export default function showOpinion() {
    const [opinion, setOpinion] = useState<OpinionModel[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/opinion')
            .then((resp) => {
                if(!resp.ok) {
                    throw new Error("Failed to fetch group details");

                }
                return resp.json();
            })
            .then((data) => {
                setOpinion(data)
            })
    }, []);


    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                    <tr className="bg-blue-600 text-white">
                        <th className="py-3 px-4 border-b">번호</th>
                        <th className="py-3 px-4 border-b">내용</th>
                    </tr>
                    </thead>
                    <tbody>
                    {opinion.map((o) => (
                        <tr key={o.id} className=" text-black">
                            <th className="py-3 px-4 border-b">{o.id}</th>
                            <th className="py-3 px-4 border-b">{o.content}</th>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </main>
    )
}
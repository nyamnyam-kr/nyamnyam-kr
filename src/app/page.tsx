"use client";
import React, { useEffect, useState } from "react";

export default function Home2() {
    const [data, setData] = useState({ hotelList: [] });

    useEffect(() => {
        fetch('http://211.188.50.43:8080/restaurant/findAll')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setData({ hotelList: data });
            })
            .catch((error) => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }, []);

    const fcAlert = () => {
        alert('Insert page로 이동합니다');
        window.location.href = "/restaurant/register";
    }

    return (
        <main className="flex min-h-screen flex-col items-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <button
                    className="mb-4 bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300"
                    onClick={fcAlert}
                >
                    Go Save
                </button>
                <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
                    <thead>
                        <tr className="bg-blue-600 text-white">
                            <th className="py-3 px-4 border-b">Index</th>
                            <th className="py-3 px-4 border-b">Name</th>
                            <th className="py-3 px-4 border-b">Tel</th>
                            <th className="py-3 px-4 border-b">Address</th>
                            <th className="py-3 px-4 border-b">OperateDate</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.hotelList.map((h, index) => (
                            <tr key={index} className="hover:bg-gray-100">
                                <td className="py-3 px-4 border-b">{index}</td>
                                <td className="py-3 px-4 border-b">{h.name}</td>
                                <td className="py-3 px-4 border-b">{h.tel}</td>
                                <td className="py-3 px-4 border-b">{h.address}</td>
                                <td className="py-3 px-4 border-b">{h.operateTime}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </main>
    );
}
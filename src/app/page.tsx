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
                JSON.stringify(data);
                console.log(JSON.stringify(data))
                setData({ hotelList: data })

            })
            .catch((error) => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }, []);


    const fcAlert = () => {
        alert('insert 페이지로 이동합니다');

        window.location.href = "register";
    }

    return (
        <main className="flex min-h-screen flex-col items-center justify-between p-24">
            <button className="rounded-full" onClick={fcAlert}>Go Save</button>
            <table className="table-auto border border-indigo-600">
                <thead>
                    <tr className="border border-indigo-600">
                        <th>Index</th>
                        <th>Name</th>
                        <th>Tel</th>
                        <th>Address</th>
                        <th>OperateDate</th>
                    </tr>
                </thead>
                <tbody>
                    {data.hotelList.map((h, index) => (
                        <tr key={index} className="border border-indigo-600">
                            <td>{index}</td>
                            <td>{h.name}</td>
                            <td>{h.tel}</td>
                            <td>{h.address}</td>
                            <td>{h.operateTime}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

        
        </main>


    );
}


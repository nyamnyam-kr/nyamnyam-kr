"use client";
import React, { useEffect, useState } from "react";


export default function UserJoin() {
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
            <h1> 회원가입 화면 </h1>
        </main>


    );
}


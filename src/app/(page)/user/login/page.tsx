"use client";
import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { authenticateUser } from "src/app/service/user/user.api";

export default function Home() {
    const router = useRouter();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    // 로그인 후 사용자 정보를 Context에 저장
    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const token = await authenticateUser(username, password); // 서비스 호출

            // JWT 토큰을 로컬 스토리지에 저장
            localStorage.setItem('token', token);

            // 로그인 성공 후 리디렉션
            router.push("/"); // 로그인 후 이동할 페이지
        } catch (error) {
            console.error('Login failed:', error);
            setErrorMessage('Invalid username or password');
        }
    };

    // 회원가입 페이지로 이동하는 함수
    const handleRegister = () => {
        router.push("/register"); // 회원가입 페이지로 리디렉션
    };

    return (
        <div className="flex items-center justify-center h-screen">
            <div className="w-full max-w-xs">
                <form onSubmit={handleLogin} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                    <h2 className="text-center text-2xl mb-4">Login</h2>
                    {errorMessage && <p className="text-red-500">{errorMessage}</p>}
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="username">
                            Username
                        </label>
                        <input
                            id="username"
                            type="text"
                            placeholder="Enter your username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        />
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
                            Password
                        </label>
                        <input
                            id="password"
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                        />
                    </div>
                    <div className="flex items-center justify-between">
                        <button
                            type="submit"
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                        >
                            Login
                        </button>
                        <button
                            type="button" // form submission이 아닌 버튼 클릭으로 처리
                            onClick={handleRegister}
                            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline ml-2"
                        >
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

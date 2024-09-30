"use client";
import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

export default function Home() {
    const router = useRouter();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    // 로그인 후 사용자 정보를 Context에 저장
    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Login successful:', data);

                // JWT 토큰을 로컬 스토리지에 저장
                localStorage.setItem('token', data.token);

                // 사용자 정보를 로컬 스토리지에 저장 (선택적)
                localStorage.setItem('user', JSON.stringify(data.user)); // 예: 사용자 정보가 data.user에 포함되어 있을 때

                // 로그인 성공 후 리디렉션
                router.push("/"); // 로그인 후 이동할 페이지
            } else {
                const errorText = await response.text(); // 응답을 텍스트로 읽기
                console.error('Error response:', errorText); // 오류 메시지 출력
                setErrorMessage('Invalid username or password');
            }
        } catch (error) {
            console.error('Failed to save user data:', error);
            setErrorMessage('An error occurred. Please try again.');
        }
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
                    </div>
                </form>
            </div>
        </div>
    );
}

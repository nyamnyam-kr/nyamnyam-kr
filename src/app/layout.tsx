"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Link from "next/link"; 
import { useEffect, useState } from 'react';

const inter = Inter({ subsets: ["latin"] });

interface User {
  id: number;
  nickname: string;
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {

  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('user');
    setUser(null);
  };
  return (
    <html lang="en">
       <head>
        <title> 냠냠! </title>
      </head>
      <body className={inter.className}>
      <nav className="bg-blue-500 text-white py-4">
        <div className="container mx-auto flex justify-between items-center px-4">
          <Link href="/" className="text-xl font-bold">냠냠</Link>
            <ul className="flex space-x-4">
                <li>
                    <Link href="/" className="hover:bg-blue-600 px-3 py-2 rounded">홈</Link>
                </li>
                <li>
                    <Link href="/" className="hover:bg-blue-600 px-3 py-2 rounded">POST</Link>
                </li>
                {user ? (
                <>
                  <li className="hover:bg-blue-600 px-3 py-2 rounded">{user.nickname}님, 환영합니다!</li>
                  <li>
                    <button onClick={handleLogout} className="hover:bg-blue-600 px-3 py-2 rounded">
                      로그아웃
                    </button>
                  </li>
                  <li>
                    <Link href="/user/list" className="hover:bg-blue-600 px-3 py-2 rounded">유저 목록</Link>
                  </li>
                </>
              ) : (
                <li>
                  <Link href="/user/login" className="hover:bg-blue-600 px-3 py-2 rounded">로그인</Link>
                </li>
              )}
                <li>
                    <Link href="/chatRoom/list" className="hover:bg-blue-600 px-3 py-2 rounded">채팅방</Link>
                </li>
                <li>
                    <Link href="/tag/tags" className="hover:bg-blue-600 px-3 py-2 rounded">Tag</Link>
                </li>
                <li>
                    <Link href="/user/mypage" className="hover:bg-blue-600 px-3 py-2 rounded">MyPage</Link>
                </li>
            </ul>
        </div>
      </nav>
      <div className="container mx-auto p-4">
          {children}
      </div>
      </body>
    </html>
  );
}

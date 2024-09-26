// app/layout.tsx
"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Link from "next/link";
import { ReactNode, useEffect, useState } from 'react';
import SearchBar from "./components/SearchBox";
import React from "react";
import Home from "./page";


interface User {
  id: number;
  nickname: string;
}

interface Restaurant {
  id: number;
  name: string;
  type: string;
  address: string;
  tel: string;
  rate: number;
  thumbnailImageUrl: string;
}

interface SearchBarProps {
  searchTerm: string;
  onSearch: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onEnter: () => void;
}

interface RootLayoutProps {
  children: React.ReactElement<SearchBarProps>[];
}

export default function RootLayout({ children }: RootLayoutProps) {
  const [user, setUser] = useState<User | null>(null);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);

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

  const fetchRestaurantsBySearch = async (keyword: string): Promise<Restaurant[]> => {
    if (!keyword) {
      setRestaurants([]);
      return [];
    }
    const res = await fetch(`http://localhost:8080/api/restaurant/search?q=${keyword}`);
    const data = await res.json();
    setRestaurants(data);
    return data;
  };

  const handleSearch = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearchTerm(value);
    await fetchRestaurantsBySearch(value);
  };

  const handleEnter = async () => {
    await fetchRestaurantsBySearch(searchTerm);
    // console.log('검색어:', searchTerm);
  };

  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <title>TeamHost - Join now and play mighty games!</title>
        <meta name="author" content="Templines" />
        <meta name="description" content="TeamHost" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="HandheldFriendly" content="true" />
        <meta httpEquiv="X-UA-Compatible" content="IE=edge" />
        <link rel="shortcut icon" href="/assets/img/favicon.png" type="image/x-icon" />
        <link rel="stylesheet" href="/assets/css/libs.min.css" />
        <link rel="stylesheet" href="/assets/css/main.css" />
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Marcellus&display=swap" rel="stylesheet" />
      </head>
      <body>
        <header className="page-header">
          <div className="page-header__inner">
            <div className="page-header__sidebar">
              <div className="page-header__menu-btn">
                <button className="menu-btn ico_menu is-active"></button>
              </div>
              <div className="page-header__logo">
                <Link href="/">
                  <img src="/assets/img/nyamnyam_logo.png" alt="logo" />
                </Link>
              </div>
            </div>
            <div className="page-header__content">
              <div className="page-header__search">
                <div className="search">
                  <div className="search__input">
                    <i className="ico_search"></i>
                    <SearchBar searchTerm={searchTerm} onSearch={handleSearch} onEnter={handleEnter} />
                  </div>
                  <div className="search__btn">
                    <button type="button">
                      <i className="ico_microphone"></i>
                    </button>
                  </div>
                </div>
              </div>
              <div className="page-header__action">
                <ul className="flex space-x-4 items-center">
                  <li>
                    <Link href="/" className="hover:bg-blue-600 px-3 py-2 rounded">홈</Link>
                  </li>
                  {user ? (
                    <>
                      <li className="hover:bg-blue-600 px-3 py-2 rounded" style={{ color: "#F46119", fontSize: "16px", fontFamily: "Nunito", fontWeight: 700 }}>
                        {user.nickname}님, 환영합니다!
                      </li>
                      <li>
                        <button onClick={handleLogout} className="hover:bg-blue-600 px-3 py-2 rounded" style={{ color: "#F46119", fontSize: "16px", fontFamily: "Nunito", fontWeight: 700 }}>
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
                    <Link href="/tag/tags" className="hover:bg-blue-600 px-3 py-2 rounded">Tag</Link>
                  </li>
                  <li>
                    <Link href="/user/mypage" className="hover:bg-blue-600 px-3 py-2 rounded">MyPage</Link>
                  </li>
                </ul>
                <div className="page-header__action">
                  <Link href="/chatRoom/list" className="action-btn">
                    <i className="ico_message"></i>
                    <span className="animation-ripple-delay1"></span>
                  </Link>
                  <Link href="/07_friends" className="action-btn">
                    <i className="ico_notification"></i>
                    <span className="animation-ripple-delay2"></span>
                  </Link>
                  <Link href="/08_wallet" className="profile">
                    <img src="/assets/img/profile.png" alt="profile" />
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </header>
        <main>
          <Home searchTerm={searchTerm} />
        </main>
      </body>
    </html>
  );
}

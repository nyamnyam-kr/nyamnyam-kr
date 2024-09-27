"use client";
import Link from 'next/link';
import { useEffect, useState } from 'react';
import Search from 'src/app/components/Search';

interface User {
  id: number;
  nickname: string;
}

export default function Header() {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    // 로컬 스토리지에서 사용자 정보 가져오기
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
            <Search />
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
                    <Link href="/user/list" className="hover:bg-blue-600 px-3 py-2 rounded">
                      유저 목록
                    </Link>
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
              <Link href="/chatRoom" className="action-btn">
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
  );
}

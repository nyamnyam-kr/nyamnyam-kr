"use client";
import Link from 'next/link';
import React, { useEffect, useState } from 'react';
import Search from 'src/app/components/Search';
import * as Icon from "@phosphor-icons/react/dist/ssr";
import {Button} from "@restart/ui";


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
              <div className="page-header__action">
                {user ? (
                    <>
                      <div>
                      <Link href="/tag/tags" className="action-btn">
                      </Link>
                      </div>
                      <Button onClick={handleLogout} className="action-btn">
                        <Icon.SignOut size={40} />
                      </Button>
                    </>
                    ) : (

                    <Link href="/user/login" className="action-btn">
                      <Icon.SignIn size={40}/>
                    </Link>
                    )}



                <Link href="/tag/tags" className="action-btn">
                  <Icon.Tag size={40}/>
                </Link>
                <Link href="/user/mypage" className="action-btn">
                  <Icon.ListHeart size={40}/>
                </Link>
                <Link href="/chatRoom" className="action-btn">
                  <i className="ico_message"></i>
                  <span className="animation-ripple-delay1"></span>
                </Link>
                <Link href="/notice" className="action-btn">
                  <Icon.Bell size={40}/>
                  <span className="animation-ripple-delay2"></span>
                </Link>
                <Link href="/user/mypage" className="profile">
                  <img src="/assets/img/profile.png" alt="profile" />
                </Link>
              </div>
            </div>
          </div>
        </div>
      </header>
  );
}
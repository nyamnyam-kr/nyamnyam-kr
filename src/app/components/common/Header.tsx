"use client";
import Link from 'next/link';
import { useEffect, useState } from 'react';
import Search from 'src/app/components/Search';
import * as Icon from "@phosphor-icons/react/dist/ssr";
 import { useModalWishlistContext } from 'src/app/context/ModalWishlistContext';
import { useRouter } from 'next/navigation';


interface User {
  id: number;
  nickname: string;
}

export default function Header() {
   const { openModalWishlist } = useModalWishlistContext();
  const [user, setUser] = useState<User | null>(null);
  const router = useRouter();

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
    router.push('/');
  };



  const handleOpenModal = () => {
    console.log("Opening Modal"); // 로그 추가
    openModalWishlist();
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
                      <button onClick={handleLogout} className="action-btn">
                        <Icon.SignOut size={40}/>
                      </button>
                    </>
                ) : (

                    <Link href="/user/login" className="action-btn">
                      <Icon.SignIn size={40}/>
                    </Link>
                )}
                <Link href="/admin/dash" className="action-btn">
                  <Icon.LegoSmiley size={40}/>
                  <span className="animation-ripple-delay2"></span>
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
                  <img src="/assets/img/profile.png" alt="profile"/>
                </Link>
                <button onClick={handleOpenModal} className="action-btn">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="black" viewBox="0 0 256 256">
                    <path
                        d="M178,40c-20.65,0-38.73,8.88-50,23.89C116.73,48.88,98.65,40,78,40a62.07,62.07,0,0,0-62,62c0,70,103.79,126.66,108.21,129a8,8,0,0,0,7.58,0C136.21,228.66,240,172,240,102A62.07,62.07,0,0,0,178,40ZM128,214.8C109.74,204.16,32,155.69,32,102A46.06,46.06,0,0,1,78,56c19.45,0,35.78,10.36,42.6,27a8,8,0,0,0,14.8,0c6.82-16.67,23.15-27,42.6-27a46.06,46.06,0,0,1,46,46C224,155.61,146.24,204.15,128,214.8Z"></path>
                  </svg>
                  <i className="ico_wishlist"></i>
                  <span className="animation-ripple-delay3"></span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </header>
  );
}

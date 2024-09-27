"use client";
import "/src/app/globals.css";
import Link from "next/link";
import { useEffect, useState } from 'react';



interface User {
    id: number;
    nickname: string;
}

export default function Header() {

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
            <meta charSet="utf-8" />
            <title>TeamHost - Join now and play mighty games!</title>
            <meta name="author" content="Templines" />
            <meta name="description" content="TeamHost" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <meta name="HandheldFriendly" content="true" />
            <meta name="format-detection" content="telephone=no" />
            <meta httpEquiv="X-UA-Compatible" content="IE=edge" />

            <link rel="shortcut icon" href="/assets/img/favicon.png" type="image/x-icon" />

            <link rel="stylesheet" href="/assets/css/libs.min.css" />
            <link rel="stylesheet" href="/assets/css/main.css" />

            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap"
                rel="stylesheet"
            />
            <link
                href="https://fonts.googleapis.com/css2?family=Marcellus&display=swap"
                rel="stylesheet"
            />
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
                                    <input type="search" name="search" placeholder="Search" />
                                </div>
                                <div className="search__btn">
                                    <button type="button">
                                        <i className="ico_microphone"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div className="page-header__action">
                            <Link href="/06_chats" className="action-btn">
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
            </header>
        </body>
        </html>
    );
}

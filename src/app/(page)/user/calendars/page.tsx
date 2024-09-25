"use client";
import React, {useEffect, useState} from "react"
import Link from 'next/link';
import Head from 'next/head';

export default function calendars() {
    const [isClient, setIsClient] = useState(false)

    useEffect(() => {
        setIsClient(true)
    }, [])



    return (
        <>
            <Head>
                <meta charSet="utf-8"/>
                <title>Starter Page | OpenDash - Tailwind CSS 3 Admin Layout & UI Kit Template</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc."
                      name="description"/>
                <meta content="MyraStudio" name="author"/>
                <link rel="shortcut icon" href="/assets/img/favicon.ico"/>
                <link rel="stylesheet" href="/assets/css/icons.min.css"  type="text/css"/>
                <link href="/assets/css/app.min.css" rel="stylesheet" type="text/css"/>
            </Head>
            <main className="p-6">
                {/* 페이지 제목 시작 */}
                <div className="flex items-center md:justify-between flex-wrap gap-2 mt-100">
                    <h4 className="text-default-900 text-lg font-medium mb-2">Starter Page</h4>

                    <div className="md:flex hidden items-center gap-3 text-sm font-semibold">
                        <Link href="#" className="text-sm font-medium text-default-700">
                            OpenDash
                        </Link>
                        <i className="material-symbols-rounded text-xl flex-shrink-0 text-default-500">
                            chevron_right
                        </i>
                        <Link href="#" className="text-sm font-medium text-default-700">
                            Menu
                        </Link>
                        <i className="material-symbols-rounded text-xl flex-shrink-0 text-default-500">
                            chevron_right
                        </i>
                        <Link href="#" className="text-sm font-medium text-default-700" aria-current="page">
                            Starter Page
                        </Link>
                    </div>
                </div>
                {/* 페이지 제목 끝 */}

                <div className="card dark:bg-zinc-800 dark:border-zinc-600">
                    <div className="space-y-5 card-body mt-100">
                        {/* 여기에 캘린더 컴포넌트를 통합할 곳입니다 */}
                        <div id="calendar"></div>
                    </div>
                </div>
            </main>

            <script src="/assets/libs/jquery/jquery.min.js"></script>
            <script src="/assets/libs/preline/preline.js"></script>
            <script src="/assets/libs/simplebar/simplebar.min.js"></script>
            <script src="/assets/libs/iconify-icon/iconify-icon.min.js"></script>
            <script src="/assets/libs/node-waves/waves.min.js"></script>

            <script src="/assets/js/app.js"></script>

            <script src="/assets/libs/fullcalendar/index.global.min.js"></script>

            <script src="/assets/js/pages/app-calendar.js"></script>


        </>
    );
}

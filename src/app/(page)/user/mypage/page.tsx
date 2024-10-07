"use client";
import React, {useEffect, useState} from "react";
import {fetchInsertOpinion} from "src/app/service/opinion/opinion.serivce";
import Image from 'next/image'
import Link from "next/link";
import * as Icon from "@phosphor-icons/react/dist/ssr";
import {fetchShowArea, fetchShowCount, fetchShowRankByAge} from "src/app/service/admin/admin.service";
import {Area, CountItem, RestaurantList, UserPostModel} from "src/app/model/dash.model";
import {OpinionModel} from "src/app/model/opinion.model";
import {ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from "chart.js";
import styles from "src/css/mypage.module.css";
import {Bar, Doughnut} from "react-chartjs-2";
import MyCalendar from "src/app/(page)/user/calendar/[id]/page";
import MyWallet from "src/app/(page)/user/wallet/[id]/page";
import {fetchPostList} from "src/app/service/post/post.service";
import {PostModel} from "src/app/model/post.model";

ChartJS.register(Title, Tooltip, Legend, BarElement, ArcElement, CategoryScale, LinearScale);


export default function MyPage() {
    const [count, setCount] = useState<CountItem[]>([]);
    const [region, setRegion] = useState<Area[]>([]);
    const [restaurant, setRestaurant] = useState<RestaurantList[]>([]);
    const [post, setPost] = useState<UserPostModel[]>([]);
    const [activeTab, setActiveTab] = useState<string | undefined>('myPage')
    const [isModalOpen, setIsModalOpen] = useState(false);


    useEffect(() => {
        const countList = async () => {
            const data = await fetchShowCount();
            setCount(data)
        };
        countList()
    }, []);

    useEffect(() => {
        const showArea = async () => {
            const data = await fetchShowArea();
            setRegion(data)
        };
        showArea()
    }, []);

    useEffect(() => {
        const showRestaurant = async () => {
            const data = await fetchShowRankByAge(userId);
            setRestaurant(data)
        };
        showRestaurant()
    }, []);


    useEffect(() => {
        const showPostListByUserId = async () => {
            const data = await fetchPostList(userId)
            setPost(data)
        }
        showPostListByUserId()
    },[])


    const [content, setContent] = useState("");
    const userId = 1; // Replace this with the actual user ID
    const currentDate = new Date().toISOString(); // Adjust format if needed

    const submit = async (content: string) => {
        const report: OpinionModel = {
            id: Date.now(),
            userId,
            content,
            entryDate: currentDate,
        };

        try {
            const result = await fetchInsertOpinion(report);
            if (result) {
                alert('의견이 성공적으로 제출되었습니다.');
                setContent(""); // Clear the textarea after submission
            } else {
                alert('의견 제출에 실패하였습니다.');
            }
        } catch (error) {
            console.error('오류가 발생했습니다:', error);
            alert('의견 제출 중 오류가 발생했습니다.');
        }
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (window.confirm("냠냠에 의견을 보낼까요?")) {
            submit(content);
        }
    };

    const countData = {
        labels: count.map(item => item.nickname),
        datasets: [
            {
                label: 'UserRank',
                data: count.map(item => item.count),
                backgroundColor: 'rgba(255, 159, 64, 0.2)',
                borderColor: 'rgba(255, 159, 64, 1)',
                borderWidth: 1,
            }
        ],
    };

    const areaData = {
        labels: region.map(item => item.area),
        datasets: [{
            data: region.map(item => item.total),
            backgroundColor: ["red", "orange", "yellow", "green", "blue"],
            borderColor: ["#fff", "#fff", "#fff", "#fff", "#fff"],
            borderWidth: 1,
        }],
    };


    const restaurantData = {
        labels: restaurant.map(item => item.restaurantName),
        datasets: [{

            label: 'RestaurantRank',
            data: restaurant.map(item => item.total),
            backgroundColor: 'rgba(255, 159, 64, 0.2)',
            borderColor: 'rgba(255, 159, 64, 1)',
            borderWidth: 1,
        }],
    };

    const totalPost = post.length;


    return (
        <>

            <div className="profile-block md:py-20 py-10 mt-10">
                <div className="container">
                    <div className="content-main flex gap-y-8 max-md:flex-col w-full">
                        <div className="left md:w-1/3 w-full xl:pr-[3.125rem] lg:pr-[28px] md:pr-[16px]">
                            <div
                                className="user-infor bg-surface lg:px-7 px-4 lg:py-10 py-5 md:rounded-[20px] rounded-xl">
                                <div className="heading flex flex-col items-center justify-center">
                                    <div className="avatar">
                                        <Image
                                            src={'/assets/img/profile.png'}
                                            width={300}
                                            height={300}
                                            alt='avatar'
                                            className='md:w-[140px] w-[120px] md:h-[140px] h-[120px] rounded-full'
                                        />
                                    </div>
                                    <div className="name heading6 mt-4 text-center">Tony Nguyen</div>
                                    <div
                                        className="mail heading6 font-normal normal-case text-secondary text-center mt-1">hi.avitex@gmail.com
                                    </div>
                                </div>
                                <div className="menu-tab w-full max-w-none lg:mt-10 mt-6">
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white ${activeTab === 'myPage' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('myPage')}>
                                        <Icon.HouseLine size={20}/>
                                        <strong className="heading6">MyPage</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'myWallet' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('myWallet')}>
                                        <Icon.Wallet size={20}/>
                                        <strong className="heading6">MyWallet</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'dash' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('dash')}>
                                        <Icon.ChartDonut size={20}/>
                                        <strong className="heading6">Dashboard</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'opinion' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('opinion')}>
                                        <Icon.Clipboard size={20}/>
                                        <strong className="heading6">MyOpinion</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'edit' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('edit')}>
                                        <Icon.Clipboard size={20}/>
                                        <strong className="heading6">Edit</strong>
                                    </Link>
                                    <Link href={'/login'}
                                          className="item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5">
                                        <Icon.SignOut size={20}/>
                                        <strong className="heading6">Logout</strong>
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="right md:w-2/3 w-full pl-2.5">
                            <div
                                className={`tab text-content w-full ${activeTab === 'myPage' ? 'block' : 'hidden'}`}>
                                <div className="overview grid sm:grid-cols-3 gap-5 mt-7 ">
                                    <div
                                        className="item flex items-center justify-between p-5 border border-line rounded-lg box-shadow-xs w-full ">
                                        <Link href="/receipt/insertReceipt">
                                            <div className="counter">
                                                <span className="tese text-orange-700">Receipt</span>
                                                <h5 className="heading5 mt-1">insert</h5>
                                            </div>
                                        </Link>
                                        <Icon.Receipt className='text-4xl'/>
                                    </div>
                                    <div
                                        className="item flex items-center justify-between p-5 border border-line rounded-lg box-shadow-xs">
                                        <div className="counter">
                                            <span className="tese">Total Post</span>
                                            <h5 className="heading5 mt-1">{totalPost}</h5>
                                        </div>
                                        <Icon.NotePencil className='text-4xl'/>
                                    </div>
                                    <div
                                        className="item flex items-center justify-between p-5 border border-line rounded-lg box-shadow-xs">
                                        <div className="counter">
                                            <span className="tese">MY FOLLOWER</span>
                                            <h5 className="heading5 mt-1">200</h5>
                                        </div>
                                        <Icon.UserCircle className='text-4xl'/>
                                    </div>
                                </div>
                                <div className="recent_order pt-5 px-5 pb-2 mt-7 border border-line rounded-xl">
                                    <div>
                                        <div className={styles.cardHeader}>TOTAL POST USER RANKING</div>
                                        <div></div>
                                    </div>
                                    <div className={styles.cardBody}>
                                        <div className={styles.chartContainer}>
                                            <Bar
                                                data={countData}
                                                options={{
                                                    responsive: true,
                                                    maintainAspectRatio: false,
                                                    scales: {
                                                        x: {title: {display: true, text: 'Nickname'}},
                                                        y: {title: {display: true, text: 'Count'}},
                                                    },
                                                }}
                                            />
                                        </div>
                                    </div>
                                    <h6 className="heading6"> MY POST </h6>
                                    <div className="list overflow-x-auto w-full mt-5">
                                        <table className="w-full max-[1400px]:w-[700px] max-md:w-[700px] text-center text-sm">
                                            <thead className="border-b border-line">
                                            <tr className="text-center">
                                                <th scope="col"
                                                    className="pb-3 text-sm font-bold uppercase text-secondary whitespace-nowrap">음식점
                                                </th>
                                                <th scope="col"
                                                    className="pb-3 text-sm font-bold uppercase text-secondary whitespace-nowrap">내용
                                                </th>
                                                <th scope="col"
                                                    className="pb-3 text-sm font-bold uppercase text-secondary whitespace-nowrap">작성날짜
                                                </th>
                                                <th scope="col"
                                                    className="pb-3 text-sm font-bold uppercase text-secondary whitespace-nowrap">좋아요 수
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {post.map(p => (
                                                <tr key={p.postId} className="item duration-300 border-b border-line">
                                                    <Link className=" text-sm text-secondary" href={`/restaurant/${p.restaurantId}`}>
                                                    <th scope="row" className="py-3">
                                                        <strong className="text-title">{p.name}</strong>
                                                    </th>
                                                    </Link>
                                                    <td className="py-3 text-left">
                                                        <div className="info flex flex-col font-bold">
                                                            {p.content}
                                                        </div>
                                                    </td>
                                                    <td className="py-3 price">
                                                        {new Date(p.entryDate).toISOString().slice(0, 19).replace('T', ' ')}
                                                    </td>
                                                    <td className="py-3">
                                                        <div className="info flex flex-col">
                                                            {p.upvoteCount}
                                                        </div>
                                                    </td>
                                                </tr>
                                            ))}

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div
                                className={`tab text-content overflow-hidden w-full h-auto p-7 mt-7 border border-line rounded-xl ${activeTab === 'myWallet' ? 'block' : 'hidden'}`}>
                                <h6 className="heading6">My Wallet</h6>
                                <div className="mb-10"><MyCalendar/></div>
                                <div><MyWallet/></div>
                            </div>
                            <div
                                className={`tab text-content overflow-hidden w-full p-7 mt-7 border border-line rounded-xl ${activeTab === 'dash' ? 'block' : 'hidden'}`}>
                                <h6 className="heading6">DashBoard</h6>
                                <div className={styles.cardHeader}>연령 별 음식점 랭킹</div>
                                <div className={styles.cardBody}>
                                    <div className={styles.chartContainer}>
                                        <Bar
                                            data={restaurantData}
                                            options={{
                                                responsive: true,
                                                maintainAspectRatio: false,
                                                scales: {
                                                    x: {title: {display: true, text: 'Restaurant'}},
                                                    y: {title: {display: true, text: 'Count'}},
                                                },
                                            }}
                                        />
                                    </div>
                                </div>


                                <div className={styles.cardHeader}>음식점 많은 지역 랭킹</div>
                                <div className={styles.cardBody}>
                                    <div className={styles.chartContainer}>
                                        <Doughnut data={areaData} options={{
                                            responsive: true,
                                            maintainAspectRatio: false,
                                            plugins: {
                                                legend: {
                                                    position: 'top',
                                                },
                                                tooltip: {
                                                    callbacks: {
                                                        label: (context) => {
                                                            const label = context.label || '';
                                                            const value = context.raw || 0;
                                                            return `${label}: ${value}`;
                                                        }
                                                    }
                                                }
                                            }
                                        }}/>
                                    </div>
                                </div>
                            </div>
                            <div
                                className={`tab text-content overflow-hidden w-full p-7 mt-7 border border-line rounded-xl ${activeTab === 'opinion' ? 'block' : 'hidden'}`}>
                                <h6 className="heading6">My Opinion</h6>
                                <h2 className="text-lg font-semibold text-gray-800 mb-2">냠냠에 전하고 싶은 의견이 있나요?</h2>
                                <h2 className="text-md text-gray-600 mb-4">00님의 소중한 의견을 꼼꼼히 읽어볼게요</h2>
                                <form onSubmit={handleSubmit}>
                             <textarea
                                 value={content}
                                 onChange={(e) => setContent(e.target.value)}
                                 placeholder="여기에 의견을 남겨주세요"
                                 rows={4}
                                 className="w-full border border-gray-300 rounded-md p-2 mb-2"
                                 style={{borderBottom: '2px solid #ccc', marginBottom: '10px'}}
                             />
                                    <button
                                        type="submit"
                                        className="mt-2 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition duration-200"
                                    >제출
                                    </button>
                                </form>
                            </div>
                            <div
                                className={`tab text-content overflow-hidden w-full p-7 mt-7 border border-line rounded-xl ${activeTab === 'edit' ? 'block' : 'hidden'}`}>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )


}


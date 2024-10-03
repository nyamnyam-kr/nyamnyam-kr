"use client";
import React, {useEffect, useState} from "react";
import {fetchInsertOpinion} from "src/app/service/opinion/opinion.serivce";
import Image from 'next/image'
import Link from "next/link";
import * as Icon from "@phosphor-icons/react/dist/ssr";
import {fetchShowArea, fetchShowCount, fetchShowRestaurant} from "src/app/service/admin/admin.service";
import {Area, CountItem, RestaurantList} from "src/app/model/dash.model";
import {OpinionModel} from "src/app/model/opinion.model";
import {ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from "chart.js";
import styles from "src/css/mypage.module.css";
import {Bar, Doughnut} from "react-chartjs-2";
import MyCalendar from "src/app/(page)/user/calendar/[id]/page";
import Modal from "src/app/components/Modal";
import ShowOpinion from "src/app/(page)/admin/showOpinion/page";

ChartJS.register(Title, Tooltip, Legend, BarElement, ArcElement, CategoryScale, LinearScale);


export default function user() {
    const [count, setCount] = useState<CountItem[]>([]);
    const [region, setRegion] = useState<Area[]>([]);
    const [restaurant, setRestaurant] = useState<RestaurantList[]>([]);

    const [activeTab, setActiveTab] = useState<string | undefined>('user')
    const [isModalOpen, setIsModalOpen] = useState(false);



    useEffect(() => {
        const countList = async () => {
            const data = await fetchShowCount();
            setCount(data);
        };
        countList();
    }, []);

    useEffect(() => {
        const showArea = async () => {
            const data = await fetchShowArea();
            setRegion(data);
        };
        showArea();
    }, []);

    useEffect(() => {
        const showRestaurant = async () => {
            const data = await fetchShowRestaurant();
            setRestaurant(data);
        };
        showRestaurant();
    }, []);



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
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white ${activeTab === 'user' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('user')}>
                                        <Icon.UserCheck size={20}/>
                                        <strong className="heading6">User</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'post' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('post')}>
                                        <Icon.Note size={20}/>
                                        <strong className="heading6">Post</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'opinion' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('opinion')}>
                                        <Icon.PaperPlaneTilt size={20}/>
                                        <strong className="heading6">Opinion</strong>
                                    </Link>
                                    <Link href={'#!'} scroll={false}
                                          className={`item flex items-center gap-3 w-full px-5 py-4 rounded-lg cursor-pointer duration-300 hover:bg-white mt-1.5 ${activeTab === 'dash' ? 'active' : ''}`}
                                          onClick={() => setActiveTab('dash')}>
                                        <Icon.ChartLine size={20}/>
                                        <strong className="heading6">Dashboard</strong>
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
                            <div className="recent_order pt-5 px-5 pb-2 mt-7 border border-line rounded-xl">

                            </div>
                            <div
                                className={`tab text-content w-full ${activeTab === 'user' ? 'block' : 'hidden'}`}>
                                <div className="overview grid sm:grid-cols-3 gap-5 mt-7 ">
                                    <div
                                        className="item flex items-center justify-between p-5 border border-line rounded-lg box-shadow-xs w-full ">
                                        <Link href="/tag/tags">
                                            <div className="counter">
                                                <h5 className="heading5 mt-1">Tag </h5>
                                            </div>
                                        </Link>
                                        <Icon.Tag className='text-4xl'/>
                                    </div>
                                    <div
                                        className="item flex items-center justify-between p-5 border border-line rounded-lg box-shadow-xs">
                                        <div className="counter">
                                            <span className="tese">Cancelled post</span>
                                            <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}> </Modal>
                                        </div>
                                        <Icon.ReceiptX className='text-4xl'/>
                                    </div>
                                    <div
                                        className="item flex items-center justify-between p-5 border border-line rounded-lg box-shadow-xs">
                                        <div className="counter">
                                            <span className="tese">Total Number of post</span>
                                            <h5 className="heading5 mt-1">200</h5>
                                        </div>
                                        <Icon.Package className='text-4xl'/>
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
                                        <table className="w-full max-[1400px]:w-[700px] max-md:w-[700px]">
                                            <thead className="border-b border-line">
                                            <tr>
                                                <th scope="col"
                                                    className="pb-3 text-left text-sm font-bold uppercase text-secondary whitespace-nowrap">Order
                                                </th>
                                                <th scope="col"
                                                    className="pb-3 text-left text-sm font-bold uppercase text-secondary whitespace-nowrap">Products
                                                </th>
                                                <th scope="col"
                                                    className="pb-3 text-left text-sm font-bold uppercase text-secondary whitespace-nowrap">Pricing
                                                </th>
                                                <th scope="col"
                                                    className="pb-3 text-right text-sm font-bold uppercase text-secondary whitespace-nowrap">Status
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr className="item duration-300 border-b border-line">
                                                <th scope="row" className="py-3 text-left">
                                                    <strong className="text-title">postId</strong>
                                                </th>
                                                <td className="py-3">
                                                        <div className="info flex flex-col">
                                                            <strong className="product_name text-button">postcontent</strong>
                                                            <span className="product_tag caption1 text-secondary"></span>
                                                        </div>
                                                </td>
                                                <td className="py-3 price">restaurantname</td>
                                                <td className="py-3 text-right">
                                                    <span
                                                        className="tag px-4 py-1.5 rounded-full bg-opacity-10 bg-yellow text-yellow caption1 font-semibold">뭐넣지</span>
                                                </td>
                                            </tr>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div
                                className={`tab text-content overflow-hidden w-full h-auto p-7 mt-7 border border-line rounded-xl ${activeTab === 'post' ? 'block' : 'hidden'}`}>
                                <h6 className="heading6">My Wallet</h6>
                                <div className="mb-10"><MyCalendar/> </div>
                            </div>
                            <div
                                className={`tab_opinion text-content w-full text-center p-7 mt-7 border border-line rounded-xl ${activeTab === 'opinion' ? 'block' : 'hidden'}`}>
                                <h3 className="heading6">의견보기</h3>
                                <div className="mb-10"><ShowOpinion/></div>
                            </div>
                            <div
                                className={`tab text-content overflow-hidden w-full p-7 mt-7 border border-line rounded-xl ${activeTab === 'dash' ? 'block' : 'hidden'}`}>
                                <h6 className="heading6">DashBoard</h6>
                                <div className={styles.cardHeader}>음식점 랭킹</div>
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
                        </div>
                    </div>
                </div>
            </div>
        </>
    )


}


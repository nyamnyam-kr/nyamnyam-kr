"use client";
import React, { useEffect, useState } from "react";
import { Bar, Doughnut } from "react-chartjs-2";
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, ArcElement, CategoryScale, LinearScale } from "chart.js"; // ArcElement 추가
import styles from "src/css/mypage.module.css";
import axios from "axios";
import Link from "next/link";
import {fetchShowCount} from "src/app/service/admin/admin.service";
import {Area, CountItem, RestaurantList} from "src/app/model/dash.model";

ChartJS.register(Title, Tooltip, Legend, BarElement, ArcElement, CategoryScale, LinearScale); // ArcElement 등록



const DashBoard = () => {
    const [count, setCount] = useState<CountItem[]>([]);
    const [region, setRegion] = useState<Area[]>([]);
    const [restaurant, setRestaurant] = useState<RestaurantList[]>([]);

    useEffect(() => {
        const countList = async () => {
            const data = await fetchShowCount();
            setCount(data);
        };
        countList();
    }, []);

    useEffect(() => {
        const showArea = async () => {
            try {
                const resp = await axios.get('http://localhost:8080/api/admin/countAreaList');
                if (resp.status === 200) {
                    setRegion(resp.data);
                }
            } catch (error) {
                console.error("Error fetching count data", error);
            }
        };
        showArea();
    }, [region]);

    useEffect(() => {
        const showRestaurant = async () => {
            try {
                const resp = await axios.get('http://localhost:8080/api/admin/countPostList');
                console.log(resp.data);
                if (resp.status === 200) {
                    setRestaurant(resp.data)
                }
            } catch (error) {
                console.error("Error fetching count data", error);
            }
        };
        showRestaurant();
    }, [restaurant]);


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
            data: restaurant.map(item => item.total),
            backgroundColor: ["red", "orange", "yellow", "green", "blue"],
            borderColor: ["#fff", "#fff", "#fff", "#fff", "#fff"],
            borderWidth: 1,
        }],
    };



    return (

        <div className={styles.row}>
            <div>
                <Link href='/admin/showOpinion'>고객이 보낸 의견 보기</Link>
            </div>
            <div className={styles.col}>
                <div className={styles.card}>
                    <div className={styles.cardHeader}>TOTAL POST USER RANKING</div>
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
                </div>
            </div>

            <div className={`${styles.col} mb-10`}>
                <div className={styles.card}>
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

            <div className={`${styles.col} mb-10`}>
                <div className={styles.card}>
                    <div className={styles.cardHeader}>포스팅 많은 음식점 랭킹</div>
                    <div className={styles.cardBody}>
                        <div className={styles.chartContainer}>
                            <Doughnut data={restaurantData} options={{
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
    );
};

export default DashBoard;

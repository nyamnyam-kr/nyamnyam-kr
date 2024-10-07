"use client";
import React, {useEffect, useState} from "react";
import {Bar, Doughnut, Line} from "react-chartjs-2";
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    BarElement,
    ArcElement,
    CategoryScale,
    LinearScale,
    PointElement, LineElement
} from "chart.js"; // ArcElement 추가
import styles from "src/css/mypage.module.css";
import axios from "axios";
import Link from "next/link";
import {
    fetchReceiptList,
    fetchShowArea,
    fetchShowCount,
    fetchShowRestaurant
} from "src/app/service/admin/admin.service";
import {Area, CountCost, CountItem, RestaurantList} from "src/app/model/dash.model";

ChartJS.register(Title, Tooltip, Legend, BarElement, ArcElement, CategoryScale, LinearScale, PointElement, LineElement);
const DashBoard = () => {
    const [count, setCount] = useState<CountItem[]>([]);
    const [region, setRegion] = useState<Area[]>([]);
    const [restaurant, setRestaurant] = useState<RestaurantList[]>([]);
    const [countRestaurant, setCountRestaurant] = useState<CountCost[]>([]);
    const [sumList, setSumList] = useState<RestaurantList[]>([]);


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

    useEffect(() => {
        const countRestaurant = async () => {
            const data = await fetchReceiptList();
            setCountRestaurant(data);
        };
        countRestaurant();
    }, []);



    const [content, setContent] = useState("");
    const userId = 1; // Replace this with the actual user ID
    const currentDate = new Date().toISOString(); // Adjust format if needed

    const areaData = {
        labels: region.map(item => item.area),
        datasets: [{
            data: region.map(item => item.total),
            backgroundColor: ["#F46119", "#ed6d2b", "#f37f48", "#ea966d", "#EAB5A0FF"],
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
    const lineData = {
        labels: countRestaurant.map(item => item.date),
        datasets: [
            {
                label: 'Monthly Revenue',
                data: countRestaurant.map(item => item.price),
                fill: false,
                backgroundColor: 'rgba(75, 192, 192, 1)',
                borderColor: 'rgba(75, 192, 192, 1)',
                tension: 0.1,
            },
        ],
    };


    return (
        <>
            <div className={styles.row}>
                <div className={styles.col}>
                    <div className={styles.card}>
                        <div className={styles.cardHeader}>TOTAL POST USER RANKING</div>
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
                                        animation: false
                                    }}
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <div className={styles.col}>
                    <div className={styles.card}>
                        <div className={styles.cardHeader}>TOTAL POST USER RANKING</div>
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
                                        animation: false
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

            </div>

            <div className={styles.row}>
                <div className={styles.col}>
                    <div className={styles.card}>
                        <div className={styles.cardHeader}>포스팅 많은 음식점 랭킹</div>
                        <div className={styles.cardBody}>
                            <div className={styles.chartContainer}>
                                <Line data={lineData} options={{responsive: true}}/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default DashBoard;

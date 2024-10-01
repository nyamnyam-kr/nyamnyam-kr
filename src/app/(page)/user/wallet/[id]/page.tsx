"use client";
import React, { useEffect, useState } from "react";
import {Line} from "react-chartjs-2";
import {registerables, Chart} from "chart.js"; // ArcElement 추가
import styles from "src/css/mypage.module.css";
import axios from "axios";
import {CountCost} from "src/app/model/dash.model";
import {useParams} from "next/navigation";

Chart.register(...registerables);

export default function MyWallet() {
    const [cost, setCost] = useState<CountCost[]>([]);
    const {id}  = useParams();


    useEffect(() => {
        const Count = async () => {
            try {
                const resp = await axios.get(`http://localhost:8080/api/admin/wallet/cost/${id}`);
                if (resp.status === 200) {
                    setCost(resp.data);
                    console.log(resp.data)
                }
            } catch (error) {
                console.error("Error fetching count data", error);
            }
        };
        Count();
    }, []);

    const costData = {
        labels: cost.map(item => item.date),
        datasets: [
            {
                label: 'UserRank',
                data: cost.map(item => item.price),
                backgroundColor: 'rgba(255, 159, 64, 0.2)',
                borderColor: 'rgba(255, 159, 64, 1)',
                borderWidth: 1,
            }
        ],
    };




    return (
        <div className={styles.col}>
            <div className={styles.card}>
                <div className={styles.cardHeader}>TOTAL POST USER RANKING</div>
                <div className={styles.cardBody}>
                    <div className={styles.chartContainer}>
                        <Line
                            data={costData}
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
    )
}
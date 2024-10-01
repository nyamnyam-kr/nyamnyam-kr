"use client";
import React, { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import { registerables, Chart } from "chart.js";
import styles from "src/css/mypage.module.css";
import axios from "axios";
import { CountCost } from "src/app/model/dash.model";
import { useParams } from "next/navigation";

Chart.register(...registerables);

export default function MyWallet() {
    const [cost, setCost] = useState<CountCost[]>([]);
    const { id } = useParams();

    useEffect(() => {
        const Count = async () => {
            try {
                const resp = await axios.get(`http://localhost:8080/api/receipt/wallet/cost/${id}`);
                if (resp.status === 200) {
                    setCost(resp.data);
                    console.log(resp.data);
                }
            } catch (error) {
                console.error("Error fetching count data", error);
            }
        };
        Count();
    }, [id]); // id를 의존성 배열에 추가

    const getAllMonths = (year: number) => {
        return Array.from({ length: 12 }, (_, i) => `${year}-${String(i + 1).padStart(2, '0')}`);
    };

    const predefinedOrder = getAllMonths(2024);

    const sortedCost = cost.sort((a, b) => {
        return predefinedOrder.indexOf(a.date) - predefinedOrder.indexOf(b.date);
    });

    const costData = {
        labels: sortedCost.map(item => item.date),
        datasets: [
            {
                label: 'cost',
                data: sortedCost.map(item => item.price),
                backgroundColor: 'rgba(255, 159, 64, 0.2)',
                borderColor: 'rgba(255, 159, 64, 1)',
                borderWidth: 1,
            }
        ],
    };

    return (
        <div className={styles.col}>
                <div className={styles.cardHeader}>TOTAL COST</div>
                <div className={styles.cardBody}>
                    <div className={styles.chartContainer}>
                        <Line
                            data={costData}
                            options={{
                                responsive: true,
                                maintainAspectRatio: false,
                                scales: {
                                    x: { title: { display: true, text: 'Month' } },
                                    y: { title: { display: true, text: 'Cost' } },
                                },
                            }}
                        />
                    </div>
                </div>
        </div>
    )
}

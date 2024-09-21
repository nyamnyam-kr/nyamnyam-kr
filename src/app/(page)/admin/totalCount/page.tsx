"use client";
import React, {useEffect, useState} from "react";
import styles from "@/app/(page)/admin/dashboard/mypage.module.css";
import {Bar, Doughnut} from "react-chartjs-2";
import Link from "next/link";

interface Revenue {
    restaurantName: string;
    total: number;

}
const data3 = {
    labels: [1, "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: 'Dataset 1',
            data: [65, 59, 80, 81, 56, 55, 40],
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1,
        },
        {
            label: 'Dataset 2',
            data: [28, 48, 40, 19, 86, 27, 90],
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1,
        },
    ],
};

export default function TotalRevenue() {
    const [revenue, setRevenue] = useState<Revenue | null>(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/receipt/total')
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch group details");
                }
                return response.json();
            })
            .then((data) => {
                setRevenue(data)
                console.log(data);
            })
            .catch((error) => {
                console.error("Fetch error:", error);
            });
    }, []);


    return (
        <div className={styles.row}>
            <div className={styles.col}>
                <div className={styles.card}>
                    <div className={styles.cardHeader}>Bar Chart 1</div>
                    <div className={styles.cardBody}>
                        <div className={styles.chartContainer}>
                            <Bar data={data3}
                                 options={{responsive: true, scales: {x: {stacked: true}, y: {stacked: true}}}}/>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <Link href="/receipt/insertReceipt">내 영수증 추가하기</Link>
            </div>

        </div>

    );
}




// "use client";
// import React, { useEffect, useState } from "react";
// import { Doughnut, Bar } from "react-chartjs-2";
// import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, BarElement, CategoryScale, LinearScale } from "chart.js";
// import styles from "./mypage.module.css";
// import axios from "axios";
// import Link from "next/link";
//
// ChartJS.register(Title, Tooltip, Legend, ArcElement, BarElement, CategoryScale, LinearScale);
//
// const data1 = {
//     labels: ["Red", "Blue", "Yellow"],
//     datasets: [{
//         data: [20, 50, 100],
//         backgroundColor: ["red", "blue", "yellow"],
//         borderColor: ["#fff", "#fff", "#fff"],
//         borderWidth: 1,
//     }],
// };
//
// const data2 = {
//     labels: ["Green", "Purple", "Orange"],
//     datasets: [{
//         data: [200, 150, 50],
//         backgroundColor: ["green", "purple", "orange"],
//         borderColor: ["#fff", "#fff", "#fff"],
//         borderWidth: 1,
//     }],
// };
//
// const data3 = {
//     labels: ["January", "February", "March", "April", "May", "June", "July"],
//     datasets: [
//         {
//             label: 'Dataset 1',
//             data: [65, 59, 80, 81, 56, 55, 40],
//             backgroundColor: 'rgba(255, 99, 132, 0.2)',
//             borderColor: 'rgba(255, 99, 132, 1)',
//             borderWidth: 1,
//         },
//         {
//             label: 'Dataset 2',
//             data: [28, 48, 40, 19, 86, 27, 90],
//             backgroundColor: 'rgba(54, 162, 235, 0.2)',
//             borderColor: 'rgba(54, 162, 235, 1)',
//             borderWidth: 1,
//         },
//     ],
// };
//
// interface CountItem {
//     nickname: string;
//     count: number;
// }
//
//
//
// const DashBoard = () => {
//     const [count, setCount] = useState<{ CountList: CountItem[] }>({ CountList: [] });
//     const [selectedFiles, setSelectedFiles] = useState<FileList | null>(null); // 선택된 파일 상태
//
//
//
//     useEffect(() => {
//         const showCount = async () => {
//             try {
//                 const resp = await axios.get("http://localhost:8080/admin/count");
//                 console.log(resp.data); // 응답 데이터 확인
//                 if (resp.status === 200) {
//                     setCount(resp.data);
//                     console.log(countList[0].nickname)
//                 }
//             } catch (error) {
//                 console.error("Error fetching count data", error);
//             }
//         };
//         showCount();
//     }, []);
//
//
//     const countList = count.CountList || []; // count.CountList가 정의되지 않았을 경우 빈 배열로 설정
//
//     const chartData = {
//         labels: countList.length > 0 ? [countList[0].nickname] : [], // 첫 번째 nickname만 추출
//         datasets: [
//             {
//                 label: 'UserRank',
//                 data: countList.length > 0 ? [countList[0].count] : [], // 첫 번째 count만 추출
//                 backgroundColor: 'rgba(255, 159, 64, 0.2)',
//                 borderColor: 'rgba(255, 159, 64, 1)',
//                 borderWidth: 1,
//             }
//         ],
//     };
//
//
//
//
//
//
//     return (
//         <div className={styles.row}>
//             <div className={styles.col}>
//                 <div className={styles.card}>
//                     <div className={styles.cardHeader}>서울시 구별 List</div>
//                     <div className={styles.cardBody}>
//                         <div className={styles.chartContainer}>
//                             <Doughnut data={data1}/>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//             <div className={`${styles.col} mb-10`}>
//                 <div className={styles.card}>
//                     <div className={styles.cardHeader}>특정 구별 category</div>
//                     <div className={styles.cardBody}>
//                         <div className={styles.chartContainer}>
//                             <Doughnut data={data2}/>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//             <div className={styles.col}>
//                 <div className={styles.card}>
//                     <div className={styles.cardHeader}>Bar Chart 1</div>
//                     <div className={styles.cardBody}>
//                         <div className={styles.chartContainer}>
//                             <Bar data={data3}
//                                  options={{responsive: true, scales: {x: {stacked: true}, y: {stacked: true}}}}/>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//             <div className={styles.col}>
//                 <div className={styles.card}>
//                     <div className={styles.cardHeader}>Bar Chart 2</div>
//                     <div className={styles.cardBody}>
//                         <div className={styles.chartContainer}>
//                             <Bar data={chartData}
//                                  options={{responsive: true, scales: {x: {stacked: true}, y: {stacked: true}}}}/>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//
//             <div>
//                 <Link href="/receipt/insertReceipt">내 영수증 추가하기</Link>
//             </div>
//
//         </div>
//
//     );
// };
//
// export default DashBoard;



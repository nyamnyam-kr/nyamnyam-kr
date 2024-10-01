"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import Head from "next/head";
import axios from "axios"; // Import Axios

// Define the Area interface
interface Area {
    area: string;
    total: number;
}

export default function Dash() {
    const [region, setRegion] = useState<Area[]>([]); // State for holding fetched data

    // Load ApexCharts and charts script
    useEffect(() => {
        const apexChartsScript = document.createElement("script");
        apexChartsScript.src = "/assets/libs/apexcharts/apexcharts.min.js";
        apexChartsScript.async = true;
        document.body.appendChild(apexChartsScript);

        const chartsScript = document.createElement("script");
        chartsScript.src = "/assets/js/pages/charts-apex.js";
        chartsScript.async = true;
        document.body.appendChild(chartsScript);

        return () => {
            document.body.removeChild(apexChartsScript);
            document.body.removeChild(chartsScript);
        };
    }, []);

    // Fetching region data
    useEffect(() => {
        const showArea = async () => {
            try {
                const resp = await axios.get('http://localhost:8080/api/admin/countAreaList');
                if (resp.status === 200) {
                    setRegion(resp.data); // Update state with fetched data
                }
            } catch (error) {
                console.error("Error fetching count data", error);
            }
        };
        showArea();
    }, []); // Fetch once on component mount

    const chartData = region.map(item => item.total); // Prepare data for the chart
    const chartLabels = region.map(item => item.area); // Prepare labels for the chart

    const options8 = {
        chart: {
            height: 320,
            type: "pie"
        },
        series: chartData, // Use chartData for series
        labels: chartLabels, // Use areas as labels
        colors: ["#34c38f", "#556ee6", "#f46a6a", "#50a5f1", "#f1b44c"],
        legend: {
            show: true,
            position: "bottom",
            horizontalAlign: "center",
            verticalAlign: "middle",
            floating: false,
            fontSize: "14px",
            offsetX: 0
        },
        responsive: [{
            breakpoint: 600,
            options: { chart: { height: 240 }, legend: { show: false } }
        }]
    };

    return (
        <>
            <Head>
                <meta charSet="utf-8" />
                <title>Dashboard | OpenDash - Tailwind CSS 3 Admin Layout & UI Kit Template</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc." />
                <meta name="author" content="MyraStudio" />
                <link rel="shortcut icon" href="/assets/images/favicon.ico" />
                <link href="/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
                <link href="/assets/css/app.min.css" rel="stylesheet" type="text/css" />
            </Head>

            <main>
                <div className="flex items-center md:justify-between flex-wrap gap-2 mt-20">
                    <h4 className="text-default-900 text-lg font-medium mb-2">Changelog</h4>
                    <div className="md:flex hidden items-center gap-3 text-sm font-semibold">
                        <Link href="#" className="text-sm font-medium text-default-700">OpenDash</Link>
                        <i className="material-symbols-rounded text-xl flex-shrink-0 text-default-500">chevron_right</i>
                        <Link href="#" className="text-sm font-medium text-default-700">Documentation</Link>
                        <i className="material-symbols-rounded text-xl flex-shrink-0 text-default-500">chevron_right</i>
                        <Link href="#" className="text-sm font-medium text-default-700" aria-current="page">Changelog</Link>
                    </div>
                </div>

                <div className="grid lg:grid-cols-2 gap-6">
                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Pie Chart</h4>
                            <div id="pie_chart" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Region Data</h4>
                            <ul>
                                {region.map((item, index) => (
                                    <li key={index} className="text-default-700">{item.area}: {item.total}</li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
            </main>

            <script src="/assets/libs/apexcharts/apexcharts.min.js"></script>
            <script src="/assets/js/pages/charts-apex.js"></script>
            <script src="/assets/libs/jquery/jquery.min.js"></script>
            <script src="/assets/libs/preline/preline.js"></script>
            <script src="/assets/libs/simplebar/simplebar.min.js"></script>
            <script src="/assets/libs/iconify-icon/iconify-icon.min.js"></script>
            <script src="/assets/libs/node-waves/waves.min.js"></script>
            <script src="/assets/js/app.js"></script>
        </>
    );
}

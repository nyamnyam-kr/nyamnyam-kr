"use client"
import React, {useEffect} from "react"
import Link from "next/link";
import Head from "next/head";

export default function Dash() {
    useEffect(() => {
        // ApexCharts 초기화 코드
        const apexChartsScript = document.createElement("script");
        apexChartsScript.src = "/assets/libs/apexcharts/apexcharts.min.js";
        apexChartsScript.async = true;
        document.body.appendChild(apexChartsScript);

        return () => {
            document.body.removeChild(apexChartsScript);
        };
    }, []);

    useEffect(() => {
        const chartsScript = document.createElement("script");
        chartsScript.src = "/assets/js/pages/charts-apex.js";
        chartsScript.async = true;
        document.body.appendChild(chartsScript);

        return () => {
            document.body.removeChild(chartsScript);
        };
    }, []);

    return (
        <>
            <Head>
                <meta charSet="utf-8"/>
                <title>Dashboard | OpenDash - Tailwind CSS 3 Admin Layout & UI Kit Template</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <meta name="description"
                      content="A fully featured admin theme which can be used to build CRM, CMS, etc."/>
                <meta name="author" content="MyraStudio"/>

                {/* App favicon */}
                <link rel="shortcut icon" href="/assets/images/favicon.ico"/>

                {/* Icons CSS (Mandatory in All Pages) */}
                <link href="/assets/css/icons.min.css" rel="stylesheet" type="text/css"/>

                {/* App CSS (Mandatory in All Pages) */}
                <link href="/assets/css/app.min.css" rel="stylesheet" type="text/css"/>
            </Head>

            <main>
                {/* Page Title Start */}
                <div className="flex items-center md:justify-between flex-wrap gap-2 mb-6">
                    <h4 className="text-default-900 text-lg font-medium mb-2">Changelog</h4>

                    <div className="md:flex hidden items-center gap-3 text-sm font-semibold">
                        <Link href="#" className="text-sm font-medium text-default-700">OpenDash</Link>
                        <i className="material-symbols-rounded text-xl flex-shrink-0 text-default-500">chevron_right</i>
                        <Link href="#" className="text-sm font-medium text-default-700">Documentation</Link>
                        <i className="material-symbols-rounded text-xl flex-shrink-0 text-default-500">chevron_right</i>
                        <Link href="#" className="text-sm font-medium text-default-700"
                              aria-current="page">Changelog</Link>
                    </div>
                </div>
                {/* Page Title End */}

                <div className="grid lg:grid-cols-2 gap-6">
                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Line with Data Labels</h4>
                            <div id="line_chart_datalabel" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Dashed Line</h4>
                            <div id="line_chart_dashed" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Spline Area</h4>
                            <div id="spline_area" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Column Chart</h4>
                            <div id="column_chart" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Column with Data Labels</h4>
                            <div id="column_chart_datalabel" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Bar Chart</h4>
                            <div id="bar_chart" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Line, Column & Area Chart</h4>
                            <div id="mixed_chart" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Radial Chart</h4>
                            <div id="radial_chart" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Pie Chart</h4>
                            <div id="pie_chart" className="apex-charts" dir="ltr"></div>
                        </div>
                    </div>

                    <div className="card">
                        <div className="p-6">
                            <h4 className="card-title mb-4">Donut Chart</h4>
                            <div id="donut_chart" className="apex-charts" dir="ltr"></div>
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
    )
}
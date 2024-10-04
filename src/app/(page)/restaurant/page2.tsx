'use client'

import React, { useEffect, useState } from 'react'
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Navigation } from 'swiper/modules';
import 'swiper/css/bundle';
import { ProductType } from '@/type/ProductType'
import { motion } from 'framer-motion'
import { getRestaurantsBySearch, getRestaurantsByTag } from '@/app/service/restaurant/restaurant.service';
import { useSearchContext } from '@/app/components/SearchContext';
import ScrollToTop from '@/app/components/ScrollToTop';
import Product from '@/app/components/Product';

interface Props {
    data: Array<ProductType>;
    start: number;
    limit: number;
}

const TabFeatures: React.FC<Props> = ({ data, start, limit }) => {
    const [restaurantsByMeeting, setRestaurantsByMeeting] = useState<RestaurantModel[]>([]);
    const [restaurantsByDate, setRestaurantsByDate] = useState<RestaurantModel[]>([]);
    const [activeTab, setActiveTab] = useState<string>('on sale')


    const handleTabClick = (item: string) => {
        setActiveTab(item)
    }


    useEffect(() => {
        const fetchRestaurants = async () => {
            try {


                const meetingData = await getRestaurantsByTag(["회식"]);
                setRestaurantsByMeeting(meetingData); // 회식 태그에 맞는 식당 설정

                const dateData = await getRestaurantsByTag(["데이트"]);
                setRestaurantsByDate(dateData); // 데이트 태그에 맞는 식당 설정
            } catch (error) {
                console.error("Error fetching restaurants by tag:", error);
            }
        };

        fetchRestaurants();
    }, []);

    const getFilterData = () => {
        if (activeTab === 'on sale') {
            return data.filter((product) => product.sale && (product.category === 'fashion'))
        }

        if (activeTab === 'new arrivals') {
            return data.filter((product) => product.new && (product.category === 'fashion'))
        }

        if (activeTab === 'best sellers') {
            return data
                .filter((product) => product.category === 'fashion')
                .slice()
                .sort((a, b) => b.sold - a.sold)
        }

        return data
    }

    const filteredProducts = getFilterData()

    return (
        <>
            <div className="tab-features-block md:pt-20 pt-10">

                <div className="list-product hide-product-sold section-swiper-navigation style-outline style-border md:mt-10 mt-6">
                    <Swiper
                        spaceBetween={12}
                        slidesPerView={2}
                        navigation
                        loop={true}
                        modules={[Navigation, Autoplay]}
                        breakpoints={{
                            576: {
                                slidesPerView: 2,
                                spaceBetween: 12,
                            },
                            768: {
                                slidesPerView: 3,
                                spaceBetween: 20,
                            },
                            1200: {
                                slidesPerView: 4,
                                spaceBetween: 30,
                            },
                        }}
                    >
                        {filteredProducts.slice(start, limit).map((prd, index) => (
                            <SwiperSlide key={index}>
                                <Product data={prd} type='grid' />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
            </div>



            <div className="container">
                {/* 회식 태그 식당 스와이프 */}
                <div className="heading flex flex-col items-center text-center mb-6">
                    <h2 className="text-2xl font-bold">#회식</h2>
                </div>
                <div className="heading flex flex-col items-center text-center">
                    <div className="menu-tab flex items-center gap-2 p-1 bg-surface rounded-2xl">
                        {[].map((item, index) => (
                            <div
                                key={index}
                                className={`tab-item relative text-secondary heading5 py-2 px-5 cursor-pointer duration-500 hover:text-black ${activeTab === item ? 'active' : ''}`}
                                onClick={() => handleTabClick(item)}
                            >
                                {activeTab === item && (
                                    <motion.div layoutId='active-pill' className='absolute inset-0 rounded-2xl bg-white'></motion.div>
                                )}
                                <span className='relative heading5 z-[1]'>
                                    {item}
                                </span>
                            </div>
                        ))}
                    </div>
                </div>
                <div className="list-product hide-product-sold section-swiper-navigation style-outline style-border md:mt-10 mt-6">
                    <Swiper
                        spaceBetween={12}
                        slidesPerView={2}
                        navigation
                        loop={true}
                        modules={[Navigation, Autoplay]}
                        breakpoints={{
                            576: {
                                slidesPerView: 2,
                                spaceBetween: 12,
                            },
                            768: {
                                slidesPerView: 3,
                                spaceBetween: 20,
                            },
                            1200: {
                                slidesPerView: 4,
                                spaceBetween: 30,
                            },
                        }}
                    >
                        {restaurantsByMeeting.slice(start, limit).map((restaurant, index) => (
                            <SwiperSlide key={index}>
                                <div>
                                    <img
                                        src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                        alt={restaurant.name}
                                        className="w-full h-48 object-cover"
                                    />
                                    <h2 className="text-xl font-bold">{restaurant.name}</h2>
                                    <p>{restaurant.type}</p>
                                    <p>{restaurant.address}</p>
                                    <p>{restaurant.tel}</p>
                                    <p>평점: {restaurant.rate}</p>
                                </div>
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>

                {/* 데이트 태그 식당 스와이프 */}
                <div className="heading flex flex-col items-center text-center mb-6 mt-10">
                    <h2 className="text-2xl font-bold">#데이트</h2>
                </div>
                <div className="list-product hide-product-sold section-swiper-navigation style-outline style-border md:mt-10 mt-6">
                    <Swiper
                        spaceBetween={12}
                        slidesPerView={2}
                        navigation
                        loop={true}
                        modules={[Navigation, Autoplay]}
                        breakpoints={{
                            576: {
                                slidesPerView: 2,
                                spaceBetween: 12,
                            },
                            768: {
                                slidesPerView: 3,
                                spaceBetween: 20,
                            },
                            1200: {
                                slidesPerView: 4,
                                spaceBetween: 30,
                            },
                        }}
                    >
                        {restaurantsByDate.slice(start, limit).map((restaurant, index) => (
                            <SwiperSlide key={index}>
                                <div>
                                    <img
                                        src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                        alt={restaurant.name}
                                        className="w-full h-48 object-cover"
                                    />
                                    <h2 className="text-xl font-bold">{restaurant.name}</h2>
                                    <p>{restaurant.type}</p>
                                    <p>{restaurant.address}</p>
                                    <p>{restaurant.tel}</p>
                                    <p>평점: {restaurant.rate}</p>
                                </div>
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
                <ScrollToTop />
            </div>

        </>
    );
}

export default TabFeatures;

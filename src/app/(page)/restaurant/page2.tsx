"use client";

import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Navigation } from 'swiper/modules';
import 'swiper/css/bundle';
import { getRestaurantsByTag } from '@/app/service/restaurant/restaurant.service';
import ScrollToTop from '@/app/components/ScrollToTop';
import Product from '@/app/components/Product';


interface Props {
    start: number;
    limit: number;
}

const TabFeatures: React.FC<Props> = ({ start, limit }) => {
    const [restaurantsByMeeting, setRestaurantsByMeeting] = useState<RestaurantModel[]>([]);
    const [restaurantsByDate, setRestaurantsByDate] = useState<RestaurantModel[]>([]);

    useEffect(() => {
        const fetchRestaurants = async () => {
            try {
                const meetingData = await getRestaurantsByTag(['회식']);
                setRestaurantsByMeeting(meetingData);

                const dateData = await getRestaurantsByTag(['데이트']);
                setRestaurantsByDate(dateData);
            } catch (error) {
                console.error('Error fetching restaurants by tag:', error);
            }
        };

        fetchRestaurants();
    }, []);

    return (
        <div className="tab-features-block md:pt-20 pt-10">
            <div className="container">
                {/* 회식 태그 식당 스와이프 */}
                <div className="heading flex flex-col items-center text-center mb-6">
                    <h2 className="text-2xl font-bold">#회식</h2>
                </div>
                <div className="list-product hide-product-sold section-swiper-navigation style-outline style-border md:mt-10 mt-6">
                    <Swiper
                        spaceBetween={12}
                        slidesPerView={2}
                        navigation
                        loop={true}
                        modules={[Navigation, Autoplay]}
                        breakpoints={{
                            576: { slidesPerView: 2, spaceBetween: 12 },
                            768: { slidesPerView: 3, spaceBetween: 20 },
                            1200: { slidesPerView: 4, spaceBetween: 30 },
                        }}
                    >
                        {restaurantsByMeeting.slice(start, limit).map((restaurant) => (
                            <SwiperSlide key={restaurant.id}>
                                <Product data={restaurant} type='grid' />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
            </div>
            <div className="container">
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
                            576: { slidesPerView: 2, spaceBetween: 12 },
                            768: { slidesPerView: 3, spaceBetween: 20 },
                            1200: { slidesPerView: 4, spaceBetween: 30 },
                        }}
                    >
                        {restaurantsByDate.slice(start, limit).map((restaurant) => (
                            <SwiperSlide key={restaurant.id}>
                                <Product data={restaurant} type='grid' />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
            </div>
            <ScrollToTop />
        </div>
    );
};

export default TabFeatures;
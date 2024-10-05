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

    const renderSwiper = (title: string, restaurants: RestaurantModel[], index: number) => (
        <div className="container" key={index}>
            <div className="heading flex flex-col items-center text-center">
                <h2 className="text-2xl font-bold">{title}</h2>
            </div>
            <div className="relative list-product hide-product-sold section-swiper-navigation style-outline style-border md:mt-10 mt-6">
                <Swiper
                    spaceBetween={12}
                    slidesPerView={2}
                    navigation={{ nextEl: `.swiper-button-next-${index}`, prevEl: `.swiper-button-prev-${index}` }}
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
                    {restaurants.slice(start, limit).map((restaurant) => (
                        <SwiperSlide key={restaurant.id}>
                            <Product data={restaurant} type='grid' />
                        </SwiperSlide>
                    ))}
                </Swiper>
                <div className={`swiper-button-next swiper-button-next-${index}`} style={{ position: 'absolute', top: '50%', right: '10px', transform: 'translateY(-50%)', zIndex: 10 }} />
                <div className={`swiper-button-prev swiper-button-prev-${index}`} style={{ position: 'absolute', top: '50%', left: '10px', transform: 'translateY(-50%)', zIndex: 10 }} />
            </div>
        </div>
    );

    return (
        <div className="tab-features-block md:pt-20 pt-10">
            {renderSwiper('#회식', restaurantsByMeeting, 0)}
            {renderSwiper('#데이트', restaurantsByDate, 1)}
            <ScrollToTop />
        </div>
    );
};

export default TabFeatures;

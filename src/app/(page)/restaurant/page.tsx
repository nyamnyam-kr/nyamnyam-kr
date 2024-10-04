"use client";
// app/page.tsx

import { useState, useEffect } from 'react';
import Link from 'next/link';
import Sidebar from 'src/app/components/SideBar';
import ScrollToTop from 'src/app/components/ScrollToTop';
import { useSearchContext } from 'src/app/components/SearchContext';
import { getRestaurantsByCategory, getRestaurantsBySearch, getRestaurantsByTag } from 'src/app/service/restaurant/restaurant.service';
import HeartButton from 'src/app/modal/AddHeart';
import { useRouter, useSearchParams } from 'next/navigation';

export default function Home() {
    const [restaurants, setRestaurants] = useState<RestaurantModel[]>([]);
    const { searchTerm } = useSearchContext();
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
    const router = useRouter();
    const searchParams = useSearchParams();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const term = searchParams.get('search') || searchTerm; // 쿼리 파라미터에서 검색어 추출


                let data: RestaurantModel[] = await getRestaurantsBySearch(term || '');

                // 태그 필터링
                if (selectedTags.length > 0) {
                    const tagData = await getRestaurantsByTag(selectedTags);
                    data = data.filter(restaurant => tagData.some((tagged: { id: number; }) => tagged.id === restaurant.id));
                }

                // 카테고리 필터링
                if (selectedCategories.length > 0) {
                    const categoryData = await getRestaurantsByCategory(selectedCategories);
                    data = data.filter(restaurant => categoryData.some((categorized: { id: number; }) => categorized.id === restaurant.id));
                }

                setRestaurants(data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [searchParams, searchTerm,  selectedTags, selectedCategories]); // 변경 사항에 따라 다시 fetch

    const handleFilterChange = (tags: string[], categories: string[]) => {
        setSelectedTags(tags);
        setSelectedCategories(categories);
    };

    return (
        <>
            <div className="profile-block py-10 mt-10">
                <div className="container">
                    <div className="content-main">
                        <div className="left">
                            <Sidebar onFilterChange={handleFilterChange} />
                        </div>
                        <div className="right">
                            <div>
                                {restaurants.length > 0 ? (
                                    <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                        {restaurants.map((restaurant) => (
                                            <li key={restaurant.id} className="bg-white shadow-md rounded-lg overflow-hidden relative">
                                                <Link href={`/restaurant/${restaurant.id}`}>
                                                    <img
                                                        src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                                        alt={restaurant.name}
                                                        className="w-full h-48 object-cover"
                                                    />
                                                </Link>
                                                <div className="absolute top-2 right-2">
                                                    <HeartButton restaurantId={restaurant.id} />
                                                </div>
                                                <Link href={`/restaurant/${restaurant.id}`}>
                                                    <div className="p-4">
                                                        <h2 className="text-xl font-bold mb-2">{restaurant.name}</h2>
                                                        <p className="text-gray-600">유형: {restaurant.type}</p>
                                                        <p className="text-gray-600">주소: {restaurant.address}</p>
                                                        <p className="text-gray-600">전화번호: {restaurant.tel}</p>
                                                        <p className="text-gray-600">평점: {restaurant.rate}</p>
                                                    </div>
                                                </Link>
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <p className="text-gray-600">조건에 맞는 결과가 존재하지 않습니다</p>
                                )}
                            </div>
                            <ScrollToTop />
                        </div>
                    </div>
                </div>
            </div>

            <style jsx>
                {`
                    .content-main {
                        display: flex;
                        justify-content: space-between;
                        max-width: 100%;
                        margin: 0 auto;
                    }

                    .left {
                        flex: 0 0 250px;
                        margin-right: 20px;
                    }

                    .right {
                        flex: 1;
                        display: flex;
                        justify-content: center;
                    }
                `}
            </style>
        </>
    );
}

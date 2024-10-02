// app/page.tsx
"use client";
import { useState, useEffect, useRef } from 'react';
import Link from 'next/link';
import Sidebar from 'src/app/components/SideBar';
import ScrollToTop from 'src/app/components/ScrollToTop';
import { SearchProvider, useSearchContext } from 'src/app/components/SearchContext';
import { getRestaurantsByCategory, getRestaurantsBySearch, getRestaurantsByTag } from 'src/app/service/restaurant/restaurant.service';
import HeartButton from 'src/app/modal/AddHeart';





export default function Home() {
    const [restaurants, setRestaurants] = useState<RestaurantModel[]>([]);
    const { searchTerm } = useSearchContext();
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                let data: RestaurantModel[] = await getRestaurantsBySearch(searchTerm || '');

                if (selectedTags.length > 0) {
                    const tagData = await getRestaurantsByTag(selectedTags);
                    data = data.filter(restaurant => tagData.some((tagged: { id: number; }) => tagged.id === restaurant.id));
                }

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
    }, [searchTerm, selectedTags, selectedCategories]);

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
                {
                    `.content-main {
    display: flex;
    justify-content: space-between; /* 사이드바와 오른쪽 컨텐츠의 공간을 최대한 활용 */
    max-width: 100%; /* 전체 최대 너비 설정 */
    margin: 0 auto; /* 중앙 정렬 */
}

.left {
    flex: 0 0 250px; /* 사이드바의 너비를 고정 */
    margin-right: 20px; /* 사이드바와 컨텐츠 사이의 간격 */
}

.right {
    flex: 1; /* 오른쪽 영역은 남은 공간을 모두 차지 */
    display: flex;
    justify-content: center; /* 가운데 정렬 */
}
`
                }
            </style>
        </>
    );
}

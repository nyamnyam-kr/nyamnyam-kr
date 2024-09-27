// app/page.tsx
"use client";
import { useState, useEffect, useRef } from 'react';
import Link from 'next/link';
import Sidebar from 'src/app/components/SideBar';
import ScrollToTop from 'src/app/components/ScrollToTop';
import { SearchProvider, useSearchContext } from 'src/app/components/SearchContext';
import { fetchRestaurantsByCategory, fetchRestaurantsBySearch, fetchRestaurantsByTag } from 'src/app/service/restaurant/restaurant.service';




export default function Home() {
    const [restaurants, setRestaurants] = useState<RestaurantModel[]>([]);
    const { searchTerm} = useSearchContext();
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                let data: RestaurantModel[] = await fetchRestaurantsBySearch(searchTerm || '');

                if (selectedTags.length > 0) {
                    const tagData = await fetchRestaurantsByTag(selectedTags);
                    data = data.filter(restaurant => tagData.some((tagged: { id: number; }) => tagged.id === restaurant.id));
                }

                if (selectedCategories.length > 0) {
                    const categoryData = await fetchRestaurantsByCategory(selectedCategories);
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
        <div className="overflow-y-auto">
            <SearchProvider>
                <div className="container mx-auto px-4 py-4 flex">
                    <div className="w-64">
                        <Sidebar onFilterChange={handleFilterChange} />
                    </div>

                    <div className="flex-grow ml-4">
                        {restaurants.length > 0 ? (
                            <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                {restaurants.map((restaurant) => (
                                    <li key={restaurant.id} className="bg-white shadow-md rounded-lg overflow-hidden">
                                        <Link href={`/restaurant/${restaurant.id}`}>
                                            <img
                                                src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                                alt={restaurant.name}
                                                className="w-full h-48 object-cover"
                                            />
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
                </div>
                <ScrollToTop />
            </SearchProvider>
        </div>
    );
}

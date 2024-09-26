"use client";
import React, { useState, useEffect, useRef } from 'react';
import Link from 'next/link';
import Sidebar from "src/app/components/SideBar";
import SearchBar from "src/app/components/SearchBox";
import ScrollToTop from "src/app/components/ScrollToTop";

interface Restaurant {
    id: number;
    name: string;
    type: string;
    address: string;
    tel: string;
    rate: number;
    thumbnailImageUrl: string;
}

const fetchRestaurantsBySearch = async (keyword: string): Promise<Restaurant[]> => {
    const res = await fetch(`http://localhost:8080/api/restaurant/search?q=${keyword}`);
    const data = await res.json();
    return data;
};

const fetchRestaurantsByTag = async (tags: string[]): Promise<Restaurant[]> => {
    const tagQuery = tags.length > 0 ? `name=${tags.join(',')}` : '';
    const res = await fetch(`http://localhost:8080/api/restaurant/tag?${tagQuery}`);
    const data = await res.json();
    return data;
};


const fetchRestaurantsByCategory = async (categories: string[]): Promise<Restaurant[]> => {
    const categoryQuery = categories.length > 0 ? `category=${categories.join(',')}` : '';
    const res = await fetch(`http://localhost:8080/api/restaurant/category?${categoryQuery}`);
    const data = await res.json();
    return data;
};


export default function Home() {
    const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
    const scrollContainerRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                let data: Restaurant[] = [];

                if (searchTerm) {
                    data = await fetchRestaurantsBySearch(searchTerm);
                } else if (selectedTags.length === 0  && selectedCategories.length === 0) {
                    // 검색어와 태그가 모두 없을 경우 모든 레스토랑 가져오기
                    data = await fetchRestaurantsBySearch('');
                }

                if (selectedTags.length > 0) {
                    const tagData = await fetchRestaurantsByTag(selectedTags);
                    // 검색어와 태그가 둘 다 있을 경우 교차합집합으로 결합

                    data = Array.from(new Set([...data, ...tagData]));
                    if (searchTerm) {
                        data = data.filter(restaurant => tagData.some(tagged => tagged.id === restaurant.id));
                    } else {
                        data = tagData; // 태그만 있을 경우 태그 데이터 사용
                    }
                }

                if (selectedCategories.length > 0) {
                    const categoryData = await fetchRestaurantsByCategory(selectedCategories);
                    data = Array.from(new Set([...data, ...categoryData]));
                    data = data.length > 0
                        ? data.filter(restaurant => categoryData.some(cat => cat.id === restaurant.id))
                        : categoryData;
                }



                setRestaurants(data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [searchTerm, selectedTags, selectedCategories]);

    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(e.target.value);
    };

    const handleFilterChange = (tags: string[], categories:string[]) => {
        setSelectedTags(tags);
        setSelectedCategories(categories);
    };

    return (
        <div className="overflow-y-auto">
            <div className="container mx-auto px-4 py-4 flex">
                <div className="w-64">
                    <Sidebar onFilterChange={handleFilterChange} />
                </div>

                <div className="flex-grow ml-4">
                    <div className="w-full max-w-lg mx-auto mb-6">
                        <SearchBar searchTerm={searchTerm} onSearch={handleSearch} />
                    </div>

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
            <ScrollToTop/>
        </div>
    );
}
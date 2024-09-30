"use client";
import { useState, useEffect, useRef } from 'react';
import Link from 'next/link';
import Search from 'src/app/components/Search';
import Sidebar from 'src/app/components/SideBar';
import ScrollToTop from 'src/app/components/ScrollToTop';
import { useSearchContext } from 'src/app/components/SearchContext';

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
    const { searchTerm } = useSearchContext();
    // const [searchTerm, setSearchTerm] = useState<string>('');
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
    const scrollContainerRef = useRef<HTMLDivElement | null>(null);


    useEffect(() => {
        //console.log("가져온 검색어:", searchTerm); 
        const fetchData = async () => {
            try {
                // 검색어로 레스토랑 데이터 가져오기
                let data: Restaurant[] = await fetchRestaurantsBySearch(searchTerm || '');
                //console.log("가져온 검색어:", searchTerm); 

                // 태그와 카테고리에 따른 필터링
                if (selectedTags.length > 0) {
                    const tagData = await fetchRestaurantsByTag(selectedTags);
                    data = data.filter(restaurant => tagData.some(tagged => tagged.id === restaurant.id));
                }

                if (selectedCategories.length > 0) {
                    const categoryData = await fetchRestaurantsByCategory(selectedCategories);
                    data = data.filter(restaurant => categoryData.some(categorized => categorized.id === restaurant.id));
                }

                setRestaurants(data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [searchTerm, selectedTags, selectedCategories]); 


    // const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    //     setSearchTerm(e.target.value);
    // };

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
                    {/* <div className="w-full max-w-lg mx-auto mb-6">
                        <SearchBar searchTerm={searchTerm} onSearch={handleSearch} />
                    </div> */}
    
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
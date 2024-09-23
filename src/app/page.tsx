"use client";
import { useState, useEffect } from 'react';
import Link from 'next/link';
import SearchBar from './components/SearchBox';

interface Restaurant {
    id: number;
    name: string;
    type: string;
    address: string;
    tel: string;
    rate: number;
    thumbnailImageUrl: string;
    subImageUrl: string;
    lat: number;
    lng: number;
}

const fetchRestaurants = async (keyword: string = ''): Promise<Restaurant[]> => {
    const res = await fetch(`http://localhost:8080/api/restaurant/search?q=${keyword}`);
    const data = await res.json();
    return data;
};

export default function Home() {
    const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');

    useEffect(() => {
        const getRestaurants = async () => {
            const data = await fetchRestaurants(searchTerm);
            setRestaurants(data);
        };
        getRestaurants();
    }, [searchTerm]);

    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(e.target.value);
    };

    return (
        <div className="container mx-auto p-4">
            {/* <header className="bg-gray-800 text-white p-4 mb-6">
                <h1 className="text-3xl font-bold">Restaurant Finder</h1>
            </header> */}

            <div className="mb-6">
                <SearchBar searchTerm={searchTerm} onSearch={handleSearch} />
            </div>

            {restaurants.length > 0 ? (
                <>
                    <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
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

                    {/* 지도 컴포넌트 사용 */}
                </>
            ) : (
                <p className="text-gray-600">No restaurants found matching your search criteria.</p>
            )}
        </div>
    );
}
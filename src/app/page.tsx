"use client";
import { useState, useEffect } from 'react';

interface Restaurant {
    postId: number;
    name: string;
    address: string;
    phoneNumber: string;
    websiteUrl: string;
    useTime: string;
    subwayInfo: string;
    representativeMenu: string;
    category: string;
}

const fetchRestaurants = async (): Promise<Restaurant[]> => {
    const res = await fetch('http://localhost:8080/restaurant/api');
    const data = await res.json();
    return data;
};

export default function Home() {
    const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
    const [selectedCategory, setSelectedCategory] = useState<string>('All');

    useEffect(() => {
        const getRestaurants = async () => {
            const data = await fetchRestaurants();
            setRestaurants(data);
        };
        getRestaurants();
    }, []);

    // 필터링된 레스토랑 리스트
    const filteredRestaurants = selectedCategory === 'All'
        ? restaurants
        : restaurants.filter(restaurant => restaurant.category === selectedCategory);

    return (
        <div>
            <h1>Restaurant List</h1>
            <select onChange={(e) => setSelectedCategory(e.target.value)} value={selectedCategory}>
                <option value="All">All</option>
                <option value="중식">중식</option>
                <option value="일식">일식</option>
                <option value="한식">한식</option>
                <option value="분식">분식</option>
                <option value="경양식">경양식</option>
                <option value="양식">양식</option>
                <option value="카페">카페</option>
                <option value="디저트">디저트</option>
            </select>
            <ul>
                {filteredRestaurants.map((restaurant) => (
                    <li key={restaurant.postId}>
                        <h2>{restaurant.name}</h2>
                        <p>Address: {restaurant.address}</p>
                        <p>Phone: {restaurant.phoneNumber}</p>
                        <p>Website: {restaurant.websiteUrl}</p>
                        <p>Use Time: {restaurant.useTime}</p>
                        <p>Subway Info: {restaurant.subwayInfo}</p>
                        <p>Representative Menu: {restaurant.representativeMenu}</p>
                        <p>Category: {restaurant.category}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

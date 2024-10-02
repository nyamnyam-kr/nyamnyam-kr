import { strategy } from "../api.strategy";
import instance from "../axios";
import { api } from "../request";
// restaurant/page.tsx api

export const fetchRestaurantsBySearch = async (keyword: string) => {
    const res = await fetch(`http://localhost:8080/api/restaurant/search?q=${keyword}`);
    if (!res.ok) throw new Error('Failed to fetch data');
    return res.json();
};

export const fetchRestaurantsByTag = async (tags: string[]) => {
    const tagQuery = tags.length > 0 ? `name=${tags.join(',')}` : '';
    const res = await fetch(`http://localhost:8080/api/restaurant/tag?${tagQuery}`);
    if (!res.ok) throw new Error('Failed to fetch data');
    return res.json();
};

export const fetchRestaurantsByCategory = async (categories: string[]) => {
    const categoryQuery = categories.length > 0 ? `category=${categories.join(',')}` : '';
    const res = await fetch(`http://localhost:8080/api/restaurant/category?${categoryQuery}`);
    if (!res.ok) throw new Error('Failed to fetch data');
    return res.json();
};

// post에 restaurant 정보 불러오기
export const fetchRestaurant = async (restaurantId: number) => {
    try{
        const response = await strategy.GET(`${api.restaurant}/${restaurantId}`);
        return response.data;
    } catch (error){
    console.error('post restaurant failed:', error);
    throw error;
    }
};



// restaurant/[id]/page.tsx api
export const fetchRestaurantById = async (id: number) => {
    const response = await fetch(`http://localhost:8080/api/restaurant/${id}`);
    if (!response.ok) throw new Error("Failed to fetch restaurant");
    return await response.json();
};

export const fetchAllAverage = async (id: number) => {
    const response = await fetch(`http://localhost:8080/api/posts/${id}/allAverage`);
    if (!response.ok) throw new Error("Failed to fetch average");
    return await response.json();
};

export const fetchTopTags = async (id: number) => {
    const response = await fetch(`http://localhost:8080/api/tags/top5/${id}`);
    if (!response.ok) throw new Error("Failed to fetch tags");
    return await response.json();
};

export const searchRestaurants = async (query: string) => {
    const response = await fetch(`http://localhost:8080/api/restaurant/search?q=${query}`);
    if (!response.ok) throw new Error("Failed to search restaurants");
    return await response.json();
};

import instance from "../axios";
import { api } from "../request";
import { strategy } from "../api.strategy";

// (page)/restaurant/page.tsx api
 const fetchRestaurantsBySearch = async (keyword: string) => {
    const response = await strategy.GET(`${api.restaurant}/search`, { q: keyword });
    return response.data;
};


 const fetchRestaurantsByTag = async (tags: string[]) => {
    const tagQuery = tags.length > 0 ? { name: tags.join(',') } : {};
    const response = await strategy.GET(`${api.restaurant}/tag`, tagQuery);
    return response.data;
};


 const fetchRestaurantsByCategory = async (categories: string[]) => {
    const categoryQuery = categories.length > 0 ? { category: categories.join(',') } : {};
    const response = await strategy.GET(`${api.restaurant}/category`, categoryQuery);
    return response.data;
};


// // (page)/restaurant/[id]/page.tsx api

// Fetch restaurant information by ID
 const fetchRestaurant = async (restaurantId: number) => {
    try {
        const response = await strategy.GET(`${api.restaurant}/${restaurantId}`);
        return response.data;
    } catch (error) {
        console.error('Fetch restaurant failed:', error);
        throw error;
    }
};

 const fetchRestaurantById = async (id: number) => {
    const response = await strategy.GET(`${api.restaurant}/${id}`);
    return response.data;
};


 const fetchAllAverage = async (id: number) => {
    const response = await strategy.GET(`${api.post}/${id}/allAverage`);
    return response.data;
};


 const fetchTopTags = async (id: number) => {
    const response = await strategy.GET(`${api.tag}/top5/${id}`);
    return response.data;
};


const searchRestaurants = async (query: string) => {
    const response = await strategy.GET(`${api.restaurant}/search`, { q: query });
    return response.data;
};

export const restaurant = {
    fetchRestaurantsBySearch,
    fetchRestaurantsByTag,
    fetchRestaurantsByCategory,
    fetchRestaurant,
    fetchRestaurantById,
    fetchAllAverage,
    fetchTopTags,
    searchRestaurants,
};

// restaurant/page.tsx api

import {
    fetchAllAverage,
    fetchRestaurantById,
    fetchRestaurantsByCategory,
    fetchRestaurantsBySearch,
    fetchRestaurantsByTag,
    fetchTopTags

} from "src/app/api/restaurant/restaurant.api";

export const getRestaurantsBySearch = async (keyword: string) => {
    return await fetchRestaurantsBySearch(keyword);
};

export const getRestaurantsByTag = async (tags: string[]) => {
    return await fetchRestaurantsByTag(tags);
};

export const getRestaurantsByCategory = async (categories: string[]) => {
    return await fetchRestaurantsByCategory(categories);
};


// restaurant/[id]/page.tsx api

export const getRestaurantDetails = async (id: number) => {
    const restaurant = await fetchRestaurantById(id);
    const allAverage = await fetchAllAverage(id);
    const tags = await fetchTopTags(id);
    return { restaurant, allAverage, tags };
};

import {restaurant} from "src/app/api/restaurant/restaurant.api";
// restaurant/page.tsx의 service

import {
    fetchAllAverage,
    fetchRestaurantById,
    fetchRestaurantsByCategory,
    fetchRestaurantsBySearch,
    fetchRestaurantsByTag,
    fetchTopTags

} from "src/app/api/restaurant/restaurant.api";
import {number} from "prop-types";
import {NoticeModel} from "src/app/model/notice.model";
import {notice} from "src/app/api/notice/notice.api";

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


// post에 restautant 불러오기
export const fetchRestaurantService = async (restaurantId: number): Promise<RestaurantModel | null> => {
    try {
      const restaurantData = await restaurant.fetchRestaurant(restaurantId);
      return restaurantData;
    } catch (error) {
      console.error("Error fetching restaurant:", error);
      return null;
    }
  };

export const fetchRestaurantOne = async (id: number): Promise<RestaurantModel> => {
    const data: RestaurantModel = await restaurant.showRestaurant(id);
    return data;
}




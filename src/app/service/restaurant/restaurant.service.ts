import { fetchRestaurant } from "src/app/api/restaurant/restaurant.api";

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

// post에 restautant 불러오기 
export const fetchRestaurantService = async (restaurantId: number): Promise<RestaurantModel | null> => {
    try {
      const restaurantData = await fetchRestaurant(restaurantId);
      return restaurantData;
    } catch (error) {
      console.error("Error fetching restaurant:", error);
      return null;
    }
  };
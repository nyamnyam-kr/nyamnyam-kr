import {api} from "src/app/api/request";
import {Area, CountItem, RestaurantList} from "src/app/model/dash.model";
import {strategy} from "src/app/api/api.strategy";


const showCount = async (): Promise<CountItem[]> => {
    try {
        const resp = await strategy.GET(`${api.admin}/countUserList`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
};

const showArea = async (): Promise<Area[]> => {
    try {
        const resp = await strategy.GET(`${api.admin}/countAreaList`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
};

const showRankRestaurant = async (): Promise<RestaurantList[]> => {
    try {
        const resp = await strategy.GET(`${api.admin}/countPostList`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
}

export const admin = {showCount, showArea, showRankRestaurant}








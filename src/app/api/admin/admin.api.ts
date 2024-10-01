import axios from "axios";
import instance from "src/app/api/axios";
import {api} from "src/app/api/request";
import {Area, CountItem, RestaurantList} from "src/app/model/dash.model";




export const showCount = async (): Promise<CountItem[]> => {
    try {
        const resp = await instance.get(`${api.admin}/countUserList`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
};

export const showArea = async (): Promise<Area[]> => {
    try {
        const resp = await instance.get(`${api.admin}/countAreaList`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
};

export const showRankRestaurant = async (): Promise<RestaurantList[]> => {
    try {
        const resp = await instance.get(`${api.admin}/countPostList`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
}








import {api} from "src/app/api/request";
import {Area, CountCost, CountItem, RestaurantList} from "src/app/model/dash.model";
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

const showRestaurant = async (id: number): Promise<RestaurantModel> => {
    try {
        const response = await strategy.GET(`${api.admin}/randomByUserId/${id}`);
        return response.data; // 데이터 반환
    } catch (error) {
        console.error("Failed to fetch notice details:", error);
        throw new Error("Failed to fetch notice details");
    }
}

const showRankByAge = async (id: number): Promise<RestaurantList[]> => {
    try {
        const resp = await strategy.GET(`${api.admin}/recommendByAge/${id}`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
}

const receiptList = async (): Promise<CountCost[]> => {
    try {
        const resp = await strategy.GET(`${api.admin}/receiptCount`);
        return resp.data;
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }


}

export const admin = {showCount, showArea, showRankRestaurant,showRestaurant, showRankByAge, receiptList}








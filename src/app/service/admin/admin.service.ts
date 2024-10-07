import {admin} from "src/app/api/admin/admin.api";
import {Area, CountCost, CountItem, RestaurantList} from "src/app/model/dash.model";
import showRestaurant from "src/app/(page)/receipt/receiptRestaurant/[restaurantId]/page";
import {number} from "prop-types";
import {restaurant} from "src/app/api/restaurant/restaurant.api";



export const fetchShowCount = async (): Promise<CountItem[]> => {
    const data = await admin.showCount();
    return data.slice(0, 5);
};

export const fetchShowArea = async (): Promise<Area[]> => {
    const data = await admin.showArea();
    return data;
}

export const fetchShowRestaurant = async (): Promise<RestaurantList[]> => {
    const data = await admin.showRankRestaurant();
    return data;
}

export const fetchRestaurantOne = async (id: number): Promise<RestaurantModel> => {
    const data: RestaurantModel = await admin.showRestaurant(id);
    return data;
}

export const fetchShowRankByAge = async (id: number): Promise<RestaurantList[]> => {
    const data: RestaurantList[] = await admin.showRankByAge(id);
    return data;
}

export const fetchReceiptList = async (): Promise<CountCost[]> => {
    const data = await admin.receiptList();
    return data;
}


import {admin} from "src/app/api/admin/admin.api";
import {Area, CountItem, RestaurantList} from "src/app/model/dash.model";
import showRestaurant from "src/app/(page)/receipt/receiptRestaurant/[restaurantId]/page";



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


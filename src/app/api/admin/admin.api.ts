import axios from "axios";
import instance from "src/app/api/axios";
import {api} from "src/app/api/request";
import {CountItem} from "src/app/model/dash.model";




export const showCount = async (): Promise<CountItem[]> => {
    try {
        const resp = await instance.get(`${api.admin}/countUserList`);
        return resp.data; // CountItem[] 형태로 데이터 반환
    } catch (error) {
        console.error("Failed to fetch user counts");
        throw new Error("Failed to fetch user counts");
    }
};



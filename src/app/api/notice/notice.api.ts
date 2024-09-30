import instance from "../axios";
import {api} from "../request";
import {NoticeModel} from "src/app/model/notice.model";


export const noticeAll = async () => {
    try {
    const response = await instance.get(`${api.notice}`);
    return response.data;
    }
    catch (error) {
        console.error("Failed to fetch group details");
        throw new Error("Failed to fetch notice details");
    }
};



export const showNotice = async (id: number): Promise<NoticeModel> => {
    try {
        const response = await instance.get(`${api.notice}/${id}`);
        return response.data; // 데이터 반환
    } catch (error) {
        console.error("Failed to fetch notice details:", error);
        throw new Error("Failed to fetch notice details");
    }
}

export const noticeRegister = async (notice: NoticeModel): Promise<NoticeModel> => {
    try {
        const resp = await instance.post(`${api.notice}`, notice);
        return resp.data;
    } catch (error) {
        console.error("Failed to register notice:", error);
        throw new Error("Failed to register notice");
    }
};

export const updateNotice = async (notice: NoticeModel) => {
    try {
        const resp = await instance.put(`${api.notice}`, notice);
        return resp.data;
    } catch (error) {
        console.error("Failed to register notice:", error);
        throw new Error("Failed to register notice");
    }

}



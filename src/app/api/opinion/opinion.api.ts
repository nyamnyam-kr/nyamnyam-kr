import instance from "../axios";
import {api} from "../request";
import {OpinionModel} from "src/app/model/opinion.model";


export const insertOpinion = async (opinion: OpinionModel): Promise<OpinionModel> => {
    try {
        const resp = await instance.post(`${api.opinion}`, opinion)
        return resp.data;

    } catch (error) {
        console.error("Failed to register notice:", error);
        throw new Error("Failed to register notice");
    }
}

export const opinionAll = async () => {
    try {
        const resp = await instance.get(`${api.opinion}`)
        return resp.data;
    } catch (error) {
        console.error("Failed to register notice:", error);
        throw new Error("Failed to register notice");
    }
}




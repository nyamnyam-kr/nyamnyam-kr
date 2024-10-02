import {insertOpinion, opinionAll} from "src/app/api/opinion/opinion.api";
import showOpinion from "src/app/(page)/admin/showOpinion/page";
import {NoticeModel} from "src/app/model/notice.model";
import {noticeAll} from "src/app/api/notice/notice.api";
import {OpinionModel} from "src/app/model/opinion.model";

export const fetchInsertOpinion = async (opinion: OpinionModel): Promise<OpinionModel> => {
    try {
        const data = await insertOpinion(opinion);

        return data;
    } catch (error) {
        console.error("Failed to create notice:", error);
        throw error; // 에러 발생 시 예외 던지기
    }
}


export const fetchShowOpinion = async () => {
        const data: OpinionModel[] = await opinionAll();
        return data;
}

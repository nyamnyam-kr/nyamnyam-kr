import { Dispatch } from 'redux';
import {NoticeModel} from "src/app/model/notice.model";
import {noticeAll, showNotice} from "src/app/api/notice/notice.api";
import {saveNotice} from "src/lib/features/notice.slice";

export const noticeList = async () => {
    try {
        const data = await noticeAll(); // API 호출
        data.sort((a: NoticeModel, b: NoticeModel) => {
            return new Date(b.date).getTime() - new Date(a.date).getTime();
        });
        saveNotice(data)

    } catch (error: any) {
        console.error("Error fetching notices:", error);
        alert(error.message); // 사용자에게 오류 메시지 알림
    }
};
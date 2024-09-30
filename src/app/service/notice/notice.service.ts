import {noticeAll, noticeRegister, showNotice, updateNotice} from 'src/app/api/notice/notice.api';
import {NoticeModel} from "src/app/model/notice.model";
import {number} from "prop-types";
import instance from "src/app/api/axios";

export const fetchNoticeList = async () => {
   const data: NoticeModel[] = await noticeAll();
   return data.sort((a, b) => {
      return new Date(b.date).getTime() - new Date(a.date).getTime();
   });
};

export const fetchNoticeOne = async (id: number): Promise<NoticeModel> => {
   const data: NoticeModel = await showNotice(id);
   return data;
}

export const fetchNoticeRegister = async (notice: NoticeModel): Promise<NoticeModel> => {
   try {
      const data = await noticeRegister(notice);

      return data;
   } catch (error) {
      console.error("Failed to create notice:", error);
      throw error;
   }
};

export const fetchNoticeUpdate = async (notice : NoticeModel): Promise<NoticeModel> => {
   try {
      const data = await updateNotice(notice);

      return data;
   } catch (error) {
      console.error("Failed to create notice:", error);
      throw error;
   }
}







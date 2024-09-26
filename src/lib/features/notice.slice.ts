import { createSlice, PayloadAction } from  '@reduxjs/toolkit'
import  type { RootState } from '../store'
import {initialNotice, NoticeModel} from "src/app/model/notice.model";


const noticeSlice = createSlice ({ //자바의 POJO이다
    name : 'notice' ,
    // `createSlice`는 `initialState` 인수에서 상태 유형을 유추합니다.
    initialState: initialNotice,
    reducers : {
        saveNotice: (state, action:PayloadAction<NoticeModel>)=> {
            return action.payload
        },
        clearNotice:(state) => {
            return initialNotice
        }
    },
    extraReducers: (builder)=> {}
})

export const getReceipt = (state: RootState) => state.receipt

export const { saveNotice ,clearNotice } = noticeSlice.actions

export const selectNotices = (state: RootState) => state.notice;

// 선택자와 같은 다른 코드는 가져온 `RootState` 유형을 사용할 수 있습니다
export default noticeSlice.reducer



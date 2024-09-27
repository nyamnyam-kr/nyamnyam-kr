import { configureStore } from '@reduxjs/toolkit';
import receiptSlice from './features/receipt.slice';
import noticeSlice from "src/lib/features/notice.slice";

export const makeStore = () => {
    return configureStore({
        reducer: {
            receipt: receiptSlice,
            notice: noticeSlice,
        },
    });
};

// Infer the type of makeStore
export type AppStore = ReturnType<typeof makeStore>;
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<AppStore['getState']>;
export type AppDispatch = AppStore['dispatch'];
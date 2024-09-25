import { configureStore } from  '@reduxjs/toolkit' ;
import postSlice from  './features/post.slice' ;
import receiptSlice from './features/receipt.slice';

export  const  makeStore = ( ) => {
  return  configureStore ({
    reducer : {
        receipt: receiptSlice,
        post : postSlice,
    },
  });
};

// makeStore의 유형을 유추합니다.
export  type  AppStore = ReturnType < typeof makeStore>;
// 스토어 자체에서 `RootState` 및 `AppDispatch` 유형을 유추합니다.
export  type  RootState = ReturnType < AppStore [ 'getState' ]>;
export  type  AppDispatch = AppStore [ 'dispatch' ];
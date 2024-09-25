import { configureStore } from  '@reduxjs/toolkit' ; 
import postSlice from  './features/post.slice' ; 
import replySlice from './features/reply.slice';

export  const  makeStore = ( ) => { 
  return  configureStore ({ 
    reducer : { 
      post : postSlice, 
      reply : replySlice,
    }, 
  }); 
}; 

// makeStore의 유형을 유추합니다. 
export  type  AppStore = ReturnType < typeof makeStore>; 
// 스토어 자체에서 `RootState` 및 `AppDispatch` 유형을 유추합니다. 
export  type  RootState = ReturnType < AppStore [ 'getState' ]>; 
export  type  AppDispatch = AppStore [ 'dispatch' ];
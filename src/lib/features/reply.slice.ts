import { createSlice, PayloadAction } from  '@reduxjs/toolkit' 
import  type { RootState } from  '../store'
import { initialReply, ReplyModel } from '@/app/model/reply.model'

interface ReplyState{
  replies: { [key:number]: ReplyModel[]};
  replyInput: {[key:number]: string};
}

const initialState: ReplyState = {
  replies: {}, 
  replyInput:{},
};

const replySlice = createSlice({
  name: 'reply',
  initialState,
  reducers : {
    // 특정 게시글의 댓글 저장
    setReplies: (state, action:PayloadAction<{postId: number; replies: ReplyModel[] }>)=> {
      const {postId, replies} = action.payload;
      state.replies[postId] = replies;
    },
    // 댓글 입력값 저장 
    setReplyInput: (state, action: PayloadAction<{postId: number; content: string}>) => {
      const {postId, content} = action.payload;
      state.replyInput[postId] = content;
    }, 
    // 댓글 입력값 초기화 
    clearReplyInput:(state, action: PayloadAction<number>) => {
      state.replyInput[action.payload] = ''; 
    },
    // 단일 댓글 추가 
    addReply: (state, action: PayloadAction<{ postId: number; reply: ReplyModel }>) => {
      const { postId, reply } = action.payload;
      if (state.replies[postId]) {
        state.replies[postId] = [...state.replies[postId], reply]; 
      } else {
        state.replies[postId] = [reply];
      }
    },
    // 댓글 삭제 
    deleteReply:(state, action: PayloadAction<{postId: number; replyId:number}>) =>{
      const {postId, replyId} = action.payload;
      state.replies[postId] = state.replies[postId].filter((reply) => reply.id !== replyId); 
    }
  },
  extraReducers: (builder)=> {} 
  }) 

export const getReplies = (state: RootState) => state.reply.replies;
export const getReplyInput = (state: RootState) => state.reply.replyInput;

export  const { setReplies,setReplyInput, addReply } = replySlice. actions; 
  
export default  replySlice.reducer;
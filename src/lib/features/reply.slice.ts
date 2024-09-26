import { createSlice, PayloadAction } from  '@reduxjs/toolkit' 
import  type { RootState } from  '../store'
import { ReplyModel } from 'src/app/model/reply.model';


interface ReplyState{
  replies: { [key:number]: ReplyModel[]};
  replyInput: {[key:number]: string};
  editReply: {[key:number]: boolean};
  editInput: {[key:number]: string};
}

const initialState: ReplyState = {
  replies: {}, 
  replyInput:{},
  editReply:{},
  editInput:{}
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
    // 수정 댓글 저장 
    setReplyInput: (state, action: PayloadAction<{postId: number; content: string}>) => {
      const {postId, content} = action.payload;
      state.replyInput[postId] = content;
    },
    setEditReply : (state, action: PayloadAction<{replyId: number; isEditing: boolean}>) => {
      const {replyId, isEditing} = action.payload;
      state.editReply[replyId] = isEditing;
    },
    setEditInput : (state, action: PayloadAction<{replyId: number, content:string}>) => {
      const {replyId, content} = action.payload;
      state.editInput[replyId] = content;
    }
  },
  extraReducers: (builder)=> {} 
  }) 

export const getReplies = (state: RootState) => state.reply.replies;
export const getReplyInput = (state: RootState) => state.reply.replyInput;
export const getEditReply = (state: RootState) => state.reply.editReply;
export const getEditInput = (state: RootState) => state.reply.editInput;

export const { setReplies,setReplyInput,setEditReply,setEditInput } = replySlice. actions; 
  
export default  replySlice.reducer;
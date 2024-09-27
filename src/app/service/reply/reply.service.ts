//src/app/service/reply/reply.service.ts
import { Dispatch } from "@reduxjs/toolkit";
import { deleteReply, fetchReply, insertReply } from "src/app/api/reply/reply.api";
import { ReplyModel } from "src/app/model/reply.model";
import { addReplies, getReplies } from "src/lib/features/reply.slice";
import { AppDispatch } from "src/lib/store";

export const fetchReplyService = async (postId: number, dispatch: AppDispatch) => {
    try {
      const data = await fetchReply(postId); // API 호출
      
      // Redux 상태 업데이트
      dispatch(addReplies({ postId, replies: data }));
      console.log("setReplies: ", data);
      console.log(getReplies);
    } catch (error) {
      console.error("Reply fetch fail:", error);
    }
  };

export async function serviceInsertReply(reply: ReplyModel, postId: number, dispatch: AppDispatch): Promise<ReplyModel | null> {
    try {
        
      const response = await insertReply(reply);
  
      if (response.ok) {
        const data: ReplyModel = await response.json();
        
        dispatch(addReplies({postId, replies:[data]}));

        return data;
      } else {
        const errorMessage = await response.text();
        throw new Error(`서버 응답 에러: ${errorMessage}`);
      }
    } catch (error) {
      console.error('댓글 등록 중 오류 발생:', error);
      return null;
    }
  }

export const handleReplyDelete = async (replyId: number, postId: number, dispatch: Dispatch, replies: { [key:number]: ReplyModel[] }) => {
  if (window.confirm("삭제하시겠습니까?")) {
    try {
      await deleteReply(replyId); // 삭제 결과 반환값을 따로 사용하지 않음

      const updateReplies = replies[postId].filter((reply) => reply.id !== replyId);
      dispatch(addReplies({postId, replies: updateReplies}));

      alert("댓글이 삭제되었습니다.");
    } catch (error: any) {
      console.error("댓글 삭제 중 문제가 발생했습니다:", error);
      alert(error.message); // 에러 메시지 출력
    }
  }
};
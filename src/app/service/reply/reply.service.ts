//src/app/service/reply/reply.service.ts
import { an } from "@fullcalendar/core/internal-common";
import { deleteReply, insertReply } from "src/app/api/reply/reply.api";
import { ReplyModel } from "src/app/model/reply.model";
import { addReplies } from "src/lib/features/reply.slice";
import { AppDispatch } from "src/lib/store";


export async function serviceInsertReply(reply: ReplyModel, postId: number, dispatch: AppDispatch): Promise<ReplyModel | null> {
    try {
      const replyData = {
          id: reply.id, 
          content: reply.content,
          userId: reply.userId,
          postId: reply.postId
      }
      const response = await insertReply(reply);
  
      const contentType = response.headers.get('content-type');
  
      if (response.ok && contentType && contentType.includes('application/json')) {
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

export const handleReplyDelete = async (
  replyId: number, 
  postId: number, 
  setReplies: Function, 
  fetchPosts: Function
) => {
  if (window.confirm("삭제하시겠습니까?")) {
    try {
      await deleteReply(replyId);

      setReplies((prevReplies: any) => {
        return {
          ...prevReplies,
          [postId]: prevReplies[postId].filter((reply: any) => reply.id !== replyId)
        };
      });

      alert("댓글이 삭제되었습니다.");
      fetchPosts();
    } catch (error:any) {
      console.error("댓글 삭제 중 문제가 발생했습니다:", error);
      alert(error.message);
    }
  }
};
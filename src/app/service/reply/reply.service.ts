//src/app/service/reply/reply.service.ts
import { Dispatch } from "@reduxjs/toolkit";
import { deleteReply, fetchReply, insertReply, updateReply } from "src/app/api/reply/reply.api";
import { initialReply, ReplyModel } from "src/app/model/reply.model";
import { addReplies, getReplies } from "src/lib/features/reply.slice";
import { AppDispatch } from "src/lib/store";

export const toggleReplyService = async (id: number, replyToggles: { [key: number]: boolean }) => {
  const toggled = {
    ...replyToggles,
    [id]: !replyToggles[id],
  };
  if (!replyToggles[id]) {
    const data = await fetchReply(id); // API 호출

    return { toggled, replies: data || [] };
  }
  return { toggled, replies: null };
};

export const submitReplyService = async (postId: number, replyContent: string, currentId: number, replyToggles: { [key: number]: boolean }) => {
  const replyData: ReplyModel = {
    ...initialReply,
    postId: postId,
    content: replyContent,
    userId: currentId
  };
  try {
    const newReply = await insertReply(replyData);

    if (newReply) {
      const {toggled, replies} = await toggleReplyService(postId, replyToggles);
      return {success:true, toggled, replies: replies || []};
    } else {
      return { success: false, toggled: replyToggles, replies:[]};
    }
  } catch (error) {
    return { success: false, toggled: replyToggles, replies:[]};
  }
}

export const editSaveReplyService = async (replyId: number, postId: number, updateContent: string, currentUserId: number) => {
  const replyData = {
    ...initialReply, 
    id: replyId, 
    content : updateContent, 
    postId: postId, 
    userId: currentUserId
  }; 
  try{
    const updateReplyData = await updateReply(replyId, replyData);
    return updateReplyData;

  }catch(error){
    console.error("댓글 수정 중 오류 발생:", error);
    return null; 
  }
};

export const deleteReplyService = async (replyId: number, postId: number, replies: { [key: number]: ReplyModel[] }) => {
    try {
      await deleteReply(replyId);

      const updateReplies = replies[postId].filter((reply) => reply.id !== replyId);

      return updateReplies;
    } catch (error: any) {
      console.error("댓글 삭제 중 문제가 발생했습니다:", error);
      return null;
  }
};
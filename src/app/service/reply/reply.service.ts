//src/app/service/reply/reply.service.ts
import {reply} from "src/app/api/reply/reply.api";
import { initialReply, ReplyModel } from "src/app/model/reply.model";

const toggle = async (id: number, replyToggles: { [key: number]: boolean }) => {
  const toggled = {
    ...replyToggles,
    [id]: !replyToggles[id],
  };
  if (!replyToggles[id]) {
    const data = await reply.getById(id);

    return { toggled, replies: data || [] };
  }
  return { toggled, replies: null };
};

const submit = async (postId: number, replyContent: string, currentId: number, replyToggles: { [key: number]: boolean }) => {
  const replyData: ReplyModel = {
    ...initialReply,
    postId: postId,
    content: replyContent,
    userId: currentId,
  };

  try {
    const newReply = await reply.insert(replyData);

    if(!newReply){
      return {success: false, toggled: replyToggles, newReply: null};
    }

    return {success: true, toggled: { ...replyToggles, [postId]: true }, newReply, };
  } catch (error) {
    console.error("댓글 작성 중 오류 발생:", error);
    return { success: false, toggled: replyToggles, newReply:null };
  }
};

const editSave = async (replyId: number, postId: number, updateContent: string, currentUserId: number) => {
  const replyData = {
    ...initialReply, 
    id: replyId, 
    content : updateContent, 
    postId: postId, 
    userId: currentUserId
  }; 
  try{
    const updateReplyData = await reply.update(replyId, replyData);
    return updateReplyData;

  }catch(error){
    console.error("댓글 수정 중 오류 발생:", error);
    return null; 
  }
};

export const remove = async (replyId: number, postId: number, replies: { [key: number]: ReplyModel[] }) => {
    try {
      await reply.remove(replyId);

      const updateReplies = replies[postId].filter((reply) => reply.id !== replyId);

      return updateReplies;
    } catch (error: any) {
      console.error("댓글 삭제 중 문제가 발생했습니다:", error);
      return null;
  }
};

export const replyService = {toggle, submit, editSave, remove};